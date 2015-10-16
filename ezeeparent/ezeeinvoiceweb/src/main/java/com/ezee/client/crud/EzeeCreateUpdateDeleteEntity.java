package com.ezee.client.crud;

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

	protected final EzeeInvoiceServiceAsync invoiceService;

	public EzeeCreateUpdateDeleteEntity(final EzeeInvoiceServiceAsync invoiceService) {
		this(null, invoiceService);
	}

	public EzeeCreateUpdateDeleteEntity(final T entity, final EzeeInvoiceServiceAsync invoiceService) {
		super(false, false);
		this.entity = entity;
		this.invoiceService = invoiceService;
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