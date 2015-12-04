package com.ezee.server.dao;

import com.ezee.dao.EzeeBaseDao;

public class EzeeEntityDao<T> {

	private final Class<T> clazz;

	private final EzeeBaseDao<T> dao;

	public EzeeEntityDao(final Class<T> clazz, final EzeeBaseDao<T> dao) {
		this.clazz = clazz;
		this.dao = dao;
	}

	public final Class<T> getClazz() {
		return clazz;
	}

	public final EzeeBaseDao<T> getDao() {
		return dao;
	}
}