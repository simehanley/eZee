package com.ezee.dao;

import java.util.List;

import com.ezee.model.entity.EzeePayee;
import com.ezee.model.entity.filter.EzeeStringFilter;

/**
 * 
 * @author siborg
 *
 */
public interface EzeePayeeDao<T extends EzeePayee> extends EzeeBaseDao<T> {
	List<T> get(EzeeStringFilter<T> filter);
}