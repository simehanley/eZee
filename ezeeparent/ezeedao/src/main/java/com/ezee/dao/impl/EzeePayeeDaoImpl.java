package com.ezee.dao.impl;

import java.util.List;

import com.ezee.dao.EzeePayeeDao;
import com.ezee.model.entity.EzeePayee;

/**
 * 
 * @author siborg
 *
 */
public class EzeePayeeDaoImpl extends EzeeBaseDaoImpl<EzeePayee> implements EzeePayeeDao {
	
	@Override
	public EzeePayee get(long id) {
		return super.get(id, EzeePayee.class);
	}

	@Override
	public List<EzeePayee> get() {
		return super.get(EzeePayee.class);
	}
}