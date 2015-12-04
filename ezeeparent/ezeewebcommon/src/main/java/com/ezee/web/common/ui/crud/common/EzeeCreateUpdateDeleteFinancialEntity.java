package com.ezee.web.common.ui.crud.common;

import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.create;

import java.util.Collection;
import java.util.Map;

import com.ezee.model.entity.EzeeFinancialEntity;
import com.ezee.model.entity.EzeeHasName;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntity;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;

/**
 * 
 * @author siborg
 *
 */
public abstract class EzeeCreateUpdateDeleteFinancialEntity<T extends EzeeFinancialEntity>
		extends EzeeCreateUpdateDeleteEntity<T> {

	public EzeeCreateUpdateDeleteFinancialEntity(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<T> handler, final String[] headers) {
		this(cache, handler, null, create, headers);
	}

	public EzeeCreateUpdateDeleteFinancialEntity(final EzeeEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<T> handler, final T entity, final EzeeCreateUpdateDeleteEntityType type,
			final String[] headers) {
		super(cache, handler, entity, type, headers);
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