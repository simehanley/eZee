package com.ezee.client;

public interface EzeeLeaseWebConstants {

	String LOGIN_USER = "eZee Leases Login";
	String REGISTER_USER = "eZee Leases Register User";

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
}
