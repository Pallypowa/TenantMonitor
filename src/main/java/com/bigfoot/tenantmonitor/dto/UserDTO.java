package com.bigfoot.tenantmonitor.dto;

import lombok.Builder;

@Builder
public record UserDTO(String userName, String email, String firstName, String lastName, String userType) {
}
