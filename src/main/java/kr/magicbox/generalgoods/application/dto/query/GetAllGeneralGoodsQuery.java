package kr.magicbox.generalgoods.application.dto.query;

public record GetAllGeneralGoodsQuery(Long cursorId, int size) {
    public static GetAllGeneralGoodsQuery of(Long cursorId, int size) {
        return new GetAllGeneralGoodsQuery(cursorId, size);
    }
}
