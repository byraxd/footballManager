package com.example.footballManager.transfer.dto;

import com.example.footballManager.transfer.model.TransferStatus;

public record TransferDto(
        TransferStatus status,
        Long playerId,
        Long fromTeamId,
        Long toTeamId) {
}
