package com.bigfoot.tenantmonitor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TransHistoryDTO {
    private UUID id;
    private UUID propertyId;
    private UUID tenantId;
    private int amount;
    private LocalDateTime createdAt;
}
