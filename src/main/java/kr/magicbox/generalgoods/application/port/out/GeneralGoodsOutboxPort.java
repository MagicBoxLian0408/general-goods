package kr.magicbox.generalgoods.application.port.out;

import kr.magicbox.generalgoods.domain.event.GeneralGoodsDomainEvent;

public interface GeneralGoodsOutboxPort {
    void save(GeneralGoodsDomainEvent event);
}
