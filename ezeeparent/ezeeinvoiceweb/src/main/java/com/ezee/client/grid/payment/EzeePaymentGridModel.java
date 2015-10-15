package com.ezee.client.grid.payment;

import static com.ezee.common.web.EzeeFromatUtils.getAmountFormat;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ezee.client.grid.EzeeGridModel;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayment;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;

public class EzeePaymentGridModel extends EzeeGridModel<EzeePayment> {

	protected static final String PAYMENT_DATE = "Pay Date";
	protected static final String PAYMENT_TYPE = "Pay Type";
	protected static final String PAYMENT_AMOUNT = "Pay Amount";
	protected static final String DESCRIPTION = "Description";
	protected static final String INVOICES = "Invoices";

	protected static final String PAYMENT_TYPE_WIDTH = "150px";
	protected static final String DESCRIPTION_WIDTH = "400px";
	protected static final String INVOICES_WIDTH = "400px";

	@Override
	protected Map<String, Column<EzeePayment, ?>> createColumns(final DataGrid<EzeePayment> grid) {
		Map<String, Column<EzeePayment, ?>> columns = new HashMap<>();
		createDateColumn(columns, grid, PAYMENT_DATE, DATE_FIELD_WIDTH, true);
		createTextColumn(columns, grid, PAYMENT_TYPE, PAYMENT_TYPE_WIDTH, true);
		createNumericColumn(columns, grid, PAYMENT_AMOUNT, NUMERIC_FIELD_WIDTH);
		createTextColumn(columns, grid, DESCRIPTION, DESCRIPTION_WIDTH, false);
		createTextColumn(columns, grid, INVOICES, INVOICES_WIDTH, false);
		return columns;
	}

	@Override
	protected String resolveTextFieldValue(final String fieldName, final EzeePayment payment) {
		switch (fieldName) {
		case PAYMENT_TYPE:
			return payment.getType().toString();
		case DESCRIPTION:
			return payment.getPaymentDescription();
		case PAYMENT_AMOUNT:
			return getAmountFormat().format(payment.getPaymentAmount());
		case INVOICES:
			return resolveInvoiceNumbers(payment).toString();
		}
		return null;
	}

	@Override
	protected Date resolveDateFieldValue(final String fieldName, final EzeePayment payment) {
		return payment.getPaymentDate();
	}

	@Override
	protected boolean resolveBooleanFieldValue(final String fieldName, final EzeePayment entity) {
		/* do nothing */
		return false;
	}

	@Override
	protected void addComparators(final Map<String, Column<EzeePayment, ?>> columns) {
		handler.setComparator(columns.get(PAYMENT_DATE), new Comparator<EzeePayment>() {

			@Override
			public int compare(final EzeePayment one, final EzeePayment two) {
				return one.getPaymentDate().compareTo(two.getPaymentDate());
			}
		});

		handler.setComparator(columns.get(PAYMENT_TYPE), new Comparator<EzeePayment>() {

			@Override
			public int compare(final EzeePayment one, final EzeePayment two) {
				return one.getType().compareTo(two.getType());
			}
		});
	}

	@Override
	protected void addSortColumns(final DataGrid<EzeePayment> grid, final Map<String, Column<EzeePayment, ?>> columns) {
		grid.getColumnSortList().push(columns.get(PAYMENT_DATE));
		grid.getColumnSortList().push(columns.get(PAYMENT_TYPE));
	}

	private Set<String> resolveInvoiceNumbers(final EzeePayment payment) {
		Set<String> invoiceNumbers = new HashSet<>();
		for (EzeeInvoice invoice : payment.getInvoices()) {
			invoiceNumbers.add(invoice.getInvoiceId());
		}
		return invoiceNumbers;
	}
}