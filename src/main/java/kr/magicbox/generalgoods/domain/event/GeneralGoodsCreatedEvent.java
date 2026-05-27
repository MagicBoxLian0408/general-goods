package kr.magicbox.generalgoods.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record GeneralGoodsCreatedEvent(
        @JsonProperty("general_goods_id") Long generalGoodsId,
        @JsonProperty("creator_id") Long creatorId,
        @JsonProperty("name") String name,
        @JsonProperty("price") Long price,
        @JsonProperty("stock") Long stock,
        @JsonProperty("media_urls") List<String> mediaUrls,
        @JsonProperty("occurred_at") Instant occurredAt
) implements GeneralGoodsDomainEvent {

    @Override
    public String key() {
        return generalGoodsId.toString();
    }

    @Override
    public GeneralGoodsDomainEventType eventType() {
        return GeneralGoodsDomainEventType.GENERAL_GOODS_CREATED;
    }
}
