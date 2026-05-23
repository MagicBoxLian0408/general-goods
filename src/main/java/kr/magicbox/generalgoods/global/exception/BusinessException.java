package kr.magicbox.generalgoods.global.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends BaseException {
    public BusinessException(String message, HttpStatus status) {
        super(message, validateStatus(status));
    }

    private static HttpStatus validateStatus(HttpStatus status) {
        if (!status.is4xxClientError()) {
            throw new SystemError("클라이언트 에러가 아닙니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return status;
    }
}