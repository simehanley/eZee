package com.ezee.web.common.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EzeeUtilityServiceAsync {

	void getVersion(AsyncCallback<String> version);
}
