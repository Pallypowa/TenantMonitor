package com.bigfoot.tenantmonitor.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<Property> properties;
}
