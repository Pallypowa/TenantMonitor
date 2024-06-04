package com.bigfoot.tenantmonitor.service;

import com.bigfoot.tenantmonitor.dto.TenantDTO;
import com.bigfoot.tenantmonitor.dto.TransHistoryDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransHistoryService {
    private final List<TransHistoryDTO> transHistories = new ArrayList<>();

    public List<TransHistoryDTO> fetchAllTransHistories(){
        return transHistories;
    }

    public TransHistoryDTO fetchTransHistoryById(UUID transHistoryId){
        return transHistories
                .stream()
                .filter(thistory -> thistory.getId().equals(transHistoryId))
                .findFirst()
                .orElseThrow( () -> new RuntimeException("Transaction History not found"));
    }

    public void createTransHistory(TransHistoryDTO transHistory){
        transHistory.setId(UUID.randomUUID());
        transHistories.add(transHistory);
    }


    public TransHistoryDTO updateTransHistory(UUID transHistoryId, TransHistoryDTO transHistory) {
        for (int i = 0; i < transHistories.size(); i++) {
            if (transHistories.get(i).getId().equals(transHistoryId)) {
                transHistories.set(i, transHistory);
                return transHistory;
            }
        }
        throw new RuntimeException("Transaction history does not exist!");
    }

    public void deleteTransHistory(UUID transHistoryId) {
        transHistories.removeIf(thistory -> thistory.getId().equals(transHistoryId));
    }
}
