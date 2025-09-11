package com.example.demo.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationOwnershipDTO {
    
    // Application fields
    private Long id;
    private String uuid;
    private String app_name;
    private String business_app_id;  // renamed from app_desc
    private String version;
    private Integer params_folder_id;
    private Boolean is_express;
    private Boolean issharedlibrary;
    private String delete_status;
    
    // Business Application (Ownership) fields
    private String business_application_name;
    private String correlation_id;
    private Integer active;
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
    private String business_application_sys_id;
    private String application_tier;
    private String application_product_owner;
    private String system_architect;
    private String application_product_owner_brid;
    private String system_architect_brid;
    private String architecture_hosting;
}