package com.ezee.dao.impl;

import java.util.List;

import com.ezee.dao.EzeeLeaseDao;
import com.ezee.model.entity.lease.EzeeLease;

public class EzeeLeaseDaoImpl extends EzeeBaseDaoImpl<EzeeLease> implements EzeeLeaseDao {

	@Override
	public EzeeLease get(long id) {
		return get(id, EzeeLease.class);
	}

	@Override
	public List<EzeeLease> get() {
		return get(EzeeLease.class);
	}
}