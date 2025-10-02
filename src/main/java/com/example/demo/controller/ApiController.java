package com.example.demo.controller;

import com.example.demo.dto.ApplicationOwnershipDTO;
import com.example.demo.dto.JobSearchResultDTO;
import com.example.demo.dto.DeploymentSearchResultDTO;
import com.example.demo.model.Application;
import com.example.demo.model.OfflineExecutionJob;
import com.example.demo.service.ApiService;
import com.example.demo.util.CsvUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/applications")
    public ResponseEntity<?> searchApplications(@RequestParam(required = false) String appName,
                                                @RequestParam(required = false) String businessApplicationId,
                                                @RequestParam(required = false) String format,
                                                Pageable pageable) throws Exception {

        if (format != null) {
            List<Application> results = apiService.searchApplicationsUnpaginated(appName, businessApplicationId);

            if ("csv".equalsIgnoreCase(format)) {
                String csv = CsvUtil.convertToCsv(results);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType("text/csv"));
                headers.setContentDispositionFormData("attachment", "applications.csv");
                return ResponseEntity.ok()
                    .headers(headers)
                    .body(csv);
            } else if ("json".equalsIgnoreCase(format)) {
                String json = objectMapper.writeValueAsString(results);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setContentDispositionFormData("attachment", "applications.json");
                return ResponseEntity.ok()
                    .headers(headers)
                    .body(json);
            }
        }

        Page<Application> page = apiService.searchApplications(appName, businessApplicationId, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/jobs")
    public ResponseEntity<?> searchOfflineExecutionJobs(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) String processName,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String releaseName,
            @RequestParam(required = false) String envName,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String applicationName,
            @RequestParam(required = false) String format,
            Pageable pageable) throws Exception {

        if (format != null) {
            List<OfflineExecutionJob> results = apiService.searchOfflineExecutionJobsUnpaginated(
                startTime, processName, title, releaseName, envName, categoryName, applicationName
            );

            if ("csv".equalsIgnoreCase(format)) {
                String csv = CsvUtil.convertToCsv(results);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType("text/csv"));
                headers.setContentDispositionFormData("attachment", "jobs.csv");
                return ResponseEntity.ok()
                    .headers(headers)
                    .body(csv);
            } else if ("json".equalsIgnoreCase(format)) {
                String json = objectMapper.writeValueAsString(results);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setContentDispositionFormData("attachment", "jobs.json");
                return ResponseEntity.ok()
                    .headers(headers)
                    .body(json);
            }
        }

        Page<OfflineExecutionJob> page = apiService.searchOfflineExecutionJobs(
            startTime, processName, title, releaseName, envName, categoryName, applicationName, pageable
        );
        return ResponseEntity.ok(page);
    }

    @GetMapping("/jobs/by-application")
    public Page<OfflineExecutionJob> searchJobsByApplication(@RequestParam String appName,
                                                             Pageable pageable) {
        return apiService.searchJobsByApplication(appName, pageable);
    }
    
    @GetMapping("/jobs/detailed")
    public ResponseEntity<?> searchJobsWithDetails(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) String processName,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String releaseName,
            @RequestParam(required = false) String envName,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String appName,
            @RequestParam(required = false) String hostname,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String format,
            Pageable pageable) throws Exception {

        if (format != null) {
            List<JobSearchResultDTO> results = apiService.searchJobsWithDetailsUnpaginated(
                startTime, processName, title, releaseName, envName, categoryName, appName, hostname, region
            );

            if ("csv".equalsIgnoreCase(format)) {
                String csv = CsvUtil.convertToCsv(results);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType("text/csv"));
                headers.setContentDispositionFormData("attachment", "jobs-detailed.csv");
                return ResponseEntity.ok()
                    .headers(headers)
                    .body(csv);
            } else if ("json".equalsIgnoreCase(format)) {
                String json = objectMapper.writeValueAsString(results);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setContentDispositionFormData("attachment", "jobs-detailed.json");
                return ResponseEntity.ok()
                    .headers(headers)
                    .body(json);
            }
        }

        Page<JobSearchResultDTO> page = apiService.searchJobsWithDetails(
            startTime, processName, title, releaseName, envName, categoryName, appName, hostname, region, pageable
        );
        return ResponseEntity.ok(page);
    }
    
    @GetMapping("/applications/ownership")
    public ResponseEntity<?> searchApplicationsWithOwnership(
            @RequestParam(required = false) String appName,
            @RequestParam(required = false) String businessAppId,
            @RequestParam(required = false) String applicationType,
            @RequestParam(required = false) String productOwner,
            @RequestParam(required = false) String systemArchitect,
            @RequestParam(required = false) String operationalStatus,
            @RequestParam(required = false) String architectureType,
            @RequestParam(required = false) String businessApplicationName,
            @RequestParam(required = false) String active,
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
            @RequestParam(required = false) String format,
            Pageable pageable) throws Exception {

        if (format != null) {
            List<ApplicationOwnershipDTO> results = apiService.searchApplicationsWithOwnershipUnpaginated(
                appName, businessAppId, applicationType, productOwner, systemArchitect,
                operationalStatus, architectureType, businessApplicationName, active,
                owningTransactionCycle, owningTransactionCycleId, resilienceCategory, installType,
                applicationParent, applicationParentCorrelationId, housePosition, businessApplicationSysId,
                applicationTier, applicationProductOwnerBrid, systemArchitectBrid, architectureHosting
            );

            if ("csv".equalsIgnoreCase(format)) {
                String csv = CsvUtil.convertToCsv(results);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType("text/csv"));
                headers.setContentDispositionFormData("attachment", "applications-ownership.csv");
                return ResponseEntity.ok()
                    .headers(headers)
                    .body(csv);
            } else if ("json".equalsIgnoreCase(format)) {
                String json = objectMapper.writeValueAsString(results);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setContentDispositionFormData("attachment", "applications-ownership.json");
                return ResponseEntity.ok()
                    .headers(headers)
                    .body(json);
            }
        }

        Page<ApplicationOwnershipDTO> page = apiService.searchApplicationsWithOwnership(
            appName, businessAppId, applicationType, productOwner,
            systemArchitect, operationalStatus, architectureType,
            businessApplicationName, active, owningTransactionCycle,
            owningTransactionCycleId, resilienceCategory, installType,
            applicationParent, applicationParentCorrelationId, housePosition,
            businessApplicationSysId, applicationTier, applicationProductOwnerBrid,
            systemArchitectBrid, architectureHosting, pageable
        );
        return ResponseEntity.ok(page);
    }

    @GetMapping("/deployments")
    public ResponseEntity<?> searchDeployments(
            @RequestParam(required = false) String appName,
            @RequestParam(required = false) String envName,
            @RequestParam(required = false) String releaseVersion,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) String owningTransactionCycle,
            @RequestParam(required = false) String owningTransactionCycleId,
            @RequestParam(required = false) String businessAppId,
            @RequestParam(required = false) String format,
            Pageable pageable) throws Exception {

        if (format != null) {
            List<DeploymentSearchResultDTO> results = apiService.searchDeploymentsUnpaginated(
                appName, envName, releaseVersion, startTime, owningTransactionCycle,
                owningTransactionCycleId, businessAppId
            );

            if ("csv".equalsIgnoreCase(format)) {
                String csv = CsvUtil.convertToCsv(results);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType("text/csv"));
                headers.setContentDispositionFormData("attachment", "deployments.csv");
                return ResponseEntity.ok()
                    .headers(headers)
                    .body(csv);
            } else if ("json".equalsIgnoreCase(format)) {
                String json = objectMapper.writeValueAsString(results);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setContentDispositionFormData("attachment", "deployments.json");
                return ResponseEntity.ok()
                    .headers(headers)
                    .body(json);
            }
        }

        Page<DeploymentSearchResultDTO> page = apiService.searchDeployments(
            appName, envName, releaseVersion, startTime, owningTransactionCycle,
            owningTransactionCycleId, businessAppId, pageable
        );
        return ResponseEntity.ok(page);
    }
}
