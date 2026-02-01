ALTER SESSION SET CURRENT_SCHEMA = SDMDEV01;
/

-- Departments
DECLARE
  v_count NUMBER;
BEGIN
  SELECT COUNT(*) INTO v_count FROM departments;
  IF v_count = 0 THEN
    INSERT INTO departments (name, created_at, updated_at)
    SELECT 'IT Department', SYSTIMESTAMP, SYSTIMESTAMP FROM dual
    UNION ALL
    SELECT 'HR Department', SYSTIMESTAMP, SYSTIMESTAMP FROM dual;
  END IF;
END;
/

-- Users
DECLARE
  v_count NUMBER;
BEGIN
  SELECT COUNT(*) INTO v_count FROM users;
  IF v_count = 0 THEN
    INSERT INTO users (created_at, updated_at, username, name, enabled, department_id)
    SELECT SYSTIMESTAMP, SYSTIMESTAMP, 'jdoe', 'John Doe', 1, (SELECT id FROM departments WHERE name = 'IT Department') FROM dual
    UNION ALL
    SELECT SYSTIMESTAMP, SYSTIMESTAMP, 'asmith', 'Alice Smith', 1, (SELECT id FROM departments WHERE name = 'HR Department') FROM dual;
  END IF;
END;
/

-- Devices
DECLARE
  v_count NUMBER;
BEGIN
  SELECT COUNT(*) INTO v_count FROM devices;
  IF v_count = 0 THEN
    INSERT INTO devices (created_at, updated_at, name, type, status, serial_number, manufacturer, location, purchase_date, user_id)
    SELECT SYSTIMESTAMP, SYSTIMESTAMP, 'Laptop X', 'Laptop', 'ACTIVE', 'SN123456', 'Dell', 'Office 101', DATE '2023-01-15', (SELECT id FROM users WHERE username = 'jdoe') FROM dual
    UNION ALL
    SELECT SYSTIMESTAMP, SYSTIMESTAMP, 'Phone Y', 'Mobile', 'INACTIVE', 'SN789012', 'Apple', 'Office 102', DATE '2022-06-01', (SELECT id FROM users WHERE username = 'asmith') FROM dual;
  END IF;
END;
/

-- Handover Protocols
DECLARE
  v_count NUMBER;
BEGIN
  SELECT COUNT(*) INTO v_count FROM handover_protocols;
  IF v_count = 0 THEN
    INSERT INTO handover_protocols (
      device_id, receiver_user_id, performed_by_user_id, department_id, 
      description, is_confirmed, confirmed_at, created_at, updated_at, action_type, handover_date
    )
    SELECT 
      d.id, u_rec.id, u_perf.id, dept.id,
      'Initial handover of Laptop X to John Doe.',
      1,
      SYSTIMESTAMP,
      SYSTIMESTAMP,
      SYSTIMESTAMP,
      'ASSIGN',
      SYSTIMESTAMP
    FROM devices d, users u_rec, users u_perf, departments dept
    WHERE d.serial_number = 'SN123456'
      AND u_rec.username = 'jdoe'
      AND u_perf.username = 'jdoe'
      AND dept.name = 'IT Department';

    INSERT INTO handover_protocols (
      device_id, receiver_user_id, performed_by_user_id, department_id, 
      description, is_confirmed, confirmed_at, created_at, updated_at, action_type, handover_date
    )
    SELECT 
      d.id, u_rec.id, u_perf.id, dept.id,
      'Phone Y issued to Alice Smith.',
      0,
      NULL,
      SYSTIMESTAMP,
      SYSTIMESTAMP,
      'ASSIGN',
      SYSTIMESTAMP
    FROM devices d, users u_rec, users u_perf, departments dept
    WHERE d.serial_number = 'SN789012'
      AND u_rec.username = 'asmith'
      AND u_perf.username = 'jdoe'
      AND dept.name = 'HR Department';
  END IF;
END;
/

COMMIT;
