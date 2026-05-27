package kr.magicbox.generalgoods.domain.event;

public interface GeneralGoodsDomainEvent {
    String key();
    GeneralGoodsDomainEventType eventType();
}
