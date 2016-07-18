/****************************************
 * BASE SCHEMA
 ****************************************/

/* DROP */
DROP DATABASE IF EXISTS EZEE;

/* CRAETE */
CREATE DATABASE IF NOT EXISTS EZEE;
USE EZEE;

/* TABLES */
CREATE TABLE IF NOT EXISTS EZEE_PAYER (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	GRIDID VARCHAR(100) NULL,
	NAME VARCHAR(200) NOT NULL,
	ADDRESS_LINE_1 VARCHAR(200) NULL,
	ADDRESS_LINE_2 VARCHAR(200) NULL,
	SUBURB VARCHAR(50) NULL,
	CITY VARCHAR(50) NULL,
	STATE VARCHAR(10) NULL,
	POST_CODE VARCHAR(10) NULL,
	PHONE VARCHAR(20) NULL,
	FAX VARCHAR(20) NULL,
	EMAIL VARCHAR(50) NULL,
	CONTACT VARCHAR(100) NULL,
	PAYER_TYPE VARCHAR(50) NOT NULL DEFAULT 'Premises',
	CREATED VARCHAR(10) NULL,
	UPDATED VARCHAR(10) NULL,
	PRIMARY KEY(ID),
	UNIQUE(NAME)
);

CREATE TABLE IF NOT EXISTS EZEE_PAYEE (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	GRIDID VARCHAR(100) NULL,
	NAME VARCHAR(200) NOT NULL,
	ADDRESS_LINE_1 VARCHAR(200) NULL,
	ADDRESS_LINE_2 VARCHAR(200) NULL,
	SUBURB VARCHAR(50) NULL,
	CITY VARCHAR(50) NULL,
	STATE VARCHAR(10) NULL,
	POST_CODE VARCHAR(10) NULL,
	PHONE VARCHAR(20) NULL,
	FAX VARCHAR(20) NULL,
	EMAIL VARCHAR(50) NULL,
	CONTACT VARCHAR(100) NULL,
	BANK VARCHAR(50) NULL,
	ACCOUNT_NAME VARCHAR(100) NULL,
	ACCOUNT_NUMBER VARCHAR(50) NULL,
	ACCOUNT_BSB VARCHAR(20) NULL,
	PAYEE_TYPE VARCHAR(50) NOT NULL DEFAULT 'Supplier',
	CREATED VARCHAR(10) NULL,
	UPDATED VARCHAR(10) NULL,
	PRIMARY KEY(ID),
	UNIQUE(NAME)
);

CREATE TABLE IF NOT EXISTS EZEE_DEBT_AGE_RULE (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	GRIDID VARCHAR(100) NULL,
	NAME VARCHAR(200) NOT NULL,
	EOM BOOLEAN NOT NULL DEFAULT TRUE,
	INTERVAL_LENGTH SMALLINT NOT NULL,
	INTERVAL_TYPE VARCHAR(10) NOT NULL,
	CREATED VARCHAR(10) NULL,
	UPDATED VARCHAR(10) NULL,
	PRIMARY KEY(ID),
	UNIQUE(NAME)
);

CREATE TABLE IF NOT EXISTS EZEE_CONFIGURATION (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	GRIDID VARCHAR(100) NULL,
	VERSION VARCHAR(20) NOT NULL,
	INVOICE_TAX_RATE DECIMAL(4,2) NOT NULL DEFAULT 0.1,
	DEFAULT_DEBT_AGE_RULE VARCHAR(200) NULL,
	DEFAULT_INVOICE_SUPPLIER VARCHAR(200) NULL,
	DEFAULT_INVOICE_PREMISES VARCHAR(200) NULL,
	DEFAULT_MANUAL_TAX BOOLEAN NULL DEFAULT TRUE,
	LICENSEE VARCHAR(200) NULL,
	DEFAULT_REVERSE_TAX BOOLEAN NULL DEFAULT FALSE,
	DEFAULT_SHOW_PAID BOOLEAN NULL DEFAULT TRUE,
	CREATED VARCHAR(10) NULL,
	UPDATED VARCHAR(10) NULL,
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS EZEE_INVOICE (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	GRIDID VARCHAR(100) NULL,
	INVOICE_ID VARCHAR(100) NOT NULL,
	AMOUNT DECIMAL(10,2) NOT NULL,
	TAX DECIMAL(10,2) NOT NULL,
	DESCRIPTION VARCHAR(200) NULL,
	MANUAL_TAX BOOLEAN NOT NULL DEFAULT TRUE,
	CLASSIFICATION VARCHAR(20) NOT NULL DEFAULT 'expense',
	FILENAME VARCHAR(100) NULL,
	FILE MEDIUMBLOB NULL,
	DUE_DATE VARCHAR(10) NULL,
	INVOICE_DATE VARCHAR(10) NULL,
	PAYMENT_DATE VARCHAR(10) NULL,
	REVERSE_TAX BOOLEAN NOT NULL DEFAULT FALSE,
	CREATED VARCHAR(10) NULL,
	UPDATED VARCHAR(10) NULL,
	UNIQUE(INVOICE_ID),
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS EZEE_INVOICE_TO_SUPPLIER_MAPPING (
	INVOICE_ID INTEGER NOT NULL,
	PAYEE_ID INTEGER NOT NULL,
	FOREIGN KEY (INVOICE_ID) REFERENCES EZEE_INVOICE(ID),
	FOREIGN KEY (PAYEE_ID) REFERENCES EZEE_PAYEE(ID),
	PRIMARY KEY(INVOICE_ID,PAYEE_ID)
);

CREATE TABLE IF NOT EXISTS EZEE_INVOICE_TO_PAYER_MAPPING (
	INVOICE_ID INTEGER NOT NULL,
	PAYER_ID INTEGER NOT NULL,
	FOREIGN KEY (INVOICE_ID) REFERENCES EZEE_INVOICE(ID),
	FOREIGN KEY (PAYER_ID) REFERENCES EZEE_PAYER(ID),
	PRIMARY KEY(INVOICE_ID,PAYER_ID)
);

CREATE TABLE IF NOT EXISTS EZEE_INVOICE_TO_DEBT_AGE_MAPPING (
	INVOICE_ID INTEGER NOT NULL,
	DEBT_AGE_RULE_ID INTEGER NOT NULL,
	FOREIGN KEY (INVOICE_ID) REFERENCES EZEE_INVOICE(ID),
	FOREIGN KEY (DEBT_AGE_RULE_ID) REFERENCES EZEE_DEBT_AGE_RULE(ID),
	PRIMARY KEY(INVOICE_ID,DEBT_AGE_RULE_ID)
);

CREATE TABLE IF NOT EXISTS EZEE_PAYMENT (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	GRIDID VARCHAR(100) NULL,
	PAYMENT_DATE VARCHAR(10) NOT NULL,
	PAYMENT_TYPE VARCHAR(20) NOT NULL,
	DESCRIPTION VARCHAR(200) NOT NULL,
	CHEQUE_NUMBER VARCHAR(30) NULL,
	CHEQUE_PRESENTED BOOLEAN NOT NULL DEFAULT FALSE,
	CREATED VARCHAR(10) NULL,
	UPDATED VARCHAR(10) NULL,
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS EZEE_PAYMENT_TO_INVOICE_MAPPING (
	INVOICE_ID INTEGER NOT NULL,
	PAYMENT_ID INTEGER NOT NULL,
	FOREIGN KEY (INVOICE_ID) REFERENCES EZEE_INVOICE(ID),
	FOREIGN KEY (PAYMENT_ID) REFERENCES EZEE_PAYMENT(ID),
	PRIMARY KEY(INVOICE_ID,PAYMENT_ID)
);

CREATE TABLE IF NOT EXISTS EZEE_USER (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	GRIDID VARCHAR(100) NULL,
	FIRST_NAME VARCHAR(100) NULL,
	LAST_NAME VARCHAR(100) NULL,
	USER_NAME VARCHAR(50) NOT NULL,
	PASSWORD VARCHAR(100) NOT NULL,
	EMAIL VARCHAR(100) NULL,
	CREATED VARCHAR(10) NULL,
	UPDATED VARCHAR(10) NULL,
	PRIMARY KEY(ID),
	UNIQUE(USER_NAME),
	UNIQUE(EMAIL)
);

CREATE TABLE IF NOT EXISTS EZEE_PROJECT (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	GRIDID VARCHAR(100) NULL,
	NAME VARCHAR(200) NOT NULL,
	START_DATE VARCHAR(10) NULL,
	END_DATE VARCHAR(10) NULL,
	ADDRESS_LINE_1 VARCHAR(200) NULL,
	ADDRESS_LINE_2 VARCHAR(200) NULL,
	SUBURB VARCHAR(50) NULL,
	CITY VARCHAR(50) NULL,
	STATE VARCHAR(10) NULL,
	POST_CODE VARCHAR(10) NULL,
	PHONE VARCHAR(20) NULL,
	FAX VARCHAR(20) NULL,
	EMAIL VARCHAR(50) NULL,
	CONTACT VARCHAR(100) NULL,
	CREATED VARCHAR(10) NULL,
	UPDATED VARCHAR(10) NULL,
	PRIMARY KEY(ID),
	UNIQUE(NAME)
);

CREATE TABLE IF NOT EXISTS EZEE_PROJECT_ITEM (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	GRIDID VARCHAR(100) NULL,
	NAME VARCHAR(200) NOT NULL,
	PROJECT_ID INTEGER NULL,
	CREATED VARCHAR(10) NULL,
	UPDATED VARCHAR(10) NULL,
	FOREIGN KEY (PROJECT_ID) REFERENCES EZEE_PROJECT(ID),
	PRIMARY KEY(ID),
	UNIQUE(NAME, PROJECT_ID)
);

CREATE TABLE IF NOT EXISTS EZEE_PROJECT_ITEM_TO_CONTRACTOR_MAPPING (
	ITEM_ID INTEGER NOT NULL,
	CONTRACTOR_ID INTEGER NOT NULL,
	FOREIGN KEY (ITEM_ID) REFERENCES EZEE_PROJECT_ITEM(ID),
	FOREIGN KEY (CONTRACTOR_ID) REFERENCES EZEE_PAYEE(ID),
	PRIMARY KEY(ITEM_ID,CONTRACTOR_ID)
);

CREATE TABLE IF NOT EXISTS EZEE_PROJECT_ITEM_DETAIL (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	GRIDID VARCHAR(100) NULL,
	DESCRIPTION TEXT NOT NULL,
	ITEM_TYPE VARCHAR(10) NOT NULL,
	AMOUNT DECIMAL(10,2) NOT NULL,
	TAX DECIMAL(10,2) NOT NULL,
	ITEM_ID INTEGER NULL,
	MANUAL_TAX BOOLEAN NOT NULL DEFAULT TRUE,
	REVERSE_TAX BOOLEAN NOT NULL DEFAULT FALSE,
	CREATED VARCHAR(10) NULL,
	UPDATED VARCHAR(10) NULL,
	FOREIGN KEY (ITEM_ID) REFERENCES EZEE_PROJECT_ITEM(ID),
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS EZEE_PROJECT_PAYMENT (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	GRIDID VARCHAR(100) NULL,
	PAYMENT_DATE VARCHAR(10) NOT NULL,
	PAYMENT_TYPE VARCHAR(20) NOT NULL,
	DESCRIPTION TEXT NOT NULL,
	AMOUNT DECIMAL(10,2) NOT NULL,
	TAX DECIMAL(10,2) NOT NULL,
	CHEQUE_NUMBER VARCHAR(30) NULL,
	ITEM_ID INTEGER NULL,
	MANUAL_TAX BOOLEAN NOT NULL DEFAULT TRUE,
	REVERSE_TAX BOOLEAN NOT NULL DEFAULT FALSE,
	INVOICE_REF VARCHAR(100) NULL,
	CREATED VARCHAR(10) NULL,
	UPDATED VARCHAR(10) NULL,
	FOREIGN KEY (ITEM_ID) REFERENCES EZEE_PROJECT_ITEM(ID),
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS EZEE_LEASE_CATEGORY (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	GRIDID VARCHAR(100) NULL,
	NAME VARCHAR(200) NOT NULL,
	CATEGORY_COMPANY VARCHAR(200) NOT NULL,
	ADDRESS_LINE_1 VARCHAR(200) NULL,
	ADDRESS_LINE_2 VARCHAR(200) NULL,
	SUBURB VARCHAR(50) NULL,
	CITY VARCHAR(50) NULL,
	STATE VARCHAR(10) NULL,
	POST_CODE VARCHAR(10) NULL,
	PHONE VARCHAR(20) NULL,
	FAX VARCHAR(20) NULL,
	EMAIL VARCHAR(50) NULL,
	CONTACT VARCHAR(100) NULL,
	BANK VARCHAR(50) NULL,
	ACCOUNT_NAME VARCHAR(100) NULL,
	ACCOUNT_NUMBER VARCHAR(50) NULL,
	ACCOUNT_BSB VARCHAR(20) NULL,
	ABN VARCHAR(20) NULL,
	CREATED VARCHAR(10) NULL,
	UPDATED VARCHAR(10) NULL,
	PRIMARY KEY(ID),
	UNIQUE(NAME)
);

CREATE TABLE IF NOT EXISTS EZEE_LEASE_BOND (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	GRIDID VARCHAR(100) NULL,
	BOND_TYPE VARCHAR(20) NOT NULL,
	BOND_AMOUNT DECIMAL(10,2) NOT NULL,
	BOND_NOTES VARCHAR(1000) NULL,
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS EZEE_LEASE_INCIDENTAL (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	GRIDID VARCHAR(100) NULL,
	NAME VARCHAR(100) NOT NULL,
	AMOUNT DECIMAL(10,2) NOT NULL,
	TAX_RATE DECIMAL(10,4) NOT NULL,
	PERCENTAGE DECIMAL(10,4) NOT NULL,
	ACCOUNT_NUMBER VARCHAR(20) NULL,
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS EZEE_LEASE_META_DATA (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	GRIDID VARCHAR(100) NULL,
	SORT_ORDER INTEGER NOT NULL DEFAULT 0,
	CREATED VARCHAR(10) NOT NULL,
	META_DATA_TYPE VARCHAR(30) NOT NULL,
	META_DATA_DESCRIPTION VARCHAR(150) NOT NULL,
	META_DATA_VALUE VARCHAR(150) NOT NULL,
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS EZEE_LEASE (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	GRIDID VARCHAR(100) NULL,
	LEASE_START VARCHAR(10) NOT NULL,
	LEASE_END VARCHAR(10) NOT NULL,
	UNITS VARCHAR(100) NOT NULL,
	AREA DECIMAL(10,2) NULL DEFAULT 0.0,
	RESIDENTIAL BOOLEAN NOT NULL DEFAULT FALSE,
	INACTIVE BOOLEAN NOT NULL DEFAULT FALSE,
	HAS_OPTION BOOLEAN NOT NULL DEFAULT FALSE,
	OPTION_START_DATE  VARCHAR(10) NULL,
	OPTION_END_DATE  VARCHAR(10) NULL,
	OPTION_EXERCISED BOOLEAN NOT NULL DEFAULT FALSE,
	JOB_NUMBER VARCHAR(20) NULL,
	CREATED VARCHAR(10) NULL,
	UPDATED VARCHAR(10) NULL,
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS EZEE_LEASE_TO_META_DATA_MAPPING (
	LEASE_ID INTEGER NOT NULL,
	META_DATA_ID INTEGER NOT NULL,
	FOREIGN KEY (LEASE_ID) REFERENCES EZEE_LEASE(ID),
	FOREIGN KEY (META_DATA_ID) REFERENCES EZEE_LEASE_META_DATA(ID),
	PRIMARY KEY(LEASE_ID,META_DATA_ID)
);

CREATE TABLE IF NOT EXISTS EZEE_LEASE_TO_LEASE_INCIDENTAL_MAPPING (
	LEASE_ID INTEGER NOT NULL,
	LEASE_INCIDENTAL_ID INTEGER NOT NULL,
	FOREIGN KEY (LEASE_ID) REFERENCES EZEE_LEASE(ID),
	FOREIGN KEY (LEASE_INCIDENTAL_ID) REFERENCES EZEE_LEASE_INCIDENTAL(ID),
	PRIMARY KEY(LEASE_ID,LEASE_INCIDENTAL_ID)
);

CREATE TABLE IF NOT EXISTS EZEE_LEASE_TO_TENANT_MAPPING (
	LEASE_ID INTEGER NOT NULL,
	TENANT_ID INTEGER NOT NULL,
	FOREIGN KEY (LEASE_ID) REFERENCES EZEE_LEASE(ID),
	FOREIGN KEY (TENANT_ID) REFERENCES EZEE_PAYER(ID),
	PRIMARY KEY(LEASE_ID,TENANT_ID)
);

CREATE TABLE IF NOT EXISTS EZEE_LEASE_TO_PREMISES_MAPPING (
	LEASE_ID INTEGER NOT NULL,
	PREMISES_ID INTEGER NOT NULL,
	FOREIGN KEY (LEASE_ID) REFERENCES EZEE_LEASE(ID),
	FOREIGN KEY (PREMISES_ID) REFERENCES EZEE_PAYEE(ID),
	PRIMARY KEY(LEASE_ID,PREMISES_ID)
);

CREATE TABLE IF NOT EXISTS EZEE_LEASE_TO_CATEGORY_MAPPING (
	LEASE_ID INTEGER NOT NULL,
	CATEGORY_ID INTEGER NOT NULL,
	FOREIGN KEY (LEASE_ID) REFERENCES EZEE_LEASE(ID),
	FOREIGN KEY (CATEGORY_ID) REFERENCES EZEE_LEASE_CATEGORY(ID),
	PRIMARY KEY(LEASE_ID,CATEGORY_ID)
);

CREATE TABLE IF NOT EXISTS EZEE_LEASE_TO_BOND_MAPPING (
	LEASE_ID INTEGER NOT NULL,
	BOND_ID INTEGER NOT NULL,
	FOREIGN KEY (LEASE_ID) REFERENCES EZEE_LEASE(ID),
	FOREIGN KEY (BOND_ID) REFERENCES EZEE_LEASE_BOND(ID),
	PRIMARY KEY(LEASE_ID,BOND_ID)
);


CREATE TABLE IF NOT EXISTS EZEE_LEASE_NOTE (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	GRIDID VARCHAR(100) NULL,
	SORT_ORDER INTEGER NOT NULL DEFAULT 0,
	CREATED VARCHAR(10) NOT NULL,
	NOTE VARCHAR(1000) NULL,
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS EZEE_LEASE_FILE (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	GRIDID VARCHAR(100) NULL,
	SORT_ORDER INTEGER NOT NULL DEFAULT 0,
	CREATED VARCHAR(10) NOT NULL,
	FILENAME VARCHAR(200) NOT NULL,
	FILE MEDIUMBLOB NULL,
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS EZEE_LEASE_TO_NOTES_MAPPING (
	LEASE_ID INTEGER NOT NULL,
	NOTE_ID INTEGER NOT NULL,
	FOREIGN KEY (LEASE_ID) REFERENCES EZEE_LEASE(ID),
	FOREIGN KEY (NOTE_ID) REFERENCES EZEE_LEASE_NOTE(ID),
	PRIMARY KEY(LEASE_ID,NOTE_ID)
);

CREATE TABLE IF NOT EXISTS EZEE_LEASE_TO_FILES_MAPPING (
	LEASE_ID INTEGER NOT NULL,
	FILE_ID INTEGER NOT NULL,
	FOREIGN KEY (LEASE_ID) REFERENCES EZEE_LEASE(ID),
	FOREIGN KEY (FILE_ID) REFERENCES EZEE_LEASE_FILE(ID),
	PRIMARY KEY(LEASE_ID,FILE_ID)
);

/****************************************
 * BASE DATA
 ****************************************/
INSERT INTO EZEE_CONFIGURATION (VERSION, INVOICE_TAX_RATE) VALUES ('1.0.0', 0.1);
UPDATE EZEE_CONFIGURATION SET DEFAULT_DEBT_AGE_RULE = '2M';
UPDATE EZEE_CONFIGURATION SET LICENSEE = 'Test Company Pty Ltd';

INSERT INTO EZEE_DEBT_AGE_RULE (NAME, EOM, INTERVAL_LENGTH, INTERVAL_TYPE) VALUES ('7D', FALSE, 7, 'days');
INSERT INTO EZEE_DEBT_AGE_RULE (NAME, EOM, INTERVAL_LENGTH, INTERVAL_TYPE) VALUES ('1M', TRUE, 1, 'months');
INSERT INTO EZEE_DEBT_AGE_RULE (NAME, EOM, INTERVAL_LENGTH, INTERVAL_TYPE) VALUES ('2M', TRUE, 2, 'months');
INSERT INTO EZEE_DEBT_AGE_RULE (NAME, EOM, INTERVAL_LENGTH, INTERVAL_TYPE) VALUES ('3M', TRUE, 3, 'months');

INSERT INTO EZEE_PAYER(NAME, PAYER_TYPE) VALUES ('VACANT', 'LeaseTenant')