package com.bigfoot.tenantmonitor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class FileMapping {
    @Id
    UUID file_id;
    UUID object_id; //can be property or idk maybe the user
    String file_name;
    Long file_size; //bytes
    String file_type;
    @JsonIgnore //TODO it has issues with this field when creating the JSON, temporarily ignored
    LocalDateTime created_at;

}
