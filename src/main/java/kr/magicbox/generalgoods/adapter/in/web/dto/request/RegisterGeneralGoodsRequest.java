package kr.magicbox.generalgoods.adapter.in.web.dto.request;

import kr.magicbox.generalgoods.application.dto.command.MediaCommand;
import kr.magicbox.generalgoods.application.dto.command.RegisterGeneralGoodsCommand;
import kr.magicbox.generalgoods.domain.enums.MagicGenre;
import kr.magicbox.generalgoods.domain.vo.UserId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record RegisterGeneralGoodsRequest(
        @NotBlank(message = "이름은 필수입니다.") String name,
        @NotNull(message = "가격은 필수입니다.") @Min(value = 1, message = "가격은 1 이상이어야 합니다.") Long price,
        @NotNull(message = "재고는 필수입니다.") @Min(value = 1, message = "재고는 1 이상이어야 합니다.") Long stock,
        String description,
        @NotEmpty(message = "카테고리는 하나 이상 필수입니다.") Set<@NotNull(message = "카테고리 값은 null일 수 없습니다.") MagicGenre> categories,
        @NotEmpty(message = "미디어는 하나 이상 필수입니다.") List<@Valid @NotNull(message = "미디어 항목은 null일 수 없습니다.") MediaRequest> mediaList
) {
    public RegisterGeneralGoodsCommand toCommand(UserId userId) {
        List<MediaCommand> mediaCommands = mediaList.stream()
                .map(m -> new MediaCommand(m.mediaUrl(), m.sortOrder()))
                .toList();

        return new RegisterGeneralGoodsCommand(
                userId,
                name,
                price,
                stock,
                description,
                categories,
                mediaCommands
        );
    }
}