package com.ezee.client.crud;

import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.create;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.model.entity.EzeeDatabaseEntity;
import com.google.gwt.user.client.ui.DialogBox;

/**
 * 
 * @author siborg
 *
 */
public abstract class EzeeCreateUpdateDeleteEntity<T extends EzeeDatabaseEntity> extends DialogBox {

	protected T entity;

	protected final EzeeInvoiceServiceAsync service;

	protected final EzeeCreateUpdateDeleteEntityType type;

	public EzeeCreateUpdateDeleteEntity(final EzeeInvoiceServiceAsync service) {
		this(null, service, create);
	}

	public EzeeCreateUpdateDeleteEntity(final T entity, final EzeeInvoiceServiceAsync service,
			final EzeeCreateUpdateDeleteEntityType type) {
		super(false, false);
		this.entity = entity;
		this.service = service;
		this.type = type;
	}

	public final T getEntity() {
		return entity;
	}

	public void close() {
		this.hide(true);
	}

	protected abstract void initialise();

	protected abstract void bind();
}