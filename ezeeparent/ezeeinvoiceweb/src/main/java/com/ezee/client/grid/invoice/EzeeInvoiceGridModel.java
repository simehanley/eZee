package com.ezee.client.grid.invoice;

import java.util.HashMap;
import java.util.Map;

import com.ezee.client.grid.EzeeGridModel;
import com.ezee.model.entity.EzeeInvoice;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceGridModel extends EzeeGridModel<EzeeInvoice> {

	@Override
	protected Map<String, Column<EzeeInvoice, ?>> createColumns(DataGrid<EzeeInvoice> grid) {
		return new HashMap<>();
	}

	@Override
	protected void addSortHandling(DataGrid<EzeeInvoice> grid, Map<String, Column<EzeeInvoice, ?>> columns) {
	}
}