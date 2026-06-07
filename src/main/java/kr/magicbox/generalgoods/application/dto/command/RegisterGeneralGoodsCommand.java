package kr.magicbox.generalgoods.application.dto.command;

import kr.magicbox.generalgoods.domain.enums.GeneralGoodsLevel;
import kr.magicbox.generalgoods.domain.enums.MagicGenre;
import kr.magicbox.generalgoods.domain.vo.UserId;

import java.util.List;
import java.util.Set;

public record RegisterGeneralGoodsCommand(
        UserId userId,
        String name,
        Long price,
        Long stock,
        String description,
        GeneralGoodsLevel level,
        Set<MagicGenre> categories,
        List<MediaCommand> mediaList
) {
    public static RegisterGeneralGoodsCommand of(UserId userId, String name, Long price, Long stock, String description, GeneralGoodsLevel level, Set<MagicGenre> categories, List<MediaCommand> mediaList) {
        return new RegisterGeneralGoodsCommand(userId, name, price, stock, description, level, categories, mediaList);
    }
}