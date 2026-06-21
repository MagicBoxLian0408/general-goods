package kr.magicbox.generalgoods.application.service;

import kr.magicbox.generalgoods.application.dto.query.GetGeneralGoodsQuery;
import kr.magicbox.generalgoods.application.dto.result.GeneralGoodsResult;
import kr.magicbox.generalgoods.application.dto.result.MediaResult;
import kr.magicbox.generalgoods.application.port.in.GetGeneralGoodsUseCase;
import kr.magicbox.generalgoods.application.port.out.GeneralGoodsRepositoryPort;
import kr.magicbox.generalgoods.domain.aggregate.GeneralGoods;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetGeneralGoodsService implements GetGeneralGoodsUseCase {
    private final GeneralGoodsRepositoryPort generalGoodsRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public GeneralGoodsResult getGeneralGoods(GetGeneralGoodsQuery query) {
        GeneralGoods goods = generalGoodsRepositoryPort.findById(query.id());

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
