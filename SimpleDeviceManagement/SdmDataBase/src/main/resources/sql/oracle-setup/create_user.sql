-- create_user.sql (idempotent)
-- Dieses Skript wird als SYSDBA ausgeführt.

WHENEVER SQLERROR CONTINUE;

-- Sicherstellen, dass wir in der PDB sind
ALTER SESSION SET CONTAINER = FREEPDB1;

-- User sdmdev01 anlegen oder Passwort aktualisieren
DECLARE
  v_count NUMBER;
BEGIN
  SELECT count(*) INTO v_count FROM dba_users WHERE username = 'SDMDEV01';
  IF v_count = 0 THEN
    EXECUTE IMMEDIATE 'CREATE USER sdmdev01 IDENTIFIED BY tyKul25 DEFAULT TABLESPACE users QUOTA UNLIMITED ON users';
  ELSE
    EXECUTE IMMEDIATE 'ALTER USER sdmdev01 IDENTIFIED BY tyKul25';
  END IF;
END;
/

-- Berechtigungen vergeben
GRANT CREATE SESSION TO sdmdev01;
GRANT CREATE TABLE TO sdmdev01;
GRANT CREATE SEQUENCE TO sdmdev01;
GRANT CREATE VIEW TO sdmdev01;
GRANT CREATE PROCEDURE TO sdmdev01;
GRANT CREATE ANY INDEX TO sdmdev01;

-- Wichtig für Hibernate/JPA
ALTER USER sdmdev01 QUOTA UNLIMITED ON users;

COMMIT;
