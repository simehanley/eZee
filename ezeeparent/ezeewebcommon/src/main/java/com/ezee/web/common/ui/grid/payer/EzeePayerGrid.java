package com.ezee.web.common.ui.grid.payer;

import com.ezee.model.entity.EzeePayer;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.grid.EzeeFinancialEntityGrid;

public abstract class EzeePayerGrid<T extends EzeePayer> extends EzeeFinancialEntityGrid<T> {

	public EzeePayerGrid(final EzeeEntityCache cache, final String[] crudHeaders) {
		super(cache, crudHeaders);
	}

	protected void initGrid(final int pageSize) {
		super.initGrid(pageSize);
		model = new EzeePayerGridModel<T>();
		model.bind(grid);
	}

	@Override
	protected void initFilter() {
		super.initFilter();
		createToolbar();
	}

	protected abstract void createToolbar();
}