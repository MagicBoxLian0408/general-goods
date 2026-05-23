package kr.magicbox.generalgoods.domain.vo;

import kr.magicbox.generalgoods.domain.exception.InvalidFieldException;

public record CreatorId(Long value) {
    public CreatorId {
        if (value == null || value <= 0) {
            throw new InvalidFieldException("크리에이터 ID는 양수여야 합니다.");
        }
    }

    public static CreatorId of(Long value) {
        return new CreatorId(value);
    }
}