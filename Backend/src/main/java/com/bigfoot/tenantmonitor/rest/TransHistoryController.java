package com.bigfoot.tenantmonitor.rest;

import com.bigfoot.tenantmonitor.dto.TenantDTO;
import com.bigfoot.tenantmonitor.dto.TransHistoryDTO;
import com.bigfoot.tenantmonitor.service.TransHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(TransHistoryController.API_ENDPOINT)
public class TransHistoryController {
    public static final String API_ENDPOINT = "/api/v1/transhistory";
    private final TransHistoryService transHistoryService;

    public TransHistoryController(TransHistoryService transHistoryService) {
        this.transHistoryService = transHistoryService;
    }

    @GetMapping
    public ResponseEntity<List<TransHistoryDTO>> fetchAll(){
        return ResponseEntity.ok(transHistoryService.fetchAllTransHistories());
    }

    @GetMapping("/{transHistoryId}")
    public ResponseEntity<TransHistoryDTO> fetchById(@PathVariable UUID transHistoryId){
        return ResponseEntity.ok(transHistoryService.fetchTransHistoryById(transHistoryId));
    }

    @PostMapping
    public ResponseEntity<Void> createTransHistory(@RequestBody TransHistoryDTO transHistory){
        transHistoryService.createTransHistory(transHistory);
        return ResponseEntity.created(URI.create(API_ENDPOINT)).build();
    }

    @PatchMapping("/{transHistoryId}")
    public ResponseEntity<TransHistoryDTO> updateTransHistory(@PathVariable UUID transHistoryId, @RequestBody TransHistoryDTO transHistory){
        TransHistoryDTO updatedTransHistory = transHistoryService.updateTransHistory(transHistoryId, transHistory);
        return ResponseEntity.ok(updatedTransHistory);
    }

    @DeleteMapping("/{transHistoryId}")
    public ResponseEntity<Void> deleteTransHistory(@PathVariable UUID transHistoryId){
        transHistoryService.deleteTransHistory(transHistoryId);
        return ResponseEntity.noContent().build();
    }
}
