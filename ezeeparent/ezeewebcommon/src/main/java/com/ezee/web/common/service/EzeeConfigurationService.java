package com.ezee.web.common.service;

import com.ezee.model.entity.EzeeConfiguration;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("configservice")
public interface EzeeConfigurationService extends RemoteService {

	String getVersion();

	String getLicensee();
	
	EzeeConfiguration getConfiguration();
}