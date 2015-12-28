package com.ezee.web.common.ui.grid.payee;

import com.ezee.model.entity.EzeePayee;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.grid.EzeeFinancialEntityGrid;

/**
 * 
 * @author siborg
 *
 */
public abstract class EzeePayeeGrid<T extends EzeePayee> extends EzeeFinancialEntityGrid<T> {

	public EzeePayeeGrid(final EzeeEntityCache cache, final String[] crudHeaders) {
		super(cache, crudHeaders);
	}

	protected void initGrid() {
		super.initGrid();
		model = new EzeePayeeGridModel<T>();
		model.bind(grid);
	}

	@Override
	protected void initFilter() {
		super.initFilter();
		createToolbar();
	}

	protected abstract void createToolbar();
}