package kr.magicbox.generalgoods.domain.exception;

import kr.magicbox.generalgoods.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class GeneralGoodsNotFoundException extends BusinessException {
    public GeneralGoodsNotFoundException() {
        super("상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}