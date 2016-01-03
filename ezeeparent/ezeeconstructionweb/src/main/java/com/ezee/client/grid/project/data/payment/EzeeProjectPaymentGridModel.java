package com.ezee.client.grid.project.data.payment;

import static com.ezee.common.web.EzeeFormatUtils.getAmountFormat;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_LEFT;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_RIGHT;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ezee.model.entity.enums.EzeePaymentType;
import com.ezee.model.entity.project.EzeeProjectPayment;
import com.ezee.web.common.ui.grid.EzeeGridModel;
import com.ezee.web.common.ui.utils.EzeeDateComparator;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;

public class EzeeProjectPaymentGridModel extends EzeeGridModel<EzeeProjectPayment> {

	private static final String PAYMENT_DATE = "Date";
	private static final String PAYMENT_TYPE = "Type";
	private static final String INVOICE_REF = "Invoice Ref.";
	private static final String DESCRIPTION = "Description";
	private static final String AMOUNT = "Amount";
	private static final String TAX = "Tax";
	private static final String TOTAL = "Total";

	private static final double INVOICE_REF_WIDTH = 150.;
	private static final double DESCRIPTION_WIDTH = 400.;
	private static final double TYPE_WIDTH = 100.;

	private final EzeeDateComparator dateComparator = new EzeeDateComparator();

	public EzeeProjectPaymentGridModel() {
		super();
	}

	@Override
	protected Map<String, Column<EzeeProjectPayment, ?>> createColumns(DataGrid<EzeeProjectPayment> grid) {
		Map<String, Column<EzeeProjectPayment, ?>> columns = new HashMap<>();
		createDateColumn(columns, grid, PAYMENT_DATE, DATE_FIELD_WIDTH, true);
		createTextColumn(columns, grid, PAYMENT_TYPE, TYPE_WIDTH, true, ALIGN_CENTER);
		createTextColumn(columns, grid, INVOICE_REF, INVOICE_REF_WIDTH, true, ALIGN_LEFT);
		createTextColumn(columns, grid, DESCRIPTION, DESCRIPTION_WIDTH, true, ALIGN_LEFT);
		createTextColumn(columns, grid, AMOUNT, NUMERIC_FIELD_WIDTH, true, ALIGN_RIGHT);
		createTextColumn(columns, grid, TAX, NUMERIC_FIELD_WIDTH, true, ALIGN_RIGHT);
		createTextColumn(columns, grid, TOTAL, NUMERIC_FIELD_WIDTH, true, ALIGN_RIGHT);
		return columns;
	}

	@Override
	protected String resolveTextFieldValue(final String fieldName, final EzeeProjectPayment payment) {
		switch (fieldName) {
		case INVOICE_REF:
			return payment.getInvoiceRef();
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
			return DATE_UTILS.fromString(payment.getPaymentDate());
		}
		return null;
	}

	@Override
	protected void setDateFieldValue(final String fieldName, final Date fieldValue, final EzeeProjectPayment payment) {
		switch (fieldName) {
		case PAYMENT_DATE:
			payment.setPaymentDate(DATE_UTILS.toString(fieldValue));
		}
	}

	@Override
	protected void addComparators(Map<String, Column<EzeeProjectPayment, ?>> columns) {
		handler.setComparator(columns.get(PAYMENT_DATE), new Comparator<EzeeProjectPayment>() {
			@Override
			public int compare(final EzeeProjectPayment one, final EzeeProjectPayment two) {
				return dateComparator.compare(DATE_UTILS.fromString(one.getPaymentDate()),
						DATE_UTILS.fromString(two.getPaymentDate()));
			}
		});
		handler.setComparator(columns.get(PAYMENT_TYPE), new Comparator<EzeeProjectPayment>() {

			@Override
			public int compare(final EzeeProjectPayment one, final EzeeProjectPayment two) {
				return one.getType().compareTo(two.getType());
			}
		});
		handler.setComparator(columns.get(INVOICE_REF), new Comparator<EzeeProjectPayment>() {

			@Override
			public int compare(final EzeeProjectPayment one, final EzeeProjectPayment two) {
				return one.getInvoiceRef().compareTo(two.getInvoiceRef());
			}
		});
		handler.setComparator(columns.get(DESCRIPTION), new Comparator<EzeeProjectPayment>() {
			@Override
			public int compare(final EzeeProjectPayment one, final EzeeProjectPayment two) {
				return one.getDescription().compareTo(two.getDescription());
			}
		});
		handler.setComparator(columns.get(AMOUNT), new Comparator<EzeeProjectPayment>() {
			@Override
			public int compare(final EzeeProjectPayment one, final EzeeProjectPayment two) {
				return new BigDecimal(one.getAmount()).compareTo(new BigDecimal(two.getAmount()));
			}
		});
		handler.setComparator(columns.get(TAX), new Comparator<EzeeProjectPayment>() {
			@Override
			public int compare(final EzeeProjectPayment one, final EzeeProjectPayment two) {
				return new BigDecimal(one.getTax()).compareTo(new BigDecimal(two.getTax()));
			}
		});
		handler.setComparator(columns.get(TOTAL), new Comparator<EzeeProjectPayment>() {
			@Override
			public int compare(final EzeeProjectPayment one, final EzeeProjectPayment two) {
				return new BigDecimal(one.getTotal()).compareTo(new BigDecimal(two.getTotal()));
			}
		});
	}

	@Override
	protected void addSortColumns(DataGrid<EzeeProjectPayment> grid,
			Map<String, Column<EzeeProjectPayment, ?>> columns) {
		grid.getColumnSortList().push(columns.get(PAYMENT_DATE));
		grid.getColumnSortList().push(columns.get(PAYMENT_TYPE));
		grid.getColumnSortList().push(columns.get(DESCRIPTION));
		grid.getColumnSortList().push(columns.get(AMOUNT));
		grid.getColumnSortList().push(columns.get(TAX));
		grid.getColumnSortList().push(columns.get(TOTAL));
		grid.getColumnSortList().push(columns.get(INVOICE_REF));
	}

	@Override
	protected boolean resolveBooleanFieldValue(String fieldName, EzeeProjectPayment entity) {
		return false;
	}

	@Override
	protected void setBooleanFieldValue(String fieldName, boolean fieldValue, EzeeProjectPayment entity) {
	}
}