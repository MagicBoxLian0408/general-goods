package kr.magicbox.generalgoods.adapter.in.web.exception.handler;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ErrorResponse(int status, String message) {
    public static ErrorResponse of(HttpStatus status, String message) {
        return ErrorResponse.builder()
                .status(status.value())
                .message(message)
                .build();
    }
}