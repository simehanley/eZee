package com.ezee.dao;

import java.util.List;

import com.ezee.model.entity.filter.EzeeEntityFilter;

public interface EzeeBaseDao<T> {

	void save(T entity);

	void delete(T entity);

	T get(long id, Class<T> entityClazz);

	List<T> get(final Class<T> entity);

	List<T> get(EzeeEntityFilter<T> filter, Class<T> entityClazz);
}