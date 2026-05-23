package kr.magicbox.generalgoods.application.port.in;

import kr.magicbox.generalgoods.application.dto.command.HandleCreatorRevokedCommand;

public interface HandleCreatorRevokedUseCase {
    void handleCreatorRevoked(HandleCreatorRevokedCommand command);
}
