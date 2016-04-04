package com.ezee.web.common.ui.crud.common;

import static com.ezee.web.common.EzeeWebCommonConstants.ENTITY_SERVICE;
import static com.ezee.web.common.EzeeWebCommonConstants.ERROR;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.create;
import static com.ezee.web.common.ui.dialog.EzeeMessageDialog.showNew;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showDefaultCursor;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showWaitCursor;

import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.model.entity.EzeeFinancialEntity;
import com.ezee.model.entity.EzeeHasName;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntity;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;

/**
 * 
 * @author siborg
 *
 */
public abstract class EzeeCreateUpdateDeleteFinancialEntity<T extends EzeeFinancialEntity>
		extends EzeeCreateUpdateDeleteEntity<T> {

	private static final Logger log = Logger.getLogger("EzeeCreateUpdateDeleteFinancialEntity");

	@UiField
	public Button btnClose;

	@UiField
	public Button btnSave;

	@UiField
	public Button btnDelete;

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

	@UiHandler("btnClose")
	public void onCloseClick(ClickEvent event) {
		close();
	}

	public void onSaveClick(ClickEvent event) {
		btnSave.setEnabled(false);
		showWaitCursor();
		bind();
		ENTITY_SERVICE.saveEntity(entity.getClass().getName(), entity, new AsyncCallback<T>() {

			@Override
			public void onFailure(final Throwable caught) {
				btnSave.setEnabled(true);
				showDefaultCursor();
				log.log(Level.SEVERE, "Error persisting entity '" + entity + "'.", caught);
				showNew(ERROR, "Error persisting entity '" + entity + "'.  Please see log for details.");
			}

			@Override
			public void onSuccess(final T result) {
				log.log(Level.INFO, "Saved entity '" + entity + "' successfully");
				handler.onSave(result);
				updateCache(result, type);
				btnSave.setEnabled(true);
				showDefaultCursor();
				close();
			}
		});
	}

	public void onDeleteClick(ClickEvent event) {
		btnDelete.setEnabled(false);
		showWaitCursor();
		ENTITY_SERVICE.deleteEntity(entity.getClass().getName(), entity, new AsyncCallback<T>() {

			@Override
			public void onFailure(Throwable caught) {
				btnDelete.setEnabled(true);
				showDefaultCursor();
				log.log(Level.SEVERE, "Error deleting entity '" + entity + "'.", caught);
				showNew(ERROR, "Error deleting entity '" + entity + "'.  Please see log for details");
			}

			@Override
			public void onSuccess(T result) {
				log.log(Level.INFO, "Entity '" + entity + "' deleted successfully");
				handler.onDelete(result);
				updateCache(result, type);
				btnDelete.setEnabled(true);
				showDefaultCursor();
				close();
			}
		});
	}
}