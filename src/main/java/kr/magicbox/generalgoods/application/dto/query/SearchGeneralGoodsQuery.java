package kr.magicbox.generalgoods.application.dto.query;

public record SearchGeneralGoodsQuery(
        String keyword
) {
    public static SearchGeneralGoodsQuery of(String keyword) {
        return new SearchGeneralGoodsQuery(keyword);
    }
}