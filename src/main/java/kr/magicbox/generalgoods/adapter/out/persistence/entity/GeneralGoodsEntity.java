package kr.magicbox.generalgoods.adapter.out.persistence.entity;

import jakarta.persistence.*;
import kr.magicbox.generalgoods.domain.aggregate.GeneralGoods;
import kr.magicbox.generalgoods.domain.enums.GeneralGoodsLevel;
import kr.magicbox.generalgoods.domain.enums.MagicGenre;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "general_goods")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GeneralGoodsEntity extends BaseEntity {
    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "stock", nullable = false)
    private Long stock;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private GeneralGoodsLevel level;

    @ElementCollection
    @CollectionTable(name = "general_goods_category", joinColumns = @JoinColumn(name = "general_goods_id"))
    @Column(name = "category")
    private Set<MagicGenre> categories;

    @OneToMany(mappedBy = "generalGoods", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GeneralGoodsMediaEntity> generalGoodsMediaList = new ArrayList<>();

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Version
    private Integer version;

    @Builder
    public GeneralGoodsEntity(Long creatorId, String name, Long price, Long stock, String description, GeneralGoodsLevel level, Set<MagicGenre> categories) {
        this.creatorId = creatorId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.level = level;
        this.categories = categories;
        this.isDeleted = false;
    }

    public void addMedia(GeneralGoodsMediaEntity media) {
        media.assignGeneralGoods(this);
        this.generalGoodsMediaList.add(media);
    }

    public void updateFromDomain(GeneralGoods domain) {
        this.name = domain.getName();
        this.price = domain.getPrice();
        this.stock = domain.getStock();
        this.description = domain.getDescription();
        this.level = domain.getLevel();
        this.categories = domain.getCategories();
        this.isDeleted = domain.isDeleted();
    }
}
