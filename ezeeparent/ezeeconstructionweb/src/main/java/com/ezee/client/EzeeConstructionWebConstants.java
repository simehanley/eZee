package com.ezee.client;

public interface EzeeConstructionWebConstants {

	String LOGIN_USER = "eZee Project Login";
	String REGISTER_USER = "eZee Project Register User";

	String PROJECTS = "PROJECTS";
	String CONTRACTORS = "CONTRACTORS";

	String EDIT_CONTRACTOR = "Edit Contractor";
	String NEW_CONTRACTOR = "New Contractor";
	String DELETE_CONTRACTOR = "Delete Contractor";
	String[] CONTRACTOR_CRUD_HEADERS = new String[] { NEW_CONTRACTOR, EDIT_CONTRACTOR, DELETE_CONTRACTOR };

	String EDIT_PROJECT = "Edit Project";
	String NEW_PROJECT = "New Project";
	String DELETE_PROJECT = "Delete Project";
	String[] PROJECT_CRUD_HEADERS = new String[] { NEW_PROJECT, EDIT_PROJECT, DELETE_PROJECT };

	String EDIT_PROJECT_ITEM = "Edit Project Item";
	String NEW_PROJECT_ITEM = "New Project Item";
	String DELETE_PROJECT_ITEM = "Delete Project Item";
	String[] PROJECT_ITEM_CRUD_HEADERS = new String[] { NEW_PROJECT_ITEM, EDIT_PROJECT_ITEM, DELETE_PROJECT_ITEM };

	String EDIT_PROJECT_ITEM_DETAIL = "Edit Project Item Detail";
	String NEW_PROJECT_ITEM_DETAIL = "New Project Item Detail";
	String DELETE_PROJECT_ITEM_DETAIL = "Delete Project Item Detail";
	String[] PROJECT_ITEM_DETAIL_CRUD_HEADERS = new String[] { NEW_PROJECT_ITEM_DETAIL, EDIT_PROJECT_ITEM_DETAIL,
			DELETE_PROJECT_ITEM_DETAIL };

	String EDIT_PROJECT_PAYMENT = "Edit Project Payment";
	String NEW_PROJECT_PAYMENT = "New Project Payment";
	String DELETE_PROJECT_PAYMENT = "Delete Project Payment";
	String[] PROJECT_PAYMENT_CRUD_HEADERS = new String[] { NEW_PROJECT_PAYMENT, EDIT_PROJECT_PAYMENT,
			DELETE_PROJECT_PAYMENT };

	String MODIFIED = "MODIFIED";
	String UNMODIFIED = "UN-MODIFIED";
	String ERROR = "ERROR";

	double DEFAULT_PAYMENT = 5000.;
	double DEFAULT_ITEM_DETAIL = 10000.;
}