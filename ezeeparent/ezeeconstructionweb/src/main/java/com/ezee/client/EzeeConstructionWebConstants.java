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

	String MODIFIED = "MODIFIED";
	String UNMODIFIED = "UN-MODIFIED";
	String ERROR = "ERROR";

	double DEFAULT_PAYMENT = 5000.;
	double DEFAULT_ITEM_DETAIL = 10000.;
}