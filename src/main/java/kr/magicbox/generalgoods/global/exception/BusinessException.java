package kr.magicbox.generalgoods.global.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends BaseException {

    public BusinessException(String message, HttpStatus status) {
        super(message, status);
    }
}