package kr.magicbox.generalgoods.application.service;

import kr.magicbox.generalgoods.application.dto.command.DeleteGeneralGoodsCommand;
import kr.magicbox.generalgoods.application.port.in.DeleteGeneralGoodsUseCase;
import kr.magicbox.generalgoods.application.port.out.CreatorIdQueryPort;
import kr.magicbox.generalgoods.application.port.out.GeneralGoodsRepositoryPort;
import kr.magicbox.generalgoods.domain.aggregate.GeneralGoods;
import kr.magicbox.generalgoods.domain.exception.GeneralGoodsUnauthorizedException;
import kr.magicbox.generalgoods.domain.vo.CreatorId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteGeneralGoodsService implements DeleteGeneralGoodsUseCase {
    private final GeneralGoodsRepositoryPort generalGoodsRepositoryPort;
    private final CreatorIdQueryPort creatorIdQueryPort;

    @Transactional
    @Override
    public void deleteGeneralGoods(DeleteGeneralGoodsCommand command) {
        GeneralGoods generalGoods = generalGoodsRepositoryPort.findById(command.id());

        CreatorId creatorId = creatorIdQueryPort.getCreatorId(command.userId());
        if (!generalGoods.getCreatorId().equals(creatorId)) {
            throw new GeneralGoodsUnauthorizedException();
        }

        generalGoods.delete();
        generalGoodsRepositoryPort.update(generalGoods);
    }
}