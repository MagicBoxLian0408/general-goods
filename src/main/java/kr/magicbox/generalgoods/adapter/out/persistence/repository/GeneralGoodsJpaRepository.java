package kr.magicbox.generalgoods.adapter.out.persistence.repository;

import kr.magicbox.generalgoods.adapter.out.persistence.entity.GeneralGoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GeneralGoodsJpaRepository extends JpaRepository<GeneralGoodsEntity, Long>, JpaSpecificationExecutor<GeneralGoodsEntity> {
    Optional<GeneralGoodsEntity> findByIdAndIsDeletedFalse(Long id);

    List<GeneralGoodsEntity> findByCreatorIdAndIsDeletedFalse(Long creatorId);

    @Query("SELECT g FROM GeneralGoodsEntity g WHERE (g.name LIKE %:keyword% OR g.description LIKE %:keyword%) AND g.isDeleted = false")
    List<GeneralGoodsEntity> findByNameOrDescriptionContaining(@Param("keyword") String keyword);

    @Modifying
    @Query("UPDATE GeneralGoodsEntity g SET g.isDeleted = true WHERE g.creatorId = :creatorId AND g.isDeleted = false")
    void softDeleteByCreatorId(@Param("creatorId") Long creatorId);
}