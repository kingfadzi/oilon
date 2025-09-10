-- Alter 'applications' table
ALTER TABLE applications
    ALTER COLUMN uuid TYPE UUID USING uuid::uuid,
    ALTER COLUMN params_folder_id TYPE INTEGER USING params_folder_id::integer,
    ALTER COLUMN is_express TYPE INTEGER USING is_express::integer,
    ALTER COLUMN issharedlibrary TYPE INTEGER USING issharedlibrary::integer;

-- Alter 'offline_execution_jobs' table
-- Note: 'major' and 'minor' are left as text types (converted to VARCHAR for consistency).
ALTER TABLE offline_execution_jobs
    ALTER COLUMN creation_time TYPE TIMESTAMP USING creation_time::timestamp,
    ALTER COLUMN start_time TYPE TIMESTAMP USING start_time::timestamp,
    ALTER COLUMN end_time TYPE TIMESTAMP USING end_time::timestamp,
    ALTER COLUMN last_distribution_time TYPE TIMESTAMP USING last_distribution_time::timestamp,
    ALTER COLUMN calculation_time TYPE BIGINT USING calculation_time::bigint,


-- Alter 'spdw_server_inventory' table
ALTER TABLE spdw_server_inventory
    ALTER COLUMN application_id TYPE VARCHAR USING application_id::varchar;