package kr.magicbox.generalgoods.adapter.out.persistence.repository;

import kr.magicbox.generalgoods.adapter.out.persistence.entity.GeneralGoodsOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralGoodsOutboxRepository extends JpaRepository<GeneralGoodsOutboxEntity, Long> {
}
