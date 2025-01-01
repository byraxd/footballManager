package com.example.footballManager.player.controller;

import com.example.footballManager.player.dto.PlayerDto;
import com.example.footballManager.player.model.Player;
import com.example.footballManager.player.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<Player>> getAll() {
        return ResponseEntity.ok(playerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getById(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Player> create(@RequestBody PlayerDto playerDto) {
        return new ResponseEntity<>(playerService.save(playerDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Player> update(@PathVariable Long id, @RequestBody PlayerDto playerDto) {
        return ResponseEntity.ok(playerService.updateById(id, playerDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        playerService.deleteById(id);

        return ResponseEntity.ok("Deleting operation was done successfully");
    }

}
