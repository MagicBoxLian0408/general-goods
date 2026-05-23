package kr.magicbox.generalgoods.adapter.in.web.dto.request;

import kr.magicbox.generalgoods.application.dto.command.MediaCommand;
import kr.magicbox.generalgoods.application.dto.command.UpdateGeneralGoodsCommand;
import kr.magicbox.generalgoods.domain.enums.GeneralGoodsLevel;
import kr.magicbox.generalgoods.domain.enums.MagicGenre;
import kr.magicbox.generalgoods.domain.vo.GeneralGoodsId;
import jakarta.validation.constraints.Min;
import kr.magicbox.generalgoods.domain.vo.UserId;
import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record UpdateGeneralGoodsRequest(
        String name,
        @Min(value = 1, message = "가격은 1 이상이어야 합니다.") Long price,
        @Min(value = 1, message = "재고는 1 이상이어야 합니다.") Long stock,
        String description,
        GeneralGoodsLevel level,
        Set<MagicGenre> categories,
        List<MediaRequest> mediaList
) {
    public UpdateGeneralGoodsCommand toCommand(Long id, UserId userId) {
        List<MediaCommand> mediaCommands = mediaList == null ? null : mediaList.stream()
                .map(m -> new MediaCommand(m.mediaUrl(), m.sortOrder()))
                .toList();

        return new UpdateGeneralGoodsCommand(
                GeneralGoodsId.of(id),
                name,
                userId,
                price,
                stock,
                description,
                level,
                categories,
                mediaCommands
        );
    }
}