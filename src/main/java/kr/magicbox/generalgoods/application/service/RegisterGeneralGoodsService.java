package kr.magicbox.generalgoods.application.service;

import kr.magicbox.generalgoods.application.dto.command.MediaCommand;
import kr.magicbox.generalgoods.application.dto.command.RegisterGeneralGoodsCommand;
import kr.magicbox.generalgoods.application.port.in.RegisterGeneralGoodsUseCase;
import kr.magicbox.generalgoods.application.port.out.CreatorIdQueryPort;
import kr.magicbox.generalgoods.application.port.out.GeneralGoodsOutboxPort;
import kr.magicbox.generalgoods.application.port.out.GeneralGoodsRepositoryPort;
import kr.magicbox.generalgoods.domain.aggregate.GeneralGoods;
import kr.magicbox.generalgoods.domain.event.GeneralGoodsCreatedEvent;
import kr.magicbox.generalgoods.domain.vo.CreatorId;
import kr.magicbox.generalgoods.domain.vo.GeneralGoodsMedia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterGeneralGoodsService implements RegisterGeneralGoodsUseCase {
    private final GeneralGoodsRepositoryPort generalGoodsRepositoryPort;
    private final CreatorIdQueryPort creatorIdQueryPort;
    private final GeneralGoodsOutboxPort generalGoodsOutboxPort;

    @Transactional
    @Override
    public void registerGeneralGoods(RegisterGeneralGoodsCommand command) {
        CreatorId creatorId = creatorIdQueryPort.getCreatorId(command.userId()).join();

        List<GeneralGoodsMedia> mediaList = command.mediaList().stream()
                .map(this::toGeneralGoodsMedia)
                .toList();

        GeneralGoods generalGoods = GeneralGoods.createBuilder()
                .creatorId(creatorId)
                .name(command.name())
                .price(command.price())
                .stock(command.stock())
                .description(command.description())
                .level(command.level())
                .categories(command.categories())
                .generalGoodsMediaList(mediaList)
                .build();

        Long generalGoodsId = generalGoodsRepositoryPort.save(generalGoods);

        List<String> mediaUrls = mediaList.stream()
                .map(GeneralGoodsMedia::getMediaUrl)
                .toList();

        generalGoodsOutboxPort.save(GeneralGoodsCreatedEvent.builder()
                .generalGoodsId(generalGoodsId)
                .creatorId(creatorId.value())
                .name(command.name())
                .description(command.description())
                .level(command.level())
                .categories(command.categories())
                .price(command.price())
                .stock(command.stock())
                .isDeleted(false)
                .mediaUrls(mediaUrls)
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
