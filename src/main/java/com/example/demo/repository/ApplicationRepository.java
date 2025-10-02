package com.example.demo.repository;

import com.example.demo.model.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long>, JpaSpecificationExecutor<Application> {
    
    @Query(value = """
        SELECT 
            app.id,
            CAST(app.uuid AS varchar) as uuid,
            app.app_name,
            app.app_desc as business_app_id,
            app.version,
            app.params_folder_id,
            app.is_express,
            app.issharedlibrary,
            app.delete_status,
            ba.business_application_name,
            ba.correlation_id,
            ba.active,
            ba.owning_transaction_cycle,
            ba.owning_transaction_cycle_id,
            ba.resilience_category,
            ba.operational_status,
            ba.application_type,
            ba.architecture_type,
            ba.install_type,
            ba.application_parent,
            ba.application_parent_correlation_id,
            ba.house_position,
            ba.business_application_sys_id,
            ba.application_tier,
            ba.application_product_owner,
            ba.system_architect,
            ba.application_product_owner_brid,
            ba.system_architect_brid,
            ba.architecture_hosting
        FROM applications app
        LEFT JOIN spdw_vwsfbusinessapplication ba 
            ON ba.correlation_id = ANY(string_to_array(app.app_desc, ';'))
        WHERE 
            (CAST(:appName AS text) IS NULL OR app.app_name ILIKE '%' || CAST(:appName AS text) || '%')
            AND (CAST(:businessAppId AS text) IS NULL OR app.app_desc ILIKE '%' || CAST(:businessAppId AS text) || '%')
            AND (CAST(:applicationType AS text) IS NULL OR ba.application_type ILIKE '%' || CAST(:applicationType AS text) || '%')
            AND (CAST(:productOwner AS text) IS NULL OR ba.application_product_owner ILIKE '%' || CAST(:productOwner AS text) || '%')
            AND (CAST(:systemArchitect AS text) IS NULL OR ba.system_architect ILIKE '%' || CAST(:systemArchitect AS text) || '%')
            AND (CAST(:operationalStatus AS text) IS NULL OR ba.operational_status ILIKE '%' || CAST(:operationalStatus AS text) || '%')
            AND (CAST(:architectureType AS text) IS NULL OR ba.architecture_type ILIKE '%' || CAST(:architectureType AS text) || '%')
            AND (CAST(:businessApplicationName AS text) IS NULL OR ba.business_application_name ILIKE '%' || CAST(:businessApplicationName AS text) || '%')
            AND (:active IS NULL OR ba.active = :active)
            AND (CAST(:owningTransactionCycle AS text) IS NULL OR ba.owning_transaction_cycle ILIKE '%' || CAST(:owningTransactionCycle AS text) || '%')
            AND (CAST(:owningTransactionCycleId AS text) IS NULL OR ba.owning_transaction_cycle_id ILIKE '%' || CAST(:owningTransactionCycleId AS text) || '%')
            AND (CAST(:resilienceCategory AS text) IS NULL OR ba.resilience_category ILIKE '%' || CAST(:resilienceCategory AS text) || '%')
            AND (CAST(:installType AS text) IS NULL OR ba.install_type ILIKE '%' || CAST(:installType AS text) || '%')
            AND (CAST(:applicationParent AS text) IS NULL OR ba.application_parent ILIKE '%' || CAST(:applicationParent AS text) || '%')
            AND (CAST(:applicationParentCorrelationId AS text) IS NULL OR ba.application_parent_correlation_id ILIKE '%' || CAST(:applicationParentCorrelationId AS text) || '%')
            AND (CAST(:housePosition AS text) IS NULL OR ba.house_position ILIKE '%' || CAST(:housePosition AS text) || '%')
            AND (CAST(:businessApplicationSysId AS text) IS NULL OR ba.business_application_sys_id ILIKE '%' || CAST(:businessApplicationSysId AS text) || '%')
            AND (CAST(:applicationTier AS text) IS NULL OR ba.application_tier ILIKE '%' || CAST(:applicationTier AS text) || '%')
            AND (CAST(:applicationProductOwnerBrid AS text) IS NULL OR ba.application_product_owner_brid ILIKE '%' || CAST(:applicationProductOwnerBrid AS text) || '%')
            AND (CAST(:systemArchitectBrid AS text) IS NULL OR ba.system_architect_brid ILIKE '%' || CAST(:systemArchitectBrid AS text) || '%')
            AND (CAST(:architectureHosting AS text) IS NULL OR ba.architecture_hosting ILIKE '%' || CAST(:architectureHosting AS text) || '%')
        ORDER BY app.app_name ASC
        """, 
        countQuery = """
        SELECT COUNT(DISTINCT app.id)
        FROM applications app
        LEFT JOIN spdw_vwsfbusinessapplication ba 
            ON ba.correlation_id = ANY(string_to_array(app.app_desc, ';'))
        WHERE 
            (CAST(:appName AS text) IS NULL OR app.app_name ILIKE '%' || CAST(:appName AS text) || '%')
            AND (CAST(:businessAppId AS text) IS NULL OR app.app_desc ILIKE '%' || CAST(:businessAppId AS text) || '%')
            AND (CAST(:applicationType AS text) IS NULL OR ba.application_type ILIKE '%' || CAST(:applicationType AS text) || '%')
            AND (CAST(:productOwner AS text) IS NULL OR ba.application_product_owner ILIKE '%' || CAST(:productOwner AS text) || '%')
            AND (CAST(:systemArchitect AS text) IS NULL OR ba.system_architect ILIKE '%' || CAST(:systemArchitect AS text) || '%')
            AND (CAST(:operationalStatus AS text) IS NULL OR ba.operational_status ILIKE '%' || CAST(:operationalStatus AS text) || '%')
            AND (CAST(:architectureType AS text) IS NULL OR ba.architecture_type ILIKE '%' || CAST(:architectureType AS text) || '%')
            AND (CAST(:businessApplicationName AS text) IS NULL OR ba.business_application_name ILIKE '%' || CAST(:businessApplicationName AS text) || '%')
            AND (:active IS NULL OR ba.active = :active)
            AND (CAST(:owningTransactionCycle AS text) IS NULL OR ba.owning_transaction_cycle ILIKE '%' || CAST(:owningTransactionCycle AS text) || '%')
            AND (CAST(:owningTransactionCycleId AS text) IS NULL OR ba.owning_transaction_cycle_id ILIKE '%' || CAST(:owningTransactionCycleId AS text) || '%')
            AND (CAST(:resilienceCategory AS text) IS NULL OR ba.resilience_category ILIKE '%' || CAST(:resilienceCategory AS text) || '%')
            AND (CAST(:installType AS text) IS NULL OR ba.install_type ILIKE '%' || CAST(:installType AS text) || '%')
            AND (CAST(:applicationParent AS text) IS NULL OR ba.application_parent ILIKE '%' || CAST(:applicationParent AS text) || '%')
            AND (CAST(:applicationParentCorrelationId AS text) IS NULL OR ba.application_parent_correlation_id ILIKE '%' || CAST(:applicationParentCorrelationId AS text) || '%')
            AND (CAST(:housePosition AS text) IS NULL OR ba.house_position ILIKE '%' || CAST(:housePosition AS text) || '%')
            AND (CAST(:businessApplicationSysId AS text) IS NULL OR ba.business_application_sys_id ILIKE '%' || CAST(:businessApplicationSysId AS text) || '%')
            AND (CAST(:applicationTier AS text) IS NULL OR ba.application_tier ILIKE '%' || CAST(:applicationTier AS text) || '%')
            AND (CAST(:applicationProductOwnerBrid AS text) IS NULL OR ba.application_product_owner_brid ILIKE '%' || CAST(:applicationProductOwnerBrid AS text) || '%')
            AND (CAST(:systemArchitectBrid AS text) IS NULL OR ba.system_architect_brid ILIKE '%' || CAST(:systemArchitectBrid AS text) || '%')
            AND (CAST(:architectureHosting AS text) IS NULL OR ba.architecture_hosting ILIKE '%' || CAST(:architectureHosting AS text) || '%')
        """,
        nativeQuery = true)
    Page<Object[]> searchApplicationsWithOwnership(
        @Param("appName") String appName,
        @Param("businessAppId") String businessAppId,
        @Param("applicationType") String applicationType,
        @Param("productOwner") String productOwner,
        @Param("systemArchitect") String systemArchitect,
        @Param("operationalStatus") String operationalStatus,
        @Param("architectureType") String architectureType,
        @Param("businessApplicationName") String businessApplicationName,
        @Param("active") String active,
        @Param("owningTransactionCycle") String owningTransactionCycle,
        @Param("owningTransactionCycleId") String owningTransactionCycleId,
        @Param("resilienceCategory") String resilienceCategory,
        @Param("installType") String installType,
        @Param("applicationParent") String applicationParent,
        @Param("applicationParentCorrelationId") String applicationParentCorrelationId,
        @Param("housePosition") String housePosition,
        @Param("businessApplicationSysId") String businessApplicationSysId,
        @Param("applicationTier") String applicationTier,
        @Param("applicationProductOwnerBrid") String applicationProductOwnerBrid,
        @Param("systemArchitectBrid") String systemArchitectBrid,
        @Param("architectureHosting") String architectureHosting,
        Pageable pageable
    );

    @Query(value = """
        SELECT
            app.id,
            CAST(app.uuid AS varchar) as uuid,
            app.app_name,
            app.app_desc as business_app_id,
            app.version,
            app.params_folder_id,
            app.is_express,
            app.issharedlibrary,
            app.delete_status,
            ba.business_application_name,
            ba.correlation_id,
            ba.active,
            ba.owning_transaction_cycle,
            ba.owning_transaction_cycle_id,
            ba.resilience_category,
            ba.operational_status,
            ba.application_type,
            ba.architecture_type,
            ba.install_type,
            ba.application_parent,
            ba.application_parent_correlation_id,
            ba.house_position,
            ba.business_application_sys_id,
            ba.application_tier,
            ba.application_product_owner,
            ba.system_architect,
            ba.application_product_owner_brid,
            ba.system_architect_brid,
            ba.architecture_hosting
        FROM applications app
        LEFT JOIN spdw_vwsfbusinessapplication ba
            ON ba.correlation_id = ANY(string_to_array(app.app_desc, ';'))
        WHERE
            (CAST(:appName AS text) IS NULL OR app.app_name ILIKE '%' || CAST(:appName AS text) || '%')
            AND (CAST(:businessAppId AS text) IS NULL OR app.app_desc ILIKE '%' || CAST(:businessAppId AS text) || '%')
            AND (CAST(:applicationType AS text) IS NULL OR ba.application_type ILIKE '%' || CAST(:applicationType AS text) || '%')
            AND (CAST(:productOwner AS text) IS NULL OR ba.application_product_owner ILIKE '%' || CAST(:productOwner AS text) || '%')
            AND (CAST(:systemArchitect AS text) IS NULL OR ba.system_architect ILIKE '%' || CAST(:systemArchitect AS text) || '%')
            AND (CAST(:operationalStatus AS text) IS NULL OR ba.operational_status ILIKE '%' || CAST(:operationalStatus AS text) || '%')
            AND (CAST(:architectureType AS text) IS NULL OR ba.architecture_type ILIKE '%' || CAST(:architectureType AS text) || '%')
            AND (CAST(:businessApplicationName AS text) IS NULL OR ba.business_application_name ILIKE '%' || CAST(:businessApplicationName AS text) || '%')
            AND (:active IS NULL OR ba.active = :active)
            AND (CAST(:owningTransactionCycle AS text) IS NULL OR ba.owning_transaction_cycle ILIKE '%' || CAST(:owningTransactionCycle AS text) || '%')
            AND (CAST(:owningTransactionCycleId AS text) IS NULL OR ba.owning_transaction_cycle_id ILIKE '%' || CAST(:owningTransactionCycleId AS text) || '%')
            AND (CAST(:resilienceCategory AS text) IS NULL OR ba.resilience_category ILIKE '%' || CAST(:resilienceCategory AS text) || '%')
            AND (CAST(:installType AS text) IS NULL OR ba.install_type ILIKE '%' || CAST(:installType AS text) || '%')
            AND (CAST(:applicationParent AS text) IS NULL OR ba.application_parent ILIKE '%' || CAST(:applicationParent AS text) || '%')
            AND (CAST(:applicationParentCorrelationId AS text) IS NULL OR ba.application_parent_correlation_id ILIKE '%' || CAST(:applicationParentCorrelationId AS text) || '%')
            AND (CAST(:housePosition AS text) IS NULL OR ba.house_position ILIKE '%' || CAST(:housePosition AS text) || '%')
            AND (CAST(:businessApplicationSysId AS text) IS NULL OR ba.business_application_sys_id ILIKE '%' || CAST(:businessApplicationSysId AS text) || '%')
            AND (CAST(:applicationTier AS text) IS NULL OR ba.application_tier ILIKE '%' || CAST(:applicationTier AS text) || '%')
            AND (CAST(:applicationProductOwnerBrid AS text) IS NULL OR ba.application_product_owner_brid ILIKE '%' || CAST(:applicationProductOwnerBrid AS text) || '%')
            AND (CAST(:systemArchitectBrid AS text) IS NULL OR ba.system_architect_brid ILIKE '%' || CAST(:systemArchitectBrid AS text) || '%')
            AND (CAST(:architectureHosting AS text) IS NULL OR ba.architecture_hosting ILIKE '%' || CAST(:architectureHosting AS text) || '%')
        ORDER BY app.app_name ASC
        """,
        nativeQuery = true)
    List<Object[]> searchApplicationsWithOwnershipUnpaginated(
        @Param("appName") String appName,
        @Param("businessAppId") String businessAppId,
        @Param("applicationType") String applicationType,
        @Param("productOwner") String productOwner,
        @Param("systemArchitect") String systemArchitect,
        @Param("operationalStatus") String operationalStatus,
        @Param("architectureType") String architectureType,
        @Param("businessApplicationName") String businessApplicationName,
        @Param("active") String active,
        @Param("owningTransactionCycle") String owningTransactionCycle,
        @Param("owningTransactionCycleId") String owningTransactionCycleId,
        @Param("resilienceCategory") String resilienceCategory,
        @Param("installType") String installType,
        @Param("applicationParent") String applicationParent,
        @Param("applicationParentCorrelationId") String applicationParentCorrelationId,
        @Param("housePosition") String housePosition,
        @Param("businessApplicationSysId") String businessApplicationSysId,
        @Param("applicationTier") String applicationTier,
        @Param("applicationProductOwnerBrid") String applicationProductOwnerBrid,
        @Param("systemArchitectBrid") String systemArchitectBrid,
        @Param("architectureHosting") String architectureHosting
    );
}
