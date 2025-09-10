package com.example.demo.service;

import com.example.demo.model.Application;
import com.example.demo.model.OfflineExecutionJob;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.OfflineExecutionJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApiService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private OfflineExecutionJobRepository offlineExecutionJobRepository;

    public List<Application> searchApplications(String appName, String appDesc, Long id) {
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

        return applicationRepository.findAll(spec);
    }

    public List<OfflineExecutionJob> searchOfflineExecutionJobs(LocalDateTime startTime, String processName, String title, String releaseName, String envName, String categoryName) {
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

        return offlineExecutionJobRepository.findAll(spec);
    }

    public List<OfflineExecutionJob> searchJobsByApplication(String appName) {
        Specification<OfflineExecutionJob> spec = (root, query, criteriaBuilder) -> {
            if (appName != null) {
                return criteriaBuilder.like(root.get("application").get("app_name"), "%" + appName + "%");
            }
            return criteriaBuilder.conjunction();
        };
        return offlineExecutionJobRepository.findAll(spec);
    }
}
