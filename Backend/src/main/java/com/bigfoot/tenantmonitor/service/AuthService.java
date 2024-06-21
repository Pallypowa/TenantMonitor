package com.bigfoot.tenantmonitor.service;

import com.bigfoot.tenantmonitor.dto.AccessTokenDTO;
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
    @Value("${application.security.jwt.expiration-time}")
    private Integer expiresIn;

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
    public AccessTokenDTO login(LoginDTO loginDTO){
        Optional<User> user = ownerRepository.findByUserName(loginDTO.getUserName());

        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist!");
        }

        if(passwordEncoder.matches(loginDTO.getPassword(), user.get().getPassword())){
            String accessToken = jwtService.generateToken(user.get());
             return AccessTokenDTO.builder().accessToken(accessToken).tokenType("Bearer").expiresIn(expiresIn).build();
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password!");
    }
}
