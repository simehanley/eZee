package com.ezee.client.grid.payee;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.grid.EzeeFinancialEntityGrid;
import com.ezee.model.entity.EzeePayee;

/**
 * 
 * @author siborg
 *
 */
public class EzeePayeeGrid extends EzeeFinancialEntityGrid<EzeePayee> {

	public EzeePayeeGrid(final EzeeInvoiceServiceAsync service) {
		super(service);
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
}