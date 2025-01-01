package com.example.footballManager.transfer.service.impl;

import com.example.footballManager.exception.model.NotEnoughMoneyException;
import com.example.footballManager.exception.model.PlayerNotFoundException;
import com.example.footballManager.exception.model.TeamNotFoundException;
import com.example.footballManager.exception.model.TransferNotFoundException;
import com.example.footballManager.player.model.Player;
import com.example.footballManager.player.repository.PlayerRepository;
import com.example.footballManager.team.model.Team;
import com.example.footballManager.team.repository.TeamRepository;
import com.example.footballManager.transfer.dto.TransferDto;
import com.example.footballManager.transfer.model.Transfer;
import com.example.footballManager.transfer.model.TransferStatus;
import com.example.footballManager.transfer.repository.TransferRepository;
import com.example.footballManager.transfer.service.TransferService;
import com.example.footballManager.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public List<Transfer> getAll() {
        log.info("Fetching list of all transfers");

        List<Transfer> transfers = transferRepository.findAll();
        log.info("Fetched list of all transfers: {}", transfers);

        return transfers;
    }

    @Override
    public Transfer getById(Long id) {
        log.info("Fetching transfer by id: {}", id);

        ValidationUtils.validateId(id);
        Transfer transfer = findTransferById(id);

        log.info("Fetched transfer: {}", transfer);
        return transfer;
    }

    @Override
    @Transactional
    public Transfer save(TransferDto transferDto) {
        log.info("Saving transfer: {}", transferDto);

        ValidationUtils.validateDto(transferDto);

        Player player = findPlayerById(transferDto.playerId());
        Team oldPlayerTeam = findTeamById(transferDto.fromTeamId());
        Team newPlayerTeam = findTeamById(transferDto.toTeamId());

        double playerCost = (player.getYearsOfExperience() * 100000)/player.getAge();
        Double transferCost = playerCost * (1 + oldPlayerTeam.getCommissionForPlayer());

        Transfer transfer = Transfer
                .builder()
                .status(TransferStatus.IN_PROGRESS)
                .fromTeam(oldPlayerTeam)
                .toTeam(newPlayerTeam)
                .player(player)
                .transferCost(transferCost)
                .build();

        transferRepository.save(transfer);
        log.info("Saved transfer: {}", transfer);

        return transfer;
    }

    @Override
    @Transactional
    public Transfer updateById(Long id, TransferDto transferDto) {
        log.info("Updating transfer: {}", transferDto);

        ValidationUtils.validateDto(transferDto);

        ValidationUtils.validateId(id);
        Transfer transferForUpdate = findTransferById(id);

        Player player = findPlayerById(transferDto.playerId());
        Team oldPlayerTeam = findTeamById(transferDto.fromTeamId());
        Team newPlayerTeam = findTeamById(transferDto.toTeamId());

        double playerCost = (player.getYearsOfExperience() * 100000)/player.getAge();
        Double transferCost = playerCost * (1 + oldPlayerTeam.getCommissionForPlayer());

        transferForUpdate.setPlayer(player);
        transferForUpdate.setFromTeam(oldPlayerTeam);
        transferForUpdate.setToTeam(newPlayerTeam);
        transferForUpdate.setTransferCost(transferCost);

        transferRepository.save(transferForUpdate);
        log.info("Updated transfer {}, by id {}", transferForUpdate, id);

        return transferForUpdate;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Deleting transfer by id: {}", id);
        ValidationUtils.validateId(id);

        if (!transferRepository.existsById(id)) {
            log.error("Transfer not exists by id: {}", id);
            throw new TransferNotFoundException("Transfer with following id: " + id + " not found");
        }

        transferRepository.deleteById(id);
        log.info("Deleted transfer: {}", id);
    }

    @Override
    @Transactional
    public Transfer payForTransfer(Long transferId) {
        log.info("Paying for transfer with id: {}", transferId);

        ValidationUtils.validateId(transferId);

        Transfer transfer = findTransferById(transferId);

        Player player = findPlayerById(transfer.getPlayer().getId());

        Team oldPlayerTeam = findTeamById(transfer.getFromTeam().getId());
        Team newPlayerTeam = findTeamById(transfer.getToTeam().getId());

        if(newPlayerTeam.getAmountOfMoney() < transfer.getTransferCost()) {
            log.error("Team: {}, not have enough money for transfer {}", newPlayerTeam, transfer);
            throw new NotEnoughMoneyException("Team not have enough money for transfer");
        }

        newPlayerTeam.setAmountOfMoney(newPlayerTeam.getAmountOfMoney() - transfer.getTransferCost());
        oldPlayerTeam.setAmountOfMoney(oldPlayerTeam.getAmountOfMoney() + transfer.getTransferCost());

        oldPlayerTeam.getPlayers().remove(player);
        newPlayerTeam.getPlayers().add(player);

        player.setTeam(newPlayerTeam);

        transfer.setStatus(TransferStatus.COMPLETED);

        playerRepository.save(player);

        teamRepository.save(oldPlayerTeam);
        teamRepository.save(newPlayerTeam);

        transferRepository.save(transfer);
        log.info("Successfully payed for the transfer {}", transfer);

        return transfer;
    }

    private Player findPlayerById(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> {
            log.error("No player found");
            return new PlayerNotFoundException("Player with following id: " + id + " not found");
        });
    }

    private Team findTeamById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> {
            log.error("No team found");
            return new TeamNotFoundException("Team with following id: " + id + " not found");
        });
    }

    private Transfer findTransferById(Long id) {
        return transferRepository.findById(id).orElseThrow(() -> {
            log.error("No transfer found");
            return new TransferNotFoundException("Transfer with following id: " + id + " not found");
        });
    }
}
