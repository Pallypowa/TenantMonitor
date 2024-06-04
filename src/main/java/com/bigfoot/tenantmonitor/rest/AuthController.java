package com.bigfoot.tenantmonitor.rest;

import com.bigfoot.tenantmonitor.dto.JwtDTO;
import com.bigfoot.tenantmonitor.dto.LoginDTO;
import com.bigfoot.tenantmonitor.dto.RegistrationDTO;
import com.bigfoot.tenantmonitor.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AuthController.API_ENDPOINT)
public class AuthController {
    public static final String API_ENDPOINT = "/api/v1";
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDTO> login(@RequestBody LoginDTO loginDTO){
        try {
            String jwt = authService.login(loginDTO);
            JwtDTO jwtDTO = new JwtDTO();
            jwtDTO.setAccessToken(jwt);
            jwtDTO.setTokenType("Bearer");
            jwtDTO.setExpiresIn(31600);
            return ResponseEntity.ok(jwtDTO);
        }catch (RuntimeException ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegistrationDTO registrationDTO){
        authService.register(registrationDTO);
        return ResponseEntity.noContent().build();
    }
}
