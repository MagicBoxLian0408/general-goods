package kr.magicbox.generalgoods.application.port.in;

import kr.magicbox.generalgoods.application.dto.query.GetGeneralGoodsQuery;
import kr.magicbox.generalgoods.application.dto.result.GeneralGoodsResult;

public interface GetGeneralGoodsUseCase {
    GeneralGoodsResult getGeneralGoods(GetGeneralGoodsQuery query);
}