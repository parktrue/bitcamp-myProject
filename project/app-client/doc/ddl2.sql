CREATE TABLE myapp_soldier (
    soldier_no INT AUTO_INCREMENT PRIMARY KEY,
    soldier_milnum VARCHAR(255),
    name VARCHAR(255),
    age INT,
    password VARCHAR(255),
    `rank` VARCHAR(255),
    enlis_date DATE,
    dis_date DATE,
    d_day BIGINT
);
