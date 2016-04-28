package com.ezee.dao;

import java.util.List;

import com.ezee.model.entity.filter.lease.EzeeLeaseFilter;
import com.ezee.model.entity.lease.EzeeLease;

public interface EzeeLeaseDao extends EzeeBaseDao<EzeeLease> {
	List<EzeeLease> get(EzeeLeaseFilter filter);
}