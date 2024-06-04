package com.bigfoot.tenantmonitor.service;

import com.bigfoot.tenantmonitor.dto.LoginDTO;
import com.bigfoot.tenantmonitor.dto.RegistrationDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {
    private final List<RegistrationDTO> registeredUsers = new ArrayList<>();

    public void register(RegistrationDTO registrationDTO){
        registeredUsers.add(registrationDTO);
    }

    public String login(LoginDTO loginDTO){
        if(registeredUsers
                .stream()
                .anyMatch( user ->
                        user.getUserName().equals(loginDTO.getUserName()) && user.getPassword().equals(loginDTO.getPassword()))){
            return "SUPER_SECRET_JWT";
        }
        throw new RuntimeException("Login failed");
    }
}
