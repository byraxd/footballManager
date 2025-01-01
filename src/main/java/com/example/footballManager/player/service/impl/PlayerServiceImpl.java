package com.example.footballManager.player.service.impl;

import com.example.footballManager.exception.model.PlayerNotFoundException;
import com.example.footballManager.exception.model.TeamNotFoundException;
import com.example.footballManager.player.dto.PlayerDto;
import com.example.footballManager.player.model.Player;
import com.example.footballManager.player.repository.PlayerRepository;
import com.example.footballManager.player.service.PlayerService;
import com.example.footballManager.team.model.Team;
import com.example.footballManager.team.repository.TeamRepository;
import com.example.footballManager.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public List<Player> getAll() {
        log.info("Fetching list of all players");

        List<Player> players = playerRepository.findAll();
        log.info("Fetched list of all players: {}", players);

        return players;
    }

    @Override
    public Player getById(Long id) {
        log.info("Fetching player by id: {}", id);

        ValidationUtils.validateId(id);
        Player player = playerRepository.findById(id).orElseThrow(() -> {
            log.error("No player found");
            return new PlayerNotFoundException("Player with following id: " + id + " not found");
        });

        log.info("Fetched player: {}", player);
        return player;
    }

    @Override
    public Player save(PlayerDto playerDto) {
        log.info("Saving player: {}", playerDto);

        ValidationUtils.validateId(playerDto.teamId());
        Team team = teamRepository.findById(playerDto.teamId()).orElseThrow(() -> {
           log.error("No team found");
           return new TeamNotFoundException("Team with following id: " + playerDto.teamId() + " not found");
        });

        Player player =
                Player
                .builder()
                .name(playerDto.name())
                .age(playerDto.age())
                .yearsOfExperience(playerDto.yearsOfExperience())
                .team(team)
                .build();

        playerRepository.save(player);
        log.info("Saved player: {}", player);

        return player;
    }

    @Override
    public Player updateById(Long id, PlayerDto playerDto) {
        log.info("Updating player: {}", playerDto);

        ValidationUtils.validateDto(playerDto);

        ValidationUtils.validateId(id);
        Player playerForUpdate = playerRepository.findById(id).orElseThrow(() -> {
            log.error("No player found");
            return new PlayerNotFoundException("Player with following id: " + id + " not found");
        });

        ValidationUtils.validateId(playerDto.teamId());
        Team team = teamRepository.findById(playerDto.teamId()).orElseThrow(() -> {
            log.error("No team found");
            return new TeamNotFoundException("Team with following id: " + playerDto.teamId() + " not found");
        });

        playerForUpdate.setName(playerDto.name());
        playerForUpdate.setAge(playerDto.age());
        playerForUpdate.setYearsOfExperience(playerDto.yearsOfExperience());
        playerForUpdate.setTeam(team);

        playerRepository.save(playerForUpdate);
        log.info("Updated player {}, by id {}", playerForUpdate, id);

        return playerForUpdate;
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting player by id: {}", id);
        ValidationUtils.validateId(id);

        if(!playerRepository.existsById(id)) {
            log.error("Player not exists by id: {}", id);
            throw new PlayerNotFoundException("Player with following id: " + id + " not found");
        }

        playerRepository.deleteById(id);
        log.info("Deleted player: {}", id);
    }

}
