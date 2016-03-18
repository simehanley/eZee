package com.ezee.dao.impl;

import java.util.List;

import com.ezee.dao.EzeeLeaseCategoryDao;
import com.ezee.model.entity.lease.EzeeLeaseCategory;

public class EzeeLeaseCategoryDaoImpl extends EzeeBaseDaoImpl<EzeeLeaseCategory> implements EzeeLeaseCategoryDao {

	@Override
	public EzeeLeaseCategory get(long id) {
		return get(id, EzeeLeaseCategory.class);
	}

	@Override
	public List<EzeeLeaseCategory> get() {
		return get(EzeeLeaseCategory.class);
	}
}