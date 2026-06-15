package kr.magicbox.generalgoods.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;

@Builder
public record StockReserveSucceededEvent(
        @JsonProperty("order_id") Long orderId,
        @JsonProperty("customer_id") Long customerId,
        @JsonProperty("total_amount") Long totalAmount,
        @JsonProperty("occurred_at") Instant occurredAt
) implements GeneralGoodsDomainEvent {

    @Override
    public String key() {
        return orderId.toString();
    }

    @Override
    public GeneralGoodsDomainEventType eventType() {
        return GeneralGoodsDomainEventType.STOCK_RESERVE_SUCCEEDED;
    }
}
