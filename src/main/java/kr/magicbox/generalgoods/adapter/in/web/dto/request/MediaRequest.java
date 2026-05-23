package kr.magicbox.generalgoods.adapter.in.web.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MediaRequest(
        @NotBlank(message = "미디어 URL은 필수입니다.") String mediaUrl,
        @NotNull(message = "정렬 순서는 필수입니다.") @Min(value = 0, message = "정렬 순서는 0 이상이어야 합니다.") Integer sortOrder
) { }