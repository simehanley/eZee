package com.ezee.client.grid.project.data.detail;

import static com.ezee.common.web.EzeeFromatUtils.getAmountFormat;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_RIGHT;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ezee.model.entity.enums.EzeeProjectItemType;
import com.ezee.model.entity.project.EzeeProjectItemDetail;
import com.ezee.web.common.ui.grid.EzeeGridModel;
import com.ezee.web.common.ui.grid.EzeeGridModelListener;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;

public class EzeeProjectItemDetailGridModel extends EzeeGridModel<EzeeProjectItemDetail> {

	private static final String DESCRIPTION = "Description";
	private static final String TYPE = "Type";
	private static final String AMOUNT = "Amount";
	private static final String TAX = "Tax";
	private static final String TOTAL = "Total";

	private static final double DESCRIPTION_WIDTH = 400.;
	private static final double TYPE_WIDTH = 100.;

	public EzeeProjectItemDetailGridModel(final EzeeGridModelListener<EzeeProjectItemDetail> listener) {
		super(listener);
	}

	@Override
	protected Map<String, Column<EzeeProjectItemDetail, ?>> createColumns(final DataGrid<EzeeProjectItemDetail> grid) {
		Map<String, Column<EzeeProjectItemDetail, ?>> columns = new HashMap<>();
		createEditableTextColumn(columns, grid, DESCRIPTION, DESCRIPTION_WIDTH, true);
		createProjectItemTypeListBoxColumn(columns, grid, TYPE, TYPE_WIDTH);
		createTextColumn(columns, grid, AMOUNT, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, TAX, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, TOTAL, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		return columns;
	}

	@Override
	protected void setTextFieldValue(final String fieldName, final String fieldValue,
			final EzeeProjectItemDetail entity) {
		switch (fieldName) {
		case DESCRIPTION:
			entity.setDescription(fieldValue);
			break;
		case TYPE:
			entity.setType(EzeeProjectItemType.get(fieldValue));
			break;
		case AMOUNT:
			entity.setAmount(getAmountFormat().parse(fieldValue));
			break;
		case TAX:
			entity.setTax(getAmountFormat().parse(fieldValue));
			break;
		}
	}

	@Override
	protected String resolveTextFieldValue(final String fieldName, final EzeeProjectItemDetail entity) {
		switch (fieldName) {
		case DESCRIPTION:
			return entity.getDescription();
		case TYPE:
			return entity.getType().name();
		case AMOUNT:
			return getAmountFormat().format(entity.getAmount());
		case TAX:
			return getAmountFormat().format(entity.getTax());
		case TOTAL:
			return getAmountFormat().format(entity.getTotal());
		}
		return null;
	}

	@Override
	protected Date resolveDateFieldValue(String fieldName, EzeeProjectItemDetail entity) {
		return null;
	}

	@Override
	protected boolean resolveBooleanFieldValue(String fieldName, EzeeProjectItemDetail entity) {
		/* do nothing */
		return false;
	}

	@Override
	protected void setBooleanFieldValue(String fieldName, boolean fieldValue, EzeeProjectItemDetail entity) {
		/* do nothing */
	}

	@Override
	protected void addComparators(Map<String, Column<EzeeProjectItemDetail, ?>> columns) {
		handler.setComparator(columns.get(DESCRIPTION), new Comparator<EzeeProjectItemDetail>() {
			@Override
			public int compare(final EzeeProjectItemDetail one, final EzeeProjectItemDetail two) {
				return one.getDescription().compareTo(two.getDescription());
			}
		});
	}

	@Override
	protected void addSortColumns(DataGrid<EzeeProjectItemDetail> grid,
			Map<String, Column<EzeeProjectItemDetail, ?>> columns) {
		grid.getColumnSortList().push(columns.get(DESCRIPTION));

	}

	private void createProjectItemTypeListBoxColumn(final Map<String, Column<EzeeProjectItemDetail, ?>> columns,
			final DataGrid<EzeeProjectItemDetail> grid, final String fieldName, final double width) {

		SelectionCell cell = new SelectionCell(EzeeProjectItemType.types());
		Column<EzeeProjectItemDetail, String> typeColumn = new Column<EzeeProjectItemDetail, String>(cell) {
			@Override
			public String getValue(final EzeeProjectItemDetail item) {
				return item.getType().name();
			}

			@Override
			public String getCellStyleNames(final Context context, final EzeeProjectItemDetail item) {
				return resolveCellStyleNames(item);
			}
		};

		typeColumn.setFieldUpdater(new FieldUpdater<EzeeProjectItemDetail, String>() {
			@Override
			public void update(final int index, final EzeeProjectItemDetail item, final String value) {

				item.setType(EzeeProjectItemType.get(value));
				if (listener != null) {
					listener.modelUpdated(item);
				}
			}
		});
		typeColumn.setHorizontalAlignment(ALIGN_CENTER);
		createColumn(columns, grid, typeColumn, fieldName, width, false);
	}
}