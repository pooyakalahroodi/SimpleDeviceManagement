-- pgcrypto for UUID
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Departments
INSERT INTO departments (name, created_at, updated_at)
SELECT name, NOW(), NOW()
FROM (VALUES ('IT Department'), ('HR Department'), ('Finance'), ('Sales')) t(name)
WHERE NOT EXISTS (SELECT 1 FROM departments WHERE name = t.name);

-- Users (email_address + name/surname)
INSERT INTO users (created_at, updated_at, email_address, name, surname, enabled, department_id)
SELECT NOW(), NOW(), email_address, name, surname, enabled, dept_id
FROM (VALUES
  ('john.doe@company.com', 'John', 'Doe', true, 1),
  ('alice.manager@company.com', 'Alice', 'Manager', true, 2)
) u(email_address, name, surname, enabled, dept_id)
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email_address = u.email_address);

-- Devices (VALID statuses ONLY)
INSERT INTO devices (created_at, updated_at, name, type, status, serial_number, manufacturer, location, purchase_date, user_id)
SELECT NOW(), NOW(), name, type, status, serial_number, manufacturer, location, purchase_date::date, user_id
FROM (VALUES
  ('Laptop X', 'Laptop', 'ACTIVE', 'SN123456', 'Dell', 'Office 101', '2023-01-15', (SELECT id FROM users WHERE email_address = 'john.doe@company.com')),
  ('Phone Y', 'Mobile', 'INACTIVE', 'SN789012', 'Apple', 'Office 102', '2022-06-01', (SELECT id FROM users WHERE email_address = 'alice.manager@company.com'))
) d(name, type, status, serial_number, manufacturer, location, purchase_date, user_id)
ON CONFLICT (serial_number) DO NOTHING;


-- Handover Protocols (complete this section)
INSERT INTO handover_protocols (
  created_at, updated_at, device_id, handover_date,
  performed_by_user_id, receiver_user_id, action_type,
  is_confirmed, confirmed_at, description
)
SELECT NOW(), NOW(), device_id, handover_date, performed_by_user_id,
       receiver_user_id, action_type, is_confirmed, confirmed_at, description
FROM (VALUES
  -- Example: Handover Laptop X from John to Alice
  ((SELECT id FROM devices WHERE serial_number = 'SN123456'),
   NOW(),
   (SELECT id FROM users WHERE email_address = 'john.doe@company.com'),
   (SELECT id FROM users WHERE email_address = 'alice.manager@company.com'),
   'HANDOVER', true, NOW(), 'Initial handover confirmed'),

  -- Example: Return Phone Y
  ((SELECT id FROM devices WHERE serial_number = 'SN789012'),
   NOW() - INTERVAL '1 day',
   (SELECT id FROM users WHERE email_address = 'alice.manager@company.com'),
   (SELECT id FROM users WHERE email_address = 'john.doe@company.com'),
   'RETURN', true, NOW(), 'Device returned')
) h(device_id, handover_date, performed_by_user_id, receiver_user_id,
   action_type, is_confirmed, confirmed_at, description)
ON CONFLICT DO NOTHING;

