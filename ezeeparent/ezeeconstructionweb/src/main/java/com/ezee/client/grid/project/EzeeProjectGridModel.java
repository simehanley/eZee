package com.ezee.client.grid.project;

import static com.ezee.common.web.EzeeFromatUtils.getAmountFormat;
import static com.ezee.web.common.ui.css.EzeeDefaultResources.INSTANCE;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ezee.model.entity.project.EzeeProject;
import com.ezee.web.common.ui.grid.EzeeGridModel;
import com.ezee.web.common.ui.utils.EzeeDateComparator;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;

public class EzeeProjectGridModel extends EzeeGridModel<EzeeProject> {

	public static final String PROJECT = "Project";
	public static final String START_DATE = "Start Date";
	public static final String END_DATE = "End Date";
	public static final String BUDGETED = "Budget";
	public static final String ACTUAL = "Actual";
	public static final String PAID = "Paid";
	public static final String BALANCE = "Balance";
	public static final String PERCENT_COMPLETE = "% Complete";

	public static final double PROJECT_WIDTH = 300.;

	private final EzeeDateComparator dateComparator = new EzeeDateComparator();

	@Override
	protected Map<String, Column<EzeeProject, ?>> createColumns(final DataGrid<EzeeProject> grid) {
		Map<String, Column<EzeeProject, ?>> columns = new HashMap<>();
		createTextColumn(columns, grid, PROJECT, PROJECT_WIDTH, true);
		createDateColumn(columns, grid, START_DATE, DATE_FIELD_WIDTH, true);
		createDateColumn(columns, grid, END_DATE, DATE_FIELD_WIDTH, true);
		createNumericColumn(columns, grid, BUDGETED, NUMERIC_FIELD_WIDTH);
		createNumericColumn(columns, grid, ACTUAL, NUMERIC_FIELD_WIDTH);
		createNumericColumn(columns, grid, PAID, NUMERIC_FIELD_WIDTH);
		createNumericColumn(columns, grid, BALANCE, NUMERIC_FIELD_WIDTH);
		createTextColumn(columns, grid, PERCENT_COMPLETE, COLUMN_WIDTH_NOT_SET, false);
		return columns;
	}

	@Override
	protected String resolveTextFieldValue(final String fieldName, final EzeeProject project) {
		switch (fieldName) {
		case PROJECT:
			return project.getName();
		case BUDGETED:
			return getAmountFormat().format(project.budgeted().getTotal());
		case ACTUAL:
			return getAmountFormat().format(project.actual().getTotal());
		case PAID:
			return getAmountFormat().format(project.paid().getTotal());
		case BALANCE:
			return getAmountFormat().format(project.balance().getTotal());
		case PERCENT_COMPLETE:
			return project.percent();
		}
		return null;
	}

	@Override
	protected void setTextFieldValue(final String fieldName, final String fieldValue, final EzeeProject project) {
		/* do nothing */
	}

	@Override
	protected Date resolveDateFieldValue(final String fieldName, final EzeeProject project) {
		switch (fieldName) {
		case START_DATE:
			return dateUtilities.fromString(project.getStartDate());
		case END_DATE:
			return dateUtilities.fromString(project.getEndDate());
		}
		return null;
	}

	@Override
	protected boolean resolveBooleanFieldValue(final String fieldName, final EzeeProject entity) {
		/* do nothing */
		return false;
	}

	@Override
	protected void setBooleanFieldValue(final String fieldName, final boolean fieldValue, final EzeeProject entity) {
		/* do nothing */
	}

	@Override
	protected void addComparators(final Map<String, Column<EzeeProject, ?>> columns) {

		handler.setComparator(columns.get(PROJECT), new Comparator<EzeeProject>() {

			@Override
			public int compare(final EzeeProject one, final EzeeProject two) {
				return one.getName().compareTo(two.getName());
			}
		});

		handler.setComparator(columns.get(START_DATE), new Comparator<EzeeProject>() {

			@Override
			public int compare(final EzeeProject one, final EzeeProject two) {
				return dateComparator.compare(dateUtilities.fromString(one.getStartDate()),
						dateUtilities.fromString(two.getEndDate()));
			}
		});

		handler.setComparator(columns.get(END_DATE), new Comparator<EzeeProject>() {

			@Override
			public int compare(final EzeeProject one, final EzeeProject two) {
				return dateComparator.compare(dateUtilities.fromString(one.getEndDate()),
						dateUtilities.fromString(two.getEndDate()));
			}

		});
	}

	@Override
	protected void addSortColumns(final DataGrid<EzeeProject> grid, final Map<String, Column<EzeeProject, ?>> columns) {
		grid.getColumnSortList().push(columns.get(PROJECT));
		grid.getColumnSortList().push(columns.get(START_DATE));
		grid.getColumnSortList().push(columns.get(END_DATE));
	}

	protected String resolveCellStyleNames(final EzeeProject project) {
		return INSTANCE.css().black();
	}
}