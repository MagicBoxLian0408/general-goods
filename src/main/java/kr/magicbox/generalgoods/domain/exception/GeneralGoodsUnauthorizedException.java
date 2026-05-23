package kr.magicbox.generalgoods.domain.exception;

import kr.magicbox.generalgoods.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class GeneralGoodsUnauthorizedException extends BusinessException {
    public GeneralGoodsUnauthorizedException() {
        super("해당 상품에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }
}