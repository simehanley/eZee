package com.ezee.client.grid.payer;

import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.crud.payer.EzeeCreateUpdateDeletePayer;
import com.ezee.client.grid.EzeeFinancialEntityGrid;
import com.ezee.model.entity.EzeePayer;

public class EzeePayerGrid extends EzeeFinancialEntityGrid<EzeePayer> {

	public EzeePayerGrid(final EzeeInvoiceServiceAsync service, final EzeeInvoiceEntityCache cache) {
		super(service, cache);
	}

	protected void initGrid() {
		super.initGrid();
		model = new EzeePayerGridModel();
		model.bind(grid);
	}

	@Override
	public String getGridClass() {
		return EzeePayer.class.getName();
	}

	@Override
	protected void deleteEntity() {
		EzeePayer entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayer(service, cache, this, entity, delete).center();
		}
	}

	@Override
	protected void newEntity() {
		new EzeeCreateUpdateDeletePayer(service, cache, this).center();
	}

	@Override
	protected void editEntity() {
		EzeePayer entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayer(service, cache, this, entity, update).center();
		}
	}
}