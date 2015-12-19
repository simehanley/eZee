package com.ezee.dao.impl;

import java.util.List;

import com.ezee.dao.EzeeResourceDao;
import com.ezee.model.entity.EzeeResource;

public class EzeeResourceDaoImpl extends EzeeBaseDaoImpl<EzeeResource> implements EzeeResourceDao {

	@Override
	public EzeeResource get(long id) {
		return super.get(id, EzeeResource.class);
	}

	@Override
	public List<EzeeResource> get() {
		return super.get(EzeeResource.class);
	}
}