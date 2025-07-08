-- Insert departments
INSERT INTO departments (name, created_at, updated_at)
VALUES
  ('IT Department', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO departments (name, created_at, updated_at)
VALUES
  ('HR Department', SYSTIMESTAMP, SYSTIMESTAMP);

-- Insert users
INSERT INTO users (
  created_at, updated_at, username, name, enabled, department_id
)
VALUES
  (SYSTIMESTAMP, SYSTIMESTAMP, 'jdoe', 'John Doe', 1, 1);

INSERT INTO users (
  created_at, updated_at, username, name, enabled, department_id
)
VALUES
  (SYSTIMESTAMP, SYSTIMESTAMP, 'asmith', 'Alice Smith', 1, 2);

-- Insert devices
INSERT INTO devices (
  created_at, updated_at, name, type, status, serial_number,
  manufacturer, location, purchase_date, user_id
)
VALUES
  (
    SYSTIMESTAMP,
    SYSTIMESTAMP,
    'Laptop X',
    'Laptop',
    'Active',
    'SN123456',
    'Dell',
    'Office 101',
    DATE '2023-01-15',
    1
  );

INSERT INTO devices (
  created_at, updated_at, name, type, status, serial_number,
  manufacturer, location, purchase_date, user_id
)
VALUES
  (
    SYSTIMESTAMP,
    SYSTIMESTAMP,
    'Phone Y',
    'Mobile',
    'Inactive',
    'SN789012',
    'Apple',
    'Office 102',
    DATE '2022-06-01',
    2
  );

-- Insert handover protocols
INSERT INTO handover_protocols (
  device_id, user_id, department_id, description, confirmed,
  confirmed_at, created_at, updated_at
)
VALUES
  (
    1,
    1,
    1,
    'Initial handover of Laptop X to John Doe.',
    1,
    SYSTIMESTAMP,
    SYSTIMESTAMP,
    SYSTIMESTAMP
  );

INSERT INTO handover_protocols (
  device_id, user_id, department_id, description, confirmed,
  confirmed_at, created_at, updated_at
)
VALUES
  (
    2,
    2,
    2,
    'Phone Y issued to Alice Smith.',
    0,
    NULL,
    SYSTIMESTAMP,
    SYSTIMESTAMP
  );
