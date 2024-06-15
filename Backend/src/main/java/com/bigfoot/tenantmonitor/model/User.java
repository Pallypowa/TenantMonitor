package com.bigfoot.tenantmonitor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @OneToMany(mappedBy = "owner")
    private List<Property> properties;
}
