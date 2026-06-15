package kr.magicbox.generalgoods.application.port.in;

import kr.magicbox.generalgoods.adapter.in.kafka.event.StockReserveCommandEvent;

public interface HandleStockReserveUseCase {
    void handleStockReserve(StockReserveCommandEvent event);
}
