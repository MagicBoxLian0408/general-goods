package kr.magicbox.generalgoods.application.dto.command;

import kr.magicbox.generalgoods.domain.enums.GeneralGoodsLevel;
import kr.magicbox.generalgoods.domain.enums.MagicGenre;
import kr.magicbox.generalgoods.domain.vo.GeneralGoodsId;
import kr.magicbox.generalgoods.domain.vo.UserId;

import java.util.List;
import java.util.Set;

public record UpdateGeneralGoodsCommand(
        GeneralGoodsId id,
        String name,
        UserId userId,
        Long price,
        Long stock,
        String description,
        GeneralGoodsLevel level,
        Set<MagicGenre> categories,
        List<MediaCommand> mediaList
) {
    public static UpdateGeneralGoodsCommand of(GeneralGoodsId id, String name, UserId userId, Long price, Long stock, String description, GeneralGoodsLevel level, Set<MagicGenre> categories, List<MediaCommand> mediaList) {
        return new UpdateGeneralGoodsCommand(id, name, userId, price, stock, description, level, categories, mediaList);
    }
}