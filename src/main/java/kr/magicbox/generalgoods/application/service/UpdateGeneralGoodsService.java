package kr.magicbox.generalgoods.application.service;

import kr.magicbox.generalgoods.application.dto.command.MediaCommand;
import kr.magicbox.generalgoods.application.dto.command.UpdateGeneralGoodsCommand;
import kr.magicbox.generalgoods.application.port.in.UpdateGeneralGoodsUseCase;
import kr.magicbox.generalgoods.application.port.out.CreatorIdQueryPort;
import kr.magicbox.generalgoods.application.port.out.GeneralGoodsOutboxPort;
import kr.magicbox.generalgoods.application.port.out.GeneralGoodsRepositoryPort;
import kr.magicbox.generalgoods.domain.aggregate.GeneralGoods;
import kr.magicbox.generalgoods.domain.event.GeneralGoodsUpdatedEvent;
import kr.magicbox.generalgoods.domain.event.GeneralGoodsUpdatedEvent.GoodsSnapshot;
import kr.magicbox.generalgoods.domain.exception.GeneralGoodsUnauthorizedException;
import kr.magicbox.generalgoods.domain.vo.CreatorId;
import kr.magicbox.generalgoods.domain.vo.GeneralGoodsMedia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateGeneralGoodsService implements UpdateGeneralGoodsUseCase {
    private final GeneralGoodsRepositoryPort generalGoodsRepositoryPort;
    private final CreatorIdQueryPort creatorIdQueryPort;
    private final GeneralGoodsOutboxPort generalGoodsOutboxPort;

    @Transactional
    @Override
    public void updateGeneralGoods(UpdateGeneralGoodsCommand command) {
        GeneralGoods generalGoods = generalGoodsRepositoryPort.findById(command.id());

        CreatorId creatorId = creatorIdQueryPort.getCreatorId(command.userId());
        if (!generalGoods.getCreatorId().equals(creatorId)) {
            throw new GeneralGoodsUnauthorizedException();
        }

        GoodsSnapshot before = new GoodsSnapshot(
                generalGoods.getName(),
                generalGoods.getPrice(),
                generalGoods.getStock(),
                generalGoods.getGeneralGoodsMediaList().stream().map(GeneralGoodsMedia::getMediaUrl).toList()
        );

        List<GeneralGoodsMedia> mediaList = Optional.ofNullable(command.mediaList())
                .map(list -> list.stream().map(this::toGeneralGoodsMedia).toList())
                .orElse(null);

        generalGoods.update(command.name(), command.price(), command.stock(), command.description(), command.level(), command.categories(), mediaList);

        generalGoodsRepositoryPort.update(generalGoods);

        GoodsSnapshot after = new GoodsSnapshot(
                generalGoods.getName(),
                generalGoods.getPrice(),
                generalGoods.getStock(),
                generalGoods.getGeneralGoodsMediaList().stream().map(GeneralGoodsMedia::getMediaUrl).toList()
        );

        generalGoodsOutboxPort.save(GeneralGoodsUpdatedEvent.builder()
                .generalGoodsId(command.id().value())
                .creatorId(creatorId.value())
                .before(before)
                .after(after)
                .occurredAt(Instant.now())
                .build());
    }

    private GeneralGoodsMedia toGeneralGoodsMedia(MediaCommand mediaCommand) {
        return GeneralGoodsMedia.builder()
                .mediaUrl(mediaCommand.mediaUrl())
                .sortOrder(mediaCommand.sortOrder())
                .build();
    }
}