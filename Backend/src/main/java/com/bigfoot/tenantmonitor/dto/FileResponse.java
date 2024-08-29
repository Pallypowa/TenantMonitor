package com.bigfoot.tenantmonitor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.InputStreamResource;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class FileResponse {
    String fileName;
    String contentType;
    Long fileSize;
    InputStreamResource stream;
}
