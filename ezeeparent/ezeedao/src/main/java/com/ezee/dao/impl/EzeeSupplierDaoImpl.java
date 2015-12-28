package com.ezee.dao.impl;

import java.util.List;

import com.ezee.dao.EzeeSupplierDao;
import com.ezee.model.entity.EzeeSupplier;

public class EzeeSupplierDaoImpl extends EzeePayeeDaoImpl<EzeeSupplier> implements EzeeSupplierDao {

	@Override
	public EzeeSupplier get(final long id) {
		return super.get(id, EzeeSupplier.class);
	}

	@Override
	public List<EzeeSupplier> get() {
		return super.get(EzeeSupplier.class);
	}
}