package kr.magicbox.generalgoods.application.port.out;

import kr.magicbox.generalgoods.domain.vo.CreatorId;
import kr.magicbox.generalgoods.domain.vo.UserId;

import java.util.concurrent.CompletableFuture;

public interface CreatorIdQueryPort {

    CompletableFuture<CreatorId> getCreatorId(UserId userId);
}
