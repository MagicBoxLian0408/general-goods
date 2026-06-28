package kr.magicbox.generalgoods.application.port.out;

import kr.magicbox.generalgoods.domain.aggregate.GeneralGoods;
import kr.magicbox.generalgoods.domain.vo.CreatorId;
import kr.magicbox.generalgoods.domain.vo.GeneralGoodsId;

import java.util.List;

public interface GeneralGoodsRepositoryPort {
    Long save(GeneralGoods generalGoods);

    void update(GeneralGoods generalGoods);

    void softDeleteByCreatorId(CreatorId creatorId);

    GeneralGoods findById(GeneralGoodsId id);

    List<GeneralGoods> findAllByCursor(Long cursorId, int size);
}