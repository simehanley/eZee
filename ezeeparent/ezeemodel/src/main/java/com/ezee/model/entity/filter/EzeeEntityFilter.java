package com.ezee.model.entity.filter;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeEntityFilter<T> {
	boolean include(T entity);
}