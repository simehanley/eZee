package com.ezee.client;

public interface EzeeLeaseWebConstants {

	String LOGIN_USER = "eZee Leases Login";
	String REGISTER_USER = "eZee Leases Register User";

	String LEASES = "LEASES";
	String TENANTS = "TENANTS";
	String PREMISES = "PREMISES";
	String CATEGORIES = "CATEGORIES";

	String EDIT_LEASE_TENANT = "Edit Tenant";
	String NEW_LEASE_TENANT = "New Tenant";
	String DELETE_LEASE_TENANT = "Delete Tenant";
	String[] LEASE_TENANT_CRUD_HEADERS = new String[] { NEW_LEASE_TENANT, EDIT_LEASE_TENANT, DELETE_LEASE_TENANT };

	String EDIT_LEASE_PREMISES = "Edit Premises";
	String NEW_LEASE_PREMISES = "New Premises";
	String DELETE_LEASE_PREMISES = "Delete Premises";
	String[] LEASE_PREMISES_CRUD_HEADERS = new String[] { NEW_LEASE_PREMISES, EDIT_LEASE_PREMISES,
			DELETE_LEASE_PREMISES };

	String EDIT_LEASE_CATEGORY = "Edit Category";
	String NEW_LEASE_CATEGORY = "New Category";
	String DELETE_LEASE_CATEGORY = "Delete Category";
	String[] LEASE_CATEGORY_CRUD_HEADERS = new String[] { NEW_LEASE_CATEGORY, EDIT_LEASE_CATEGORY,
			DELETE_LEASE_CATEGORY };

	String EDIT_LEASE = "Edit Lease";
	String NEW_LEASE = "New Lease";
	String DELETE_LEASE = "Delete Lease";
	String[] LEASE_CRUD_HEADERS = new String[] { NEW_LEASE, EDIT_LEASE, DELETE_LEASE };

	String EDIT_LEASE_META_DATA = "Edit Lease Meta Data";
	String NEW_LEASE_META_DATA = "New Lease Meta Data";
	String DELETE_LEASE_META_DATA = "Delete Lease Meta Data";
	String[] LEASE_META_DATA_CRUD_HEADERS = new String[] { NEW_LEASE_META_DATA, EDIT_LEASE_META_DATA,
			DELETE_LEASE_META_DATA };

	String EDIT_LEASE_NOTE = "Edit Lease Note";
	String NEW_LEASE_NOTE = "New Lease Note";
	String DELETE_LEASE_NOTE = "Delete Lease Note";
	String[] LEASE_NOTE_CRUD_HEADERS = new String[] { NEW_LEASE_NOTE, EDIT_LEASE_NOTE, DELETE_LEASE_NOTE };

	String SHOW_INACTIVE_LEASES = "SHOW_INACTIVE";
	String SHOW_LEASE_SUMMARY = "SHOW_SUMMARY";
	String LEASE_SUMMARY_IN_MONTHS = "LEASE_SUMMARY_IN_MONTHS";

	String VACANT_TENANT_NAME = "VACANT";

	String LEASE_ID = "leaseId";
	String LEASE_FILE_NAME = "filename";
}