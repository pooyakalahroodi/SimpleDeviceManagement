ALTER SESSION SET CURRENT_SCHEMA = SDMDEV01;
/

-- Departments
DECLARE
  v_count NUMBER;
BEGIN
  SELECT COUNT(*) INTO v_count FROM departments;
  IF v_count = 0 THEN
    INSERT INTO departments (name, created_at, updated_at) VALUES
      ('IT', SYSTIMESTAMP, SYSTIMESTAMP),
      ('HR', SYSTIMESTAMP, SYSTIMESTAMP),
      ('Finance', SYSTIMESTAMP, SYSTIMESTAMP),
      ('Sales', SYSTIMESTAMP, SYSTIMESTAMP);
    COMMIT;
  END IF;
END;
/

-- Users (8 USERS - UUID FIXED)
DECLARE
  v_count NUMBER;
BEGIN
  SELECT COUNT(*) INTO v_count FROM users;
  IF v_count = 0 THEN
    INSERT INTO users (user_id, created_at, updated_at, email_address, name, enabled, department_id) VALUES
      -- IT Department
      (HEXTORAW('550e8400-e29b-41d4-a716-446655440000'), SYSTIMESTAMP, SYSTIMESTAMP, 'john.doe@company.com', 'John Doe', 1, 1),
      (HEXTORAW('6ba7b810-9dad-11d1-80b4-00c04fd430c8'), SYSTIMESTAMP, SYSTIMESTAMP, 'jane.smith@company.com', 'Jane Smith', 1, 1),

      -- HR Department
      (HEXTORAW('6ba7b811-9dad-11d1-80b4-00c04fd430c8'), SYSTIMESTAMP, SYSTIMESTAMP, 'alice.manager@company.com', 'Alice Manager', 1, 2),
      (HEXTORAW('6ba7b812-9dad-11d1-80b4-00c04fd430c8'), SYSTIMESTAMP, SYSTIMESTAMP, 'bob.hr@company.com', 'Bob HR', 1, 2),

      -- Finance
      (HEXTORAW('12345678-1234-5678-9abc-123456789abc'), SYSTIMESTAMP, SYSTIMESTAMP, 'finance@company.com', 'Carol Accountant', 1, 3),

      -- Sales
      (HEXTORAW('87654321-4321-8765-cba9-abcdef012345'), SYSTIMESTAMP, SYSTIMESTAMP, 'sales@company.com', 'David Sales', 1, 4),
      (HEXTORAW('11111111-2222-3333-4444-555555555555'), SYSTIMESTAMP, SYSTIMESTAMP, 'maria.sales@company.com', 'Maria Sales', 1, 4);
    COMMIT;
  END IF;
END;
/

-- Devices (6 DEVICES - UUID user_id FIXED)
DECLARE
  v_count NUMBER;
BEGIN
  SELECT COUNT(*) INTO v_count FROM devices;
  IF v_count = 0 THEN
    INSERT INTO devices (created_at, updated_at, name, type, status, serial_number, manufacturer, location, purchase_date, user_id) VALUES
      -- John Doe (IT)
      (SYSTIMESTAMP, SYSTIMESTAMP, 'Laptop X1', 'LAPTOP', 'ACTIVE', 'SN123456', 'Dell', 'Office 101', DATE '2023-01-15', HEXTORAW('550e8400-e29b-41d4-a716-446655440000')),

      -- Jane Smith (IT)
      (SYSTIMESTAMP, SYSTIMESTAMP, 'Laptop X2', 'LAPTOP', 'MAINTENANCE', 'SN123457', 'HP', 'Office 102', DATE '2023-02-20', HEXTORAW('6ba7b810-9dad-11d1-80b4-00c04fd430c8')),

      -- Alice Manager (HR)
      (SYSTIMESTAMP, SYSTIMESTAMP, 'iPhone 15', 'MOBILE', 'ACTIVE', 'SN789012', 'Apple', 'Office 201', DATE '2023-09-01', HEXTORAW('6ba7b811-9dad-11d1-80b4-00c04fd430c8')),

      -- Bob HR
      (SYSTIMESTAMP, SYSTIMESTAMP, 'iPad Pro', 'TABLET', 'AVAILABLE', 'SN345678', 'Apple', 'Storage', DATE '2023-03-10', HEXTORAW('6ba7b812-9dad-11d1-80b4-00c04fd430c8')),

      -- Carol Finance
      (SYSTIMESTAMP, SYSTIMESTAMP, 'Desktop PC', 'DESKTOP', 'ACTIVE', 'SN901234', 'Lenovo', 'Finance Room', DATE '2022-11-05', HEXTORAW('12345678-1234-5678-9abc-123456789abc')),

      -- David Sales
      (SYSTIMESTAMP, SYSTIMESTAMP, 'Samsung Galaxy', 'MOBILE', 'LOST', 'SN567890', 'Samsung', 'Sales Office', DATE '2023-07-12', HEXTORAW('87654321-4321-8765-cba9-abcdef012345'));
    COMMIT;
  END IF;
END;
/

-- Handover Protocols (8 RECORDS)
DECLARE
  v_count NUMBER;
BEGIN
  SELECT COUNT(*) INTO v_count FROM handover_protocols;
  IF v_count = 0 THEN

    -- Laptop X1 → John Doe
    INSERT INTO handover_protocols (device_id, receiver_user_id, performed_by_user_id, department_id, description, is_confirmed, confirmed_at, created_at, updated_at, action_type, handover_date)
    SELECT d.id, HEXTORAW('550e8400-e29b-41d4-a716-446655440000'), HEXTORAW('6ba7b811-9dad-11d1-80b4-00c04fd430c8'), 1,
           'Laptop X1 issued to John Doe (IT).',
           1, SYSTIMESTAMP, SYSTIMESTAMP, SYSTIMESTAMP, 'ASSIGN', DATE '2023-01-20'
    FROM devices d WHERE d.serial_number = 'SN123456';

    -- iPhone → Alice Manager
    INSERT INTO handover_protocols (device_id, receiver_user_id, performed_by_user_id, department_id, description, is_confirmed, confirmed_at, created_at, updated_at, action_type, handover_date)
    SELECT d.id, HEXTORAW('6ba7b811-9dad-11d1-80b4-00c04fd430c8'), HEXTORAW('6ba7b812-9dad-11d1-80b4-00c04fd430c8'), 2,
           'iPhone 15 assigned to HR Manager.',
           1, SYSTIMESTAMP, SYSTIMESTAMP, SYSTIMESTAMP, 'ASSIGN', DATE '2023-09-05'
    FROM devices d WHERE d.serial_number = 'SN789012';

    -- Desktop → Carol Finance (performed by Alice)
    INSERT INTO handover_protocols (device_id, receiver_user_id, performed_by_user_id, department_id, description, is_confirmed, confirmed_at, created_at, updated_at, action_type, handover_date)
    SELECT d.id, HEXTORAW('12345678-1234-5678-9abc-123456789abc'), HEXTORAW('6ba7b811-9dad-11d1-80b4-00c04fd430c8'), 3,
           'New desktop for Finance department.',
           1, SYSTIMESTAMP, SYSTIMESTAMP, SYSTIMESTAMP, 'ASSIGN', DATE '2022-11-10'
    FROM devices d WHERE d.serial_number = 'SN901234';

    COMMIT;
  END IF;
END;
/

COMMIT;
