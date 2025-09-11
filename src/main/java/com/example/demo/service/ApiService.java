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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private OfflineExecutionJobRepository offlineExecutionJobRepository;

    public Page<Application> searchApplications(String appName, String businessApplicationId, Pageable pageable) {
        Specification<Application> spec = Specification.where(null);

        if (appName != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("app_name"), "%" + appName + "%"));
        }
        if (businessApplicationId != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("app_desc"), "%" + businessApplicationId + "%"));
        }

        return applicationRepository.findAll(spec, pageable);
    }

    public Page<OfflineExecutionJob> searchOfflineExecutionJobs(LocalDateTime startTime, String processName, String title, String releaseName, String envName, String categoryName, String applicationName, Pageable pageable) {
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
        if (applicationName != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("application_name"), "%" + applicationName + "%"));
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
                                                                         String active, String owningTransactionCycle,
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
        dto.setUuid(convertToString(row[1]));
        dto.setApp_name(convertToString(row[2]));
        dto.setBusiness_app_id(convertToString(row[3]));
        dto.setVersion(convertToString(row[4]));
        dto.setParams_folder_id(row[5] != null ? ((Number) row[5]).intValue() : null);
        dto.setIs_express((Boolean) row[6]);
        dto.setIssharedlibrary((Boolean) row[7]);
        dto.setDelete_status(convertToString(row[8]));
        
        // Business Application (Ownership) fields
        dto.setBusiness_application_name(convertToString(row[9]));
        dto.setCorrelation_id(convertToString(row[10]));
        dto.setActive(convertToString(row[11]));
        dto.setOwning_transaction_cycle(convertToString(row[12]));
        dto.setOwning_transaction_cycle_id(convertToString(row[13]));
        dto.setResilience_category(convertToString(row[14]));
        dto.setOperational_status(convertToString(row[15]));
        dto.setApplication_type(convertToString(row[16]));
        dto.setArchitecture_type(convertToString(row[17]));
        dto.setInstall_type(convertToString(row[18]));
        dto.setApplication_parent(convertToString(row[19]));
        dto.setApplication_parent_correlation_id(convertToString(row[20]));
        dto.setHouse_position(convertToString(row[21]));
        dto.setBusiness_application_sys_id(convertToString(row[22]));
        dto.setApplication_tier(convertToString(row[23]));
        dto.setApplication_product_owner(convertToString(row[24]));
        dto.setSystem_architect(convertToString(row[25]));
        dto.setApplication_product_owner_brid(convertToString(row[26]));
        dto.setSystem_architect_brid(convertToString(row[27]));
        dto.setArchitecture_hosting(convertToString(row[28]));
        
        return dto;
    }
    
    private JobSearchResultDTO mapToJobSearchResultDTO(Object[] row) {
        JobSearchResultDTO dto = new JobSearchResultDTO();
        
        // OfflineExecutionJob fields
        dto.setId(row[0] != null ? ((Number) row[0]).longValue() : null);
        dto.setCreation_time(convertToLocalDateTime(row[1]));
        dto.setStart_time(convertToLocalDateTime(row[2]));
        dto.setEnd_time(convertToLocalDateTime(row[3]));
        dto.setLast_distribution_time(convertToLocalDateTime(row[4]));
        dto.setFlow_id(convertToString(row[5]));
        dto.setProcess_name(convertToString(row[6]));
        dto.setTag_name(convertToString(row[7]));
        dto.setTitle(convertToString(row[8]));
        dto.setMajor(convertToString(row[9]));
        dto.setMinor(convertToString(row[10]));
        dto.setRelease_name(convertToString(row[11]));
        dto.setRelease_version(convertToString(row[12]));
        dto.setApplication_name(convertToString(row[13]));
        dto.setArchitecture_id(row[14] != null ? ((Number) row[14]).intValue() : null);
        dto.setSystem_id(row[15] != null ? ((Number) row[15]).intValue() : null);
        dto.setEnv_name(convertToString(row[16]));
        dto.setCategory_id(row[17] != null ? ((Number) row[17]).intValue() : null);
        dto.setCategory_name(convertToString(row[18]));
        dto.setCustomer_id(row[19] != null ? ((Number) row[19]).intValue() : null);
        dto.setCreator_username(convertToString(row[20]));
        dto.setManifest(convertToString(row[21]));
        
        // Application fields
        dto.setApp_name(convertToString(row[22]));
        dto.setApp_desc(convertToString(row[23]));
        dto.setApp_version(convertToString(row[24]));
        dto.setApp_uuid(convertToString(row[25]));
        
        // Server inventory fields
        dto.setHostname(convertToString(row[26]));
        dto.setBusiness_line_group_company(convertToString(row[27]));
        dto.setBusiness_line(convertToString(row[28]));
        dto.setApplication_instance_environment(convertToString(row[29]));
        dto.setOs_name(convertToString(row[30]));
        dto.setServer_type(convertToString(row[31]));
        dto.setLifecycle(convertToString(row[32]));
        dto.setLifecycle_sub_status(convertToString(row[33]));
        dto.setRegion(convertToString(row[34]));
        dto.setCountry(convertToString(row[35]));
        dto.setCity(convertToString(row[36]));
        dto.setBuilding(convertToString(row[37]));
        
        return dto;
    }
    
    private LocalDateTime convertToLocalDateTime(Object timestamp) {
        if (timestamp == null) {
            return null;
        }
        
        if (timestamp instanceof java.sql.Timestamp) {
            return ((java.sql.Timestamp) timestamp).toLocalDateTime();
        } else if (timestamp instanceof Instant) {
            return ((Instant) timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
        } else if (timestamp instanceof LocalDateTime) {
            return (LocalDateTime) timestamp;
        } else {
            throw new IllegalArgumentException("Unsupported timestamp type: " + timestamp.getClass());
        }
    }
    
    private LocalDate convertToLocalDate(Object date) {
        if (date == null) {
            return null;
        }
        
        if (date instanceof java.sql.Date) {
            return ((java.sql.Date) date).toLocalDate();
        } else if (date instanceof LocalDate) {
            return (LocalDate) date;
        } else {
            throw new IllegalArgumentException("Unsupported date type: " + date.getClass());
        }
    }
    
    private String convertToString(Object obj) {
        if (obj == null) {
            return null;
        }
        
        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof Number) {
            return obj.toString();
        } else {
            return obj.toString();
        }
    }
    
    private Integer convertToInteger(Object obj) {
        if (obj == null) {
            return null;
        }
        
        if (obj instanceof Integer) {
            return (Integer) obj;
        } else if (obj instanceof Number) {
            return ((Number) obj).intValue();
        } else if (obj instanceof Boolean) {
            return ((Boolean) obj) ? 1 : 0;
        } else {
            return Integer.valueOf(obj.toString());
        }
    }
}
