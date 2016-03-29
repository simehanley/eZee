package com.ezee.dao.impl;

import java.util.List;

import com.ezee.dao.EzeeLeaseTenantDao;
import com.ezee.model.entity.lease.EzeeLeaseTenant;

public class EzeeLeaseTenantDaoImpl extends EzeePayerDaoImpl<EzeeLeaseTenant> implements EzeeLeaseTenantDao {

	@Override
	public EzeeLeaseTenant get(long id) {
		return super.get(id, EzeeLeaseTenant.class);
	}

	@Override
	public List<EzeeLeaseTenant> get() {
		return super.get(EzeeLeaseTenant.class);
	}
}