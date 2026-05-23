package kr.magicbox.generalgoods.application.service;

import kr.magicbox.generalgoods.application.dto.command.HandleCreatorRevokedCommand;
import kr.magicbox.generalgoods.application.port.in.HandleCreatorRevokedUseCase;
import kr.magicbox.generalgoods.application.port.out.GeneralGoodsRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HandleCreatorRevokedService implements HandleCreatorRevokedUseCase {

    private final GeneralGoodsRepositoryPort generalGoodsRepositoryPort;

    @Override
    @Transactional
    public void handleCreatorRevoked(HandleCreatorRevokedCommand command) {
        generalGoodsRepositoryPort.softDeleteByCreatorId(command.creatorId());
    }
}
