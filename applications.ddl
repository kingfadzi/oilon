CREATE TABLE applications (
    Id SERIAL PRIMARY KEY,
    uuid UUID,
    app_name VARCHAR(255),
    app_desc TEXT,
    version VARCHAR(50),
    params_folder_id INTEGER,
    is_express BOOLEAN,
    issharedlibrary BOOLEAN,
    delete_status VARCHAR(50)
);

CREATE TABLE offline_execution_jobs (
    id SERIAL PRIMARY KEY,
    creation_time TIMESTAMP,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    last_distribution_time TIMESTAMP,
    flow_id VARCHAR(255),
    process_name VARCHAR(255),
    tag_name VARCHAR(255),
    title VARCHAR(255),
    major INTEGER,
    minor INTEGER,
    release_name VARCHAR(255),
    release_version VARCHAR(255),
    calculation_time BIGINT,
    application_id INTEGER,
    application_name VARCHAR(255),
    architecture_id INTEGER,
    system_id INTEGER,
    env_name VARCHAR(255),
    category_id INTEGER,
    category_name VARCHAR(255),
    customer_id INTEGER,
    creator_username VARCHAR(255),
    manifest TEXT,
    parameterroot TEXT
);

CREATE TABLE spdw_server_inventory (
    hostname VARCHAR(255),
    business_line_group_company VARCHAR(255),
    business_line VARCHAR(255),
    application VARCHAR(255),
    application_instance_environment VARCHAR(255),
    os_name VARCHAR(255),
    server_type VARCHAR(255),
    lifecycle VARCHAR(255),
    lifecycle_sub_status VARCHAR(255),
    application_id INTEGER,
    application_bsh VARCHAR(255),
    region VARCHAR(255),
    country VARCHAR(255),
    city VARCHAR(255),
    building VARCHAR(255)
);
