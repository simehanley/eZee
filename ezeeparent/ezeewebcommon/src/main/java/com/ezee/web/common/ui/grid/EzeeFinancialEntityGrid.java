package com.ezee.web.common.ui.grid;

import com.ezee.model.entity.EzeeFinancialEntity;
import com.ezee.web.common.cache.EzeeEntityCache;

public abstract class EzeeFinancialEntityGrid<T extends EzeeFinancialEntity> extends EzeeGrid<T> {

	protected final String[] crudHeaders;

	public EzeeFinancialEntityGrid(final EzeeEntityCache cache, final String[] crudHeaders) {
		super(cache);
		this.crudHeaders = crudHeaders;
	}

	@Override
	protected void initFilter() {
		super.initFilter();
	}
}