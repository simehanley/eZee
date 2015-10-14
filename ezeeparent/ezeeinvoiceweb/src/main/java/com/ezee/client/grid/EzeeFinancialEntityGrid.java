package com.ezee.client.grid;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.model.entity.EzeeFinancialEntity;

public abstract class EzeeFinancialEntityGrid<T extends EzeeFinancialEntity> extends EzeeGrid<T> {

	public EzeeFinancialEntityGrid(final EzeeInvoiceServiceAsync service) {
		super(service);
	}
}