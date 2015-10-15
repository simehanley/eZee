package com.ezee.client.grid;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.DataGrid;

public class EzeeInvoiceGrid<T> extends Composite {

	private static EzeeInvoiceGridUiBinder uiBinder = GWT.create(EzeeInvoiceGridUiBinder.class);

	@UiField
	DataGrid<T> grid = new DataGrid<T>();

	interface EzeeInvoiceGridUiBinder extends UiBinder<Widget, EzeeInvoiceGrid<?>> {
	}

	public EzeeInvoiceGrid() {
		grid.setMinimumTableWidth(500, Unit.PX);
		initWidget(uiBinder.createAndBindUi(this));
	}

	public final DataGrid<T> getGrid() {
		return grid;
	}

	public void setGrid(DataGrid<T> grid) {
		this.grid = grid;
	}
}