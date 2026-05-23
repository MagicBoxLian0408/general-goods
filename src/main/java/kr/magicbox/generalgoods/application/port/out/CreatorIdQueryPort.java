package kr.magicbox.generalgoods.application.port.out;

import kr.magicbox.generalgoods.domain.vo.CreatorId;
import kr.magicbox.generalgoods.domain.vo.UserId;

public interface CreatorIdQueryPort {

    CreatorId getCreatorId(UserId userId);
}