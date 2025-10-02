package com.example.demo.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeploymentSearchResultDTO {

    // OfflineExecutionJob (deployment) fields
    private Long id;
    private LocalDateTime creation_time;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private String flow_id;
    private String process_name;
    private String title;
    private String release_name;
    private String release_version;
    private String env_name;
    private String application_name;
    private String creator_username;

    // Application fields (nullable - for deployments without apps)
    private String app_name;
    private String app_desc;
    private String app_version;
    private String app_uuid;

    // Business Application (Ownership) fields (nullable)
    private String business_application_name;
    private String correlation_id;
    private String owning_transaction_cycle;
    private String owning_transaction_cycle_id;
    private String business_app_id;
}
