package kr.magicbox.generalgoods.adapter.in.web;

import kr.magicbox.generalgoods.adapter.in.web.dto.request.RegisterGeneralGoodsRequest;
import kr.magicbox.generalgoods.adapter.in.web.dto.request.UpdateGeneralGoodsRequest;
import kr.magicbox.generalgoods.application.dto.command.DeleteGeneralGoodsCommand;
import kr.magicbox.generalgoods.application.port.in.DeleteGeneralGoodsUseCase;
import kr.magicbox.generalgoods.application.port.in.RegisterGeneralGoodsUseCase;
import kr.magicbox.generalgoods.application.port.in.UpdateGeneralGoodsUseCase;
import kr.magicbox.generalgoods.domain.vo.GeneralGoodsId;
import kr.magicbox.generalgoods.domain.vo.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequiredArgsConstructor
@Validated
public class GeneralGoodsCommandController {
    private final RegisterGeneralGoodsUseCase registerGeneralGoodsUseCase;
    private final UpdateGeneralGoodsUseCase updateGeneralGoodsUseCase;
    private final DeleteGeneralGoodsUseCase deleteGeneralGoodsUseCase;

    @PostMapping
    public ResponseEntity<Void> register(
            @AuthenticationPrincipal UserId userId,
            @Valid @RequestBody RegisterGeneralGoodsRequest request
    ) {
        registerGeneralGoodsUseCase.registerGeneralGoods(request.toCommand(userId));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
            @AuthenticationPrincipal UserId userId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateGeneralGoodsRequest request
    ) {
        updateGeneralGoodsUseCase.updateGeneralGoods(request.toCommand(id, userId));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserId userId,
            @PathVariable Long id
    ) {
        deleteGeneralGoodsUseCase.deleteGeneralGoods(DeleteGeneralGoodsCommand.of(GeneralGoodsId.of(id), userId));
        return ResponseEntity.noContent().build();
    }
}