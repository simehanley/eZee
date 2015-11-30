package com.ezee.web.common.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("utilityservice")
public interface EzeeUtilityService extends RemoteService {

	String getVersion();
}
