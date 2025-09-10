package com.example.demo.service;

import com.example.demo.dto.ApplicationOwnershipDTO;
import com.example.demo.dto.JobSearchResultDTO;
import com.example.demo.model.Application;
import com.example.demo.model.OfflineExecutionJob;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.OfflineExecutionJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private OfflineExecutionJobRepository offlineExecutionJobRepository;

    public Page<Application> searchApplications(String appName, String appDesc, Long id, Pageable pageable) {
        Specification<Application> spec = Specification.where(null);

        if (appName != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("app_name"), "%" + appName + "%"));
        }
        if (appDesc != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("app_desc"), "%" + appDesc + "%"));
        }
        if (id != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("Id"), id));
        }

        return applicationRepository.findAll(spec, pageable);
    }

    public Page<OfflineExecutionJob> searchOfflineExecutionJobs(LocalDateTime startTime, String processName, String title, String releaseName, String envName, String categoryName, Pageable pageable) {
        Specification<OfflineExecutionJob> spec = Specification.where(null);

        if (startTime != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("start_time"), startTime));
        }
        if (processName != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("process_name"), "%" + processName + "%"));
        }
        if (title != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + title + "%"));
        }
        if (releaseName != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("release_name"), "%" + releaseName + "%"));
        }
        if (envName != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("env_name"), "%" + envName + "%"));
        }
        if (categoryName != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("category_name"), "%" + categoryName + "%"));
        }

        return offlineExecutionJobRepository.findAll(spec, pageable);
    }

    public Page<OfflineExecutionJob> searchJobsByApplication(String appName, Pageable pageable) {
        Specification<OfflineExecutionJob> spec = (root, query, criteriaBuilder) -> {
            if (appName != null) {
                return criteriaBuilder.like(root.get("application").get("app_name"), "%" + appName + "%");
            }
            return criteriaBuilder.conjunction();
        };
        return offlineExecutionJobRepository.findAll(spec, pageable);
    }
    
    public Page<JobSearchResultDTO> searchJobsWithDetails(LocalDateTime startTime, String processName, String title, 
                                                          String releaseName, String envName, String categoryName,
                                                          String appName, String hostname, String region, Pageable pageable) {
        Page<Object[]> results = offlineExecutionJobRepository.searchJobsWithDetails(
            startTime, processName, title, releaseName, envName, categoryName, appName, hostname, region, pageable
        );
        
        List<JobSearchResultDTO> dtoList = results.getContent().stream()
            .map(this::mapToJobSearchResultDTO)
            .collect(Collectors.toList());
        
        return new PageImpl<>(dtoList, pageable, results.getTotalElements());
    }
    
    public Page<ApplicationOwnershipDTO> searchApplicationsWithOwnership(String appName, String businessAppId, 
                                                                         String applicationType, String productOwner,
                                                                         String systemArchitect, String operationalStatus,
                                                                         String architectureType, String businessApplicationName,
                                                                         Boolean active, String owningTransactionCycle,
                                                                         String owningTransactionCycleId, String resilienceCategory, String installType,
                                                                         String applicationParent, String applicationParentCorrelationId, 
                                                                         String housePosition, String businessApplicationSysId, String applicationTier,
                                                                         String applicationProductOwnerBrid, String systemArchitectBrid, 
                                                                         String architectureHosting, Pageable pageable) {
        Page<Object[]> results = applicationRepository.searchApplicationsWithOwnership(
            appName, businessAppId, applicationType, productOwner, systemArchitect, 
            operationalStatus, architectureType, businessApplicationName, active,
            owningTransactionCycle, owningTransactionCycleId, resilienceCategory, installType,
            applicationParent, applicationParentCorrelationId, housePosition, businessApplicationSysId,
            applicationTier, applicationProductOwnerBrid, systemArchitectBrid, architectureHosting, pageable
        );
        
        List<ApplicationOwnershipDTO> dtoList = results.getContent().stream()
            .map(this::mapToApplicationOwnershipDTO)
            .collect(Collectors.toList());
        
        return new PageImpl<>(dtoList, pageable, results.getTotalElements());
    }
    
    private ApplicationOwnershipDTO mapToApplicationOwnershipDTO(Object[] row) {
        ApplicationOwnershipDTO dto = new ApplicationOwnershipDTO();
        
        // Application fields
        dto.setId(row[0] != null ? ((Number) row[0]).longValue() : null);
        dto.setUuid((String) row[1]);
        dto.setApp_name((String) row[2]);
        dto.setBusiness_app_id((String) row[3]);
        dto.setVersion((String) row[4]);
        dto.setParams_folder_id(row[5] != null ? ((Number) row[5]).intValue() : null);
        dto.setIs_express((Boolean) row[6]);
        dto.setIssharedlibrary((Boolean) row[7]);
        dto.setDelete_status((String) row[8]);
        
        // Business Application (Ownership) fields
        dto.setBusiness_application_name((String) row[9]);
        dto.setCorrelation_id((String) row[10]);
        dto.setActive((Boolean) row[11]);
        dto.setOwning_transaction_cycle((String) row[12]);
        dto.setOwning_transaction_cycle_id((String) row[13]);
        dto.setResilience_category((String) row[14]);
        dto.setOperational_status((String) row[15]);
        dto.setApplication_type((String) row[16]);
        dto.setArchitecture_type((String) row[17]);
        dto.setInstall_type((String) row[18]);
        dto.setApplication_parent((String) row[19]);
        dto.setApplication_parent_correlation_id((String) row[20]);
        dto.setHouse_position((String) row[21]);
        dto.setCease_date(row[22] != null ? ((java.sql.Date) row[22]).toLocalDate() : null);
        dto.setBusiness_application_sys_id((String) row[23]);
        dto.setApplication_tier((String) row[24]);
        dto.setApplication_product_owner((String) row[25]);
        dto.setSystem_architect((String) row[26]);
        dto.setApplication_product_owner_brid((String) row[27]);
        dto.setSystem_architect_brid((String) row[28]);
        dto.setArchitecture_hosting((String) row[29]);
        
        return dto;
    }
    
    private JobSearchResultDTO mapToJobSearchResultDTO(Object[] row) {
        JobSearchResultDTO dto = new JobSearchResultDTO();
        
        // OfflineExecutionJob fields
        dto.setId(row[0] != null ? ((Number) row[0]).longValue() : null);
        dto.setCreation_time(row[1] != null ? ((java.sql.Timestamp) row[1]).toLocalDateTime() : null);
        dto.setStart_time(row[2] != null ? ((java.sql.Timestamp) row[2]).toLocalDateTime() : null);
        dto.setEnd_time(row[3] != null ? ((java.sql.Timestamp) row[3]).toLocalDateTime() : null);
        dto.setLast_distribution_time(row[4] != null ? ((java.sql.Timestamp) row[4]).toLocalDateTime() : null);
        dto.setFlow_id((String) row[5]);
        dto.setProcess_name((String) row[6]);
        dto.setTag_name((String) row[7]);
        dto.setTitle((String) row[8]);
        dto.setMajor((String) row[9]);
        dto.setMinor((String) row[10]);
        dto.setRelease_name((String) row[11]);
        dto.setRelease_version((String) row[12]);
        dto.setApplication_name((String) row[13]);
        dto.setArchitecture_id(row[14] != null ? ((Number) row[14]).intValue() : null);
        dto.setSystem_id(row[15] != null ? ((Number) row[15]).intValue() : null);
        dto.setEnv_name((String) row[16]);
        dto.setCategory_id(row[17] != null ? ((Number) row[17]).intValue() : null);
        dto.setCategory_name((String) row[18]);
        dto.setCustomer_id(row[19] != null ? ((Number) row[19]).intValue() : null);
        dto.setCreator_username((String) row[20]);
        dto.setManifest((String) row[21]);
        
        // Application fields
        dto.setApp_name((String) row[22]);
        dto.setApp_desc((String) row[23]);
        dto.setApp_version((String) row[24]);
        dto.setApp_uuid((String) row[25]);
        
        // Server inventory fields
        dto.setHostname((String) row[26]);
        dto.setBusiness_line_group_company((String) row[27]);
        dto.setBusiness_line((String) row[28]);
        dto.setApplication_instance_environment((String) row[29]);
        dto.setOs_name((String) row[30]);
        dto.setServer_type((String) row[31]);
        dto.setLifecycle((String) row[32]);
        dto.setLifecycle_sub_status((String) row[33]);
        dto.setRegion((String) row[34]);
        dto.setCountry((String) row[35]);
        dto.setCity((String) row[36]);
        dto.setBuilding((String) row[37]);
        
        return dto;
    }
}
