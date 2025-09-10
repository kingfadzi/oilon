package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "offline_execution_jobs")
public class OfflineExecutionJob {

    @Id
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
    private Long calculation_time;

    @ManyToOne
    @JoinColumn(name = "application_id", insertable=false, updatable=false)
    private Application application;

    private String application_name;
    private Integer architecture_id;
    private Integer system_id;
    private String env_name;
    private Integer category_id;
    private String category_name;
    private Integer customer_id;
    private String creator_username;
    private String manifest;
    private String parameterroot;
}
