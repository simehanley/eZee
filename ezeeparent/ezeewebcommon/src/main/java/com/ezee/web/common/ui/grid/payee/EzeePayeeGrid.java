package com.ezee.web.common.ui.grid.payee;

import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.model.entity.EzeePayee;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.payee.EzeeCreateUpdateDeletePayee;
import com.ezee.web.common.ui.grid.EzeeFinancialEntityGrid;

/**
 * 
 * @author siborg
 *
 */
public class EzeePayeeGrid<T extends EzeePayee> extends EzeeFinancialEntityGrid<T> {

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

	protected void createToolbar() {
		toolBar = new EzeePayeeToolbar<T>(this);
		filterpanel.add(toolBar);
	}

	@Override
	public String getGridClass() {
		return EzeePayee.class.getName();
	}

	@Override
	public void deleteEntity() {
		T entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayee<T>(cache, this, entity, delete, crudHeaders).show();
		}
	}

	@Override
	public void newEntity() {
		new EzeeCreateUpdateDeletePayee<T>(cache, this, crudHeaders).show();
	}

	@Override
	public void editEntity() {
		T entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayee<T>(cache, this, entity, update, crudHeaders).show();
		}
	}
}