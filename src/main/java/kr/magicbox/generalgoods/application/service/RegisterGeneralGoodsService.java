package kr.magicbox.generalgoods.application.service;

import kr.magicbox.generalgoods.application.dto.command.MediaCommand;
import kr.magicbox.generalgoods.application.dto.command.RegisterGeneralGoodsCommand;
import kr.magicbox.generalgoods.application.port.in.RegisterGeneralGoodsUseCase;
import kr.magicbox.generalgoods.application.port.out.CreatorIdQueryPort;
import kr.magicbox.generalgoods.application.port.out.GeneralGoodsRepositoryPort;
import kr.magicbox.generalgoods.domain.aggregate.GeneralGoods;
import kr.magicbox.generalgoods.domain.vo.CreatorId;
import kr.magicbox.generalgoods.domain.vo.GeneralGoodsMedia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterGeneralGoodsService implements RegisterGeneralGoodsUseCase {
    private final GeneralGoodsRepositoryPort generalGoodsRepositoryPort;
    private final CreatorIdQueryPort creatorIdQueryPort;

    @Transactional
    @Override
    public void registerGeneralGoods(RegisterGeneralGoodsCommand command) {
        CreatorId creatorId = creatorIdQueryPort.getCreatorId(command.userId());

        List<GeneralGoodsMedia> mediaList = command.mediaList().stream()
                .map(this::toGeneralGoodsMedia)
                .toList();

        GeneralGoods generalGoods = GeneralGoods.createBuilder()
                .creatorId(creatorId)
                .name(command.name())
                .price(command.price())
                .stock(command.stock())
                .description(command.description())
                .categories(command.categories())
                .generalGoodsMediaList(mediaList)
                .build();

        generalGoodsRepositoryPort.save(generalGoods);
    }

    private GeneralGoodsMedia toGeneralGoodsMedia(MediaCommand mediaCommand) {
        return GeneralGoodsMedia.builder()
                .mediaUrl(mediaCommand.mediaUrl())
                .sortOrder(mediaCommand.sortOrder())
                .build();
    }
}