package com.example.demo.controller;

import com.example.demo.model.Application;
import com.example.demo.model.OfflineExecutionJob;
import com.example.demo.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

    @GetMapping("/applications/search")
    public List<Application> searchApplications(@RequestParam(required = false) String appName,
                                                @RequestParam(required = false) String appDesc,
                                                @RequestParam(required = false) Long id) {
        return apiService.searchApplications(appName, appDesc, id);
    }

    @GetMapping("/offline-execution-jobs/search")
    public List<OfflineExecutionJob> searchOfflineExecutionJobs(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) String processName,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String releaseName,
            @RequestParam(required = false) String envName,
            @RequestParam(required = false) String categoryName) {
        return apiService.searchOfflineExecutionJobs(startTime, processName, title, releaseName, envName, categoryName);
    }

    @GetMapping("/jobs/search-by-app")
    public List<OfflineExecutionJob> searchJobsByApplication(@RequestParam String appName) {
        return apiService.searchJobsByApplication(appName);
    }
}
