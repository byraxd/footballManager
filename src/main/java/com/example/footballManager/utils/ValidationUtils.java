package com.example.footballManager.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationUtils {

    public static void validateId(Long id) {
        log.info("Validating id: {}", id);

        if(id == null || id <= 0){
            log.error("Invalid id: {}", id);
            throw new IllegalArgumentException("Invalid id, id should be greater than 0 and contain not-null value");
        }
    }

    public static void validateDto(Object objectDto) {
        log.info("Validating DTO: {}", objectDto);

        if(objectDto == null){
            log.error("Invalid objectDto");
            throw new IllegalArgumentException("Invalid playerDto");
        }
    }
}
