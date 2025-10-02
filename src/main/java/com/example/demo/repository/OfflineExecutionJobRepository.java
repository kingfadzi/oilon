package com.example.demo.repository;

import com.example.demo.dto.JobSearchResultDTO;
import com.example.demo.dto.DeploymentSearchResultDTO;
import com.example.demo.model.OfflineExecutionJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.QueryHint;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public interface OfflineExecutionJobRepository extends JpaRepository<OfflineExecutionJob, Long>, JpaSpecificationExecutor<OfflineExecutionJob> {
    
    @Query(value = """
        SELECT 
            oej.id,
            oej.creation_time,
            oej.start_time,
            oej.end_time,
            oej.last_distribution_time,
            oej.flow_id,
            oej.process_name,
            oej.tag_name,
            oej.title,
            oej.major,
            oej.minor,
            oej.release_name,
            oej.release_version,
            oej.application_name,
            oej.architecture_id,
            oej.system_id,
            oej.env_name,
            oej.category_id,
            oej.category_name,
            oej.customer_id,
            oej.creator_username,
            oej.manifest,
            app.app_name,
            app.app_desc,
            app.version as app_version,
            CAST(app.uuid AS varchar) as app_uuid,
            ssi.hostname,
            ssi.business_line_group_company,
            ssi.business_line,
            ssi.application_instance_environment,
            ssi.os_name,
            ssi.server_type,
            ssi.lifecycle,
            ssi.lifecycle_sub_status,
            ssi.region,
            ssi.country,
            ssi.city,
            ssi.building
        FROM applications app
        INNER JOIN offline_execution_jobs oej ON app.Id = oej.application_id
        LEFT JOIN spdw_server_inventory ssi ON app.app_desc = ssi.application_id
        WHERE 
            (CAST(:startTime AS timestamp) IS NULL OR oej.start_time = CAST(:startTime AS timestamp))
            AND (CAST(:processName AS text) IS NULL OR oej.process_name ILIKE '%' || CAST(:processName AS text) || '%')
            AND (CAST(:title AS text) IS NULL OR oej.title ILIKE '%' || CAST(:title AS text) || '%')
            AND (CAST(:releaseName AS text) IS NULL OR oej.release_name ILIKE '%' || CAST(:releaseName AS text) || '%')
            AND (CAST(:envName AS text) IS NULL OR oej.env_name ILIKE '%' || CAST(:envName AS text) || '%')
            AND (CAST(:categoryName AS text) IS NULL OR oej.category_name ILIKE '%' || CAST(:categoryName AS text) || '%')
            AND (CAST(:appName AS text) IS NULL OR app.app_name ILIKE '%' || CAST(:appName AS text) || '%')
            AND (CAST(:hostname AS text) IS NULL OR ssi.hostname ILIKE '%' || CAST(:hostname AS text) || '%')
            AND (CAST(:region AS text) IS NULL OR ssi.region ILIKE '%' || CAST(:region AS text) || '%')
        ORDER BY oej.start_time DESC
        """, 
        countQuery = """
        SELECT COUNT(*)
        FROM applications app
        INNER JOIN offline_execution_jobs oej ON app.Id = oej.application_id
        LEFT JOIN spdw_server_inventory ssi ON app.app_desc = ssi.application_id
        WHERE 
            (CAST(:startTime AS timestamp) IS NULL OR oej.start_time = CAST(:startTime AS timestamp))
            AND (CAST(:processName AS text) IS NULL OR oej.process_name ILIKE '%' || CAST(:processName AS text) || '%')
            AND (CAST(:title AS text) IS NULL OR oej.title ILIKE '%' || CAST(:title AS text) || '%')
            AND (CAST(:releaseName AS text) IS NULL OR oej.release_name ILIKE '%' || CAST(:releaseName AS text) || '%')
            AND (CAST(:envName AS text) IS NULL OR oej.env_name ILIKE '%' || CAST(:envName AS text) || '%')
            AND (CAST(:categoryName AS text) IS NULL OR oej.category_name ILIKE '%' || CAST(:categoryName AS text) || '%')
            AND (CAST(:appName AS text) IS NULL OR app.app_name ILIKE '%' || CAST(:appName AS text) || '%')
            AND (CAST(:hostname AS text) IS NULL OR ssi.hostname ILIKE '%' || CAST(:hostname AS text) || '%')
            AND (CAST(:region AS text) IS NULL OR ssi.region ILIKE '%' || CAST(:region AS text) || '%')
        """,
        nativeQuery = true)
    Page<Object[]> searchJobsWithDetails(
        @Param("startTime") LocalDateTime startTime,
        @Param("processName") String processName,
        @Param("title") String title,
        @Param("releaseName") String releaseName,
        @Param("envName") String envName,
        @Param("categoryName") String categoryName,
        @Param("appName") String appName,
        @Param("hostname") String hostname,
        @Param("region") String region,
        Pageable pageable
    );

    @Query(value = """
        SELECT
            oej.id,
            oej.creation_time,
            oej.start_time,
            oej.end_time,
            oej.flow_id,
            oej.process_name,
            oej.title,
            oej.release_name,
            oej.release_version,
            oej.env_name,
            oej.application_name,
            oej.creator_username,
            app.app_name,
            app.app_desc,
            app.version as app_version,
            CAST(app.uuid AS varchar) as app_uuid,
            ba.business_application_name,
            ba.correlation_id,
            ba.owning_transaction_cycle,
            ba.owning_transaction_cycle_id,
            app.app_desc as business_app_id
        FROM offline_execution_jobs oej
        LEFT JOIN applications app ON app.Id = oej.application_id
        LEFT JOIN spdw_vwsfbusinessapplication ba
            ON ba.correlation_id = ANY(string_to_array(app.app_desc, ';'))
        WHERE
            (CAST(:appName AS text) IS NULL OR app.app_name ILIKE '%' || CAST(:appName AS text) || '%')
            AND (CAST(:envName AS text) IS NULL OR oej.env_name ILIKE '%' || CAST(:envName AS text) || '%')
            AND (CAST(:releaseVersion AS text) IS NULL OR oej.release_version ILIKE '%' || CAST(:releaseVersion AS text) || '%')
            AND (CAST(:startTime AS timestamp) IS NULL OR oej.start_time = CAST(:startTime AS timestamp))
            AND (CAST(:owningTransactionCycle AS text) IS NULL OR ba.owning_transaction_cycle ILIKE '%' || CAST(:owningTransactionCycle AS text) || '%')
            AND (CAST(:owningTransactionCycleId AS text) IS NULL OR ba.owning_transaction_cycle_id ILIKE '%' || CAST(:owningTransactionCycleId AS text) || '%')
            AND (CAST(:businessAppId AS text) IS NULL OR app.app_desc ILIKE '%' || CAST(:businessAppId AS text) || '%')
        ORDER BY oej.start_time DESC
        """,
        countQuery = """
        SELECT COUNT(*)
        FROM offline_execution_jobs oej
        LEFT JOIN applications app ON app.Id = oej.application_id
        LEFT JOIN spdw_vwsfbusinessapplication ba
            ON ba.correlation_id = ANY(string_to_array(app.app_desc, ';'))
        WHERE
            (CAST(:appName AS text) IS NULL OR app.app_name ILIKE '%' || CAST(:appName AS text) || '%')
            AND (CAST(:envName AS text) IS NULL OR oej.env_name ILIKE '%' || CAST(:envName AS text) || '%')
            AND (CAST(:releaseVersion AS text) IS NULL OR oej.release_version ILIKE '%' || CAST(:releaseVersion AS text) || '%')
            AND (CAST(:startTime AS timestamp) IS NULL OR oej.start_time = CAST(:startTime AS timestamp))
            AND (CAST(:owningTransactionCycle AS text) IS NULL OR ba.owning_transaction_cycle ILIKE '%' || CAST(:owningTransactionCycle AS text) || '%')
            AND (CAST(:owningTransactionCycleId AS text) IS NULL OR ba.owning_transaction_cycle_id ILIKE '%' || CAST(:owningTransactionCycleId AS text) || '%')
            AND (CAST(:businessAppId AS text) IS NULL OR app.app_desc ILIKE '%' || CAST(:businessAppId AS text) || '%')
        """,
        nativeQuery = true)
    Page<Object[]> searchDeployments(
        @Param("appName") String appName,
        @Param("envName") String envName,
        @Param("releaseVersion") String releaseVersion,
        @Param("startTime") LocalDateTime startTime,
        @Param("owningTransactionCycle") String owningTransactionCycle,
        @Param("owningTransactionCycleId") String owningTransactionCycleId,
        @Param("businessAppId") String businessAppId,
        Pageable pageable
    );

    @Query(value = """
        SELECT
            oej.id,
            oej.creation_time,
            oej.start_time,
            oej.end_time,
            oej.flow_id,
            oej.process_name,
            oej.title,
            oej.release_name,
            oej.release_version,
            oej.env_name,
            oej.application_name,
            oej.creator_username,
            app.app_name,
            app.app_desc,
            app.version as app_version,
            CAST(app.uuid AS varchar) as app_uuid,
            ba.business_application_name,
            ba.correlation_id,
            ba.owning_transaction_cycle,
            ba.owning_transaction_cycle_id,
            app.app_desc as business_app_id
        FROM offline_execution_jobs oej
        LEFT JOIN applications app ON app.Id = oej.application_id
        LEFT JOIN spdw_vwsfbusinessapplication ba
            ON ba.correlation_id = ANY(string_to_array(app.app_desc, ';'))
        WHERE
            (CAST(:appName AS text) IS NULL OR app.app_name ILIKE '%' || CAST(:appName AS text) || '%')
            AND (CAST(:envName AS text) IS NULL OR oej.env_name ILIKE '%' || CAST(:envName AS text) || '%')
            AND (CAST(:releaseVersion AS text) IS NULL OR oej.release_version ILIKE '%' || CAST(:releaseVersion AS text) || '%')
            AND (CAST(:startTime AS timestamp) IS NULL OR oej.start_time = CAST(:startTime AS timestamp))
            AND (CAST(:owningTransactionCycle AS text) IS NULL OR ba.owning_transaction_cycle ILIKE '%' || CAST(:owningTransactionCycle AS text) || '%')
            AND (CAST(:owningTransactionCycleId AS text) IS NULL OR ba.owning_transaction_cycle_id ILIKE '%' || CAST(:owningTransactionCycleId AS text) || '%')
            AND (CAST(:businessAppId AS text) IS NULL OR app.app_desc ILIKE '%' || CAST(:businessAppId AS text) || '%')
        ORDER BY oej.start_time DESC
        """,
        nativeQuery = true)
    List<Object[]> searchDeploymentsUnpaginated(
        @Param("appName") String appName,
        @Param("envName") String envName,
        @Param("releaseVersion") String releaseVersion,
        @Param("startTime") LocalDateTime startTime,
        @Param("owningTransactionCycle") String owningTransactionCycle,
        @Param("owningTransactionCycleId") String owningTransactionCycleId,
        @Param("businessAppId") String businessAppId
    );

    @Query(value = """
        SELECT
            oej.id,
            oej.creation_time,
            oej.start_time,
            oej.end_time,
            oej.last_distribution_time,
            oej.flow_id,
            oej.process_name,
            oej.tag_name,
            oej.title,
            oej.major,
            oej.minor,
            oej.release_name,
            oej.release_version,
            oej.application_name,
            oej.architecture_id,
            oej.system_id,
            oej.env_name,
            oej.category_id,
            oej.category_name,
            oej.customer_id,
            oej.creator_username,
            oej.manifest,
            app.app_name,
            app.app_desc,
            app.version as app_version,
            CAST(app.uuid AS varchar) as app_uuid,
            ssi.hostname,
            ssi.business_line_group_company,
            ssi.business_line,
            ssi.application_instance_environment,
            ssi.os_name,
            ssi.server_type,
            ssi.lifecycle,
            ssi.lifecycle_sub_status,
            ssi.region,
            ssi.country,
            ssi.city,
            ssi.building
        FROM applications app
        INNER JOIN offline_execution_jobs oej ON app.Id = oej.application_id
        LEFT JOIN spdw_server_inventory ssi ON app.app_desc = ssi.application_id
        WHERE
            (CAST(:startTime AS timestamp) IS NULL OR oej.start_time = CAST(:startTime AS timestamp))
            AND (CAST(:processName AS text) IS NULL OR oej.process_name ILIKE '%' || CAST(:processName AS text) || '%')
            AND (CAST(:title AS text) IS NULL OR oej.title ILIKE '%' || CAST(:title AS text) || '%')
            AND (CAST(:releaseName AS text) IS NULL OR oej.release_name ILIKE '%' || CAST(:releaseName AS text) || '%')
            AND (CAST(:envName AS text) IS NULL OR oej.env_name ILIKE '%' || CAST(:envName AS text) || '%')
            AND (CAST(:categoryName AS text) IS NULL OR oej.category_name ILIKE '%' || CAST(:categoryName AS text) || '%')
            AND (CAST(:appName AS text) IS NULL OR app.app_name ILIKE '%' || CAST(:appName AS text) || '%')
            AND (CAST(:hostname AS text) IS NULL OR ssi.hostname ILIKE '%' || CAST(:hostname AS text) || '%')
            AND (CAST(:region AS text) IS NULL OR ssi.region ILIKE '%' || CAST(:region AS text) || '%')
        ORDER BY oej.start_time DESC
        """,
        nativeQuery = true)
    List<Object[]> searchJobsWithDetailsUnpaginated(
        @Param("startTime") LocalDateTime startTime,
        @Param("processName") String processName,
        @Param("title") String title,
        @Param("releaseName") String releaseName,
        @Param("envName") String envName,
        @Param("categoryName") String categoryName,
        @Param("appName") String appName,
        @Param("hostname") String hostname,
        @Param("region") String region
    );

    @QueryHints(value = @QueryHint(name = org.hibernate.jpa.HibernateHints.HINT_FETCH_SIZE, value = "1000"))
    @Query(value = """
        SELECT
            oej.id,
            oej.creation_time,
            oej.start_time,
            oej.end_time,
            oej.flow_id,
            oej.process_name,
            oej.title,
            oej.release_name,
            oej.release_version,
            oej.env_name,
            oej.application_name,
            oej.creator_username,
            app.app_name,
            app.app_desc,
            app.version as app_version,
            CAST(app.uuid AS varchar) as app_uuid,
            ba.business_application_name,
            ba.correlation_id,
            ba.owning_transaction_cycle,
            ba.owning_transaction_cycle_id,
            app.app_desc as business_app_id
        FROM offline_execution_jobs oej
        LEFT JOIN applications app ON app.Id = oej.application_id
        LEFT JOIN spdw_vwsfbusinessapplication ba
            ON ba.correlation_id = ANY(string_to_array(app.app_desc, ';'))
        WHERE
            (CAST(:appName AS text) IS NULL OR app.app_name ILIKE '%' || CAST(:appName AS text) || '%')
            AND (CAST(:envName AS text) IS NULL OR oej.env_name ILIKE '%' || CAST(:envName AS text) || '%')
            AND (CAST(:releaseVersion AS text) IS NULL OR oej.release_version ILIKE '%' || CAST(:releaseVersion AS text) || '%')
            AND (CAST(:startTime AS timestamp) IS NULL OR oej.start_time = CAST(:startTime AS timestamp))
            AND (CAST(:owningTransactionCycle AS text) IS NULL OR ba.owning_transaction_cycle ILIKE '%' || CAST(:owningTransactionCycle AS text) || '%')
            AND (CAST(:owningTransactionCycleId AS text) IS NULL OR ba.owning_transaction_cycle_id ILIKE '%' || CAST(:owningTransactionCycleId AS text) || '%')
            AND (CAST(:businessAppId AS text) IS NULL OR app.app_desc ILIKE '%' || CAST(:businessAppId AS text) || '%')
        ORDER BY oej.start_time DESC
        """,
        nativeQuery = true)
    Stream<Object[]> streamDeployments(
        @Param("appName") String appName,
        @Param("envName") String envName,
        @Param("releaseVersion") String releaseVersion,
        @Param("startTime") LocalDateTime startTime,
        @Param("owningTransactionCycle") String owningTransactionCycle,
        @Param("owningTransactionCycleId") String owningTransactionCycleId,
        @Param("businessAppId") String businessAppId
    );

    @QueryHints(value = @QueryHint(name = org.hibernate.jpa.HibernateHints.HINT_FETCH_SIZE, value = "1000"))
    @Query(value = """
        SELECT
            oej.id,
            oej.creation_time,
            oej.start_time,
            oej.end_time,
            oej.last_distribution_time,
            oej.flow_id,
            oej.process_name,
            oej.tag_name,
            oej.title,
            oej.major,
            oej.minor,
            oej.release_name,
            oej.release_version,
            oej.application_name,
            oej.architecture_id,
            oej.system_id,
            oej.env_name,
            oej.category_id,
            oej.category_name,
            oej.customer_id,
            oej.creator_username,
            oej.manifest,
            app.app_name,
            app.app_desc,
            app.version as app_version,
            CAST(app.uuid AS varchar) as app_uuid,
            ssi.hostname,
            ssi.business_line_group_company,
            ssi.business_line,
            ssi.application_instance_environment,
            ssi.os_name,
            ssi.server_type,
            ssi.lifecycle,
            ssi.lifecycle_sub_status,
            ssi.region,
            ssi.country,
            ssi.city,
            ssi.building
        FROM applications app
        INNER JOIN offline_execution_jobs oej ON app.Id = oej.application_id
        LEFT JOIN spdw_server_inventory ssi ON app.app_desc = ssi.application_id
        WHERE
            (CAST(:startTime AS timestamp) IS NULL OR oej.start_time = CAST(:startTime AS timestamp))
            AND (CAST(:processName AS text) IS NULL OR oej.process_name ILIKE '%' || CAST(:processName AS text) || '%')
            AND (CAST(:title AS text) IS NULL OR oej.title ILIKE '%' || CAST(:title AS text) || '%')
            AND (CAST(:releaseName AS text) IS NULL OR oej.release_name ILIKE '%' || CAST(:releaseName AS text) || '%')
            AND (CAST(:envName AS text) IS NULL OR oej.env_name ILIKE '%' || CAST(:envName AS text) || '%')
            AND (CAST(:categoryName AS text) IS NULL OR oej.category_name ILIKE '%' || CAST(:categoryName AS text) || '%')
            AND (CAST(:appName AS text) IS NULL OR app.app_name ILIKE '%' || CAST(:appName AS text) || '%')
            AND (CAST(:hostname AS text) IS NULL OR ssi.hostname ILIKE '%' || CAST(:hostname AS text) || '%')
            AND (CAST(:region AS text) IS NULL OR ssi.region ILIKE '%' || CAST(:region AS text) || '%')
        ORDER BY oej.start_time DESC
        """,
        nativeQuery = true)
    Stream<Object[]> streamJobsWithDetails(
        @Param("startTime") LocalDateTime startTime,
        @Param("processName") String processName,
        @Param("title") String title,
        @Param("releaseName") String releaseName,
        @Param("envName") String envName,
        @Param("categoryName") String categoryName,
        @Param("appName") String appName,
        @Param("hostname") String hostname,
        @Param("region") String region
    );
}
