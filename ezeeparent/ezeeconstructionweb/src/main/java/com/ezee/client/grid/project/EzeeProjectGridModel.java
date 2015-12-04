package com.ezee.client.grid.project;

import static com.ezee.common.web.EzeeFromatUtils.getAmountFormat;

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

	private final EzeeDateComparator dateComparator = new EzeeDateComparator();

	@Override
	protected Map<String, Column<EzeeProject, ?>> createColumns(final DataGrid<EzeeProject> grid) {
		Map<String, Column<EzeeProject, ?>> columns = new HashMap<>();
		createTextColumn(columns, grid, PROJECT, true);
		createDateColumn(columns, grid, START_DATE, true);
		createDateColumn(columns, grid, END_DATE, true);
		createNumericColumn(columns, grid, BUDGETED);
		createNumericColumn(columns, grid, ACTUAL);
		createNumericColumn(columns, grid, PAID);
		createNumericColumn(columns, grid, BALANCE);
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
		}
		return null;
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
	protected void addComparators(final Map<String, Column<EzeeProject, ?>> columns) {

	}

	@Override
	protected void addSortColumns(final DataGrid<EzeeProject> grid, final Map<String, Column<EzeeProject, ?>> columns) {
	}
}