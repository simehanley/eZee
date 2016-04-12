package com.ezee.client.grid.lease;

import static com.ezee.common.web.EzeeFormatUtils.getAmountFormat;
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

	private static final String START_DATE = "Start";
	private static final String END_DATE = "End";
	private static final String UPDATE_DATE = "Update";
	private static final String TENANT = "Tenant";
	private static final String PREMISES = "Premises";
	private static final String UNITS = "Units";
	private static final String AREA = "Area";
	private static final String CATEGORY = "Category";
	private static final String RENT = "Rent";
	private static final String OUTGOINGS = "Outgoings";
	private static final String SIGNAGE = "Signage";
	private static final String PARKING = "Parking";
	private static final String TOTAL = "Total";

	private static final String NET_ANNUAL_RENT = "Net Rent (A)";
	private static final String ANNUAL_RENT_GST = "Rent Gst (A)";
	private static final String GROSS_ANNUAL_RENT = "Gross Rent (A)";
	private static final String NET_MONTHLY_RENT = "Net Rent (M)";
	private static final String MONTHLY_RENT_GST = "Rent Gst (M)";
	private static final String GROSS_MONTHLY_RENT = "Gross Rent (M)";

	private static final String NET_ANNUAL_OUTGOINGS = "Net Outgoings (A)";
	private static final String ANNUAL_OUTGOINGS_GST = "Outgoings Gst (A)";
	private static final String GROSS_ANNUAL_OUTGOINGS = "Gross Outgoings (A)";
	private static final String NET_MONTHLY_OUTGOINGS = "Net Outgoings (M)";
	private static final String MONTHLY_OUTGOINGS_GST = "Outgoings Gst (M)";
	private static final String GROSS_MONTHLY_OUTGOINGS = "Gross Outgoings (M)";

	private static final String NET_ANNUAL_PARKING = "Net Parking (A)";
	private static final String ANNUAL_PARKING_GST = "Parking Gst (A)";
	private static final String GROSS_ANNUAL_PARKING = "Gross Parking (A)";
	private static final String NET_MONTHLY_PARKING = "Net Parking (M)";
	private static final String MONTHLY_PARKING_GST = "Parking Gst (M)";
	private static final String GROSS_MONTHLY_PARKING = "Gross Parking (M)";

	private static final String NET_ANNUAL_SIGNAGE = "Net Signage (A)";
	private static final String ANNUAL_SIGNAGE_GST = "Signage Gst (A)";
	private static final String GROSS_ANNUAL_SIGNAGE = "Gross Signage (A)";
	private static final String NET_MONTHLY_SIGNAGE = "Net Signage (M)";
	private static final String MONTHLY_SIGNAGE_GST = "Signage Gst (M)";
	private static final String GROSS_MONTHLY_SIGNAGE = "Gross Signage (M)";

	private static final String NET_ANNUAL_TOTAL = "Net Total (A)";
	private static final String ANNUAL_TOTAL_GST = "Total Gst (A)";
	private static final String GROSS_ANNUAL_TOTAL = "Gross Total (A)";
	private static final String NET_MONTHLY_TOTAL = "Net Total (M)";
	private static final String MONTHLY_TOTAL_GST = "Total Gst (M)";
	private static final String GROSS_MONTHLY_TOTAL = "Gross Total (M)";

	private static final double TENANT_COLUMN_WIDTH = 400.;
	private static final double PREMISES_COLUMN_WIDTH = 400.;
	private static final double UNITS_COLUMN_WIDTH = 200;
	private static final double AREA_COLUMN_WIDTH = 100;
	private static final double CATEGORY_COLUMN_WIDTH = 200;

	private final EzeeDateComparator dateComparator = new EzeeDateComparator();

	public EzeeLeaseGridModel() {
		super();
	}

	public EzeeLeaseGridModel(final Set<String> hiddenColumns) {
		super(null, hiddenColumns);
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
		createTextColumn(columns, grid, NET_ANNUAL_RENT, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, ANNUAL_RENT_GST, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, GROSS_ANNUAL_RENT, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, NET_MONTHLY_RENT, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, MONTHLY_RENT_GST, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, GROSS_MONTHLY_RENT, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, NET_ANNUAL_OUTGOINGS, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, ANNUAL_OUTGOINGS_GST, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, GROSS_ANNUAL_OUTGOINGS, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, NET_MONTHLY_OUTGOINGS, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, MONTHLY_OUTGOINGS_GST, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, GROSS_MONTHLY_OUTGOINGS, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, NET_ANNUAL_PARKING, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, ANNUAL_PARKING_GST, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, GROSS_ANNUAL_PARKING, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, NET_MONTHLY_PARKING, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, MONTHLY_PARKING_GST, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, GROSS_MONTHLY_PARKING, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, NET_ANNUAL_SIGNAGE, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, ANNUAL_SIGNAGE_GST, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, GROSS_ANNUAL_SIGNAGE, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, NET_MONTHLY_SIGNAGE, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, MONTHLY_SIGNAGE_GST, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, GROSS_MONTHLY_SIGNAGE, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, NET_ANNUAL_TOTAL, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, ANNUAL_TOTAL_GST, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, GROSS_ANNUAL_TOTAL, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, NET_MONTHLY_TOTAL, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, MONTHLY_TOTAL_GST, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, GROSS_MONTHLY_TOTAL, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, CATEGORY, CATEGORY_COLUMN_WIDTH, true);
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
}