package kr.magicbox.generalgoods.domain.vo;

import kr.magicbox.generalgoods.domain.exception.InvalidFieldException;
import lombok.Builder;
import lombok.Getter;

@Getter
public final class GeneralGoodsMedia {
    private final String mediaUrl;
    private final int sortOrder;

    @Builder
    public GeneralGoodsMedia(String mediaUrl, int sortOrder) {
        if (mediaUrl == null || mediaUrl.isBlank()) {
            throw new InvalidFieldException("미디어 URL은 필수입니다.");
        }
        if (sortOrder < 0) {
            throw new InvalidFieldException("정렬 순서는 0 이상이어야 합니다.");
        }
        this.mediaUrl = mediaUrl;
        this.sortOrder = sortOrder;
    }
}
