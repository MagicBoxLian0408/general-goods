package kr.magicbox.generalgoods.application.service;

import kr.magicbox.generalgoods.adapter.in.kafka.event.StockReserveCommandEvent;
import kr.magicbox.generalgoods.application.port.in.HandleStockReserveUseCase;
import kr.magicbox.generalgoods.application.port.out.GeneralGoodsOutboxPort;
import kr.magicbox.generalgoods.application.port.out.GeneralGoodsRepositoryPort;
import kr.magicbox.generalgoods.domain.event.StockReserveFailedEvent;
import kr.magicbox.generalgoods.domain.event.StockReserveSucceededEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class HandleStockReserveService implements HandleStockReserveUseCase {

    private final GeneralGoodsRepositoryPort generalGoodsRepositoryPort;
    private final GeneralGoodsOutboxPort generalGoodsOutboxPort;

    @Transactional
    @Override
    public void handleStockReserve(StockReserveCommandEvent event) {
        if (event.items() == null || event.items().isEmpty()) {
            log.warn("[StockReserve] items 없음. orderId={}", event.orderId());
            generalGoodsOutboxPort.save(StockReserveFailedEvent.builder()
                    .orderId(event.orderId())
                    .customerId(event.customerId())
                    .reason("items 없음")
                    .occurredAt(Instant.now())
                    .build());
            return;
        }

        for (StockReserveCommandEvent.ItemPayload item : event.items()) {
            boolean decreased = generalGoodsRepositoryPort.decreaseStock(item.productId(), item.quantity());
            if (!decreased) {
                log.warn("[StockReserve] 재고 부족. orderId={}, productId={}", event.orderId(), item.productId());
                generalGoodsOutboxPort.save(StockReserveFailedEvent.builder()
                        .orderId(event.orderId())
                        .customerId(event.customerId())
                        .reason("재고 부족. productId=" + item.productId())
                        .occurredAt(Instant.now())
                        .build());
                return;
            }
        }

        log.info("[StockReserve] 재고 예약 성공. orderId={}", event.orderId());
        generalGoodsOutboxPort.save(StockReserveSucceededEvent.builder()
                .orderId(event.orderId())
                .customerId(event.customerId())
                .totalAmount(event.totalAmount())
                .occurredAt(Instant.now())
                .build());
    }
}
