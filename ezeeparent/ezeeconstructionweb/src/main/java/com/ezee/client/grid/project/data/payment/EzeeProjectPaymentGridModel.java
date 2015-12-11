package com.ezee.client.grid.project.data.payment;

import static com.ezee.common.web.EzeeFromatUtils.getAmountFormat;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_RIGHT;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ezee.model.entity.enums.EzeePaymentType;
import com.ezee.model.entity.project.EzeeProjectPayment;
import com.ezee.web.common.EzeeWebCommonConstants;
import com.ezee.web.common.ui.grid.EzeeGridModel;
import com.ezee.web.common.ui.grid.EzeeGridModelListener;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;

public class EzeeProjectPaymentGridModel extends EzeeGridModel<EzeeProjectPayment> {

	private static final String PAYMENT_DATE = "Date";
	private static final String PAYMENT_TYPE = "Type";
	private static final String DESCRIPTION = "Description";
	private static final String AMOUNT = "Amount";
	private static final String TAX = "Tax";
	private static final String TOTAL = "Total";

	private static final double DESCRIPTION_WIDTH = 400.;
	private static final double TYPE_WIDTH = 100.;

	public EzeeProjectPaymentGridModel(final EzeeGridModelListener<EzeeProjectPayment> listener) {
		super(listener);
	}

	@Override
	protected Map<String, Column<EzeeProjectPayment, ?>> createColumns(DataGrid<EzeeProjectPayment> grid) {
		Map<String, Column<EzeeProjectPayment, ?>> columns = new HashMap<>();
		createDateColumn(columns, grid, PAYMENT_DATE, DATE_FIELD_WIDTH, true);
		createProjectPaymentTypeListBoxColumn(columns, grid, PAYMENT_TYPE, TYPE_WIDTH);
		createEditableTextColumn(columns, grid, DESCRIPTION, DESCRIPTION_WIDTH, true);
		createTextColumn(columns, grid, AMOUNT, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, TAX, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		createTextColumn(columns, grid, TOTAL, NUMERIC_FIELD_WIDTH, false, ALIGN_RIGHT);
		return columns;
	}

	@Override
	protected String resolveTextFieldValue(final String fieldName, final EzeeProjectPayment payment) {
		switch (fieldName) {
		case DESCRIPTION:
			return payment.getDescription();
		case PAYMENT_TYPE:
			return payment.getType().name();
		case AMOUNT:
			return getAmountFormat().format(payment.getAmount());
		case TAX:
			return getAmountFormat().format(payment.getTax());
		case TOTAL:
			return getAmountFormat().format(payment.getTotal());
		}
		return null;
	}

	@Override
	protected void setTextFieldValue(final String fieldName, final String fieldValue,
			final EzeeProjectPayment payment) {
		switch (fieldName) {
		case DESCRIPTION:
			payment.setDescription(fieldValue);
			break;
		case PAYMENT_TYPE:
			payment.setType(EzeePaymentType.get(fieldValue));
			break;
		case AMOUNT:
			payment.setAmount(getAmountFormat().parse(fieldValue));
			break;
		case TAX:
			payment.setTax(getAmountFormat().parse(fieldValue));
			break;
		}
	}

	@Override
	protected Date resolveDateFieldValue(final String fieldName, final EzeeProjectPayment payment) {
		switch (fieldName) {
		case PAYMENT_DATE:
			return EzeeWebCommonConstants.DATE_UTILS.fromString(payment.getPaymentDate());
		}
		return null;
	}

	@Override
	protected void addComparators(Map<String, Column<EzeeProjectPayment, ?>> columns) {
		handler.setComparator(columns.get(DESCRIPTION), new Comparator<EzeeProjectPayment>() {
			@Override
			public int compare(final EzeeProjectPayment one, final EzeeProjectPayment two) {
				return one.getDescription().compareTo(two.getDescription());
			}
		});
	}

	@Override
	protected void addSortColumns(DataGrid<EzeeProjectPayment> grid,
			Map<String, Column<EzeeProjectPayment, ?>> columns) {
		grid.getColumnSortList().push(columns.get(DESCRIPTION));
	}

	@Override
	protected boolean resolveBooleanFieldValue(String fieldName, EzeeProjectPayment entity) {
		return false;
	}

	@Override
	protected void setBooleanFieldValue(String fieldName, boolean fieldValue, EzeeProjectPayment entity) {
	}

	private void createProjectPaymentTypeListBoxColumn(final Map<String, Column<EzeeProjectPayment, ?>> columns,
			final DataGrid<EzeeProjectPayment> grid, final String fieldName, final double width) {

		SelectionCell cell = new SelectionCell(EzeePaymentType.types());
		Column<EzeeProjectPayment, String> typeColumn = new Column<EzeeProjectPayment, String>(cell) {
			@Override
			public String getValue(final EzeeProjectPayment item) {
				return item.getType().name();
			}

			@Override
			public String getCellStyleNames(final Context context, final EzeeProjectPayment item) {
				return resolveCellStyleNames(item);
			}
		};

		typeColumn.setFieldUpdater(new FieldUpdater<EzeeProjectPayment, String>() {
			@Override
			public void update(final int index, final EzeeProjectPayment payment, final String value) {
				payment.setType(EzeePaymentType.get(value));
				if (listener != null) {
					listener.modelUpdated(payment);
				}
			}
		});
		typeColumn.setHorizontalAlignment(ALIGN_CENTER);
		createColumn(columns, grid, typeColumn, fieldName, width, false);
	}
}