package com.ezee.client.grid.payee;

import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.crud.payee.EzeeCreateUpdateDeletePayee;
import com.ezee.client.grid.EzeeFinancialEntityGrid;
import com.ezee.model.entity.EzeePayee;

/**
 * 
 * @author siborg
 *
 */
public class EzeePayeeGrid extends EzeeFinancialEntityGrid<EzeePayee> {

	public EzeePayeeGrid(final EzeeInvoiceServiceAsync service, final EzeeInvoiceEntityCache cache) {
		super(service, cache);
	}

	protected void initGrid() {
		super.initGrid();
		model = new EzeePayeeGridModel();
		model.bind(grid);
	}

	@Override
	public String getGridClass() {
		return EzeePayee.class.getName();
	}

	@Override
	protected void deleteEntity() {
		EzeePayee entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayee(service, cache, this, entity, delete).center();
		}
	}

	@Override
	protected void newEntity() {
		new EzeeCreateUpdateDeletePayee(service, cache, this).center();
	}

	@Override
	protected void editEntity() {
		EzeePayee entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayee(service, cache, this, entity, update).center();
		}
	}
}