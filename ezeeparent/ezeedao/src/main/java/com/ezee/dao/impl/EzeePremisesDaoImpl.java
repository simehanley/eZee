package com.ezee.dao.impl;

import java.util.List;

import com.ezee.dao.EzeePremisesDao;
import com.ezee.model.entity.EzeePremises;

public class EzeePremisesDaoImpl extends EzeePayerDaoImpl<EzeePremises> implements EzeePremisesDao {

	@Override
	public EzeePremises get(long id) {
		return super.get(id, EzeePremises.class);
	}

	@Override
	public List<EzeePremises> get() {
		return super.get(EzeePremises.class);
	}
}