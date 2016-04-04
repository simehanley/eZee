package com.ezee.dao;

import java.util.List;

import com.ezee.model.entity.filter.EzeeStringFilter;
import com.ezee.model.entity.lease.EzeeLeaseCategory;

public interface EzeeLeaseCategoryDao extends EzeeBaseDao<EzeeLeaseCategory> {
	List<EzeeLeaseCategory> get(EzeeStringFilter<EzeeLeaseCategory> filter);
}