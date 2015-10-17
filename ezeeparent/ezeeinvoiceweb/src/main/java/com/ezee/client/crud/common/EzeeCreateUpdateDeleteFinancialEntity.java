package com.ezee.client.crud.common;

import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.create;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.crud.EzeeCreateUpdateDeleteEntity;
import com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.model.entity.EzeeFinancialEntity;

/**
 * 
 * @author siborg
 *
 */
public abstract class EzeeCreateUpdateDeleteFinancialEntity<T extends EzeeFinancialEntity>
		extends EzeeCreateUpdateDeleteEntity<T> {

	public EzeeCreateUpdateDeleteFinancialEntity(final EzeeInvoiceServiceAsync service) {
		this(null, service, create);
	}

	public EzeeCreateUpdateDeleteFinancialEntity(final T entity, final EzeeInvoiceServiceAsync service,
			final EzeeCreateUpdateDeleteEntityType type) {
		super(entity, service, type);
	}
}