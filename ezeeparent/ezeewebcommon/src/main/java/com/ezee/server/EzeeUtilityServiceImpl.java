package com.ezee.server;

import static com.ezee.web.common.EzeeWebCommonConstants.EZEE_VERSION_PROPERTIES;
import static com.ezee.web.common.EzeeWebCommonConstants.EZEE_WEB_VERSION;

import com.ezee.server.util.EzeePropertyLoader;
import com.ezee.web.common.service.EzeeUtilityService;

public class EzeeUtilityServiceImpl extends AbstractRemoteService implements EzeeUtilityService {

	private static final long serialVersionUID = 428314274074301097L;

	private final EzeePropertyLoader properties = new EzeePropertyLoader(EZEE_VERSION_PROPERTIES);

	@Override
	public String getVersion() {
		return properties.getProperty(EZEE_WEB_VERSION);
	}
}