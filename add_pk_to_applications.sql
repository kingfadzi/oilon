-- This script adds a new serial primary key column named 'Id' to the 'applications' table.

ALTER TABLE applications ADD COLUMN Id SERIAL PRIMARY KEY;
