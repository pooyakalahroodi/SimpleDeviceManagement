-- Drop Handover Protocols first because it depends on devices, users, departments
DROP TABLE handover_protocols CASCADE CONSTRAINTS;

-- Then drop Devices, which depends on Users
DROP TABLE devices CASCADE CONSTRAINTS;

-- Then drop Users, which depends on Departments
DROP TABLE users CASCADE CONSTRAINTS;

-- Finally drop Departments
DROP TABLE departments CASCADE CONSTRAINTS;
