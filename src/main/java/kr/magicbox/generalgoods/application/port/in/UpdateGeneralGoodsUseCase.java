package kr.magicbox.generalgoods.application.port.in;

import kr.magicbox.generalgoods.application.dto.command.UpdateGeneralGoodsCommand;

public interface UpdateGeneralGoodsUseCase {
    void updateGeneralGoods(UpdateGeneralGoodsCommand command);
}