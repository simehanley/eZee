package com.ezee.client.grid.invoice;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.grid.EzeeGrid;
import com.ezee.model.entity.EzeeInvoice;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceGrid extends EzeeGrid<EzeeInvoice> {

	public EzeeInvoiceGrid(final EzeeInvoiceServiceAsync service) {
		super(service);
	}

	protected void initGrid() {
		super.initGrid();
		model = new EzeeInvoiceGridModel();
		model.bind(grid);
	}

	@Override
	public String getGridClass() {
		return EzeeInvoice.class.getName();
	}

	@Override
	public void initContextMenu() {
		/* implement me */
	}
}