package kr.magicbox.generalgoods.domain.exception;

import kr.magicbox.generalgoods.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class InvalidFieldException extends BusinessException {
    public InvalidFieldException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}