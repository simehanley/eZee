package com.ezee.web.common.ui.grid;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.common.web.EzeeFromatUtils.getDateFormat;
import static com.google.gwt.dom.client.Style.Unit.PX;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_LEFT;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_RIGHT;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ezee.common.EzeeDateUtilities;
import com.ezee.common.web.EzeeClientDateUtils;
import com.ezee.model.entity.EzeeDatabaseEntity;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
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

	protected static final double COLUMN_WIDTH_NOT_SET = -1.;

	protected static final double DATE_FIELD_WIDTH = 85.;
	protected static final double NUMERIC_FIELD_WIDTH = 100.;

	protected final EzeeDateUtilities dateUtilities = new EzeeClientDateUtils();

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

	protected abstract void setTextFieldValue(String fieldName, String fieldValue, T entity);

	protected abstract Date resolveDateFieldValue(String fieldName, T entity);

	protected abstract void addComparators(final Map<String, Column<T, ?>> columns);

	protected abstract void addSortColumns(DataGrid<T> grid, Map<String, Column<T, ?>> columns);

	protected abstract boolean resolveBooleanFieldValue(String fieldName, T entity);

	protected abstract void setBooleanFieldValue(String fieldName, boolean fieldValue, T entity);

	protected void createTextColumn(final Map<String, Column<T, ?>> columns, final DataGrid<T> grid,
			final String fieldName, final double width, boolean sortable) {
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

	protected void createEditableTextColumn(final Map<String, Column<T, ?>> columns, final DataGrid<T> grid,
			final String fieldName, final double width, boolean sortable) {
		if (!isHiddenColumn(fieldName)) {
			Column<T, String> column = new Column<T, String>(new EditTextCell()) {
				@Override
				public String getValue(T entity) {
					return resolveTextFieldValue(fieldName, entity);
				}

				@Override
				public String getCellStyleNames(final Context context, final T entity) {
					return resolveCellStyleNames(entity);
				}
			};
			column.setFieldUpdater(new FieldUpdater<T, String>() {

				@Override
				public void update(int index, T object, String value) {
					setTextFieldValue(fieldName, value, object);
				}
			});
			column.setHorizontalAlignment(ALIGN_LEFT);
			createColumn(columns, grid, column, fieldName, width, sortable);
		}
	}

	protected void createDateColumn(final Map<String, Column<T, ?>> columns, final DataGrid<T> grid,
			final String fieldName, final double width, boolean sortable) {
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
			final String fieldName, final double width) {
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

	protected void createEditableCheckBoxColumn(final Map<String, Column<T, ?>> columns, final DataGrid<T> grid,
			final String fieldName, final double width) {

		Column<T, Boolean> column = new Column<T, Boolean>(new CheckboxCell()) {
			@Override
			public Boolean getValue(T entity) {
				return resolveBooleanFieldValue(fieldName, entity);
			}

			@Override
			public String getCellStyleNames(final Context context, final T entity) {
				return resolveCellStyleNames(entity);
			}
		};
		column.setFieldUpdater(new FieldUpdater<T, Boolean>() {

			@Override
			public void update(int index, T entity, Boolean value) {
				setBooleanFieldValue(fieldName, value, entity);
			}
		});

		column.setHorizontalAlignment(ALIGN_CENTER);
		createColumn(columns, grid, column, fieldName, width, true);
	}

	protected void createColumn(final Map<String, Column<T, ?>> columns, final DataGrid<T> grid,
			final Column<T, ?> column, final String fieldName, final double width, boolean sortable) {
		column.setSortable(sortable);
		if (width > ZERO_DBL) {
			grid.setColumnWidth(column, width, PX);
		}
		grid.addColumn(column, fieldName);
		columns.put(fieldName, column);
	}

	protected String resolveCellStyleNames(final T entity) {
		return EMPTY_STRING;
	}
}