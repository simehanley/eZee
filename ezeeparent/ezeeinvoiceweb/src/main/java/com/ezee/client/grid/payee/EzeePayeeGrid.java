package com.ezee.client.grid.payee;

import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.crud.payee.EzeeCreateUpdateDeletePayee;
import com.ezee.client.grid.EzeeFinancialEntityGrid;
import com.ezee.client.grid.EzeeFinancialEntityToolbar;
import com.ezee.model.entity.EzeePayee;

/**
 * 
 * @author siborg
 *
 */
public class EzeePayeeGrid extends EzeeFinancialEntityGrid<EzeePayee> {

	public EzeePayeeGrid(final EzeeInvoiceEntityCache cache) {
		super(cache);
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
	protected void deleteEntity() {
		EzeePayee entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayee(cache, this, entity, delete).center();
		}
	}

	@Override
	protected void newEntity() {
		new EzeeCreateUpdateDeletePayee(cache, this).center();
	}

	@Override
	protected void editEntity() {
		EzeePayee entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayee(cache, this, entity, update).center();
		}
	}
}