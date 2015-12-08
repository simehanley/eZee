package com.ezee.client;

public interface EzeeConstructionWebConstants {

	String LOGIN_USER = "eZee Project Login";
	String REGISTER_USER = "eZee Project Register User";

	String PROJECTS = "PROJECTS";
	String RESOURCES = "RESOURCES";

	String EDIT_RESOURCE = "Edit Resource";
	String NEW_RESOURCE = "New Resource";
	String DELETE_RESOURCE = "Delete Resource";
	String[] RESOURCE_CRUD_HEADERS = new String[] { NEW_RESOURCE, EDIT_RESOURCE, DELETE_RESOURCE };

	String EDIT_PROJECT = "Edit Project";
	String NEW_PROJECT = "New Project";
	String DELETE_PROJECT = "Delete Project";
	String[] PROJECT_CRUD_HEADERS = new String[] { NEW_PROJECT, EDIT_PROJECT, DELETE_PROJECT };
}