package com.ezee.web.common.service;

import com.ezee.web.common.ui.login.EzeeLoginResult;
import com.ezee.web.common.ui.register.EzeeRegisterResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author siborg
 *
 */
@RemoteServiceRelativePath("userservice")
public interface EzeeUserService extends RemoteService {

	EzeeRegisterResult register(String firstname, String lastname, String username, String password, String email,
			String createDate);

	EzeeLoginResult authenticate(String username, String password);

	EzeeLoginResult retrieve(String username);
}
