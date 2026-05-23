package kr.magicbox.generalgoods.application.dto.command;

public record MediaCommand(
        String mediaUrl,
        int sortOrder
) { }