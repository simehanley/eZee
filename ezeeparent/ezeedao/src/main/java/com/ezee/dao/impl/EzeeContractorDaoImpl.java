package com.ezee.dao.impl;

import java.util.List;

import com.ezee.dao.EzeeContractorDao;
import com.ezee.model.entity.EzeeContractor;

public class EzeeContractorDaoImpl extends EzeePayeeDaoImpl<EzeeContractor> implements EzeeContractorDao {

	@Override
	public EzeeContractor get(long id) {
		return super.get(id, EzeeContractor.class);
	}

	@Override
	public List<EzeeContractor> get() {
		return super.get(EzeeContractor.class);
	}
}