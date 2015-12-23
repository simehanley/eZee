package com.ezee.dao;

import java.util.List;

import com.ezee.model.entity.EzeePayer;
import com.ezee.model.entity.filter.EzeeStringFilter;

/**
 * 
 * @author siborg
 *
 */
public interface EzeePayerDao extends EzeeBaseDao<EzeePayer> {
	List<EzeePayer> get(EzeeStringFilter<EzeePayer> filter);
}