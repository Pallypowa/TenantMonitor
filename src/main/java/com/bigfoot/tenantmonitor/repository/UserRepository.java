package com.bigfoot.tenantmonitor.repository;

import com.bigfoot.tenantmonitor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUserNameOrEmail(String userName, String email);
    Optional<User> findByUserName(String userName);

}
