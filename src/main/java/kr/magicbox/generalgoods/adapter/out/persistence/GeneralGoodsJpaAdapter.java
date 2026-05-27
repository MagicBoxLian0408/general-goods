package kr.magicbox.generalgoods.adapter.out.persistence;

import kr.magicbox.generalgoods.adapter.out.persistence.entity.GeneralGoodsEntity;
import kr.magicbox.generalgoods.adapter.out.persistence.mapper.GeneralGoodsMapper;
import kr.magicbox.generalgoods.adapter.out.persistence.repository.GeneralGoodsJpaRepository;
import kr.magicbox.generalgoods.application.port.out.GeneralGoodsRepositoryPort;
import kr.magicbox.generalgoods.domain.aggregate.GeneralGoods;
import kr.magicbox.generalgoods.domain.exception.GeneralGoodsNotFoundException;
import kr.magicbox.generalgoods.domain.vo.CreatorId;
import kr.magicbox.generalgoods.domain.vo.GeneralGoodsId;
import kr.magicbox.generalgoods.domain.vo.GeneralGoodsMedia;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GeneralGoodsJpaAdapter implements GeneralGoodsRepositoryPort {
    private final GeneralGoodsJpaRepository generalGoodsJpaRepository;
    private final GeneralGoodsMapper generalGoodsMapper;

    @Override
    public Long save(GeneralGoods generalGoods) {
        GeneralGoodsEntity entity = generalGoodsJpaRepository.save(generalGoodsMapper.toEntity(generalGoods));
        return entity.getId();
    }

    @Override
    public void update(GeneralGoods generalGoods) {
        GeneralGoodsEntity entity = generalGoodsJpaRepository.findByIdAndIsDeletedFalse(generalGoods.getId().value())
                .orElseThrow(GeneralGoodsNotFoundException::new);
        entity.updateFromDomain(generalGoods);

        syncMediaList(entity, generalGoods.getGeneralGoodsMediaList());
    }

    private void syncMediaList(GeneralGoodsEntity entity, List<GeneralGoodsMedia> newMediaList) {
        Set<String> incomingKeys = newMediaList.stream()
                .map(m -> m.getMediaUrl() + "|" + m.getSortOrder())
                .collect(Collectors.toSet());

        entity.getGeneralGoodsMediaList().removeIf(existing ->
                !incomingKeys.contains(existing.getMediaUrl() + "|" + existing.getSortOrder()));

        Set<String> existingKeys = entity.getGeneralGoodsMediaList().stream()
                .map(e -> e.getMediaUrl() + "|" + e.getSortOrder())
                .collect(Collectors.toSet());

        newMediaList.stream()
                .filter(m -> !existingKeys.contains(m.getMediaUrl() + "|" + m.getSortOrder()))
                .map(generalGoodsMapper::toMediaEntity)
                .forEach(entity::addMedia);
    }

    @Override
    public void softDeleteByCreatorId(CreatorId creatorId) {
        generalGoodsJpaRepository.softDeleteByCreatorId(creatorId.value());
    }

    @Override
    public GeneralGoods findById(GeneralGoodsId id) {
        return generalGoodsJpaRepository.findByIdAndIsDeletedFalse(id.value())
                .map(generalGoodsMapper::toDomain)
                .orElseThrow(GeneralGoodsNotFoundException::new);
    }

    @Override
    public List<GeneralGoods> findAllByCursor(Long cursorId, int size) {
        List<GeneralGoodsEntity> entities = cursorId == null
                ? generalGoodsJpaRepository.findAllByIsDeletedFalseOrderByIdDesc(PageRequest.of(0, size))
                : generalGoodsJpaRepository.findByIdLessThanAndIsDeletedFalseOrderByIdDesc(cursorId, PageRequest.of(0, size));
        return entities.stream()
                .map(generalGoodsMapper::toDomain)
                .toList();
    }
}
