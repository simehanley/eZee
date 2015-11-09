package com.ezee.web.common.service;

import com.ezee.model.entity.EzeeUser;
import com.ezee.web.common.ui.EzeeUserResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeUserServiceAsync {

	void register(EzeeUser created, AsyncCallback<EzeeUserResult> result);

	void authenticate(String username, String password, AsyncCallback<EzeeUserResult> result);

	void retrieve(String username, AsyncCallback<EzeeUserResult> result);

	void edit(EzeeUser existing, EzeeUser edited, String passwordCheck, AsyncCallback<EzeeUserResult> result);
}