package com.ezee.client.grid.invoice;

import static com.ezee.common.web.EzeeFromatUtils.getAmountFormat;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ezee.client.grid.EzeeGridModel;
import com.ezee.model.entity.EzeeInvoice;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceGridModel extends EzeeGridModel<EzeeInvoice> {

	private static final String INVOICE_NUM = "Invoice Num";
	private static final String SUPPLIER = "Supplier";
	private static final String PREMISES = "Premises";
	private static final String AMOUNT = "Amount";
	private static final String TAX = "Tax";
	private static final String TOTAL = "Total";
	private static final String DESCRIPTION = "Description";
	private static final String PAID = "Paid";
	private static final String DUE_DATE = "Due Date";
	private static final String PAID_PATE = "Pay Date";

	private static final String INVOICE_NUM_WIDTH = "200px";
	private static final String SUPPLIER_WIDTH = "300px";
	private static final String PREMISES_WIDTH = "300px";
	private static final String DESCRIPTION_WIDTH = "400px";

	@Override
	protected Map<String, Column<EzeeInvoice, ?>> createColumns(final DataGrid<EzeeInvoice> grid) {
		Map<String, Column<EzeeInvoice, ?>> columns = new HashMap<>();
		createTextColumn(columns, grid, INVOICE_NUM, INVOICE_NUM_WIDTH, true);
		createTextColumn(columns, grid, SUPPLIER, SUPPLIER_WIDTH, true);
		createTextColumn(columns, grid, PREMISES, PREMISES_WIDTH, true);
		createNumericColumn(columns, grid, AMOUNT, NUMERIC_FIELD_WIDTH);
		createNumericColumn(columns, grid, TAX, NUMERIC_FIELD_WIDTH);
		createNumericColumn(columns, grid, TOTAL, NUMERIC_FIELD_WIDTH);
		createTextColumn(columns, grid, DESCRIPTION, DESCRIPTION_WIDTH, false);
		createCheckBoxColumn(columns, grid, PAID, BOOLEAN_FIELD_WIDTH);
		createDateColumn(columns, grid, DUE_DATE, DATE_FIELD_WIDTH, true);
		createDateColumn(columns, grid, PAID_PATE, DATE_FIELD_WIDTH, true);
		return columns;
	}

	@Override
	protected String resolveTextFieldValue(final String fieldName, final EzeeInvoice invoice) {
		switch (fieldName) {
		case INVOICE_NUM:
			return invoice.getInvoiceId();
		case SUPPLIER:
			return invoice.getPayee().getName();
		case PREMISES:
			return invoice.getPayer().getName();
		case DESCRIPTION:
			return invoice.getDescription();
		case AMOUNT:
			return getAmountFormat().format(invoice.getAmount());
		case TAX:
			return getAmountFormat().format(invoice.getTax());
		case TOTAL:
			return getAmountFormat().format(invoice.getInvoiceAmount());
		}
		return null;
	}

	@Override
	protected Date resolveDateFieldValue(final String fieldName, final EzeeInvoice invoice) {
		switch (fieldName) {
		case DUE_DATE:
			return invoice.getDateDue();
		case PAID_PATE:
			return invoice.getDatePaid();
		}
		return null;
	}

	@Override
	protected boolean resolveBooleanFieldValue(final String fieldName, final EzeeInvoice invoice) {
		return invoice.isPaid();
	}

	@Override
	protected void addComparators(final Map<String, Column<EzeeInvoice, ?>> columns) {

		handler.setComparator(columns.get(INVOICE_NUM), new Comparator<EzeeInvoice>() {

			@Override
			public int compare(final EzeeInvoice one, final EzeeInvoice two) {
				return one.getInvoiceId().compareTo(two.getInvoiceId());
			}
		});

		handler.setComparator(columns.get(SUPPLIER), new Comparator<EzeeInvoice>() {

			@Override
			public int compare(final EzeeInvoice one, final EzeeInvoice two) {
				return one.getPayee().getName().compareTo(two.getPayee().getName());
			}
		});

		handler.setComparator(columns.get(PREMISES), new Comparator<EzeeInvoice>() {

			@Override
			public int compare(final EzeeInvoice one, final EzeeInvoice two) {
				return one.getPayer().getName().compareTo(two.getPayer().getName());
			}
		});

		handler.setComparator(columns.get(PAID), new Comparator<EzeeInvoice>() {

			@Override
			public int compare(final EzeeInvoice one, final EzeeInvoice two) {
				return new Boolean(one.isPaid()).compareTo(new Boolean(two.isPaid()));
			}
		});

		handler.setComparator(columns.get(DUE_DATE), new Comparator<EzeeInvoice>() {

			@Override
			public int compare(final EzeeInvoice one, final EzeeInvoice two) {
				return one.getDateDue().compareTo(two.getDateDue());
			}
		});

		handler.setComparator(columns.get(PAID_PATE), new Comparator<EzeeInvoice>() {

			@Override
			public int compare(final EzeeInvoice one, final EzeeInvoice two) {
				return one.getDatePaid().compareTo(two.getDatePaid());
			}
		});
	}

	@Override
	protected void addSortColumns(final DataGrid<EzeeInvoice> grid, final Map<String, Column<EzeeInvoice, ?>> columns) {
		grid.getColumnSortList().push(columns.get(INVOICE_NUM));
		grid.getColumnSortList().push(columns.get(SUPPLIER));
		grid.getColumnSortList().push(columns.get(PREMISES));
		grid.getColumnSortList().push(columns.get(PAID));
		grid.getColumnSortList().push(columns.get(DUE_DATE));
		grid.getColumnSortList().push(columns.get(PAID_PATE));
	}
}