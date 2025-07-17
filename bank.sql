-- 1. Drop and recreate database
DROP DATABASE IF EXISTS maverickbank;
CREATE DATABASE maverickbank;
USE maverickbank;

-- 2. Drop tables if they exist (in proper FK order)
DROP TABLE IF EXISTS beneficiary;
DROP TABLE IF EXISTS transaction_history;
DROP TABLE IF EXISTS loan_application;
DROP TABLE IF EXISTS account_holder;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS branch;

-- 3. Create Branch table
CREATE TABLE branch (
    branch_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    branch_name VARCHAR(100) NOT NULL,
    ifsc_code VARCHAR(20) UNIQUE NOT NULL,
    address VARCHAR(255)
);

-- 4. Create Employee table
CREATE TABLE employee (
    employee_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    mobile_no VARCHAR(15) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(30) DEFAULT 'EMPLOYEE',
    branch_id BIGINT,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    FOREIGN KEY (branch_id) REFERENCES branch(branch_id)
);

-- 5. Create Admin table
CREATE TABLE admin (
    admin_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    mobile_no VARCHAR(15) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(30) DEFAULT 'ADMIN',
    status VARCHAR(20) DEFAULT 'ACTIVE'
);

-- 6. Create Account Holder table (with branch_id and extra fields)
CREATE TABLE account_holder (
    account_no BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    mobile_no VARCHAR(15) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    balance DOUBLE DEFAULT 0.0,
    address VARCHAR(255),
    gender VARCHAR(10),
    dob DATE,
    aadhar VARCHAR(20),
    pan VARCHAR(20),
    account_type VARCHAR(30),
    branch_id BIGINT,
    ifsc_code VARCHAR(20),
    FOREIGN KEY (branch_id) REFERENCES branch(branch_id)
);

-- 7. Create Beneficiary table
CREATE TABLE beneficiary (
    beneficiary_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_no BIGINT NOT NULL,  -- owner of the beneficiary
    beneficiary_account_no BIGINT NOT NULL, -- destination account
    beneficiary_name VARCHAR(100) NOT NULL,
    bank_name VARCHAR(100),
    branch_name VARCHAR(100),
    ifsc_code VARCHAR(20),
    FOREIGN KEY (account_no) REFERENCES account_holder(account_no)
);

-- 8. Create Loan Application table
CREATE TABLE loan_application (
    loan_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_no BIGINT NOT NULL,
    loan_amount DOUBLE NOT NULL,
    tenure_in_months INT NOT NULL,
    purpose VARCHAR(255),
    status VARCHAR(20) DEFAULT 'PENDING',
    applied_date DATE,
    approved_by BIGINT, -- employee_id who approved/rejected (nullable)
    decision_date DATE,
    FOREIGN KEY (account_no) REFERENCES account_holder(account_no),
    FOREIGN KEY (approved_by) REFERENCES employee(employee_id)
);

-- 9. Create Transaction History table
CREATE TABLE transaction_history (
    transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_no BIGINT NOT NULL,
    type VARCHAR(20), -- deposit, withdrawal, transfer
    amount DOUBLE,
    description VARCHAR(255),
    timestamp DATETIME,
    target_account_no BIGINT, -- for transfers (nullable)
    FOREIGN KEY (account_no) REFERENCES account_holder(account_no)
);

-- 10. Sample Branches
INSERT INTO branch (branch_name, ifsc_code, address)
VALUES 
('Main Branch', 'MVRK000101', '123 Main St'),
('City Center', 'MVRK000102', '456 Center Ave'),
('North Side', 'MVRK000103', '789 North Road');

-- 11. Sample Employees
INSERT INTO employee (name, email, mobile_no, password, branch_id)
VALUES 
('Alice Banker', 'alice.banker@maverick.com', '9000000001', 'alicepass', 1),
('Bob Teller', 'bob.teller@maverick.com', '9000000002', 'bobpass', 2);

-- 12. Sample Admin
INSERT INTO admin (name, email, mobile_no, password)
VALUES 
('Super Admin', 'admin@maverick.com', '9999999999', 'adminpass');
DELETE FROM admin
WHERE email = 'admin@maverick.com';
-- 13. Sample Account Holders
INSERT INTO account_holder (account_no, name, email, mobile_no, password, balance, branch_id, ifsc_code, account_type, address, gender, dob, aadhar, pan)
VALUES 
(1001, 'John Doe', 'john.doe@example.com', '9876543210', 'password123', 5000.00, 1, 'MVRK000101', 'SAVINGS', '123 Main St', 'Male', '1985-01-01', 'A111122223333', 'ABCDE1234F'),
(1002, 'Jane Smith', 'jane.smith@example.com', '8765432109', 'securepass', 10000.00, 2, 'MVRK000102', 'SAVINGS', '456 Center Ave', 'Female', '1990-02-15', 'B222233334444', 'FGHIJ5678K'),
(1003, 'Robert Johnson', 'robert.j@example.com', '7654321098', 'robert123', 7500.00, 3, 'MVRK000103', 'CURRENT', '789 North Road', 'Male', '1982-03-20', 'C333344445555', 'LMNOP9123Q');

-- 14. Sample Beneficiaries
INSERT INTO beneficiary (account_no, beneficiary_account_no, beneficiary_name, bank_name, branch_name, ifsc_code)
VALUES
(1001, 1002, 'Jane Smith', 'Maverick Bank', 'City Center', 'MVRK000102'),
(1002, 1003, 'Robert Johnson', 'Maverick Bank', 'North Side', 'MVRK000103');

-- 1. Drop and recreate database
DROP DATABASE IF EXISTS maverickbank;
CREATE DATABASE maverickbank;
USE maverickbank;

-- 2. Drop tables if they exist (in proper FK order)
DROP TABLE IF EXISTS beneficiary;
DROP TABLE IF EXISTS transaction_history;
DROP TABLE IF EXISTS loan_application;
DROP TABLE IF EXISTS account_holder;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS branch;

-- 3. Create Branch table
CREATE TABLE branch (
    branch_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    branch_name VARCHAR(100) NOT NULL,
    ifsc_code VARCHAR(20) UNIQUE NOT NULL,
    address VARCHAR(255)
);

-- 4. Create Employee table
CREATE TABLE employee (
    employee_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    mobile_no VARCHAR(15) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(30) DEFAULT 'EMPLOYEE',
    branch_id BIGINT,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    FOREIGN KEY (branch_id) REFERENCES branch(branch_id)
);

-- 5. Create Admin table
CREATE TABLE admin (
    admin_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    mobile_no VARCHAR(15) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(30) DEFAULT 'ADMIN',
    status VARCHAR(20) DEFAULT 'ACTIVE'
);

-- 6. Create Account Holder table (with branch_id and extra fields)
CREATE TABLE account_holder (
    account_no BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    mobile_no VARCHAR(15) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    balance DOUBLE DEFAULT 0.0,
    address VARCHAR(255),
    gender VARCHAR(10),
    dob DATE,
    aadhar VARCHAR(20),
    pan VARCHAR(20),
    account_type VARCHAR(30),
    branch_id BIGINT,
    ifsc_code VARCHAR(20),
    FOREIGN KEY (branch_id) REFERENCES branch(branch_id)
);

-- 7. Create Beneficiary table
CREATE TABLE beneficiary (
    beneficiary_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_no BIGINT NOT NULL,  -- owner of the beneficiary
    beneficiary_account_no BIGINT NOT NULL, -- destination account
    beneficiary_name VARCHAR(100) NOT NULL,
    bank_name VARCHAR(100),
    branch_name VARCHAR(100),
    ifsc_code VARCHAR(20),
    FOREIGN KEY (account_no) REFERENCES account_holder(account_no)
);

-- 8. Create Loan Application table
CREATE TABLE loan_application (
    loan_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_no BIGINT NOT NULL,
    loan_amount DOUBLE NOT NULL,
    tenure_in_months INT NOT NULL,
    purpose VARCHAR(255),
    status VARCHAR(20) DEFAULT 'PENDING',
    applied_date DATE,
    approved_by BIGINT, -- employee_id who approved/rejected (nullable)
    decision_date DATE,
    FOREIGN KEY (account_no) REFERENCES account_holder(account_no),
    FOREIGN KEY (approved_by) REFERENCES employee(employee_id)
);

-- 9. Create Transaction History table
CREATE TABLE transaction_history (
    transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_no BIGINT NOT NULL,
    type VARCHAR(20), -- deposit, withdrawal, transfer
    amount DOUBLE,
    description VARCHAR(255),
    timestamp DATETIME,
    target_account_no BIGINT, -- for transfers (nullable)
    FOREIGN KEY (account_no) REFERENCES account_holder(account_no)
);

-- 10. Sample Branches
INSERT INTO branch (branch_name, ifsc_code, address)
VALUES 
('Main Branch', 'MVRK000101', '123 Main St'),
('City Center', 'MVRK000102', '456 Center Ave'),
('North Side', 'MVRK000103', '789 North Road');

-- 11. Sample Employees
INSERT INTO employee (name, email, mobile_no, password, branch_id)
VALUES 
('Alice Banker', 'alice.banker@maverick.com', '9000000001', 'alicepass', 1),
('Bob Teller', 'bob.teller@maverick.com', '9000000002', 'bobpass', 2);

-- 12. Sample Admin
INSERT INTO admin (name, email, mobile_no, password)
VALUES 
('Super Admin', 'admin@maverick.com', '9999999999', 'adminpass');
DELETE FROM admin
WHERE email = 'admin@maverick.com';
-- 13. Sample Account Holders
INSERT INTO account_holder (account_no, name, email, mobile_no, password, balance, branch_id, ifsc_code, account_type, address, gender, dob, aadhar, pan)
VALUES 
(1001, 'John Doe', 'john.doe@example.com', '9876543210', 'password123', 5000.00, 1, 'MVRK000101', 'SAVINGS', '123 Main St', 'Male', '1985-01-01', 'A111122223333', 'ABCDE1234F'),
(1002, 'Jane Smith', 'jane.smith@example.com', '8765432109', 'securepass', 10000.00, 2, 'MVRK000102', 'SAVINGS', '456 Center Ave', 'Female', '1990-02-15', 'B222233334444', 'FGHIJ5678K'),
(1003, 'Robert Johnson', 'robert.j@example.com', '7654321098', 'robert123', 7500.00, 3, 'MVRK000103', 'CURRENT', '789 North Road', 'Male', '1982-03-20', 'C333344445555', 'LMNOP9123Q');

-- 14. Sample Beneficiaries
INSERT INTO beneficiary (account_no, beneficiary_account_no, beneficiary_name, bank_name, branch_name, ifsc_code)
VALUES
(1001, 1002, 'Jane Smith', 'Maverick Bank', 'City Center', 'MVRK000102'),
(1002, 1003, 'Robert Johnson', 'Maverick Bank', 'North Side', 'MVRK000103');

DROP TABLE beneficiary;

select * from account_holder;
select * from admin;
select * from employee;
select * from transaction_history;
select * from branch;
select * from loan_application;
select * from account_holder;
select * from admin;
select * from employee;
select * from transaction_history;
select * from branch;
select * from loan_application;



ALTER TABLE loan_application
DROP FOREIGN KEY loan_application_ibfk_1;

ALTER TABLE loan_application
ADD CONSTRAINT loan_application_ibfk_1
FOREIGN KEY (account_no) REFERENCES account_holder(account_no)
ON DELETE CASCADE;
