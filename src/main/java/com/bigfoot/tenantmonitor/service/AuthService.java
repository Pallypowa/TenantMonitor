package com.bigfoot.tenantmonitor.service;

import com.bigfoot.tenantmonitor.dto.LoginDTO;
import com.bigfoot.tenantmonitor.dto.RegistrationDTO;
import com.bigfoot.tenantmonitor.model.Owner;
import com.bigfoot.tenantmonitor.repository.OwnerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AuthService {
    private final OwnerRepository ownerRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(OwnerRepository ownerRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.ownerRepository = ownerRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }
    public void register(RegistrationDTO registrationDTO){
        Optional<Owner> user = ownerRepository.findByUserNameOrEmail(registrationDTO.getUserName(), registrationDTO.getEmail());
        if(user.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists!");
        }
        Owner owner = modelMapper.map(registrationDTO, Owner.class);
        owner.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        ownerRepository.save(owner);

    }
    public String login(LoginDTO loginDTO){
        Optional<Owner> user = ownerRepository.findByUserName(loginDTO.getUserName());

        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist!");
        }

        if(passwordEncoder.matches(loginDTO.getPassword(), user.get().getPassword())){
            return "SUPER_SECRET_JWT";
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password!");
    }
}
