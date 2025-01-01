package com.example.footballManager.transfer.controller;

import com.example.footballManager.transfer.dto.TransferDto;
import com.example.footballManager.transfer.model.Transfer;
import com.example.footballManager.transfer.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @GetMapping
    public ResponseEntity<List<Transfer>> getAll(){
        return ResponseEntity.ok(transferService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transfer> getById(@PathVariable Long id){
        return ResponseEntity.ok(transferService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Transfer> create(@RequestBody TransferDto transferDto){
        return new ResponseEntity<>(transferService.save(transferDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transfer> update(@PathVariable Long id, @RequestBody TransferDto transferDto){
        return ResponseEntity.ok(transferService.updateById(id, transferDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        transferService.deleteById(id);

        return ResponseEntity.ok("Deleting operation was done successfully");
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<Transfer> payForTransfer(@PathVariable Long id){
        return ResponseEntity.ok(transferService.payForTransfer(id));
    }

}
