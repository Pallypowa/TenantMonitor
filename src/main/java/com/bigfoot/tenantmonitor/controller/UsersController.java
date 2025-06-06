package com.bigfoot.tenantmonitor.controller;

import com.bigfoot.tenantmonitor.dto.UserDTO;
import com.bigfoot.tenantmonitor.jwt.UserContext;
import com.bigfoot.tenantmonitor.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
@CrossOrigin(origins = "*", allowedHeaders = "*") //TODO
public class UsersController {

    private final AuthService authService;

    @GetMapping()
    public UserDTO getUserInfo() {
        return authService.getUserInfo(UserContext.getUserName());
    }
}
