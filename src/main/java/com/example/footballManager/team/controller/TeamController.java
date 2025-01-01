package com.example.footballManager.team.controller;

import com.example.footballManager.team.dto.TeamDto;
import com.example.footballManager.team.model.Team;
import com.example.footballManager.team.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping
    public ResponseEntity<List<Team>> getAll() {
        return ResponseEntity.ok(teamService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Team> create(@RequestBody TeamDto teamDto) {
        return new ResponseEntity<>(teamService.save(teamDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> update(@PathVariable Long id, @RequestBody TeamDto teamDto) {
        return ResponseEntity.ok(teamService.updateById(id, teamDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        teamService.deleteById(id);

        return ResponseEntity.ok("Deleting operation was done successfully");
    }

}
