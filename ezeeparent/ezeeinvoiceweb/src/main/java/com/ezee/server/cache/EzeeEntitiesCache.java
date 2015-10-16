package com.ezee.server.cache;

import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.collection.spi.PersistentCollection;

import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.model.entity.EzeePayment;

/**
 * 
 * @author siborg
 *
 */
public class EzeeEntitiesCache {

	private final Map<Class<?>, EzeeEntityCache<?>> cache = new ConcurrentHashMap<>();

	private final EzeeEntitiesCachePostProcessor postprocessor = new EzeeEntitiesCachePostProcessor();

	public EzeeEntitiesCache(final List<EzeeEntityCache<?>> entityCaches) {
		for (EzeeEntityCache<?> entityCache : entityCaches) {
			cache.put(entityCache.type(), entityCache);
		}
	}

	public final <T extends EzeeDatabaseEntity> List<T> get(final Class<T> clazz) {
		List<T> entities = new ArrayList<T>(getCache(clazz).values());
		if (!isEmpty(entities)) {
			for (T entity : entities) {
				postprocessor.postProcessEntity(entity);
			}
		}
		return entities;
	}

	public final <T extends EzeeDatabaseEntity> T get(final long id, final Class<T> clazz) {
		T entity = getCache(clazz).get(id);
		if (entity != null) {
			postprocessor.postProcessEntity(entity);
		}
		return entity;
	}

	public <T extends EzeeDatabaseEntity> void put(final Class<T> clazz, final T entity) {
		getCache(clazz).put(entity.getId(), entity);
	}

	public <T extends EzeeDatabaseEntity> T remove(final long id, final Class<T> clazz) {
		return getCache(clazz).remove(id);
	}

	@SuppressWarnings("unchecked")
	public <Key, T extends EzeeDatabaseEntity> EzeeEntityCache<T> getCache(final Class<T> clazz) {
		return (EzeeEntityCache<T>) cache.get(clazz);
	}

	private static class EzeeEntitiesCachePostProcessor {

		public <T extends EzeeDatabaseEntity> void postProcessEntity(final T entity) {
			if (entity instanceof EzeePayment) {
				postProcessPayment((EzeePayment) entity);
			}
		}

		/**
		 * Post process {@link EzeePayment} to remove non serializable
		 * {@link PersistentCollection}
		 */
		public void postProcessPayment(final EzeePayment payment) {
			if (!isEmpty(payment.getInvoices())) {
				payment.setInvoices(new HashSet<>(payment.getInvoices()));
			}
		}
	}
}