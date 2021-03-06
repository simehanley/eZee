USE EZEE;

ALTER TABLE EZEE_PAYER ADD GRIDID VARCHAR(100) NULL;
ALTER TABLE EZEE_PAYEE ADD GRIDID VARCHAR(100) NULL;
ALTER TABLE EZEE_DEBT_AGE_RULE ADD GRIDID VARCHAR(100) NULL;
ALTER TABLE EZEE_CONFIGURATION ADD GRIDID VARCHAR(100) NULL;
ALTER TABLE EZEE_INVOICE ADD GRIDID VARCHAR(100) NULL;
ALTER TABLE EZEE_PAYMENT ADD GRIDID VARCHAR(100) NULL;
ALTER TABLE EZEE_USER ADD GRIDID VARCHAR(100) NULL;
ALTER TABLE EZEE_PROJECT ADD GRIDID VARCHAR(100) NULL;
ALTER TABLE EZEE_PROJECT_ITEM ADD GRIDID VARCHAR(100) NULL;
ALTER TABLE EZEE_PROJECT_ITEM_DETAIL ADD GRIDID VARCHAR(100) NULL;
ALTER TABLE EZEE_PROJECT_PAYMENT ADD GRIDID VARCHAR(100) NULL;
ALTER TABLE EZEE_LEASE_CATEGORY ADD GRIDID VARCHAR(100) NULL;
ALTER TABLE EZEE_LEASE_BOND ADD GRIDID VARCHAR(100) NULL;
ALTER TABLE EZEE_LEASE_INCIDENTAL ADD GRIDID VARCHAR(100) NULL;
ALTER TABLE EZEE_LEASE_META_DATA ADD GRIDID VARCHAR(100) NULL;
ALTER TABLE EZEE_LEASE ADD GRIDID VARCHAR(100) NULL;
ALTER TABLE EZEE_LEASE_NOTE ADD GRIDID VARCHAR(100) NULL;
ALTER TABLE EZEE_LEASE_FILE ADD GRIDID VARCHAR(100) NULL;