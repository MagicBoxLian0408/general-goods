package kr.magicbox.generalgoods.adapter.out.persistence;

import kr.magicbox.generalgoods.adapter.out.persistence.entity.GeneralGoodsOutboxEntity;
import kr.magicbox.generalgoods.adapter.out.persistence.repository.GeneralGoodsOutboxRepository;
import kr.magicbox.generalgoods.application.port.out.GeneralGoodsOutboxPort;
import kr.magicbox.generalgoods.domain.event.GeneralGoodsDomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

@Repository
@RequiredArgsConstructor
public class GeneralGoodsOutboxAdapter implements GeneralGoodsOutboxPort {

    private final GeneralGoodsOutboxRepository generalGoodsOutboxRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void save(GeneralGoodsDomainEvent event) {
        String payload = objectMapper.writeValueAsString(event);
        generalGoodsOutboxRepository.save(GeneralGoodsOutboxEntity.builder()
                .eventType(event.eventType().getValue())
                .aggregateKey(event.key())
                .payload(payload)
                .build());
    }
}
