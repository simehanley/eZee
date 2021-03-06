package com.ezee.web.common;

import com.ezee.common.EzeeDateUtilities;
import com.ezee.common.web.EzeeClientDateUtils;
import com.ezee.web.common.localstorage.EzeeLocalStroage;
import com.ezee.web.common.service.EzeeConfigurationService;
import com.ezee.web.common.service.EzeeConfigurationServiceAsync;
import com.ezee.web.common.service.EzeeEntityService;
import com.ezee.web.common.service.EzeeEntityServiceAsync;
import com.ezee.web.common.service.EzeeLeaseUtilityService;
import com.ezee.web.common.service.EzeeLeaseUtilityServiceAsync;
import com.ezee.web.common.service.EzeeUserService;
import com.ezee.web.common.service.EzeeUserServiceAsync;
import com.ezee.web.common.service.EzeeUtilityService;
import com.ezee.web.common.service.EzeeUtilityServiceAsync;
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

	String EXCEL_INVOICE_SUPPLIER_FILTER = "Supplier";
	String EXCEL_INVOICE_PREMISES_FILTER = "Premises";
	String EXCEL_INVOICE_INVOICES_FILTER = "Invoices";
	String EXCEL_INVOICE_DATE_FROM_FILTER = "DateFrom";
	String EXCEL_INVOICE_DATE_TO_FILTER = "DateTo";
	String EXCEL_INVOICE_INCLUDE_PAID_FILTER = "IncludePaid";

	String EXCEL_PROJECT_ID = "ProjectId";

	String EXCEL_FE_FILTER = "FE_FILTER";

	String SUPPORT_EMAIL = "mailto:support@ezeeit.com";

	String EZEE_VERSION_PROPERTIES = "ezee.version.properties";
	String EZEE_WEB_VERSION = "ezee.web.version";

	String FILE_UPLOAD_SUCCESS = "File Upload Succeeded";
	String FILE_UPLOAD_FAIL = "File Upload Failed";
	String FILE_UPLOAD_CANCELLED = "File Upload Cancelled";

	String FILE_DOWNLOAD_SERVICE = "downloadservice";

	EzeeDateUtilities DATE_UTILS = new EzeeClientDateUtils();
	EzeeLocalStroage LOCAL_STORAGE = new EzeeLocalStroage();
	EzeeAutoLogin AUTO_LOGIN_HELPER = new EzeeAutoLogin();

	EzeeUserServiceAsync USER_SERVICE = GWT.create(EzeeUserService.class);
	EzeeUtilityServiceAsync UTILITY_SERVICE = GWT.create(EzeeUtilityService.class);
	EzeeEntityServiceAsync ENTITY_SERVICE = GWT.create(EzeeEntityService.class);
	EzeeConfigurationServiceAsync CONFIG_SERVICE = GWT.create(EzeeConfigurationService.class);
	EzeeLeaseUtilityServiceAsync LEASE_UTILITY_SERVICE = GWT.create(EzeeLeaseUtilityService.class);
}