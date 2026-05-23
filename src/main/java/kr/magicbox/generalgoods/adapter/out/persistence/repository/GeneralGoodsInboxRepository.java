package kr.magicbox.generalgoods.adapter.out.persistence.repository;

import kr.magicbox.generalgoods.adapter.out.persistence.entity.GeneralGoodsInboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GeneralGoodsInboxRepository extends JpaRepository<GeneralGoodsInboxEntity, Long> {

    boolean existsByEventId(Long eventId);

    Optional<GeneralGoodsInboxEntity> findByTopicAndPartitionAndOffset(String topic, Integer partition, Long offset);
}
