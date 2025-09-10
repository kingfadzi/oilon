package com.example.demo.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobSearchResultDTO {
    
    // OfflineExecutionJob fields
    private Long id;
    private LocalDateTime creation_time;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private LocalDateTime last_distribution_time;
    private String flow_id;
    private String process_name;
    private String tag_name;
    private String title;
    private String major;
    private String minor;
    private String release_name;
    private String release_version;
    private String application_name;
    private Integer architecture_id;
    private Integer system_id;
    private String env_name;
    private Integer category_id;
    private String category_name;
    private Integer customer_id;
    private String creator_username;
    private String manifest;
    
    // Application fields
    private String app_name;
    private String app_desc;
    private String app_version;
    private String app_uuid;
    
    // Server inventory fields
    private String hostname;
    private String business_line_group_company;
    private String business_line;
    private String application_instance_environment;
    private String os_name;
    private String server_type;
    private String lifecycle;
    private String lifecycle_sub_status;
    private String region;
    private String country;
    private String city;
    private String building;
}