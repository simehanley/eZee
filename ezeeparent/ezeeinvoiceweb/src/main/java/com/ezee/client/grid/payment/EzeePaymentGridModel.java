package com.ezee.client.grid.payment;

import java.util.HashMap;
import java.util.Map;

import com.ezee.client.grid.EzeeGridModel;
import com.ezee.model.entity.EzeePayment;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;

public class EzeePaymentGridModel extends EzeeGridModel<EzeePayment> {

	@Override
	protected Map<String, Column<EzeePayment, ?>> createColumns(final DataGrid<EzeePayment> grid) {
		return new HashMap<>();
	}

	@Override
	protected void addSortHandling(final DataGrid<EzeePayment> grid, Map<String, Column<EzeePayment, ?>> columns) {
	}
}