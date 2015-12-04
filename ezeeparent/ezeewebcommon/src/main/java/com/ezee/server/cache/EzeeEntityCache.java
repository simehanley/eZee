package com.ezee.server.cache;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.util.CollectionUtils;

import com.ezee.dao.EzeeBaseDao;
import com.ezee.model.entity.EzeeDatabaseEntity;

/**
 * 
 * @author siborg
 *
 */
public class EzeeEntityCache<T extends EzeeDatabaseEntity> extends HashMap<Long, T> {

	private static final long serialVersionUID = 5433230624742772044L;

	private final Class<T> clazz;

	private final EzeeBaseDao<T> dao;

	public EzeeEntityCache(final Class<T> clazz, final EzeeBaseDao<T> dao) {
		this.clazz = clazz;
		this.dao = dao;
	}

	@PostConstruct
	public void load() {
		List<T> entities = dao.get(clazz);
		if (!CollectionUtils.isEmpty(entities)) {
			entities.forEach(entity -> put(entity.getId(), entity));
		}
	}

	public final EzeeBaseDao<T> getDao() {
		return dao;
	}

	public Class<T> type() {
		return clazz;
	}
}