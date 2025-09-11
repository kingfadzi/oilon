package com.example.demo.controller;

import com.example.demo.dto.ApplicationOwnershipDTO;
import com.example.demo.dto.JobSearchResultDTO;
import com.example.demo.model.Application;
import com.example.demo.model.OfflineExecutionJob;
import com.example.demo.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @GetMapping("/applications")
    public Page<Application> searchApplications(@RequestParam(required = false) String appName,
                                                @RequestParam(required = false) String businessApplicationId,
                                                Pageable pageable) {
        return apiService.searchApplications(appName, businessApplicationId, pageable);
    }

    @GetMapping("/jobs")
    public Page<OfflineExecutionJob> searchOfflineExecutionJobs(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) String processName,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String releaseName,
            @RequestParam(required = false) String envName,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String applicationName,
            Pageable pageable) {
        return apiService.searchOfflineExecutionJobs(startTime, processName, title, releaseName, envName, categoryName, applicationName, pageable);
    }

    @GetMapping("/jobs/by-application")
    public Page<OfflineExecutionJob> searchJobsByApplication(@RequestParam String appName,
                                                             Pageable pageable) {
        return apiService.searchJobsByApplication(appName, pageable);
    }
    
    @GetMapping("/jobs/detailed")
    public Page<JobSearchResultDTO> searchJobsWithDetails(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) String processName,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String releaseName,
            @RequestParam(required = false) String envName,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String appName,
            @RequestParam(required = false) String hostname,
            @RequestParam(required = false) String region,
            Pageable pageable) {
        return apiService.searchJobsWithDetails(startTime, processName, title, releaseName, envName, categoryName, appName, hostname, region, pageable);
    }
    
    @GetMapping("/applications/ownership")
    public Page<ApplicationOwnershipDTO> searchApplicationsWithOwnership(
            @RequestParam(required = false) String appName,
            @RequestParam(required = false) String businessAppId,
            @RequestParam(required = false) String applicationType,
            @RequestParam(required = false) String productOwner,
            @RequestParam(required = false) String systemArchitect,
            @RequestParam(required = false) String operationalStatus,
            @RequestParam(required = false) String architectureType,
            @RequestParam(required = false) String businessApplicationName,
            @RequestParam(required = false) Integer active,
            @RequestParam(required = false) String owningTransactionCycle,
            @RequestParam(required = false) String owningTransactionCycleId,
            @RequestParam(required = false) String resilienceCategory,
            @RequestParam(required = false) String installType,
            @RequestParam(required = false) String applicationParent,
            @RequestParam(required = false) String applicationParentCorrelationId,
            @RequestParam(required = false) String housePosition,
            @RequestParam(required = false) String businessApplicationSysId,
            @RequestParam(required = false) String applicationTier,
            @RequestParam(required = false) String applicationProductOwnerBrid,
            @RequestParam(required = false) String systemArchitectBrid,
            @RequestParam(required = false) String architectureHosting,
            Pageable pageable) {
        return apiService.searchApplicationsWithOwnership(appName, businessAppId, applicationType, productOwner, 
                                                         systemArchitect, operationalStatus, architectureType, 
                                                         businessApplicationName, active, owningTransactionCycle,
                                                         owningTransactionCycleId, resilienceCategory, installType,
                                                         applicationParent, applicationParentCorrelationId, housePosition,
                                                         businessApplicationSysId, applicationTier, applicationProductOwnerBrid,
                                                         systemArchitectBrid, architectureHosting, pageable);
    }
}
