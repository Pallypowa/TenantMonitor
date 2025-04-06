package com.bigfoot.tenantmonitor.service;

import com.bigfoot.tenantmonitor.dto.*;
import com.bigfoot.tenantmonitor.exception.IncorrectLoginException;
import com.bigfoot.tenantmonitor.exception.InvalidTokenException;
import com.bigfoot.tenantmonitor.exception.UserAlreadyExistsException;
import com.bigfoot.tenantmonitor.jwt.JwtService;
import com.bigfoot.tenantmonitor.model.User;
import com.bigfoot.tenantmonitor.model.UserType;
import com.bigfoot.tenantmonitor.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailVerificationService emailVerificationService;
    @Value("${application.security.jwt.access_token_expiration}")
    private Integer accessTokenExpire;
    @Value("${application.security.jwt.refresh_token_expiration}")
    private Integer refreshTokenExpire;

    @Transactional
    public void register(RegistrationDTO registrationDTO){
        Optional<User> user = userRepository.findByUserNameOrEmail(registrationDTO.getUserName(), registrationDTO.getEmail());
        if(user.isPresent()){
            throw new UserAlreadyExistsException("User already exists!");
        }

        User owner = modelMapper.map(registrationDTO, User.class);
        owner.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        owner.setUserType(UserType.OWNER);

        final User savedOwner = userRepository.save(owner);
        emailVerificationService.sendNewVerification(savedOwner, savedOwner.getEmail());
    }

    public TokenDTO login(LoginDTO loginDTO){
        Optional<User> user = userRepository.findByUserName(loginDTO.getUserName());

        if(user.isEmpty()){
            throw new IncorrectLoginException("Username or password incorrect!");
        }

        if(!user.get().getVerified()) {
            throw new IncorrectLoginException("Username or password incorrect!"); //TODO change later to something else...
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

        throw new IncorrectLoginException("Username or password incorrect!");
    }

    public AccessTokenDTO refresh(String refreshToken) {

        if(!jwtService.isValidToken(refreshToken)){
            throw new InvalidTokenException("Invalid JWT.");
        }

        Optional<User> user = userRepository.findByUserName(jwtService.getUserName(refreshToken));

        if(user.isEmpty()){
            throw new InvalidTokenException("Invalid JWT.");
        }

        return AccessTokenDTO
                .builder()
                .withAccessToken(jwtService.generateToken(user.get(), accessTokenExpire))
                .withAccessTokenExpire(accessTokenExpire)
                .build();
    }

    public UserDTO getUserInfo(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow();
        return UserDTO.builder()
                .userName(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userType(user.getUserType().toString())
                .build();
    }
}
