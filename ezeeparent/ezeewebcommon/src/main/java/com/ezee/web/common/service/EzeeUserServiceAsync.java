package com.ezee.web.common.service;

import com.ezee.web.common.ui.login.EzeeLoginResult;
import com.ezee.web.common.ui.register.EzeeRegisterResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeUserServiceAsync {

	void register(String firstname, String lastname, String username, String password, String email,
			AsyncCallback<EzeeRegisterResult> result);

	void authenticate(String username, String password, AsyncCallback<EzeeLoginResult> result);
}