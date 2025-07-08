-- Insert departments
INSERT INTO departments (name, created_at, updated_at)
VALUES
  ('IT Department', NOW(), NOW()),
  ('HR Department', NOW(), NOW());

-- Insert users
INSERT INTO users (created_at, updated_at, username, name, enabled, department_id)
VALUES
  (NOW(), NOW(), 'jdoe', 'John Doe', TRUE, 1),
  (NOW(), NOW(), 'asmith', 'Alice Smith', TRUE, 2);

-- Insert devices
INSERT INTO devices (
  created_at, updated_at, name, type, status, serial_number, manufacturer, location, purchase_date, user_id
)
VALUES
  (NOW(), NOW(), 'Laptop X', 'Laptop', 'Active', 'SN123456', 'Dell', 'Office 101', '2023-01-15', 1),
  (NOW(), NOW(), 'Phone Y', 'Mobile', 'Inactive', 'SN789012', 'Apple', 'Office 102', '2022-06-01', 2);

-- Insert handover protocols
INSERT INTO handover_protocols (
  device_id, user_id, department_id, comment, confirmed, confirmed_at, created_at, updated_at
)
VALUES
  (1, 1, 1, 'Initial handover of Laptop X to John Doe.', TRUE, NOW(), NOW(), NOW()),
  (2, 2, 2, 'Phone Y issued to Alice Smith.', FALSE, NULL, NOW(), NOW());