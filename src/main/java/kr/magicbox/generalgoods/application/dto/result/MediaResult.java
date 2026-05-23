package kr.magicbox.generalgoods.application.dto.result;

import lombok.Builder;

@Builder
public record MediaResult(
        String mediaUrl,
        int sortOrder
) { }
