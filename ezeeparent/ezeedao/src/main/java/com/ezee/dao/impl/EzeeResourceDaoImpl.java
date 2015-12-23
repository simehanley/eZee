package com.ezee.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.ezee.dao.EzeeResourceDao;
import com.ezee.model.entity.EzeeResource;
import com.ezee.model.entity.filter.EzeeStringFilter;

public class EzeeResourceDaoImpl extends EzeeBaseDaoImpl<EzeeResource> implements EzeeResourceDao {

	@Override
	public EzeeResource get(long id) {
		return super.get(id, EzeeResource.class);
	}

	@Override
	public List<EzeeResource> get() {
		return super.get(EzeeResource.class);
	}
	
	@Override
	public List<EzeeResource> get(final EzeeStringFilter<EzeeResource> filter) {
		List<EzeeResource> unfiltered = get();
		List<EzeeResource> filtered = new ArrayList<>();
		for (EzeeResource resource : unfiltered) {
			if (filter.include(resource)) {
				filtered.add(resource);
			}
		}
		return filtered;
	}
}