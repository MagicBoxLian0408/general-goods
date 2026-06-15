package kr.magicbox.generalgoods.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GeneralGoodsDomainEventType {

    GENERAL_GOODS_CREATED("general-goods-created"),
    GENERAL_GOODS_UPDATED("general-goods-updated"),
    GENERAL_GOODS_DELETED("general-goods-deleted"),
    STOCK_RESERVE_SUCCEEDED("stock-reserve-succeeded"),
    STOCK_RESERVE_FAILED("stock-reserve-failed");

    private final String value;
}
