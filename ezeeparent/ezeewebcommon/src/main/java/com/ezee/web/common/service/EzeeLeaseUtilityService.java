package com.ezee.web.common.service;

import com.ezee.model.entity.lease.EzeeLease;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("leaseutilityservice")
public interface EzeeLeaseUtilityService extends RemoteService {

	String getCurrentLeasePeriodString(EzeeLease lease);

	void sendEmail();
}