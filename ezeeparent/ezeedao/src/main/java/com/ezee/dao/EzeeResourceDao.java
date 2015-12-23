package com.ezee.dao;

import java.util.List;

import com.ezee.model.entity.EzeeResource;
import com.ezee.model.entity.filter.EzeeStringFilter;

public interface EzeeResourceDao extends EzeeBaseDao<EzeeResource> {
	List<EzeeResource> get(EzeeStringFilter<EzeeResource> filter);
}