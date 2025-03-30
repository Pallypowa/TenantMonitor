package com.bigfoot.tenantmonitor.controller;

import com.bigfoot.tenantmonitor.dto.AccessTokenDTO;
import com.bigfoot.tenantmonitor.dto.TokenDTO;
import com.bigfoot.tenantmonitor.dto.LoginDTO;
import com.bigfoot.tenantmonitor.dto.RegistrationDTO;
import com.bigfoot.tenantmonitor.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AuthController.API_ENDPOINT)
@CrossOrigin(origins = "*", allowedHeaders = "*") //TODO
@RequiredArgsConstructor
public class AuthController {
    public static final String API_ENDPOINT = "/api/v1";
    private final AuthService authService;

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

    @PostMapping("/refresh/{refreshToken}") //TODO: refactor path variable to authorization header like an authenticated request. with this the refresh, and access tokens should be seperated as well.
    public ResponseEntity<AccessTokenDTO> refresh(@PathVariable String refreshToken){
        AccessTokenDTO accessToken = authService.refresh(refreshToken);
        return ResponseEntity.ok(accessToken);
    }

    @PostMapping("/verify")
    public void verify(@RequestParam final String email, @RequestParam final String verificationCode) {
        authService.verify(email, verificationCode);
    }
}
