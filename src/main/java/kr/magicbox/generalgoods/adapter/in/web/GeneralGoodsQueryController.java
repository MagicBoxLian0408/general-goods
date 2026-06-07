package kr.magicbox.generalgoods.adapter.in.web;

import kr.magicbox.generalgoods.adapter.in.web.dto.response.GeneralGoodsResponse;
import kr.magicbox.generalgoods.application.dto.query.GetGeneralGoodsQuery;
import kr.magicbox.generalgoods.application.dto.result.GeneralGoodsResult;
import kr.magicbox.generalgoods.application.port.in.GetGeneralGoodsUseCase;
import kr.magicbox.generalgoods.domain.vo.GeneralGoodsId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class GeneralGoodsQueryController {
    private final GetGeneralGoodsUseCase getGeneralGoodsUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<GeneralGoodsResponse> getGeneralGoods(@PathVariable Long id) {
        GeneralGoodsResult result = getGeneralGoodsUseCase.getGeneralGoods(GetGeneralGoodsQuery.of(GeneralGoodsId.of(id)));
        return ResponseEntity.ok(GeneralGoodsResponse.from(result));
    }
}