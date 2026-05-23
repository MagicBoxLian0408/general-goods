package kr.magicbox.generalgoods.global.exception;

import org.springframework.http.HttpStatus;

public class SystemError extends BaseException {
    public SystemError(String message, HttpStatus status) {
        super(message, validateStatus(status));
    }

    public SystemError(String message, HttpStatus status, Throwable cause) {
        super(message, validateStatus(status), cause);
    }

    private static HttpStatus validateStatus(HttpStatus status) {
        if (!status.is5xxServerError()) {
            throw new SystemError("서버에러가 아닙니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return status;
    }
}
