package kr.magicbox.generalgoods.application.port.in;

import kr.magicbox.generalgoods.application.dto.query.GetAllGeneralGoodsQuery;
import kr.magicbox.generalgoods.application.dto.result.GeneralGoodsResult;

import java.util.List;

public interface GetAllGeneralGoodsUseCase {
    List<GeneralGoodsResult> getAllGeneralGoods(GetAllGeneralGoodsQuery query);
}
