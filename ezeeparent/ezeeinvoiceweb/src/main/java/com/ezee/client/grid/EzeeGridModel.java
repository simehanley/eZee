package com.ezee.client.grid;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.common.web.EzeeFromatUtils.getDateFormat;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_LEFT;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_RIGHT;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ezee.model.entity.EzeeDatabaseEntity;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;

/**
 * 
 * @author siborg
 *
 */
public abstract class EzeeGridModel<T extends EzeeDatabaseEntity> {

	protected static final String DATE_FIELD_WIDTH = "75px";
	protected static final String NUMERIC_FIELD_WIDTH = "120px";
	protected static final String BOOLEAN_FIELD_WIDTH = "160px";

	protected ListHandler<T> handler;

	protected Set<String> hiddenColumns;

	public EzeeGridModel() {
		this(null);
	}

	public EzeeGridModel(Set<String> hiddenColumns) {
		this.hiddenColumns = hiddenColumns;
	}

	public final ListHandler<T> getHandler() {
		return handler;
	}

	protected boolean isHiddenColumn(final String columnName) {
		return !isEmpty(hiddenColumns) && hiddenColumns.contains(columnName);
	}

	public void bind(final DataGrid<T> grid) {
		Map<String, Column<T, ?>> columns = createColumns(grid);
		createDataHandler(grid);
		addSortHandling(grid, columns);
	}

	protected void createDataHandler(final DataGrid<T> grid) {
		ListDataProvider<T> provider = new ListDataProvider<>();
		provider.addDataDisplay(grid);
		List<T> list = provider.getList();
		handler = new ListHandler<T>(list);
	}

	protected void addSortHandling(final DataGrid<T> grid, final Map<String, Column<T, ?>> columns) {
		addComparators(columns);
		grid.addColumnSortHandler(handler);
		addSortColumns(grid, columns);
	}

	protected abstract Map<String, Column<T, ?>> createColumns(DataGrid<T> grid);

	protected abstract String resolveTextFieldValue(String fieldName, T entity);

	protected abstract Date resolveDateFieldValue(String fieldName, T entity);

	protected abstract void addComparators(final Map<String, Column<T, ?>> columns);

	protected abstract void addSortColumns(DataGrid<T> grid, Map<String, Column<T, ?>> columns);

	protected void createTextColumn(final Map<String, Column<T, ?>> columns, final DataGrid<T> grid,
			final String fieldName, final String width, boolean sortable) {
		if (!isHiddenColumn(fieldName)) {
			TextColumn<T> column = new TextColumn<T>() {
				@Override
				public String getValue(final T entity) {
					return resolveTextFieldValue(fieldName, entity);
				}

				@Override
				public String getCellStyleNames(final Context context, final T entity) {
					return resolveCellStyleNames(entity);
				}
			};
			column.setHorizontalAlignment(ALIGN_LEFT);
			createColumn(columns, grid, column, fieldName, width, sortable);
		}
	}

	protected void createDateColumn(final Map<String, Column<T, ?>> columns, final DataGrid<T> grid,
			final String fieldName, final String width, boolean sortable) {
		if (!isHiddenColumn(fieldName)) {
			Column<T, Date> column = new Column<T, Date>(new DateCell(getDateFormat())) {
				@Override
				public Date getValue(final T entity) {
					return resolveDateFieldValue(fieldName, entity);
				}

				@Override
				public String getCellStyleNames(final Context context, final T entity) {
					return resolveCellStyleNames(entity);
				}
			};
			column.setHorizontalAlignment(ALIGN_CENTER);
			createColumn(columns, grid, column, fieldName, width, sortable);
		}
	}

	protected void createNumericColumn(final Map<String, Column<T, ?>> columns, final DataGrid<T> grid,
			final String fieldName, final String width) {
		if (!isHiddenColumn(fieldName)) {
			TextColumn<T> column = new TextColumn<T>() {
				@Override
				public String getValue(final T entity) {
					return resolveTextFieldValue(fieldName, entity);
				}

				@Override
				public String getCellStyleNames(final Context context, final T entity) {
					return resolveCellStyleNames(entity);
				}
			};
			column.setHorizontalAlignment(ALIGN_RIGHT);
			createColumn(columns, grid, column, fieldName, width, false);
		}
	}

	protected void createColumn(final Map<String, Column<T, ?>> columns, final DataGrid<T> grid,
			final Column<T, ?> column, final String fieldName, final String width, boolean sortable) {
		column.setSortable(sortable);
		grid.addColumn(column, fieldName);
		grid.setColumnWidth(column, width);
		columns.put(fieldName, column);
	}

	protected String resolveCellStyleNames(final T entity) {
		return EMPTY_STRING;
	}
}