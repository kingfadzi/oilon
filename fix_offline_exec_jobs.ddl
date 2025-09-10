-- This script alters the offline_execution_jobs table to change date-related columns from TEXT to TIMESTAMP.
-- WARNING: It's recommended to back up your data before running this script.
-- The success of the cast depends on the text format of your existing date strings.
-- This script assumes a format that PostgreSQL can cast to a timestamp (e.g., ISO 8601 format like 'YYYY-MM-DD HH24:MI:SS').

ALTER TABLE offline_execution_jobs
ALTER COLUMN creation_time TYPE TIMESTAMP USING creation_time::timestamp,
ALTER COLUMN start_time TYPE TIMESTAMP USING start_time::timestamp,
ALTER COLUMN end_time TYPE TIMESTAMP USING end_time::timestamp,
ALTER COLUMN last_distribution_time TYPE TIMESTAMP USING last_distribution_time::timestamp;
