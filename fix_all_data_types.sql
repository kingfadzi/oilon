-- =====================================================================================
-- WARNING: Comprehensive Data Type Conversion Script
-- =====================================================================================
-- This script alters multiple tables to convert columns from TEXT to their correct,
-- stricter data types (UUID, BOOLEAN, INTEGER, TIMESTAMP, etc.).
--
-- !!! IMPORTANT !!!
-- 1.  **BACK UP YOUR DATABASE** before running this script. A failed data cast
--     can interrupt the process and lead to data loss or corruption.
-- 2.  This script uses direct casting. It will fail if the data in a column
--     cannot be directly cast to the target type (e.g., non-numeric text in an
--     integer column, or '1'/'0' in a boolean column).
--
-- Review your data to ensure it is clean before executing these commands.
-- =====================================================================================

-- Alter 'applications' table
ALTER TABLE applications
    ALTER COLUMN uuid TYPE UUID USING uuid::uuid,
    ALTER COLUMN params_folder_id TYPE INTEGER USING params_folder_id::integer,
    ALTER COLUMN is_express TYPE BOOLEAN USING is_express::boolean,
    ALTER COLUMN issharedlibrary TYPE BOOLEAN USING issharedlibrary::boolean;

-- Alter 'offline_execution_jobs' table
ALTER TABLE offline_execution_jobs
    ALTER COLUMN creation_time TYPE TIMESTAMP USING creation_time::timestamp,
    ALTER COLUMN start_time TYPE TIMESTAMP USING start_time::timestamp,
    ALTER COLUMN end_time TYPE TIMESTAMP USING end_time::timestamp,
    ALTER COLUMN last_distribution_time TYPE TIMESTAMP USING last_distribution_time::timestamp,
    ALTER COLUMN major TYPE INTEGER USING major::integer,
    ALTER COLUMN minor TYPE INTEGER USING minor::integer,
    ALTER COLUMN calculation_time TYPE BIGINT USING calculation_time::bigint,
    ALTER COLUMN application_id TYPE INTEGER USING application_id::integer,
    ALTER COLUMN architecture_id TYPE INTEGER USING architecture_id::integer,
    ALTER COLUMN system_id TYPE INTEGER USING system_id::integer,
    ALTER COLUMN category_id TYPE INTEGER USING category_id::integer,
    ALTER COLUMN customer_id TYPE INTEGER USING customer_id::integer;

-- Alter 'spdw_server_inventory' table
ALTER TABLE spdw_server_inventory
    ALTER COLUMN application_id TYPE INTEGER USING application_id::integer;
