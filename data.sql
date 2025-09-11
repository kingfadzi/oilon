-- Dummy data for applications table
-- Note: app_desc now contains correlation IDs for business application lookup
INSERT INTO applications (uuid, app_name, app_desc, version, params_folder_id, is_express, issharedlibrary, delete_status) VALUES
('a1b2c3d4-e5f6-4890-8234-567890abcdef'::uuid, 'Billing Engine', 'BILL-001;FIN-CORE', '1.2.3', 101, true, false, 'ACTIVE'),
('b2c3d4e5-f6a7-4901-9345-67890abcdef1'::uuid, 'CRM Suite', 'CRM-002;SALES-APP', '2.0.1', 102, false, false, 'ACTIVE'),
('c3d4e5f6-a7b8-4012-a456-7890abcdef12'::uuid, 'Logistics Manager', 'LOG-003', '3.5.0', 103, true, true, 'INACTIVE');

-- Dummy data for offline_execution_jobs table
-- Note: Assumes the applications above were inserted with Ids 1, 2, 3 respectively.
INSERT INTO offline_execution_jobs (creation_time, start_time, end_time, last_distribution_time, flow_id, process_name, tag_name, title, major, minor, release_name, release_version, calculation_time, application_id, application_name, architecture_id, system_id, env_name, category_id, category_name, customer_id, creator_username, manifest, parameterroot) VALUES
(NOW() - INTERVAL '2 day', NOW() - INTERVAL '2 day' + INTERVAL '5 minute', NOW() - INTERVAL '2 day' + INTERVAL '1 hour', NOW(), 'flow-abc-123', 'Monthly Invoicing', 'billing', 'Generate Invoices for October', 1, 2, 'October Billing Run', '1.2', 3600, 1, 'Billing Engine', 1, 1, 'production', 1, 'Finance', 1001, 'jdoe', '{}', '{}'),
(NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day' + INTERVAL '10 minute', NOW() - INTERVAL '1 day' + INTERVAL '2 hour', NOW(), 'flow-def-456', 'Customer Data Sync', 'crm', 'Sync new customer contacts', 2, 0, 'Daily Sync', '2.0', 7200, 2, 'CRM Suite', 1, 2, 'staging', 2, 'Sales', 1002, 'asmith', '{}', '{}'),
(NOW() - INTERVAL '10 hour', NOW() - INTERVAL '10 hour' + INTERVAL '1 minute', NOW() - INTERVAL '10 hour' + INTERVAL '30 minute', NOW(), 'flow-ghi-789', 'Inventory Update', 'logistics', 'Update warehouse stock levels', 3, 5, 'Hourly Stock Update', '3.5', 1800, 3, 'Logistics Manager', 2, 3, 'development', 3, 'Operations', 1003, 'bwilliams', '{}', '{}');

-- Dummy data for spdw_server_inventory table
-- Note: Assumes the applications above were inserted with Ids 1, 2, 3 respectively.
INSERT INTO spdw_server_inventory (hostname, business_line_group_company, business_line, application, application_instance_environment, os_name, server_type, lifecycle, lifecycle_sub_status, application_id, application_bsh, region, country, city, building) VALUES
('prod-billing-01', 'Global Tech Inc.', 'Finance', 'Billing Engine', 'Production', 'Ubuntu 22.04 LTS', 'Application Server', 'In Production', 'Running', 1, 'B-123', 'NA', 'USA', 'New York', 'Building A'),
('staging-crm-web-01', 'Global Tech Inc.', 'Sales', 'CRM Suite', 'Staging', 'Red Hat Enterprise Linux 9', 'Web Server', 'Pre-production', 'Testing', 2, 'S-456', 'EU', 'Germany', 'Frankfurt', 'Building C'),
('dev-logistics-db-01', 'Global Tech Inc.', 'Operations', 'Logistics Manager', 'Development', 'Windows Server 2022', 'Database Server', 'In Development', 'Active', 3, 'O-789', 'APAC', 'Japan', 'Tokyo', 'Building F');

-- Dummy data for spdw_vwsfbusinessapplication table
-- Note: correlation_id values match the app_desc correlation IDs from applications table
INSERT INTO spdw_vwsfbusinessapplication (business_application_name, correlation_id, active, owning_transaction_cycle, owning_transaction_cycle_id, resilience_category, operational_status, application_type, architecture_type, install_type, application_parent, application_parent_correlation_id, house_position, business_application_sys_id, application_tier, application_product_owner, system_architect, application_product_owner_brid, system_architect_brid, architecture_hosting) VALUES
('Billing System Core', 'BILL-001', 'true', 'Financial Operations', 'FIN-001', 'Critical', 'Production', 'Enterprise Application', 'Microservices', 'Cloud Native', NULL, NULL, 'Front Office', 'SYS-BILL-001', 'Tier 1', 'John Smith', 'Sarah Wilson', 'JS001', 'SW002', 'AWS EKS'),
('Financial Core Platform', 'FIN-CORE', 'true', 'Financial Operations', 'FIN-001', 'Critical', 'Production', 'Platform', 'Service Mesh', 'Hybrid Cloud', 'Billing System Core', 'BILL-001', 'Back Office', 'SYS-FIN-CORE', 'Tier 1', 'John Smith', 'Michael Davis', 'JS001', 'MD003', 'AWS + On-Premise'),
('Customer Relationship Management', 'CRM-002', 'true', 'Sales Operations', 'SALES-001', 'High', 'Production', 'Business Application', 'Monolithic', 'Cloud', NULL, NULL, 'Front Office', 'SYS-CRM-002', 'Tier 2', 'Alice Johnson', 'Robert Brown', 'AJ004', 'RB005', 'Azure Cloud'),
('Sales Application Suite', 'SALES-APP', 'true', 'Sales Operations', 'SALES-001', 'Medium', 'Production', 'Application Suite', 'SOA', 'Cloud', 'Customer Relationship Management', 'CRM-002', 'Front Office', 'SYS-SALES-APP', 'Tier 2', 'Alice Johnson', 'Emily Taylor', 'AJ004', 'ET006', 'Azure App Service'),
('Logistics Management System', 'LOG-003', 'false', 'Operations', 'OPS-001', 'Low', 'Decommissioned', 'Legacy Application', 'Monolithic', 'On-Premise', NULL, NULL, 'Back Office', 'SYS-LOG-003', 'Tier 3', 'David Miller', 'Lisa Anderson', 'DM007', 'LA008', 'On-Premise Data Center');
