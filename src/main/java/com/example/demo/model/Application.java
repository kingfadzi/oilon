package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "applications")
public class Application {

    @Id
    private Long Id;
    private UUID uuid;
    private String app_name;
    private String app_desc;
    private String version;
    private Integer params_folder_id;
    private Boolean is_express;
    private Boolean issharedlibrary;
    private String delete_status;
}
