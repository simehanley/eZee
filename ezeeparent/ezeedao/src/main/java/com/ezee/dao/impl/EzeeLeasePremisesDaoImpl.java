package com.ezee.dao.impl;

import java.util.List;

import com.ezee.dao.EzeeLeasePremisesDao;
import com.ezee.model.entity.lease.EzeeLeasePremises;

public class EzeeLeasePremisesDaoImpl extends EzeeBaseDaoImpl<EzeeLeasePremises> implements EzeeLeasePremisesDao {

	@Override
	public EzeeLeasePremises get(long id) {
		return super.get(id, EzeeLeasePremises.class);
	}

	@Override
	public List<EzeeLeasePremises> get() {
		return super.get(EzeeLeasePremises.class);
	}
}