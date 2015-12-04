package com.ezee.web.common.ui.grid.payee;

import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.model.entity.EzeePayee;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.payee.EzeeCreateUpdateDeletePayee;
import com.ezee.web.common.ui.grid.EzeeFinancialEntityGrid;
import com.ezee.web.common.ui.grid.EzeeFinancialEntityToolbar;

/**
 * 
 * @author siborg
 *
 */
public class EzeePayeeGrid extends EzeeFinancialEntityGrid<EzeePayee> {

	public EzeePayeeGrid(final EzeeEntityCache cache, final String[] crudHeaders) {
		super(cache, crudHeaders);
	}

	protected void initGrid() {
		super.initGrid();
		model = new EzeePayeeGridModel();
		model.bind(grid);
	}

	@Override
	protected void initFilter() {
		super.initFilter();
		toolBar = new EzeeFinancialEntityToolbar<EzeePayee>(this);
		filterpanel.add(toolBar);
	}

	@Override
	public String getGridClass() {
		return EzeePayee.class.getName();
	}

	@Override
	public void deleteEntity() {
		EzeePayee entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayee(cache, this, entity, delete, crudHeaders).show();
		}
	}

	@Override
	public void newEntity() {
		new EzeeCreateUpdateDeletePayee(cache, this, crudHeaders).show();
	}

	@Override
	public void editEntity() {
		EzeePayee entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayee(cache, this, entity, update, crudHeaders).show();
		}
	}
}