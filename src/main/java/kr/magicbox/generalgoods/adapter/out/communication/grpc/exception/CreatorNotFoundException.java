package kr.magicbox.generalgoods.adapter.out.communication.grpc.exception;

import kr.magicbox.generalgoods.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class CreatorNotFoundException extends BusinessException {
    public CreatorNotFoundException() {
        super("크리에이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}