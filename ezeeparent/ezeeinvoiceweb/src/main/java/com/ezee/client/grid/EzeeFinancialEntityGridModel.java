package com.ezee.client.grid;

import static com.ezee.web.common.ui.css.EzeeDefaultResources.INSTANCE;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ezee.model.entity.EzeeFinancialEntity;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;

public abstract class EzeeFinancialEntityGridModel<T extends EzeeFinancialEntity> extends EzeeGridModel<T> {

	protected static final String NAME = "Name";

	@Override
	protected Map<String, Column<T, ?>> createColumns(final DataGrid<T> grid) {
		Map<String, Column<T, ?>> columns = new HashMap<>();
		createTextColumn(columns, grid, NAME, true);
		return columns;
	}

	@Override
	protected String resolveTextFieldValue(final String fieldName, final T entity) {
		switch (fieldName) {
		case NAME:
			return entity.getName();
		default:
			return null;
		}
	}

	@Override
	protected Date resolveDateFieldValue(final String fieldName, final T entity) {
		/* do nothing */
		return null;
	}

	@Override
	protected void addComparators(final Map<String, Column<T, ?>> columns) {

		handler.setComparator(columns.get(NAME), new Comparator<T>() {

			@Override
			public int compare(final T one, final T two) {
				return one.getName().compareTo(two.getName());
			}
		});
	}

	@Override
	protected void addSortColumns(final DataGrid<T> grid, final Map<String, Column<T, ?>> columns) {
		grid.getColumnSortList().push(columns.get(NAME));
	}

	protected String resolveCellStyleNames(final T entity) {
		return INSTANCE.css().black();
	}
}