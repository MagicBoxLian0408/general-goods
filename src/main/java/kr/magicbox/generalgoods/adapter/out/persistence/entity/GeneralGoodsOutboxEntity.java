package kr.magicbox.generalgoods.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "general_goods_outbox")
public class GeneralGoodsOutboxEntity extends BaseEntity {

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false)
    private String aggregateKey;

    @Column(nullable = false, columnDefinition = "JSON")
    private String payload;

    @Builder
    public GeneralGoodsOutboxEntity(String eventType, String aggregateKey, String payload) {
        this.eventType = eventType;
        this.aggregateKey = aggregateKey;
        this.payload = payload;
    }
}
