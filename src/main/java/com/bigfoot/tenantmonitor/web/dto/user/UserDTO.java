package com.bigfoot.tenantmonitor.web.dto.user;

import com.bigfoot.tenantmonitor.domain.model.UserType;

import lombok.Builder;

@Builder
public record UserDTO(String userName, String email, String firstName, String lastName, String userType) {
}
