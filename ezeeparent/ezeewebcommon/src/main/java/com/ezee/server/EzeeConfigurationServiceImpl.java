package com.ezee.server;

import static com.ezee.common.EzeeCommonConstants.ZERO;

import com.ezee.dao.EzeeConfigurationDao;
import com.ezee.model.entity.EzeeConfiguration;
import com.ezee.web.common.service.EzeeConfigurationService;

public class EzeeConfigurationServiceImpl extends AbstractRemoteService implements EzeeConfigurationService {

	private static final long serialVersionUID = 4510740597161331126L;

	@Override
	public String getVersion() {
		return getConfiguration().getVersion();
	}

	@Override
	public String getLicensee() {
		return getConfiguration().getLicensee();
	}

	@Override
	public EzeeConfiguration getConfiguration() {
		return getDao().get(EzeeConfiguration.class).get(ZERO);
	}

	private EzeeConfigurationDao getDao() {
		return getSpringBean(EzeeConfigurationDao.class);
	}
}