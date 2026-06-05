package kr.magicbox.generalgoods.adapter.in.kafka.aop;

import kr.magicbox.generalgoods.adapter.in.kafka.event.InboxEvent;
import kr.magicbox.generalgoods.adapter.in.kafka.properties.InboxProperties;
import kr.magicbox.generalgoods.adapter.out.persistence.entity.GeneralGoodsInboxEntity;
import kr.magicbox.generalgoods.adapter.out.persistence.entity.GeneralGoodsInboxStatus;
import kr.magicbox.generalgoods.adapter.out.persistence.repository.GeneralGoodsInboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class IdempotentAspect {

    private final GeneralGoodsInboxRepository generalGoodsInboxRepository;
    private final TransactionTemplate transactionTemplate;
    private final InboxProperties inboxProperties;

    @Around("@annotation(kr.magicbox.generalgoods.adapter.in.kafka.annotation.Idempotent)")
    public Object around(ProceedingJoinPoint pjp) {
        ConsumerRecord<String, ?> consumerRecord = extractRecord(pjp);
        
        InboxEvent event = (InboxEvent) consumerRecord.value();
        String kafkaKey = consumerRecord.key();
        Instant occurredAt = event.occurredAt();

        if (isTooOld(occurredAt)) {
            log.warn("[Inbox] 만료된 메시지 DEAD_LETTERED 처리. kafkaKey={}, occurredAt={}", kafkaKey, occurredAt);
            transactionTemplate.executeWithoutResult(status ->
                generalGoodsInboxRepository.save(GeneralGoodsInboxEntity.builder()
                        .kafkaKey(kafkaKey)
                        .topic(consumerRecord.topic())
                        .partition(consumerRecord.partition())
                        .offset(consumerRecord.offset())
                        .status(GeneralGoodsInboxStatus.DEAD_LETTERED)
                        .occurredAt(occurredAt)
                        .build())
            );
            return null;
        }

        return transactionTemplate.execute(status -> {
            if (generalGoodsInboxRepository.existsByKafkaKey(kafkaKey)) {
                log.warn("[Inbox] 중복 메시지 폐기. kafkaKey={}", kafkaKey);
                return null;
            }
            GeneralGoodsInboxEntity inbox = generalGoodsInboxRepository.save(GeneralGoodsInboxEntity.builder()
                    .kafkaKey(kafkaKey)
                    .topic(consumerRecord.topic())
                    .partition(consumerRecord.partition())
                    .offset(consumerRecord.offset())
                    .status(GeneralGoodsInboxStatus.PENDING)
                    .occurredAt(occurredAt)
                    .build());
            try {
                pjp.proceed();
            } catch (Throwable e) {
                status.setRollbackOnly();
                throw new RuntimeException(e);
            }
            inbox.markProcessed();
            return null;
        });
    }

    private boolean isTooOld(Instant occurredAt) {
        return occurredAt.isBefore(Instant.now().minus(inboxProperties.getMaxEventAgeMinutes(), ChronoUnit.MINUTES));
    }

    @SuppressWarnings("unchecked")
    private ConsumerRecord<String, ?> extractRecord(ProceedingJoinPoint pjp) {
        return Arrays.stream(pjp.getArgs())
                .filter(ConsumerRecord.class::isInstance)
                .map(arg -> (ConsumerRecord<String, ?>) arg)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("@Idempotent 메서드에 ConsumerRecord 파라미터가 없습니다."));
    }
}
