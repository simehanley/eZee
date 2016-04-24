package com.ezee.model.entity.filter;

public class EzeeEmptyFilter<T> implements EzeeEntityFilter<T> {

	@Override
	public boolean include(final T entity) {
		return true;
	}
}