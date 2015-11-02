package com.ezee.client.grid;

import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.model.entity.EzeeFinancialEntity;

public abstract class EzeeFinancialEntityGrid<T extends EzeeFinancialEntity> extends EzeeGrid<T> {

	public EzeeFinancialEntityGrid(final EzeeInvoiceEntityCache cache) {
		super(cache);
	}

	@Override
	protected void initFilter() {
		super.initFilter();
	}
}