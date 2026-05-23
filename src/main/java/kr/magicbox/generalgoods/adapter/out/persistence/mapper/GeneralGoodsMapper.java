package kr.magicbox.generalgoods.adapter.out.persistence.mapper;

import kr.magicbox.generalgoods.adapter.out.persistence.entity.GeneralGoodsEntity;
import kr.magicbox.generalgoods.adapter.out.persistence.entity.GeneralGoodsMediaEntity;
import kr.magicbox.generalgoods.domain.aggregate.GeneralGoods;
import kr.magicbox.generalgoods.domain.vo.CreatorId;
import kr.magicbox.generalgoods.domain.vo.GeneralGoodsId;
import kr.magicbox.generalgoods.domain.vo.GeneralGoodsMedia;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GeneralGoodsMapper {

    public GeneralGoodsEntity toEntity(GeneralGoods domain) {
        GeneralGoodsEntity entity = GeneralGoodsEntity.builder()
                .creatorId(domain.getCreatorId().value())
                .name(domain.getName())
                .price(domain.getPrice())
                .stock(domain.getStock())
                .description(domain.getDescription())
                .level(domain.getLevel())
                .categories(domain.getCategories())
                .build();

        domain.getGeneralGoodsMediaList().forEach(media -> {
            GeneralGoodsMediaEntity mediaEntity = GeneralGoodsMediaEntity.builder()
                    .mediaUrl(media.getMediaUrl())
                    .sortOrder(media.getSortOrder())
                    .build();
            entity.addMedia(mediaEntity);
        });

        return entity;
    }

    public GeneralGoodsMediaEntity toMediaEntity(GeneralGoodsMedia media) {
        return GeneralGoodsMediaEntity.builder()
                .mediaUrl(media.getMediaUrl())
                .sortOrder(media.getSortOrder())
                .build();
    }

    public GeneralGoods toDomain(GeneralGoodsEntity entity) {
        List<GeneralGoodsMedia> mediaList = entity.getGeneralGoodsMediaList().stream()
                .map(media -> GeneralGoodsMedia.builder()
                        .mediaUrl(media.getMediaUrl())
                        .sortOrder(media.getSortOrder())
                        .build())
                .toList();

        return GeneralGoods.reconstructBuilder()
                .id(GeneralGoodsId.of(entity.getId()))
                .creatorId(CreatorId.of(entity.getCreatorId()))
                .name(entity.getName())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .description(entity.getDescription())
                .level(entity.getLevel())
                .categories(entity.getCategories())
                .generalGoodsMediaList(mediaList)
                .isDeleted(entity.isDeleted())
                .build();
    }
}
