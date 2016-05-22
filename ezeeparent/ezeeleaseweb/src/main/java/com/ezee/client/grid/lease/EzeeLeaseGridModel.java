package com.ezee.client.grid.lease;

import static com.ezee.common.web.EzeeFormatUtils.getAmountFormat;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.ANNUAL_OUTGOINGS_GST;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.ANNUAL_PARKING_GST;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.ANNUAL_RENT_GST;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.ANNUAL_SIGNAGE_GST;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.ANNUAL_TOTAL_GST;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.AREA;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.CATEGORY;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.END_DATE;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.GROSS_ANNUAL_OUTGOINGS;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.GROSS_ANNUAL_PARKING;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.GROSS_ANNUAL_RENT;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.GROSS_ANNUAL_SIGNAGE;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.GROSS_ANNUAL_TOTAL;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.GROSS_MONTHLY_OUTGOINGS;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.GROSS_MONTHLY_PARKING;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.GROSS_MONTHLY_RENT;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.GROSS_MONTHLY_SIGNAGE;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.GROSS_MONTHLY_TOTAL;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.MONTHLY_OUTGOINGS_GST;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.MONTHLY_PARKING_GST;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.MONTHLY_RENT_GST;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.MONTHLY_SIGNAGE_GST;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.MONTHLY_TOTAL_GST;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.NET_ANNUAL_OUTGOINGS;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.NET_ANNUAL_PARKING;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.NET_ANNUAL_RENT;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.NET_ANNUAL_SIGNAGE;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.NET_ANNUAL_TOTAL;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.NET_MONTHLY_OUTGOINGS;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.NET_MONTHLY_PARKING;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.NET_MONTHLY_RENT;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.NET_MONTHLY_SIGNAGE;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.NET_MONTHLY_TOTAL;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.OUTGOINGS;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.PARKING;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.PREMISES;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.RENT;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.SIGNAGE;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.START_DATE;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.TENANT;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.TOTAL;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.UNITS;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.UPDATE_DATE;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_RIGHT;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.web.common.ui.grid.EzeeGridModel;
import com.ezee.web.common.ui.utils.EzeeDateComparator;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;

public class EzeeLeaseGridModel extends EzeeGridModel<EzeeLease> {

	private static final double TENANT_COLUMN_WIDTH = 300.;
	private static final double PREMISES_COLUMN_WIDTH = 300.;
	private static final double UNITS_COLUMN_WIDTH = 150;
	private static final double AREA_COLUMN_WIDTH = 100;
	private static final double CATEGORY_COLUMN_WIDTH = 200;

	private static final double LEASE_NUMERIC_FIELD_WIDTH = 140.;

	private final EzeeDateComparator dateComparator = new EzeeDateComparator();

	private boolean summary = true;

	public EzeeLeaseGridModel(final boolean summary) {
		this(null, summary);
	}

	public EzeeLeaseGridModel(final Set<String> hiddenColumns, final boolean summary) {
		super(null, hiddenColumns);
		this.summary = summary;
	}

	@Override
	protected Map<String, Column<EzeeLease, ?>> createColumns(final DataGrid<EzeeLease> grid) {
		Map<String, Column<EzeeLease, ?>> columns = new HashMap<>();
		createTextColumn(columns, grid, TENANT, TENANT_COLUMN_WIDTH, true);
		createTextColumn(columns, grid, PREMISES, PREMISES_COLUMN_WIDTH, true);
		createTextColumn(columns, grid, UNITS, UNITS_COLUMN_WIDTH, false, ALIGN_CENTER);
		createTextColumn(columns, grid, AREA, AREA_COLUMN_WIDTH, false, ALIGN_CENTER);
		createDateColumn(columns, grid, START_DATE, DATE_FIELD_WIDTH, true);
		createDateColumn(columns, grid, END_DATE, DATE_FIELD_WIDTH, true);
		createDateColumn(columns, grid, UPDATE_DATE, DATE_FIELD_WIDTH, true);
		if (summary) {
			createTextColumn(columns, grid, GROSS_ANNUAL_RENT, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, GROSS_ANNUAL_OUTGOINGS, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, GROSS_ANNUAL_PARKING, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, GROSS_ANNUAL_SIGNAGE, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, GROSS_ANNUAL_TOTAL, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, CATEGORY, CATEGORY_COLUMN_WIDTH, true);
		} else {
			createTextColumn(columns, grid, NET_ANNUAL_RENT, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, ANNUAL_RENT_GST, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, GROSS_ANNUAL_RENT, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, NET_MONTHLY_RENT, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, MONTHLY_RENT_GST, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, GROSS_MONTHLY_RENT, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, NET_ANNUAL_OUTGOINGS, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, ANNUAL_OUTGOINGS_GST, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, GROSS_ANNUAL_OUTGOINGS, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, NET_MONTHLY_OUTGOINGS, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, MONTHLY_OUTGOINGS_GST, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, GROSS_MONTHLY_OUTGOINGS, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, NET_ANNUAL_PARKING, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, ANNUAL_PARKING_GST, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, GROSS_ANNUAL_PARKING, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, NET_MONTHLY_PARKING, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, MONTHLY_PARKING_GST, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, GROSS_MONTHLY_PARKING, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, NET_ANNUAL_SIGNAGE, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, ANNUAL_SIGNAGE_GST, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, GROSS_ANNUAL_SIGNAGE, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, NET_MONTHLY_SIGNAGE, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, MONTHLY_SIGNAGE_GST, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, GROSS_MONTHLY_SIGNAGE, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, NET_ANNUAL_TOTAL, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, ANNUAL_TOTAL_GST, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, GROSS_ANNUAL_TOTAL, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, NET_MONTHLY_TOTAL, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, MONTHLY_TOTAL_GST, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, GROSS_MONTHLY_TOTAL, LEASE_NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
			createTextColumn(columns, grid, CATEGORY, CATEGORY_COLUMN_WIDTH, true);
		}
		return columns;
	}

	@Override
	protected String resolveTextFieldValue(final String fieldName, final EzeeLease lease) {
		switch (fieldName) {
		case TENANT:
			return lease.getTenant().getName();
		case PREMISES:
			return lease.getPremises().getName();
		case UNITS:
			return lease.getLeasedUnits();
		case AREA:
			return getAmountFormat().format(lease.getLeasedArea());
		case NET_ANNUAL_RENT:
			return getAmountFormat().format(lease.yearlyAmount(RENT));
		case ANNUAL_RENT_GST:
			return getAmountFormat().format(lease.yearlyGst(RENT));
		case GROSS_ANNUAL_RENT:
			return getAmountFormat().format(lease.yearlyTotal(RENT));
		case NET_MONTHLY_RENT:
			return getAmountFormat().format(lease.monthlyAmount(RENT));
		case MONTHLY_RENT_GST:
			return getAmountFormat().format(lease.monthlyGst(RENT));
		case GROSS_MONTHLY_RENT:
			return getAmountFormat().format(lease.monthlyTotal(RENT));
		case NET_ANNUAL_OUTGOINGS:
			return getAmountFormat().format(lease.yearlyAmount(OUTGOINGS));
		case ANNUAL_OUTGOINGS_GST:
			return getAmountFormat().format(lease.yearlyGst(OUTGOINGS));
		case GROSS_ANNUAL_OUTGOINGS:
			return getAmountFormat().format(lease.yearlyTotal(OUTGOINGS));
		case NET_MONTHLY_OUTGOINGS:
			return getAmountFormat().format(lease.monthlyAmount(OUTGOINGS));
		case MONTHLY_OUTGOINGS_GST:
			return getAmountFormat().format(lease.monthlyGst(OUTGOINGS));
		case GROSS_MONTHLY_OUTGOINGS:
			return getAmountFormat().format(lease.monthlyTotal(OUTGOINGS));
		case NET_ANNUAL_PARKING:
			return getAmountFormat().format(lease.yearlyAmount(PARKING));
		case ANNUAL_PARKING_GST:
			return getAmountFormat().format(lease.yearlyGst(PARKING));
		case GROSS_ANNUAL_PARKING:
			return getAmountFormat().format(lease.yearlyAmount(PARKING));
		case NET_MONTHLY_PARKING:
			return getAmountFormat().format(lease.monthlyAmount(PARKING));
		case MONTHLY_PARKING_GST:
			return getAmountFormat().format(lease.monthlyGst(PARKING));
		case GROSS_MONTHLY_PARKING:
			return getAmountFormat().format(lease.monthlyTotal(PARKING));
		case NET_ANNUAL_SIGNAGE:
			return getAmountFormat().format(lease.yearlyAmount(SIGNAGE));
		case ANNUAL_SIGNAGE_GST:
			return getAmountFormat().format(lease.yearlyGst(SIGNAGE));
		case GROSS_ANNUAL_SIGNAGE:
			return getAmountFormat().format(lease.yearlyAmount(SIGNAGE));
		case NET_MONTHLY_SIGNAGE:
			return getAmountFormat().format(lease.monthlyAmount(SIGNAGE));
		case MONTHLY_SIGNAGE_GST:
			return getAmountFormat().format(lease.monthlyGst(SIGNAGE));
		case GROSS_MONTHLY_SIGNAGE:
			return getAmountFormat().format(lease.monthlyTotal(SIGNAGE));
		case NET_ANNUAL_TOTAL:
			return getAmountFormat().format(lease.yearlyAmount(TOTAL));
		case ANNUAL_TOTAL_GST:
			return getAmountFormat().format(lease.yearlyGst(TOTAL));
		case GROSS_ANNUAL_TOTAL:
			return getAmountFormat().format(lease.yearlyAmount(TOTAL));
		case NET_MONTHLY_TOTAL:
			return getAmountFormat().format(lease.monthlyAmount(TOTAL));
		case MONTHLY_TOTAL_GST:
			return getAmountFormat().format(lease.monthlyGst(TOTAL));
		case GROSS_MONTHLY_TOTAL:
			return getAmountFormat().format(lease.monthlyTotal(TOTAL));
		default:
			return lease.getCategory().getName();
		}
	}

	@Override
	protected void setTextFieldValue(final String fieldName, final String fieldValue, final EzeeLease entity) {
	}

	@Override
	protected Date resolveDateFieldValue(final String fieldName, final EzeeLease lease) {
		switch (fieldName) {
		case START_DATE:
			return DATE_UTILS.fromString(lease.getLeaseStart());
		case END_DATE:
			return DATE_UTILS.fromString(lease.getLeaseEnd());
		default:
			return DATE_UTILS.fromString(lease.getUpdated());
		}
	}

	@Override
	protected void setDateFieldValue(final String fieldName, final Date fieldValue, final EzeeLease entity) {
	}

	@Override
	protected void addComparators(final Map<String, Column<EzeeLease, ?>> columns) {
		handler.setComparator(columns.get(TENANT), new Comparator<EzeeLease>() {
			@Override
			public int compare(final EzeeLease one, final EzeeLease two) {
				return one.getTenant().getName().compareTo(two.getTenant().getName());
			}
		});
		handler.setComparator(columns.get(PREMISES), new Comparator<EzeeLease>() {
			@Override
			public int compare(final EzeeLease one, final EzeeLease two) {
				return one.getPremises().getName().compareTo(two.getPremises().getName());
			}
		});
		handler.setComparator(columns.get(START_DATE), new Comparator<EzeeLease>() {
			@Override
			public int compare(final EzeeLease one, final EzeeLease two) {
				return dateComparator.compare(DATE_UTILS.fromString(one.getLeaseStart()),
						DATE_UTILS.fromString(two.getLeaseStart()));
			}
		});
		handler.setComparator(columns.get(END_DATE), new Comparator<EzeeLease>() {
			@Override
			public int compare(final EzeeLease one, final EzeeLease two) {
				return dateComparator.compare(DATE_UTILS.fromString(one.getLeaseEnd()),
						DATE_UTILS.fromString(two.getLeaseEnd()));
			}
		});
		handler.setComparator(columns.get(UPDATE_DATE), new Comparator<EzeeLease>() {
			@Override
			public int compare(final EzeeLease one, final EzeeLease two) {
				return dateComparator.compare(DATE_UTILS.fromString(one.getUpdated()),
						DATE_UTILS.fromString(two.getUpdated()));
			}
		});
		handler.setComparator(columns.get(CATEGORY), new Comparator<EzeeLease>() {
			@Override
			public int compare(final EzeeLease one, final EzeeLease two) {
				return one.getCategory().getName().compareTo(two.getCategory().getName());
			}
		});
	}

	@Override
	protected void addSortColumns(final DataGrid<EzeeLease> grid, final Map<String, Column<EzeeLease, ?>> columns) {
		grid.getColumnSortList().push(columns.get(TENANT));
		grid.getColumnSortList().push(columns.get(PREMISES));
		grid.getColumnSortList().push(columns.get(START_DATE));
		grid.getColumnSortList().push(columns.get(END_DATE));
		grid.getColumnSortList().push(columns.get(UPDATE_DATE));
		grid.getColumnSortList().push(columns.get(CATEGORY));
	}

	@Override
	protected boolean resolveBooleanFieldValue(final String fieldName, final EzeeLease entity) {
		return false;
	}

	@Override
	protected void setBooleanFieldValue(final String fieldName, final boolean fieldValue, final EzeeLease entity) {
	}

	public final boolean isSummary() {
		return summary;
	}

	public final void setSummary(boolean summary) {
		this.summary = summary;
	}
}