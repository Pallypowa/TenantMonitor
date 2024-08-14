package com.bigfoot.tenantmonitor.rest;

import com.bigfoot.tenantmonitor.dto.AccessTokenDTO;
import com.bigfoot.tenantmonitor.dto.TokenDTO;
import com.bigfoot.tenantmonitor.dto.LoginDTO;
import com.bigfoot.tenantmonitor.dto.RegistrationDTO;
import com.bigfoot.tenantmonitor.service.AuthService;
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
        try {
            TokenDTO accessToken = authService.login(loginDTO);
            return ResponseEntity.ok(accessToken);
        }catch (RuntimeException ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegistrationDTO registrationDTO){
        authService.register(registrationDTO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh/{refreshToken}")
    public ResponseEntity<AccessTokenDTO> refresh(@PathVariable String refreshToken){
        AccessTokenDTO accessToken = authService.refresh(refreshToken);
        return ResponseEntity.ok(accessToken);
    }
}
