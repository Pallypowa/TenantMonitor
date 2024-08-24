package com.bigfoot.tenantmonitor.controller;

import com.bigfoot.tenantmonitor.dto.AccessTokenDTO;
import com.bigfoot.tenantmonitor.dto.TokenDTO;
import com.bigfoot.tenantmonitor.dto.LoginDTO;
import com.bigfoot.tenantmonitor.dto.RegistrationDTO;
import com.bigfoot.tenantmonitor.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AuthController.API_ENDPOINT)
public class AuthController {
    public static final String API_ENDPOINT = "/api/v1";
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO){
        TokenDTO accessToken = authService.login(loginDTO);
        return ResponseEntity.ok(accessToken);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegistrationDTO registrationDTO){
        authService.register(registrationDTO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh/{refreshToken}")
    public ResponseEntity<AccessTokenDTO> refresh(@PathVariable String refreshToken){
        AccessTokenDTO accessToken = authService.refresh(refreshToken);
        return ResponseEntity.ok(accessToken);
    }
}
