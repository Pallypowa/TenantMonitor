package com.bigfoot.tenantmonitor.repository;

import com.bigfoot.tenantmonitor.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OwnerRepository extends JpaRepository<Owner, UUID> {
    Optional<Owner> findByUserNameOrEmail(String userName, String email);
    Optional<Owner> findByUserName(String userName);

}
