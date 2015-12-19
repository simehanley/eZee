package com.ezee.web.common.cache;

import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.web.common.EzeeWebCommonConstants.ENTITY_SERVICE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.model.entity.EzeeConfiguration;
import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.model.entity.EzeeDebtAgeRule;
import com.ezee.model.entity.EzeeHasName;
import com.ezee.model.entity.EzeePayee;
import com.ezee.model.entity.EzeePayer;
import com.ezee.model.entity.EzeeResource;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author siborg
 *
 */
public class EzeeEntityCache {

	private static final Logger log = Logger.getLogger("EzeeEntityCache");

	private final Map<Class<?>, Map<String, EzeeHasName>> entities = new HashMap<>();

	private EzeeConfiguration configuration;

	public EzeeEntityCache() {
		loadEntities();
	}

	private void loadEntities() {
		loadEntities(EzeeResource.class);
		loadEntities(EzeePayee.class);
		loadEntities(EzeePayer.class);
		loadEntities(EzeeDebtAgeRule.class);
		loadConfiguration();
	}

	private <T extends EzeeDatabaseEntity> void loadEntities(final Class<T> clazz) {

		ENTITY_SERVICE.getEntities(clazz.getName(), new AsyncCallback<List<T>>() {

			@Override
			public void onFailure(final Throwable caught) {
				log.log(Level.SEVERE, "Unable to retrieve entities of type '" + clazz.getSimpleName() + "'.", caught);
			}

			@Override
			public void onSuccess(final List<T> result) {
				Map<String, EzeeHasName> map = new HashMap<>();
				for (T entity : result) {
					EzeeHasName name = (EzeeHasName) entity;
					map.put(name.getName(), name);
				}
				entities.put(clazz, map);
			}
		});
	}

	private void loadConfiguration() {

		ENTITY_SERVICE.getEntities(EzeeConfiguration.class.getName(), new AsyncCallback<List<EzeeConfiguration>>() {

			@Override
			public void onFailure(final Throwable caught) {
				log.log(Level.SEVERE, "Unable to retrieve ezee configuration.", caught);
			}

			@Override
			public void onSuccess(final List<EzeeConfiguration> result) {
				configuration = result.get(ZERO);
			}
		});
	}

	public final <T extends EzeeHasName> Map<String, EzeeHasName> getEntities(final Class<T> clazz) {
		return entities.get(clazz);
	}

	@SuppressWarnings("unchecked")
	public final <T> Map<String, T> getEntitiesOfType(final Class<T> clazz) {
		return (Map<String, T>) entities.get(clazz);
	}

	public final EzeeConfiguration getConfiguration() {
		return configuration;
	}
}