package com.example.footballManager.team.service;

import com.example.footballManager.team.dto.TeamDto;
import com.example.footballManager.team.model.Team;

import java.util.List;

public interface TeamService {

    List<Team> getAll();

    Team getById(Long id);

    Team save(TeamDto teamDto);

    Team updateById(Long id, TeamDto teamDto);

    void deleteById(Long id);
}
