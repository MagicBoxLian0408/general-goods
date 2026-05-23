package kr.magicbox.generalgoods.application.dto.query;

import kr.magicbox.generalgoods.domain.vo.GeneralGoodsId;

public record GetGeneralGoodsQuery(
        GeneralGoodsId id
) {
    public static GetGeneralGoodsQuery of(GeneralGoodsId id) {
        return new GetGeneralGoodsQuery(id);
    }
}