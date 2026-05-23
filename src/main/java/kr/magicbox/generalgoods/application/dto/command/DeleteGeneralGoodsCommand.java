package kr.magicbox.generalgoods.application.dto.command;

import kr.magicbox.generalgoods.domain.vo.GeneralGoodsId;
import kr.magicbox.generalgoods.domain.vo.UserId;

public record DeleteGeneralGoodsCommand(
        GeneralGoodsId id,
        UserId userId
) {
    public static DeleteGeneralGoodsCommand of(GeneralGoodsId id, UserId userId) {
        return new DeleteGeneralGoodsCommand(id, userId);
    }
}