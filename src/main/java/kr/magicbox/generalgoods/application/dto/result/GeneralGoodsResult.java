package kr.magicbox.generalgoods.application.dto.result;

import kr.magicbox.generalgoods.domain.enums.GeneralGoodsLevel;
import kr.magicbox.generalgoods.domain.enums.MagicGenre;
import kr.magicbox.generalgoods.domain.vo.CreatorId;
import kr.magicbox.generalgoods.domain.vo.GeneralGoodsId;
import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record GeneralGoodsResult(
        GeneralGoodsId id,
        CreatorId creatorId,
        String name,
        Long price,
        Long stock,
        String description,
        GeneralGoodsLevel level,
        Set<MagicGenre> categories,
        List<MediaResult> mediaList
) { }