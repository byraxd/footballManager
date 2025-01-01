package com.example.footballManager.exception.controller;

import com.example.footballManager.exception.model.PlayerNotFoundException;
import com.example.footballManager.exception.model.TeamNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<String> playerNotFoundExceptionHandler(PlayerNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<String> teamNotFoundExceptionHandler(TeamNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
