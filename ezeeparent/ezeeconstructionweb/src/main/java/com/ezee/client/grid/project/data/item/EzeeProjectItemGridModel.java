package com.ezee.client.grid.project.data.item;

import static com.ezee.common.web.EzeeFormatUtils.getAmountFormat;
import static com.ezee.common.web.EzeeFormatUtils.getPercentFormat;
import static com.ezee.web.common.ui.css.EzeeDefaultResources.INSTANCE;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_LEFT;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_RIGHT;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ezee.model.entity.project.EzeeProjectItem;
import com.ezee.web.common.ui.grid.EzeeGridModel;
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

	public EzeeProjectItemGridModel() {
		super(null);
	}

	@Override
	protected Map<String, Column<EzeeProjectItem, ?>> createColumns(final DataGrid<EzeeProjectItem> grid) {
		Map<String, Column<EzeeProjectItem, ?>> columns = new HashMap<>();
		createTextColumn(columns, grid, NAME, NAME_WIDTH, true, ALIGN_LEFT);
		createTextColumn(columns, grid, CONTRACTOR, CONTRACTOR_WIDTH, true, ALIGN_LEFT);
		createTextColumn(columns, grid, BUDGETED, NUMERIC_FIELD_WIDTH, true, ALIGN_RIGHT);
		createTextColumn(columns, grid, ACTUAL, NUMERIC_FIELD_WIDTH, true, ALIGN_RIGHT);
		createTextColumn(columns, grid, PAID, NUMERIC_FIELD_WIDTH, true, ALIGN_RIGHT);
		createTextColumn(columns, grid, BALANCE, NUMERIC_FIELD_WIDTH, true, ALIGN_RIGHT);
		createTextColumn(columns, grid, PERCENT_COMPLETE, NUMERIC_FIELD_WIDTH, true, ALIGN_RIGHT);
		return columns;
	}

	@Override
	protected String resolveTextFieldValue(final String fieldName, final EzeeProjectItem item) {
		switch (fieldName) {
		case NAME:
			return item.getName();
		case CONTRACTOR:
			return item.getContractor().getName();
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

	@Override
	protected void addComparators(Map<String, Column<EzeeProjectItem, ?>> columns) {
		handler.setComparator(columns.get(NAME), new Comparator<EzeeProjectItem>() {
			@Override
			public int compare(final EzeeProjectItem one, final EzeeProjectItem two) {
				return one.getName().compareTo(two.getName());
			}
		});
		handler.setComparator(columns.get(CONTRACTOR), new Comparator<EzeeProjectItem>() {
			@Override
			public int compare(final EzeeProjectItem one, final EzeeProjectItem two) {
				return one.getContractor().getName().compareTo(two.getContractor().getName());
			}
		});
		handler.setComparator(columns.get(BUDGETED), new Comparator<EzeeProjectItem>() {
			@Override
			public int compare(final EzeeProjectItem one, final EzeeProjectItem two) {
				return new Double(one.budgeted().getTotal()).compareTo(new Double(two.budgeted().getTotal()));
			}
		});
		handler.setComparator(columns.get(ACTUAL), new Comparator<EzeeProjectItem>() {
			@Override
			public int compare(final EzeeProjectItem one, final EzeeProjectItem two) {
				return new Double(one.actual().getTotal()).compareTo(new Double(two.actual().getTotal()));
			}
		});
		handler.setComparator(columns.get(PAID), new Comparator<EzeeProjectItem>() {
			@Override
			public int compare(final EzeeProjectItem one, final EzeeProjectItem two) {
				return new Double(one.paid().getTotal()).compareTo(new Double(two.paid().getTotal()));
			}
		});
		handler.setComparator(columns.get(BALANCE), new Comparator<EzeeProjectItem>() {
			@Override
			public int compare(final EzeeProjectItem one, final EzeeProjectItem two) {
				return new Double(one.balance().getTotal()).compareTo(new Double(two.balance().getTotal()));
			}
		});
		handler.setComparator(columns.get(PERCENT_COMPLETE), new Comparator<EzeeProjectItem>() {
			@Override
			public int compare(final EzeeProjectItem one, final EzeeProjectItem two) {
				return new Double(one.percent()).compareTo(new Double(two.percent()));
			}
		});
	}

	@Override
	protected void addSortColumns(final DataGrid<EzeeProjectItem> grid,
			final Map<String, Column<EzeeProjectItem, ?>> columns) {
		grid.getColumnSortList().push(columns.get(NAME));
		grid.getColumnSortList().push(columns.get(CONTRACTOR));
		grid.getColumnSortList().push(columns.get(BUDGETED));
		grid.getColumnSortList().push(columns.get(ACTUAL));
		grid.getColumnSortList().push(columns.get(PAID));
		grid.getColumnSortList().push(columns.get(BALANCE));
		grid.getColumnSortList().push(columns.get(PERCENT_COMPLETE));

	}

	protected String resolveCellStyleNames(final EzeeProjectItem item) {
		return INSTANCE.css().black();
	}
}