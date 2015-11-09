package com.ezee.web.common.service;

import com.ezee.model.entity.EzeeUser;
import com.ezee.web.common.ui.EzeeUserResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author siborg
 *
 */
@RemoteServiceRelativePath("userservice")
public interface EzeeUserService extends RemoteService {

	EzeeUserResult register(EzeeUser created);

	EzeeUserResult authenticate(String username, String password);

	EzeeUserResult retrieve(String username);

	EzeeUserResult edit(EzeeUser existing, EzeeUser edited, String passwordCheck);
}