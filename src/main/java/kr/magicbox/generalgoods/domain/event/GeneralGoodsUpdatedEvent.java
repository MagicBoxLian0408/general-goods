package kr.magicbox.generalgoods.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.magicbox.generalgoods.domain.enums.GeneralGoodsLevel;
import kr.magicbox.generalgoods.domain.enums.MagicGenre;
import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Builder
public record GeneralGoodsUpdatedEvent(
        @JsonProperty("general_goods_id") Long generalGoodsId,
        @JsonProperty("creator_id") Long creatorId,
        @JsonProperty("before") GoodsSnapshot before,
        @JsonProperty("after") GoodsSnapshot after,
        @JsonProperty("occurred_at") Instant occurredAt
) implements GeneralGoodsDomainEvent {

    public record GoodsSnapshot(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("level") GeneralGoodsLevel level,
            @JsonProperty("categories") Set<MagicGenre> categories,
            @JsonProperty("price") Long price,
            @JsonProperty("stock") Long stock,
            @JsonProperty("media_urls") List<String> mediaUrls
    ) {}

    @Override
    public String key() {
        return generalGoodsId.toString();
    }

    @Override
    public GeneralGoodsDomainEventType eventType() {
        return GeneralGoodsDomainEventType.GENERAL_GOODS_UPDATED;
    }
}
