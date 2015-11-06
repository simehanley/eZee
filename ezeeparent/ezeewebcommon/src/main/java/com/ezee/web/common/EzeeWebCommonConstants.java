package com.ezee.web.common;

import com.ezee.web.common.service.EzeeUserService;
import com.ezee.web.common.service.EzeeUserServiceAsync;
import com.ezee.web.common.ui.utils.EzeeAutoLogin;
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

	String REMEMBER_ME = "Remember_Me";
	String REMEMBER_ME_USER = "Remember_Me_User";

	EzeeAutoLogin AUTO_LOGIN_HELPER = new EzeeAutoLogin();
	EzeeUserServiceAsync USER_SERVICE = GWT.create(EzeeUserService.class);
}