package kr.magicbox.generalgoods.adapter.out.communication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceHost {
    CREATOR("creator");

    private final String hostName;
}