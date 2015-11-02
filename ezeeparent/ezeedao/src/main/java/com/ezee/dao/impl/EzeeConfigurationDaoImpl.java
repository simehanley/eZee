package com.ezee.dao.impl;

import java.util.List;

import com.ezee.dao.EzeeConfigurationDao;
import com.ezee.model.entity.EzeeConfiguration;

public class EzeeConfigurationDaoImpl extends EzeeBaseDaoImpl<EzeeConfiguration> implements EzeeConfigurationDao {

	@Override
	public EzeeConfiguration get(long id) {
		return super.get(id, EzeeConfiguration.class);
	}

	@Override
	public List<EzeeConfiguration> get() {
		return super.get(EzeeConfiguration.class);
	}
}