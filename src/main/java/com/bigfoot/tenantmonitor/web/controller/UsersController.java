package com.bigfoot.tenantmonitor.web.controller;

import com.bigfoot.tenantmonitor.domain.model.User;
import com.bigfoot.tenantmonitor.service.AuthService;
import com.bigfoot.tenantmonitor.web.dto.user.UserDTO;
import com.bigfoot.tenantmonitor.service.security.UserContext;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*") //TODO
public class UsersController {

    private final AuthService authService;

    @GetMapping()
    public UserDTO getUserInfo() {
        return authService.getUserInfo(UserContext.getUserName());
    }
}
