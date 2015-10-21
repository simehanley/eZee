package com.ezee.client.crud;

import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.create;
import static com.ezee.common.EzeeCommonConstants.ZERO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.model.entity.EzeeHasName;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ListBox;

/**
 * 
 * @author siborg
 *
 */
public abstract class EzeeCreateUpdateDeleteEntity<T extends EzeeDatabaseEntity> extends DialogBox {

	protected T entity;

	protected final EzeeInvoiceServiceAsync service;

	protected final EzeeInvoiceEntityCache cache;

	protected final EzeeCreateUpdateDeleteEntityType type;

	protected final EzeeCreateUpdateDeleteEntityHandler<T> handler;

	public EzeeCreateUpdateDeleteEntity(final EzeeInvoiceServiceAsync service, final EzeeInvoiceEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<T> handler) {
		this(service, cache, handler, null, create);
	}

	public EzeeCreateUpdateDeleteEntity(final EzeeInvoiceServiceAsync service, final EzeeInvoiceEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<T> handler, final T entity,
			final EzeeCreateUpdateDeleteEntityType type) {
		super(false, false);
		this.service = service;
		this.cache = cache;
		this.handler = handler;
		this.entity = entity;
		this.type = type;
	}

	public final T getEntity() {
		return entity;
	}

	public void close() {
		this.hide(true);
	}

	protected <K extends EzeeHasName> void loadEntities(final Class<K> clazz, final ListBox listBox) {
		Map<String, EzeeHasName> entities = cache.getEntities(clazz);
		List<String> entityNames = new ArrayList<>(entities.keySet());
		Collections.sort(entityNames);
		for (String key : entityNames) {
			listBox.addItem(key);
		}
	}

	protected int getItemIndex(final String value, final ListBox listBox) {
		if (value != null) {
			for (int i = ZERO; i < listBox.getItemCount(); i++) {
				if (value.equals(listBox.getItemText(i))) {
					return i;
				}
			}
		}
		return ZERO;
	}

	protected abstract void initialise();

	protected abstract void bind();
}