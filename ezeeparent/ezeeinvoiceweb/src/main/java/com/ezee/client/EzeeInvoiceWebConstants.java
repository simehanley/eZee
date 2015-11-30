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

	String EDIT_SUPPLIER = "Edit Supplier";
	String NEW_SUPPLIER = "New Supplier";
	String DELETE_SUPPLIER = "Delete Supplier";

	String EDIT_INVOICE = "Edit Invoice";
	String NEW_INVOICE = "New Invoice";
	String DELETE_INVOICE = "Delete Invoice";

	String EDIT_PAYMENT = "Edit Payment";
	String NEW_PAYMENT = "New Payment";
	String DELETE_PAYMENT = "Delete Payment";

	String INVOICE_ID = "invoiceId";

	String FILE_UPLOAD_SUCCESS = "File Upload Succeeded";
	String FILE_UPLOAD_FAIL = "File Upload Failed";
	String FILE_UPLOAD_CANCELLED = "File Upload Cancelled";

	String FILE_DOWNLOAD_SERVICE = "downloadservice";

	String LOGIN_USER = "eZee Invoices Login";
	String REGISTER_USER = "eZee Invoices Register User";

	EzeeInvoiceServiceAsync INVOICE_SERVICE = GWT.create(EzeeInvoiceService.class);
}