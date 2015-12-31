USE EZEE;

ALTER TABLE EZEE_PAYEE DROP COLUMN SOURCE;

ALTER TABLE EZEE_PAYEE ADD PAYEE_TYPE VARCHAR(50) NOT NULL DEFAULT 'Supplier';

RENAME TABLE EZEE_INVOICE_TO_PAYEE_MAPPING TO EZEE_INVOICE_TO_SUPPLIER_MAPPING;

ALTER TABLE EZEE_PROJECT_ITEM_DETAIL ADD MANUAL_TAX BOOLEAN NULL DEFAULT FALSE;

ALTER TABLE EZEE_PROJECT_ITEM_DETAIL ADD REVERSE_TAX BOOLEAN NULL DEFAULT TRUE;

ALTER TABLE EZEE_PROJECT_PAYMENT ADD MANUAL_TAX BOOLEAN NULL DEFAULT FALSE;

ALTER TABLE EZEE_PROJECT_PAYMENT ADD REVERSE_TAX BOOLEAN NULL DEFAULT TRUE;