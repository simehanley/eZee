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
public class EzeePayerDaoImpl extends EzeeBaseDaoImpl<EzeePayer> implements EzeePayerDao {

	@Override
	public EzeePayer get(long id) {
		return super.get(id, EzeePayer.class);
	}

	@Override
	public List<EzeePayer> get() {
		return super.get(EzeePayer.class);
	}

	@Override
	public List<EzeePayer> get(final EzeeStringFilter<EzeePayer> filter) {
		List<EzeePayer> unfiltered = get();
		List<EzeePayer> filtered = new ArrayList<>();
		for (EzeePayer payer : unfiltered) {
			if (filter.include(payer)) {
				filtered.add(payer);
			}
		}
		return filtered;
	}
}