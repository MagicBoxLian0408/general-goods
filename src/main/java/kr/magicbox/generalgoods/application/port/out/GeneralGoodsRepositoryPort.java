package kr.magicbox.generalgoods.application.port.out;

import kr.magicbox.generalgoods.domain.aggregate.GeneralGoods;
import kr.magicbox.generalgoods.domain.vo.CreatorId;
import kr.magicbox.generalgoods.domain.vo.GeneralGoodsId;

public interface GeneralGoodsRepositoryPort {
    void save(GeneralGoods generalGoods);

    void update(GeneralGoods generalGoods);

    void softDeleteByCreatorId(CreatorId creatorId);

    GeneralGoods findById(GeneralGoodsId id);
}