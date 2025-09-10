-- =====================================================================================
-- WARNING: Comprehensive Data Type Conversion Script
-- =====================================================================================
-- This script alters multiple tables to convert columns from TEXT to their correct,
-- stricter data types (UUID, BOOLEAN, INTEGER, TIMESTAMP, etc.).
--
-- !!! IMPORTANT !!!
-- 1.  **BACK UP YOUR DATABASE** before running this script. A failed data cast
--     can interrupt the process and lead to data loss or corruption.
-- 2.  This script assumes the existing text data is in a format that can be
--     successfully cast to the target type.
--
-- Review your data to ensure it is clean before executing these commands.
-- =====================================================================================

-- Alter 'applications' table
-- Handles numeric booleans (1=true, other=false)
ALTER TABLE applications
    ALTER COLUMN uuid TYPE UUID USING uuid::uuid,
    ALTER COLUMN params_folder_id TYPE INTEGER USING (CASE WHEN params_folder_id ~ '^[0-9]+$' THEN params_folder_id::integer ELSE NULL END),
    ALTER COLUMN is_express TYPE BOOLEAN USING (CASE WHEN is_express ~ '^[0-9]+$' THEN is_express::integer = 1 ELSE is_express::boolean END),
    ALTER COLUMN issharedlibrary TYPE BOOLEAN USING (CASE WHEN issharedlibrary ~ '^[0-9]+$' THEN issharedlibrary::integer = 1 ELSE issharedlibrary::boolean END);

-- Alter 'offline_execution_jobs' table
-- Handles potentially non-numeric text in integer/bigint columns by converting them to NULL
ALTER TABLE offline_execution_jobs
    ALTER COLUMN creation_time TYPE TIMESTAMP USING creation_time::timestamp,
    ALTER COLUMN start_time TYPE TIMESTAMP USING start_time::timestamp,
    ALTER COLUMN end_time TYPE TIMESTAMP USING end_time::timestamp,
    ALTER COLUMN last_distribution_time TYPE TIMESTAMP USING last_distribution_time::timestamp,
    ALTER COLUMN major TYPE INTEGER USING (CASE WHEN major ~ '^[0-9]+$' THEN major::integer ELSE NULL END),
    ALTER COLUMN minor TYPE INTEGER USING (CASE WHEN minor ~ '^[0-9]+$' THEN minor::integer ELSE NULL END),
    ALTER COLUMN calculation_time TYPE BIGINT USING (CASE WHEN calculation_time ~ '^[0-9]+$' THEN calculation_time::bigint ELSE NULL END),
    ALTER COLUMN application_id TYPE INTEGER USING (CASE WHEN application_id ~ '^[0-9]+$' THEN application_id::integer ELSE NULL END),
    ALTER COLUMN architecture_id TYPE INTEGER USING (CASE WHEN architecture_id ~ '^[0-9]+$' THEN architecture_id::integer ELSE NULL END),
    ALTER COLUMN system_id TYPE INTEGER USING (CASE WHEN system_id ~ '^[0-9]+$' THEN system_id::integer ELSE NULL END),
    ALTER COLUMN category_id TYPE INTEGER USING (CASE WHEN category_id ~ '^[0-9]+$' THEN category_id::integer ELSE NULL END),
    ALTER COLUMN customer_id TYPE INTEGER USING (CASE WHEN customer_id ~ '^[0-9]+$' THEN customer_id::integer ELSE NULL END);

-- Alter 'spdw_server_inventory' table
-- Handles potentially non-numeric text in integer columns by converting them to NULL
ALTER TABLE spdw_server_inventory
    ALTER COLUMN application_id TYPE INTEGER USING (CASE WHEN application_id ~ '^[0-9]+$' THEN application_id::integer ELSE NULL END);