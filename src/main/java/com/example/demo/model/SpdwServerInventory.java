package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "spdw_server_inventory")
public class SpdwServerInventory {

    @Id
    private String hostname;
    private String business_line_group_company;
    private String business_line;
    private String application;
    private String application_instance_environment;
    private String os_name;
    private String server_type;
    private String lifecycle;
    private String lifecycle_sub_status;
    private Integer application_id;
    private String application_bsh;
    private String region;
    private String country;
    private String city;
    private String building;
}
