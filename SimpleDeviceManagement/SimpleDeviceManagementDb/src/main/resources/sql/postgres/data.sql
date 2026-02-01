-- Departments
INSERT INTO departments (name, created_at, updated_at)
SELECT d.name, NOW(), NOW()
FROM (
  SELECT 'IT Department' AS name UNION ALL
  SELECT 'HR Department' AS name
) d
WHERE NOT EXISTS (SELECT 1 FROM departments);

-- Users
INSERT INTO users (created_at, updated_at, username, name, enabled, department_id)
SELECT u.created_at, u.updated_at, u.username, u.name, u.enabled, u.department_id
FROM (
  SELECT NOW() AS created_at, NOW() AS updated_at, 'jdoe' AS username, 'John Doe' AS name, TRUE AS enabled, (SELECT id FROM departments WHERE name = 'IT Department') AS department_id UNION ALL
  SELECT NOW() AS created_at, NOW() AS updated_at, 'asmith' AS username, 'Alice Smith' AS name, TRUE AS enabled, (SELECT id FROM departments WHERE name = 'HR Department') AS department_id
) u
WHERE NOT EXISTS (SELECT 1 FROM users);

-- Devices
INSERT INTO devices (created_at, updated_at, name, type, status, serial_number, manufacturer, location, purchase_date, user_id)
SELECT d.created_at, d.updated_at, d.name, d.type, d.status, d.serial_number, d.manufacturer, d.location, d.purchase_date, d.user_id
FROM (
  SELECT NOW() AS created_at, NOW() AS updated_at, 'Laptop X' AS name, 'Laptop' AS type, 'ACTIVE' AS status, 'SN123456' AS serial_number, 'Dell' AS manufacturer, 'Office 101' AS location, '2023-01-15'::DATE AS purchase_date, (SELECT id FROM users WHERE username = 'jdoe') AS user_id UNION ALL
  SELECT NOW() AS created_at, NOW() AS updated_at, 'Phone Y' AS name, 'Mobile' AS type, 'INACTIVE' AS status, 'SN789012' AS serial_number, 'Apple' AS manufacturer, 'Office 102' AS location, '2022-06-01'::DATE AS purchase_date, (SELECT id FROM users WHERE username = 'asmith') AS user_id
) d
WHERE NOT EXISTS (SELECT 1 FROM devices);

-- Handover Protocols
INSERT INTO handover_protocols (
  device_id, receiver_user_id, performed_by_user_id, department_id, 
  description, is_confirmed, confirmed_at, created_at, updated_at, action_type, handover_date
)
SELECT 
  d.id, u_rec.id, u_perf.id, dept.id,
  'Initial handover of Laptop X to John Doe.',
  TRUE,
  NOW(),
  NOW(),
  NOW(),
  'ASSIGN',
  NOW()
FROM devices d, users u_rec, users u_perf, departments dept
WHERE d.serial_number = 'SN123456'
  AND u_rec.username = 'jdoe'
  AND u_perf.username = 'jdoe'
  AND dept.name = 'IT Department'
  AND NOT EXISTS (SELECT 1 FROM handover_protocols);

INSERT INTO handover_protocols (
  device_id, receiver_user_id, performed_by_user_id, department_id, 
  description, is_confirmed, confirmed_at, created_at, updated_at, action_type, handover_date
)
SELECT 
  d.id, u_rec.id, u_perf.id, dept.id,
  'Phone Y issued to Alice Smith.',
  FALSE,
  NULL,
  NOW(),
  NOW(),
  'ASSIGN',
  NOW()
FROM devices d, users u_rec, users u_perf, departments dept
WHERE d.serial_number = 'SN789012'
  AND u_rec.username = 'asmith'
  AND u_perf.username = 'jdoe'
  AND dept.name = 'HR Department'
  AND NOT EXISTS (SELECT 1 FROM handover_protocols);