package com.ezee.client.crud;

import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.create;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.web.common.ui.dialog.EzeeDialog;

/**
 * 
 * @author siborg
 *
 */
public abstract class EzeeCreateUpdateDeleteEntity<T extends EzeeDatabaseEntity> extends EzeeDialog {

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

	protected abstract void initialise();

	protected abstract void bind();
}