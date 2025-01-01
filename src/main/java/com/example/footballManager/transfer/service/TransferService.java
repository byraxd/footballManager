package com.example.footballManager.transfer.service;

import com.example.footballManager.transfer.dto.TransferDto;
import com.example.footballManager.transfer.model.Transfer;

import java.util.List;

public interface TransferService {
    List<Transfer> getAll();

    Transfer getById(Long id);

    Transfer save(TransferDto transferDto);

    Transfer updateById(Long id, TransferDto transferDto);

    void deleteById(Long id);

    Transfer payForTransfer(Long transferId);
}
