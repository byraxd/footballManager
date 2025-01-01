package com.example.footballManager.player.dto;


public record PlayerDto(
        String name,
        Integer age,
        Double yearsOfExperience,
        Long teamId) {
}
