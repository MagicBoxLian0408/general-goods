package kr.magicbox.generalgoods.application.port.in;

import kr.magicbox.generalgoods.application.dto.command.RegisterGeneralGoodsCommand;

public interface RegisterGeneralGoodsUseCase {
    void registerGeneralGoods(RegisterGeneralGoodsCommand command);
}