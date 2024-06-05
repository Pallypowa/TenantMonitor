package com.bigfoot.tenantmonitor.service;

import com.bigfoot.tenantmonitor.dto.TenantDTO;
import com.bigfoot.tenantmonitor.dto.TransHistoryDTO;
import com.bigfoot.tenantmonitor.model.Tenant;
import com.bigfoot.tenantmonitor.model.TransactionHistory;
import com.bigfoot.tenantmonitor.repository.TransHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransHistoryService {


    private final TransHistoryRepository transHistoryRepository;
    private final ModelMapper modelMapper;

    public TransHistoryService(TransHistoryRepository transHistoryRepository, ModelMapper modelMapper) {
        this.transHistoryRepository = transHistoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<TransHistoryDTO> fetchAllTransHistories(){

        return transHistoryRepository
                .findAll()
                .stream()
                .map(thistory -> modelMapper.map(thistory, TransHistoryDTO.class))
                .toList();
    }

    public TransHistoryDTO fetchTransHistoryById(UUID transHistoryId){
        return modelMapper.map(transHistoryRepository
                .findById(transHistoryId)
                .orElseThrow(() -> new RuntimeException("Tenant has not been found!")), TransHistoryDTO.class);
    }

    public void createTransHistory(TransHistoryDTO transHistory){
        transHistoryRepository.save(modelMapper.map(transHistory, TransactionHistory.class));
    }


    public TransHistoryDTO updateTransHistory(UUID transHistoryId, TransHistoryDTO updatedTransHistory) {
        Optional<TransactionHistory> existingTHistory = transHistoryRepository.findById(transHistoryId);

        if(existingTHistory.isEmpty()){
            throw new RuntimeException("Transaction history has not been found!");
        }

        updatedTransHistory.setId(transHistoryId);
        transHistoryRepository.save(modelMapper.map(updatedTransHistory, TransactionHistory.class));
        return updatedTransHistory;
    }

    public void deleteTransHistory(UUID transHistoryId) {
        Optional<TransactionHistory> existingTHistory = transHistoryRepository.findById(transHistoryId);

        if(existingTHistory.isEmpty()){
            throw new RuntimeException("Transaction history has not been found!");
        }

        transHistoryRepository.delete(modelMapper.map(existingTHistory, TransactionHistory.class));
    }
}
