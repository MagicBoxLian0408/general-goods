package kr.magicbox.generalgoods.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GeneralGoodsDomainEventType {

    GENERAL_GOODS_CREATED("general-goods-created"),
    GENERAL_GOODS_UPDATED("general-goods-updated"),
    GENERAL_GOODS_DELETED("general-goods-deleted");

    private final String value;
}
