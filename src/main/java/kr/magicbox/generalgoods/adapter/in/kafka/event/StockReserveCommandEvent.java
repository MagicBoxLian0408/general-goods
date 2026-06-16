package kr.magicbox.generalgoods.adapter.in.kafka.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record StockReserveCommandEvent(
        @JsonProperty("order_id") Long orderId,
        @JsonProperty("customer_id") Long customerId,
        @JsonProperty("total_amount") Long totalAmount,
        @JsonProperty("items") List<ItemPayload> items,
        @JsonProperty("occurred_at") Instant occurredAt
) implements InboxEvent {

    @Builder
    public record ItemPayload(
            @JsonProperty("order_line_id") Long orderLineId,
            @JsonProperty("product_id") Long productId,
            @JsonProperty("quantity") Integer quantity,
            @JsonProperty("unit_price") Long unitPrice
    ) {}
}
