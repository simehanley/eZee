package com.ezee.dao.impl;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ezee.dao.EzeeLeaseDao;
import com.ezee.model.entity.lease.EzeeLease;

public class EzeeLeaseDaoImpl extends EzeeBaseDaoImpl<EzeeLease> implements EzeeLeaseDao {

	@Override
	@Transactional(propagation = REQUIRED, readOnly = false)
	public void delete(final EzeeLease entity) {
		deleteMappings(entity, "deleteCategoryMappingsSql");
		deleteMappings(entity, "deleteIncidentalMappingsSql");
		deleteMappings(entity, "deleteTenantMappingsSql");
		deleteMappings(entity, "deletePremisesMappingsSql");
		deleteMappings(entity, "deleteBondMappingsSql");
		deleteMappings(entity, "deleteMetaDataMappingsSql");
		entity.setCategory(null);
		entity.setTenant(null);
		entity.setPremises(null);
		super.save(entity);
		super.delete(entity);
	}

	@Override
	public EzeeLease get(long id) {
		return get(id, EzeeLease.class);
	}

	@Override
	public List<EzeeLease> get() {
		return get(EzeeLease.class);
	}
}