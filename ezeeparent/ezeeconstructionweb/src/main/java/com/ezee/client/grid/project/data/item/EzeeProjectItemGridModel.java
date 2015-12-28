package com.ezee.client.grid.project.data.item;

import static com.ezee.common.web.EzeeFormatUtils.getAmountFormat;
import static com.ezee.common.web.EzeeFormatUtils.getPercentFormat;
import static com.ezee.web.common.ui.css.EzeeDefaultResources.INSTANCE;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_LEFT;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_RIGHT;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ezee.model.entity.EzeeContractor;
import com.ezee.model.entity.project.EzeeProjectItem;
import com.ezee.web.common.ui.grid.EzeeGridModel;
import com.ezee.web.common.ui.grid.EzeeGridModelListener;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;

public class EzeeProjectItemGridModel extends EzeeGridModel<EzeeProjectItem> {

	private static final String NAME = "Name";
	private static final String CONTRACTOR = "Contractor";
	private static final String BUDGETED = "Budget";
	private static final String ACTUAL = "Actual";
	private static final String PAID = "Paid";
	private static final String BALANCE = "Balance";
	private static final String PERCENT_COMPLETE = "% Complete";

	private static final double NAME_WIDTH = 250.;
	private static final double CONTRACTOR_WIDTH = 250.;

	private final Map<String, EzeeContractor> contractors;

	public EzeeProjectItemGridModel(final EzeeGridModelListener<EzeeProjectItem> listener,
			final Map<String, EzeeContractor> contractors) {
		super(listener);
		this.contractors = contractors;
	}

	@Override
	protected Map<String, Column<EzeeProjectItem, ?>> createColumns(final DataGrid<EzeeProjectItem> grid) {
		Map<String, Column<EzeeProjectItem, ?>> columns = new HashMap<>();
		createEditableTextColumn(columns, grid, NAME, NAME_WIDTH, true, ALIGN_LEFT);
		createContractorListBoxColumn(columns, grid, CONTRACTOR, CONTRACTOR_WIDTH);
		createTextColumn(columns, grid, BUDGETED, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, ACTUAL, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, PAID, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, BALANCE, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, PERCENT_COMPLETE, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		return columns;
	}

	@Override
	protected String resolveTextFieldValue(final String fieldName, final EzeeProjectItem item) {
		switch (fieldName) {
		case NAME:
			return item.getName();
		case BUDGETED:
			return getAmountFormat().format(item.budgeted().getTotal());
		case ACTUAL:
			return getAmountFormat().format(item.actual().getTotal());
		case PAID:
			return getAmountFormat().format(item.paid().getTotal());
		case BALANCE:
			return getAmountFormat().format(item.balance().getTotal());
		case PERCENT_COMPLETE:
			return getPercentFormat().format(item.percent());
		default:
			return null;
		}
	}

	@Override
	protected void setTextFieldValue(final String fieldName, final String fieldValue, final EzeeProjectItem item) {
		switch (fieldName) {
		case NAME:
			item.setName(fieldValue);
		}
	}

	@Override
	protected Date resolveDateFieldValue(String fieldName, EzeeProjectItem entity) {
		/* do nothing */
		return null;
	}

	@Override
	protected void setDateFieldValue(final String fieldName, final Date fieldValue, final EzeeProjectItem entity) {
		/* do nothing */
	}

	@Override
	protected boolean resolveBooleanFieldValue(String fieldName, EzeeProjectItem entity) {
		/* do nothing */
		return false;
	}

	@Override
	protected void setBooleanFieldValue(String fieldName, boolean fieldValue, EzeeProjectItem entity) {
		/* do nothing */
	}

	private void createContractorListBoxColumn(final Map<String, Column<EzeeProjectItem, ?>> columns,
			final DataGrid<EzeeProjectItem> grid, final String fieldName, final double width) {

		SelectionCell cell = new SelectionCell(new ArrayList<>(contractors.keySet()));
		Column<EzeeProjectItem, String> resourceColumn = new Column<EzeeProjectItem, String>(cell) {
			@Override
			public String getValue(final EzeeProjectItem item) {
				return item.getContractor().getName();
			}

			@Override
			public String getCellStyleNames(final Context context, final EzeeProjectItem item) {
				return resolveCellStyleNames(item);
			}
		};
		resourceColumn.setFieldUpdater(new FieldUpdater<EzeeProjectItem, String>() {
			@Override
			public void update(final int index, final EzeeProjectItem item, final String value) {
				EzeeContractor resource = contractors.get(value);
				if (!resource.equals(item.getContractor())) {
					item.setContractor(contractors.get(value));
					if (listener != null) {
						listener.modelUpdated(item);
					}
				}
			}
		});
		resourceColumn.setHorizontalAlignment(ALIGN_CENTER);
		createColumn(columns, grid, resourceColumn, fieldName, width, false);
	}

	@Override
	protected void addComparators(Map<String, Column<EzeeProjectItem, ?>> columns) {
		handler.setComparator(columns.get(NAME), new Comparator<EzeeProjectItem>() {
			@Override
			public int compare(final EzeeProjectItem one, final EzeeProjectItem two) {
				return one.getName().compareTo(two.getName());
			}
		});
	}

	@Override
	protected void addSortColumns(final DataGrid<EzeeProjectItem> grid,
			final Map<String, Column<EzeeProjectItem, ?>> columns) {
		grid.getColumnSortList().push(columns.get(NAME));
	}

	protected String resolveCellStyleNames(final EzeeProjectItem item) {
		return INSTANCE.css().black();
	}
}