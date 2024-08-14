package com.bigfoot.tenantmonitor.service;

import com.bigfoot.tenantmonitor.dto.AccessTokenDTO;
import com.bigfoot.tenantmonitor.dto.TokenDTO;
import com.bigfoot.tenantmonitor.dto.LoginDTO;
import com.bigfoot.tenantmonitor.dto.RegistrationDTO;
import com.bigfoot.tenantmonitor.jwt.JwtService;
import com.bigfoot.tenantmonitor.model.User;
import com.bigfoot.tenantmonitor.model.UserType;
import com.bigfoot.tenantmonitor.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository ownerRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Value("${application.security.jwt.access_token_expiration}")
    private Integer accessTokenExpire;
    @Value("${application.security.jwt.refresh_token_expiration}")
    private Integer refreshTokenExpire;

    public AuthService(UserRepository ownerRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder, JwtService jwtService) {
        this.ownerRepository = ownerRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    public void register(RegistrationDTO registrationDTO){
        Optional<User> user = ownerRepository.findByUserNameOrEmail(registrationDTO.getUserName(), registrationDTO.getEmail());
        if(user.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists!");
        }
        User owner = modelMapper.map(registrationDTO, User.class);
        owner.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        owner.setUserType(UserType.OWNER);
        ownerRepository.save(owner);
    }
    public TokenDTO login(LoginDTO loginDTO){
        Optional<User> user = ownerRepository.findByUserName(loginDTO.getUserName());

        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist!");
        }

        if(passwordEncoder.matches(loginDTO.getPassword(), user.get().getPassword())){
            String accessToken = jwtService.generateToken(user.get(), accessTokenExpire);
            String refreshToken = jwtService.generateToken(user.get(), refreshTokenExpire);
            return TokenDTO
                    .builder()
                    .withAccessToken(accessToken)
                    .withAccessTokenExpire(accessTokenExpire)
                    .withRefreshToken(refreshToken)
                    .withRefreshTokenExpire(refreshTokenExpire)
                    .build();
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password!");
    }

    public AccessTokenDTO refresh(String refreshToken) {
        try {
            if(!jwtService.isValidToken(refreshToken)){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Optional<User> user = ownerRepository.findByUserName(jwtService.getUserName(refreshToken));

        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist!");
        }

        return AccessTokenDTO
                .builder()
                .withAccessToken(jwtService.generateToken(user.get(), accessTokenExpire))
                .withAccessTokenExpire(accessTokenExpire)
                .build();
    }
}
