package com.ezee.web.common.service;

import com.ezee.model.entity.lease.EzeeLease;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EzeeLeaseUtilityServiceAsync {

	void getCurrentLeasePeriodString(EzeeLease lease, AsyncCallback<String> result);
}
