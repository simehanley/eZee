package com.ezee.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezee.client.EzeeInvoiceService;
import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.server.cache.EzeeEntitiesCache;
import com.ezee.server.cache.EzeeEntityCache;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceServiceImpl extends AbstractRemoteService implements EzeeInvoiceService {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(EzeeInvoiceServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public <T extends EzeeDatabaseEntity> List<T> getEntities(final String clazz) {
		try {
			return getSpringBean(EzeeEntitiesCache.class).get((Class<T>) Class.forName(clazz));
		} catch (ClassNotFoundException e) {
			log.error("Unable to resolve class '" + clazz + "'.", e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends EzeeDatabaseEntity> void saveEntity(final String clazz, final T entity) {
		try {
			EzeeEntityCache<T> cache = getSpringBean(EzeeEntitiesCache.class).getCache((Class<T>) Class.forName(clazz));
			cache.getDao().save(entity);
			cache.put(entity.getId(), entity);
			/* send persisted entity back over message bus (erai) */
		} catch (ClassNotFoundException e) {
			log.error("Unable to resolve class '" + clazz + "'.", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends EzeeDatabaseEntity> void deleteEntity(String clazz, T entity) {
		try {
			EzeeEntityCache<T> cache = getSpringBean(EzeeEntitiesCache.class).getCache((Class<T>) Class.forName(clazz));
			T cached = cache.get(entity.getId());
			if (cached != null) {
				cache.getDao().delete(cached);
				cache.remove(cached.getId());
			}
		} catch (ClassNotFoundException e) {
			log.error("Unable to resolve class '" + clazz + "'.", e);
		}
	}
}