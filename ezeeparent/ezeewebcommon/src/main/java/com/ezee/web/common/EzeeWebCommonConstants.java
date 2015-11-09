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
	String EDIT_ERROR = "Edit Error";

	String REMEMBER_ME = "Remember_Me";
	String REMEMBER_ME_USER = "Remember_Me_User";

	String EDIT_USER = "Edit User";

	String REPORT_TYPE = "Report_Type";
	String REPORT_GENERATORS = "Report_Generators";

	String REPORT_SERVICE = "reportservice";

	EzeeAutoLogin AUTO_LOGIN_HELPER = new EzeeAutoLogin();
	EzeeUserServiceAsync USER_SERVICE = GWT.create(EzeeUserService.class);
}