package kr.magicbox.generalgoods.application.service;

import kr.magicbox.generalgoods.application.dto.query.GetAllGeneralGoodsQuery;
import kr.magicbox.generalgoods.application.dto.result.GeneralGoodsResult;
import kr.magicbox.generalgoods.application.dto.result.MediaResult;
import kr.magicbox.generalgoods.application.port.in.GetAllGeneralGoodsUseCase;
import kr.magicbox.generalgoods.application.port.out.GeneralGoodsRepositoryPort;
import kr.magicbox.generalgoods.domain.aggregate.GeneralGoods;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllGeneralGoodsService implements GetAllGeneralGoodsUseCase {

    private final GeneralGoodsRepositoryPort generalGoodsRepositoryPort;

    @Transactional(readOnly = true)
    @Override
    public List<GeneralGoodsResult> getAllGeneralGoods(GetAllGeneralGoodsQuery query) {
        return generalGoodsRepositoryPort.findAllByCursor(query.cursorId(), query.size() + 1)
                .stream()
                .map(this::toResult)
                .toList();
    }

    private GeneralGoodsResult toResult(GeneralGoods goods) {
        return GeneralGoodsResult.builder()
                .id(goods.getId())
                .creatorId(goods.getCreatorId())
                .name(goods.getName())
                .price(goods.getPrice())
                .stock(goods.getStock())
                .description(goods.getDescription())
                .level(goods.getLevel())
                .categories(goods.getCategories())
                .mediaList(
                        goods.getGeneralGoodsMediaList().stream().map(media ->
                                MediaResult.builder()
                                        .mediaUrl(media.getMediaUrl())
                                        .sortOrder(media.getSortOrder())
                                        .build()
                        ).toList()
                )
                .build();
    }
}
