package kr.magicbox.generalgoods.application.dto.command;

import kr.magicbox.generalgoods.domain.vo.CreatorId;
import lombok.Builder;

@Builder
public record HandleCreatorRevokedCommand(CreatorId creatorId) {
    public static HandleCreatorRevokedCommand of(CreatorId creatorId) {
        return HandleCreatorRevokedCommand.builder()
                .creatorId(creatorId)
                .build();
    }
}
