-- Dummy data for applications table
INSERT INTO applications (uuid, app_name, app_desc, version, params_folder_id, is_express, issharedlibrary, delete_status) VALUES
('a1b2c3d4-e5f6-4890-8234-567890abcdef', 'Billing Engine', 'Handles all billing and invoicing.', '1.2.3', 101, true, false, 'ACTIVE'),
('b2c3d4e5-f6a7-4901-9345-67890abcdef1', 'CRM Suite', 'Customer Relationship Management tools.', '2.0.1', 102, false, false, 'ACTIVE'),
('c3d4e5f6-a7b8-4012-a456-7890abcdef12', 'Logistics Manager', 'Coordinates shipping and receiving.', '3.5.0', 103, true, true, 'INACTIVE');

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
