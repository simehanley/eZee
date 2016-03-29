package com.ezee.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.ezee.dao.EzeePayerDao;
import com.ezee.model.entity.EzeePayer;
import com.ezee.model.entity.filter.EzeeStringFilter;

/**
 * 
 * @author siborg
 *
 */
public abstract class EzeePayerDaoImpl<T extends EzeePayer> extends EzeeBaseDaoImpl<T> implements EzeePayerDao<T> {

	@Override
	public List<T> get(final EzeeStringFilter<T> filter) {
		List<T> unfiltered = get();
		List<T> filtered = new ArrayList<>();
		for (T entity : unfiltered) {
			if (filter.include(entity)) {
				filtered.add(entity);
			}
		}
		return filtered;
	}
}