package com.ezee.dao.impl;

import java.util.List;

import com.ezee.dao.EzeePayerDao;
import com.ezee.model.entity.EzeePayer;

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
}