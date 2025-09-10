package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "spdw_vwsfbusinessapplication")
public class BusinessApplication {

    @Id
    private String business_application_name;
    private String correlation_id;
    private Boolean active;
    private String owning_transaction_cycle;
    private String owning_transaction_cycle_id;
    private String resilience_category;
    private String operational_status;
    private String application_type;
    private String architecture_type;
    private String install_type;
    private String application_parent;
    private String application_parent_correlation_id;
    private String house_position;
    private LocalDate cease_date;
    private String business_application_sys_id;
    private String application_tier;
    private String application_product_owner;
    private String system_architect;
    private String application_product_owner_brid;
    private String system_architect_brid;
    private String architecture_hosting;
}