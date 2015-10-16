package com.ezee.client.crud.common;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.crud.EzeeCreateUpdateDeleteEntity;
import com.ezee.model.entity.EzeeFinancialEntity;

/**
 * 
 * @author siborg
 *
 */
public abstract class EzeeCreateUpdateDeleteFinancialEntity<T extends EzeeFinancialEntity>
		extends EzeeCreateUpdateDeleteEntity<T> {

	public EzeeCreateUpdateDeleteFinancialEntity(final EzeeInvoiceServiceAsync invoiceService) {
		this(null, invoiceService);
	}

	public EzeeCreateUpdateDeleteFinancialEntity(T entity, EzeeInvoiceServiceAsync invoiceService) {
		super(entity, invoiceService);
	}
}