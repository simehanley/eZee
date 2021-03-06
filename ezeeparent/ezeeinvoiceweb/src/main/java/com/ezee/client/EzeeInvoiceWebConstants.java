package com.ezee.client;

import com.google.gwt.core.client.GWT;

public interface EzeeInvoiceWebConstants {

	String INVOICES = "INVOICES";
	String PAYMENTS = "PAYMENTS";
	String SUPPLIERS = "SUPPLIERS";
	String PREMISES = "PREMISES";
	String BANK = "Bank";

	String EDIT_PREMISES = "Edit Premises";
	String NEW_PREMISES = "New Premises";
	String DELETE_PREMISES = "Delete Premises";
	String[] PREMISES_CRUD_HEADERS = new String[] { NEW_PREMISES, EDIT_PREMISES, DELETE_PREMISES };

	String EDIT_SUPPLIER = "Edit Supplier";
	String NEW_SUPPLIER = "New Supplier";
	String DELETE_SUPPLIER = "Delete Supplier";
	String[] SUPPLIER_CRUD_HEADERS = new String[] { NEW_SUPPLIER, EDIT_SUPPLIER, DELETE_SUPPLIER };

	String EDIT_INVOICE = "Edit Invoice";
	String NEW_INVOICE = "New Invoice";
	String DELETE_INVOICE = "Delete Invoice";
	String[] INVOICE_CRUD_HEADERS = new String[] { NEW_INVOICE, EDIT_INVOICE, DELETE_INVOICE };

	String EDIT_PAYMENT = "Edit Payment";
	String NEW_PAYMENT = "New Payment";
	String DELETE_PAYMENT = "Delete Payment";
	String[] PAYMENT_CRUD_HEADERS = new String[] { NEW_PAYMENT, EDIT_PAYMENT, DELETE_PAYMENT };

	String INVOICE_ID = "invoiceId";

	String LOGIN_USER = "eZee Invoices Login";
	String REGISTER_USER = "eZee Invoices Register User";

	String SHOW_PAID_INVOICES = "SHOW_PAID";

	EzeeInvoiceServiceAsync INVOICE_SERVICE = GWT.create(EzeeInvoiceService.class);
}