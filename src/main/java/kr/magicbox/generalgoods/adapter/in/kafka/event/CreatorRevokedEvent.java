package kr.magicbox.generalgoods.adapter.in.kafka.event;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.magicbox.generalgoods.domain.vo.CreatorId;
import lombok.Builder;

import java.time.Instant;

@Builder
public record CreatorRevokedEvent(
        @JsonProperty("creator_id") CreatorId creatorId,
        @JsonProperty("occurred_at") @JsonAlias("revoked_at") Instant occurredAt
) implements InboxEvent {
}
