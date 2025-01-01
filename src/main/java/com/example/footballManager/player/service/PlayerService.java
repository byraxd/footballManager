package com.example.footballManager.player.service;

import com.example.footballManager.player.dto.PlayerDto;
import com.example.footballManager.player.model.Player;

import java.util.List;

public interface PlayerService {
    List<Player> getAll();

    Player getById(Long id);

    Player save(PlayerDto playerDto);

    Player updateById(Long id, PlayerDto playerDto);

    void deleteById(Long id);
}
