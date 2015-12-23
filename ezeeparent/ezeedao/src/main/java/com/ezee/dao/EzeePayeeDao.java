package com.ezee.dao;

import java.util.List;

import com.ezee.model.entity.EzeePayee;
import com.ezee.model.entity.EzeePayer;
import com.ezee.model.entity.filter.EzeeStringFilter;

/**
 * 
 * @author siborg
 *
 */
public interface EzeePayeeDao extends EzeeBaseDao<EzeePayee> {
	List<EzeePayee> get(EzeeStringFilter<EzeePayee> filter);
}