package kr.magicbox.generalgoods.application.service;

import kr.magicbox.generalgoods.application.dto.command.DeleteGeneralGoodsCommand;
import kr.magicbox.generalgoods.application.port.in.DeleteGeneralGoodsUseCase;
import kr.magicbox.generalgoods.application.port.out.CreatorIdQueryPort;
import kr.magicbox.generalgoods.application.port.out.GeneralGoodsOutboxPort;
import kr.magicbox.generalgoods.application.port.out.GeneralGoodsRepositoryPort;
import kr.magicbox.generalgoods.domain.aggregate.GeneralGoods;
import kr.magicbox.generalgoods.domain.event.GeneralGoodsDeletedEvent;
import kr.magicbox.generalgoods.domain.exception.GeneralGoodsUnauthorizedException;
import kr.magicbox.generalgoods.domain.vo.CreatorId;
import kr.magicbox.generalgoods.domain.vo.GeneralGoodsMedia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteGeneralGoodsService implements DeleteGeneralGoodsUseCase {
    private final GeneralGoodsRepositoryPort generalGoodsRepositoryPort;
    private final CreatorIdQueryPort creatorIdQueryPort;
    private final GeneralGoodsOutboxPort generalGoodsOutboxPort;

    @Transactional
    @Override
    public void deleteGeneralGoods(DeleteGeneralGoodsCommand command) {
        GeneralGoods generalGoods = generalGoodsRepositoryPort.findById(command.id());

        CreatorId creatorId = creatorIdQueryPort.getCreatorId(command.userId());
        if (!generalGoods.getCreatorId().equals(creatorId)) {
            throw new GeneralGoodsUnauthorizedException();
        }

        List<String> mediaUrls = generalGoods.getGeneralGoodsMediaList().stream()
                .map(GeneralGoodsMedia::getMediaUrl)
                .toList();
        String name = generalGoods.getName();

        generalGoods.delete();
        generalGoodsRepositoryPort.update(generalGoods);

        generalGoodsOutboxPort.save(GeneralGoodsDeletedEvent.builder()
                .generalGoodsId(command.id().value())
                .creatorId(creatorId.value())
                .name(name)
                .mediaUrls(mediaUrls)
                .occurredAt(Instant.now())
                .build());
    }
}