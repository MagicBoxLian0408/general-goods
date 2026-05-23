package kr.magicbox.generalgoods.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "general_goods_media")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GeneralGoodsMediaEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_goods_id", nullable = false)
    private GeneralGoodsEntity generalGoods;

    @Column(name = "media_url", nullable = false)
    private String mediaUrl;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Builder
    public GeneralGoodsMediaEntity(String mediaUrl, Integer sortOrder) {
        this.mediaUrl = mediaUrl;
        this.sortOrder = sortOrder;
    }

    public void assignGeneralGoods(GeneralGoodsEntity generalGoods) {
        this.generalGoods = generalGoods;
    }
}
