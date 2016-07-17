package com.ezee.server;

import static java.util.Collections.emptyList;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezee.common.exception.EzeeException;
import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.server.dao.EzeeEntitiesDao;
import com.ezee.web.common.service.EzeeEntityService;

/**
 * 
 * @author siborg
 *
 */
public class EzeeEntityServiceImpl extends AbstractRemoteService implements EzeeEntityService {

	private static final long serialVersionUID = 1922717945260718135L;

	private static final Logger log = LoggerFactory.getLogger(EzeeEntityServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public <T extends EzeeDatabaseEntity> List<T> getEntities(final String clazz) {
		try {
			return getSpringBean(EzeeEntitiesDao.class).getEntities((Class<T>) Class.forName(clazz));
		} catch (ClassNotFoundException e) {
			log.error("Unable to resolve class '" + clazz + "'.", e);
			throw new EzeeException("Unable to resolve class '" + clazz + "'.", e);
		} catch (Throwable t) {
			log.error("Error retireving entities.", t);
			throw new EzeeException("Error retireving entities.", t);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends EzeeDatabaseEntity> T saveEntity(final String clazz, final T entity) {
		try {
			return getSpringBean(EzeeEntitiesDao.class).saveEntity((Class<T>) Class.forName(clazz), entity);
		} catch (ClassNotFoundException e) {
			log.error("Unable to resolve class '" + clazz + "'.", e);
			throw new EzeeException("Unable to resolve class '" + clazz + "'.", e);
		} catch (Throwable t) {
			log.error("Error saving entity " + entity + ".", t);
			throw new EzeeException("Error saving entity " + entity + ".", t);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends EzeeDatabaseEntity> T deleteEntity(final String clazz, final T entity) {
		try {
			return getSpringBean(EzeeEntitiesDao.class).deleteEntity((Class<T>) Class.forName(clazz), entity);
		} catch (ClassNotFoundException e) {
			log.error("Unable to resolve class '" + clazz + "'.", e);
			throw new EzeeException("Unable to resolve class '" + clazz + "'.", e);
		} catch (Throwable t) {
			log.error("Error deleting entity " + entity + ".", t);
			throw new EzeeException("Error deleting entity " + entity + ".", t);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends EzeeDatabaseEntity> T getEntity(String clazz, long id) {
		try {
			return getSpringBean(EzeeEntitiesDao.class).getEntity((Class<T>) Class.forName(clazz), id);
		} catch (ClassNotFoundException e) {
			log.error("Unable to resolve class '" + clazz + "'.", e);
			throw new EzeeException("Unable to resolve class '" + clazz + "'.", e);
		} catch (Throwable t) {
			log.error("Error getting entity for id " + id + ".", t);
			throw new EzeeException("Error getting entity for id " + id + ".", t);
		}
	}

	@Override
	public <T extends EzeeDatabaseEntity> List<T> saveEntities(final String clazz, final List<T> entities) {
		if (!isEmpty(entities)) {
			List<T> saved = new ArrayList<>();
			for (T entity : entities) {
				try {
					T savedEntity = saveEntity(clazz, entity);
					saved.add(savedEntity);
				} catch (EzeeException ex) {
					log.error("Error saving entity " + entity + ".", ex);
					continue;
				}
			}
			return saved;
		}
		return emptyList();
	}
}