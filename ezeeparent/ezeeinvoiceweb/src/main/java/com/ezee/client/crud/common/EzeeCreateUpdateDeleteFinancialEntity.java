package com.ezee.client.crud.common;

import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.create;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;

import java.util.Collection;
import java.util.Map;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.crud.EzeeCreateUpdateDeleteEntity;
import com.ezee.client.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.model.entity.EzeeFinancialEntity;
import com.ezee.model.entity.EzeeHasName;

/**
 * 
 * @author siborg
 *
 */
public abstract class EzeeCreateUpdateDeleteFinancialEntity<T extends EzeeFinancialEntity>
		extends EzeeCreateUpdateDeleteEntity<T> {

	public EzeeCreateUpdateDeleteFinancialEntity(final EzeeInvoiceServiceAsync service,
			final EzeeInvoiceEntityCache cache, final EzeeCreateUpdateDeleteEntityHandler<T> handler) {
		this(service, cache, handler, null, create);
	}

	public EzeeCreateUpdateDeleteFinancialEntity(final EzeeInvoiceServiceAsync service,
			final EzeeInvoiceEntityCache cache, EzeeCreateUpdateDeleteEntityHandler<T> handler, final T entity,
			final EzeeCreateUpdateDeleteEntityType type) {
		super(service, cache, handler, entity, type);
	}

	protected void updateCache(final T entity, final EzeeCreateUpdateDeleteEntityType type) {
		Map<String, EzeeHasName> entities = cache.getEntities(entity.getClass());
		if (entities != null) {
			switch (type) {
			case create:
				entities.put(entity.getName(), entity);
			case update:
				if (entities.containsKey(entity.getName())) {
					entities.put(entity.getName(), entity);
				} else {
					T cachedEntity = getEntity(entity, entities.values());
					if (cachedEntity != null) {
						entities.remove(cachedEntity.getName());
						entities.put(entity.getName(), entity);
					}
				}
				break;
			case delete:
				entities.values().remove(entity);
				break;
			}
		}
	}

	@SuppressWarnings("unchecked")
	private T getEntity(final T entity, final Collection<EzeeHasName> values) {
		for (EzeeHasName name : values) {
			T cached = (T) name;
			if (entity.getId().equals(cached.getId())) {
				return cached;
			}
		}
		return null;
	}
}