package com.ezee.model.entity.filter;

import java.util.List;

import com.ezee.model.entity.EzeeDatabaseEntity;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeEntityFilter<T extends EzeeDatabaseEntity> {

	List<T> apply(List<T> unfiltered);

	boolean empty();
}
