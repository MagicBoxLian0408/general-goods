package kr.magicbox.generalgoods.adapter.in.web;

import kr.magicbox.generalgoods.adapter.in.web.constants.CursorConstants;
import kr.magicbox.generalgoods.adapter.in.web.dto.response.CursorResponse;
import kr.magicbox.generalgoods.adapter.in.web.dto.response.GeneralGoodsResponse;
import kr.magicbox.generalgoods.adapter.in.web.validation.CursorSize;
import kr.magicbox.generalgoods.application.dto.query.GetAllGeneralGoodsQuery;
import kr.magicbox.generalgoods.application.dto.query.GetGeneralGoodsQuery;
import kr.magicbox.generalgoods.application.dto.result.GeneralGoodsResult;
import kr.magicbox.generalgoods.application.port.in.GetAllGeneralGoodsUseCase;
import kr.magicbox.generalgoods.application.port.in.GetGeneralGoodsUseCase;
import kr.magicbox.generalgoods.domain.vo.GeneralGoodsId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/general-good")
@RequiredArgsConstructor
@Validated
public class GeneralGoodsQueryController {
    private final GetGeneralGoodsUseCase getGeneralGoodsUseCase;
    private final GetAllGeneralGoodsUseCase getAllGeneralGoodsUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<GeneralGoodsResponse> getGeneralGoods(@PathVariable Long id) {
        GeneralGoodsResult result = getGeneralGoodsUseCase.getGeneralGoods(GetGeneralGoodsQuery.of(GeneralGoodsId.of(id)));
        return ResponseEntity.ok(GeneralGoodsResponse.from(result));
    }

    @GetMapping
    public ResponseEntity<CursorResponse<GeneralGoodsResponse>> getAllGeneralGoods(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = CursorConstants.DEFAULT_SIZE) @CursorSize Integer size) {
        List<GeneralGoodsResponse> contents = getAllGeneralGoodsUseCase.getAllGeneralGoods(GetAllGeneralGoodsQuery.of(cursor, size))
                .stream()
                .map(GeneralGoodsResponse::from)
                .toList();
        return ResponseEntity.ok(CursorResponse.of(contents, size, r -> r.id()));
    }
}
