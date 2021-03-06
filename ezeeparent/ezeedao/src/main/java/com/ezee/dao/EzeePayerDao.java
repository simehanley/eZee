package com.ezee.dao;

import java.util.List;

import com.ezee.model.entity.EzeePayer;
import com.ezee.model.entity.filter.EzeeStringFilter;

/**
 * 
 * @author siborg
 *
 */
public interface EzeePayerDao<T extends EzeePayer> extends EzeeBaseDao<T> {
	List<T> get(EzeeStringFilter<T> filter);
}