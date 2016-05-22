USE EZEE;

ALTER TABLE EZEE_PAYER ADD PAYER_TYPE VARCHAR(50) NOT NULL DEFAULT 'Premises';

CREATE TABLE IF NOT EXISTS EZEE_LEASE_CATEGORY (
	ID INTEGER NOT NULL AUTO_INCREMENT,
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
	BOND_TYPE VARCHAR(20) NOT NULL,
	BOND_AMOUNT DECIMAL(10,2) NOT NULL,
	BOND_NOTES VARCHAR(1000) NULL,
	CREATED VARCHAR(10) NULL,
	UPDATED VARCHAR(10) NULL,
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS EZEE_LEASE_INCIDENTAL (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	NAME VARCHAR(100) NOT NULL,
	AMOUNT DECIMAL(10,2) NOT NULL,
	TAX_RATE DECIMAL(10,4) NOT NULL,
	PERCENTAGE DECIMAL(10,4) NOT NULL,
	ACCOUNT_NUMBER VARCHAR(20) NULL,
	CREATED VARCHAR(10) NULL,
	UPDATED VARCHAR(10) NULL,
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS EZEE_LEASE_META_DATA (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	META_DATA_TYPE VARCHAR(30) NOT NULL,
	META_DATA_DESCRIPTION VARCHAR(150) NOT NULL,
	META_DATA_VALUE VARCHAR(150) NOT NULL,
	CREATED VARCHAR(10) NULL,
	UPDATED VARCHAR(10) NULL,
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS EZEE_LEASE (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	LEASE_START VARCHAR(10) NOT NULL,
	LEASE_END VARCHAR(10) NOT NULL,
	NOTES VARCHAR(1000) NULL,
	UNITS VARCHAR(100) NOT NULL,
	AREA DECIMAL(10,2) NULL DEFAULT 0.0,
	RESIDENTIAL BOOLEAN NOT NULL DEFAULT FALSE,
	INACTIVE BOOLEAN NOT NULL DEFAULT FALSE,
	HAS_OPTION BOOLEAN NOT NULL DEFAULT FALSE,
	OPTION_START_DATE  VARCHAR(10) NULL,
	OPTION_END_DATE  VARCHAR(10) NULL,
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