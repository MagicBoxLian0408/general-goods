package kr.magicbox.generalgoods.adapter.in.kafka;

import kr.magicbox.generalgoods.adapter.in.kafka.annotation.Idempotent;
import kr.magicbox.generalgoods.adapter.in.kafka.event.StockReserveCommandEvent;
import kr.magicbox.generalgoods.adapter.out.persistence.repository.GeneralGoodsInboxRepository;
import kr.magicbox.generalgoods.application.port.in.HandleStockReserveUseCase;
import kr.magicbox.generalgoods.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrchestratorCommandKafkaListener {

    private final HandleStockReserveUseCase handleStockReserveUseCase;
    private final GeneralGoodsInboxRepository generalGoodsInboxRepository;

    @Idempotent
    @RetryableTopic(dltStrategy = DltStrategy.FAIL_ON_ERROR, dltTopicSuffix = "-dlt", exclude = {BusinessException.class})
    @KafkaListener(topics = "outbox.event.stock-reserve.general-good", groupId = "general-goods-service")
    public void handleStockReserve(ConsumerRecord<String, StockReserveCommandEvent> consumerRecord) {
        log.info("[Inbox] stock-reserve.general-good 커맨드 수신. key={}", consumerRecord.key());
        handleStockReserveUseCase.handleStockReserve(consumerRecord.value());
    }

    @DltHandler
    public void handleDlt(ConsumerRecord<String, ?> consumerRecord) {
        log.error("[Inbox] DLT 전환. topic={}, partition={}, offset={}", consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset());
        generalGoodsInboxRepository.findByTopicAndPartitionAndOffset(consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset())
                .ifPresent(inbox -> {
                    inbox.markDeadLettered();
                    generalGoodsInboxRepository.save(inbox);
                });
    }
}
