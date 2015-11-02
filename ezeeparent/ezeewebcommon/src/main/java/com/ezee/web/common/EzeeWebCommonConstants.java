package com.ezee.web.common;

import com.ezee.web.common.service.EzeeUserService;
import com.ezee.web.common.service.EzeeUserServiceAsync;
import com.google.gwt.core.client.GWT;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeWebCommonConstants {

	String ERROR = "Error";
	String REGISTRATION_ERROR = "Registration Error";
	String LOGIN_ERROR = "Login Error";

	EzeeUserServiceAsync USER_SERVICE = GWT.create(EzeeUserService.class);
}