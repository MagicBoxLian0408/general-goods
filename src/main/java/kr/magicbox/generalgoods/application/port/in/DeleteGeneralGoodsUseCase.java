package kr.magicbox.generalgoods.application.port.in;

import kr.magicbox.generalgoods.application.dto.command.DeleteGeneralGoodsCommand;

public interface DeleteGeneralGoodsUseCase {
    void deleteGeneralGoods(DeleteGeneralGoodsCommand command);
}