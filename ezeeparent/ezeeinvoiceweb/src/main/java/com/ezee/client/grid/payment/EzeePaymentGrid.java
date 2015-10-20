package com.ezee.client.grid.payment;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.grid.EzeeGrid;
import com.ezee.model.entity.EzeePayment;

/**
 * 
 * @author siborg
 *
 */
public class EzeePaymentGrid extends EzeeGrid<EzeePayment> {

	public EzeePaymentGrid(final EzeeInvoiceServiceAsync service, final EzeeInvoiceEntityCache cache) {
		super(service, cache);
	}

	protected void initGrid() {
		super.initGrid();
		model = new EzeePaymentGridModel();
		model.bind(grid);
	}

	@Override
	public String getGridClass() {
		return EzeePayment.class.getName();
	}

	@Override
	protected void deleteEntity() {
	}

	@Override
	protected void newEntity() {
	}

	@Override
	protected void editEntity() {
	}
}