package com.ezee.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.ezee.dao.EzeePayeeDao;
import com.ezee.model.entity.EzeePayee;
import com.ezee.model.entity.filter.EzeeStringFilter;

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

	@Override
	public List<EzeePayee> get(final EzeeStringFilter<EzeePayee> filter) {
		List<EzeePayee> unfiltered = get();
		List<EzeePayee> filtered = new ArrayList<>();
		for (EzeePayee payee : unfiltered) {
			if (filter.include(payee)) {
				filtered.add(payee);
			}
		}
		return filtered;
	}
}