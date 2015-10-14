package com.ezee.client.grid.payer;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.grid.EzeeFinancialEntityGrid;
import com.ezee.model.entity.EzeePayer;

public class EzeePayerGrid extends EzeeFinancialEntityGrid<EzeePayer> {

	public EzeePayerGrid(final EzeeInvoiceServiceAsync service) {
		super(service);
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

}
