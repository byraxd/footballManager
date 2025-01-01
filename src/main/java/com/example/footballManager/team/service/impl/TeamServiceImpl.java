package com.example.footballManager.team.service.impl;

import com.example.footballManager.exception.model.TeamNotFoundException;
import com.example.footballManager.team.dto.TeamDto;
import com.example.footballManager.team.model.Team;
import com.example.footballManager.team.repository.TeamRepository;
import com.example.footballManager.team.service.TeamService;
import com.example.footballManager.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public List<Team> getAll() {
        log.info("Fetching list of all teams");

        List<Team> teams = teamRepository.findAll();
        log.info("Fetched list of all teams: {}", teams);

        return teams;
    }

    @Override
    public Team getById(Long id) {
        log.info("Fetching team by id: {}", id);

        ValidationUtils.validateId(id);
        Team team = teamRepository.findById(id).orElseThrow(() -> {
            log.error("No team found");
            return new TeamNotFoundException("Team with following id: " + id + " not found");
        });

        log.info("Fetched team: {}", team);
        return team;
    }

    @Override
    @Transactional
    public Team save(TeamDto teamDto) {
        log.info("Saving team: {}", teamDto);

        ValidationUtils.validateDto(teamDto);

        Team team = Team
                .builder()
                .name(teamDto.name())
                .commissionForPlayer(teamDto.commissionForPlayer())
                .amountOfMoney(teamDto.amountOfMoney())
                .build();

        teamRepository.save(team);
        log.info("Saved team: {}", team);

        return team;
    }

    @Override
    @Transactional
    public Team updateById(Long id, TeamDto teamDto) {
        log.info("Updating team: {}", teamDto);

        ValidationUtils.validateDto(teamDto);

        ValidationUtils.validateId(id);
        Team teamForUpdate = teamRepository.findById(id).orElseThrow(() -> {
            log.error("No team found");
            return new TeamNotFoundException("Team with following id: " + id + " not found");
        });

        teamForUpdate.setName(teamDto.name());
        teamForUpdate.setCommissionForPlayer(teamDto.commissionForPlayer());
        teamForUpdate.setAmountOfMoney(teamDto.amountOfMoney());

        teamRepository.save(teamForUpdate);
        log.info("Updated team {}, by id {}", teamForUpdate, id);

        return teamForUpdate;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Deleting team by id: {}", id);
        ValidationUtils.validateId(id);

        if (!teamRepository.existsById(id)) {
            log.error("Team not exists by id: {}", id);
            throw new TeamNotFoundException("Team with following id: " + id + " not found");
        }

        teamRepository.deleteById(id);
        log.info("Deleted team: {}", id);
    }
}
