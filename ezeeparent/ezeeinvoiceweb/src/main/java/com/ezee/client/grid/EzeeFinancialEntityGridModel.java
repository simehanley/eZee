package com.ezee.client.grid;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.ezee.model.entity.EzeeFinancialEntity;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;

public abstract class EzeeFinancialEntityGridModel<T extends EzeeFinancialEntity> extends EzeeGridModel<T> {

	protected static final String NAME = "Name";
	protected static final String ADDRESS_LINE_1 = "Address (Line 1)";

	protected static final String NAME_WIDTH = "300px";
	protected static final String ADDRESS_LINE_1_WIDTH = "300px";

	@Override
	protected Map<String, Column<T, ?>> createColumns(final DataGrid<T> grid) {
		Map<String, Column<T, ?>> columns = new HashMap<>();
		createTextColumn(columns, grid, NAME, NAME_WIDTH, true);
		createTextColumn(columns, grid, ADDRESS_LINE_1, ADDRESS_LINE_1_WIDTH, true);
		return columns;
	}

	@Override
	protected void addSortHandling(final DataGrid<T> grid, final Map<String, Column<T, ?>> columns) {

		handler.setComparator(columns.get(NAME), new Comparator<T>() {

			@Override
			public int compare(final T one, final T two) {
				return one.getName().compareTo(two.getName());
			}
		});

		handler.setComparator(columns.get(ADDRESS_LINE_1), new Comparator<T>() {

			@Override
			public int compare(final T one, final T two) {
				return one.getAddressLineOne().compareTo(two.getAddressLineOne());
			}
		});

		grid.addColumnSortHandler(handler);
		grid.getColumnSortList().push(columns.get(NAME));
		grid.getColumnSortList().push(columns.get(ADDRESS_LINE_1));
	}

	private void createTextColumn(final Map<String, Column<T, ?>> columns, final DataGrid<T> grid, final String header,
			final String width, boolean sortable) {
		TextColumn<T> column = new TextColumn<T>() {
			@Override
			public String getValue(final T entity) {
				switch (header) {
				case NAME:
					return entity.getName();
				default:
					return entity.getAddressLineOne();
				}
			}
		};
		column.setSortable(sortable);
		grid.addColumn(column, header);
		grid.setColumnWidth(column, width);
		columns.put(header, column);
	}
}
