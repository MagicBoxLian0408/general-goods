package kr.magicbox.generalgoods.adapter.in.web.dto.response;

import kr.magicbox.generalgoods.application.dto.result.GeneralGoodsResult;
import kr.magicbox.generalgoods.domain.enums.GeneralGoodsLevel;
import kr.magicbox.generalgoods.domain.enums.MagicGenre;
import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record GeneralGoodsResponse(
        Long id,
        Long creatorId,
        String name,
        Long price,
        Long stock,
        String description,
        GeneralGoodsLevel level,
        Set<MagicGenre> categories,
        List<MediaResponse> mediaList
) {
    public static GeneralGoodsResponse from(GeneralGoodsResult result) {
        List<MediaResponse> mediaResponses = result.mediaList().stream()
                .map(m -> new MediaResponse(m.mediaUrl(), m.sortOrder()))
                .toList();

        return new GeneralGoodsResponse(
                result.id().value(),
                result.creatorId().value(),
                result.name(),
                result.price(),
                result.stock(),
                result.description(),
                result.level(),
                result.categories(),
                mediaResponses
        );
    }

    public record MediaResponse(
            String mediaUrl,
            int sortOrder
    ) { }
}
