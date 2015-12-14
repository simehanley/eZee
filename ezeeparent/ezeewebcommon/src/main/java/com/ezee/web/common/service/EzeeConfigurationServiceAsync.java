package com.ezee.web.common.service;

import com.ezee.model.entity.EzeeConfiguration;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EzeeConfigurationServiceAsync {

	void getVersion(AsyncCallback<String> version);

	void getLicensee(AsyncCallback<String> licensee);

	void getConfiguration(AsyncCallback<EzeeConfiguration> config);
}