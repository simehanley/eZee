package com.ezee.client.grid.payment;

import static com.ezee.client.grid.payment.EzeePaymentUtils.getInvoiceNumbers;
import static com.ezee.common.web.EzeeFromatUtils.getAmountFormat;
import static com.ezee.model.entity.enums.EzeePaymentType.cheque;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.ui.css.EzeeDefaultResources.INSTANCE;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ezee.model.entity.EzeePayment;
import com.ezee.web.common.ui.grid.EzeeGridModel;
import com.ezee.web.common.ui.utils.EzeeDateComparator;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;

public class EzeePaymentGridModel extends EzeeGridModel<EzeePayment> {

	public static final String PAYMENT_DATE = "Paid";
	public static final String PAYMENT_TYPE = "Type";
	public static final String PAYMENT_AMOUNT = "Amount";
	public static final String INVOICES = "Invoices";

	private final EzeeDateComparator dateComparator = new EzeeDateComparator();

	public EzeePaymentGridModel() {
		super();
	}

	public EzeePaymentGridModel(Set<String> hiddenColumns) {
		super(hiddenColumns);
	}

	@Override
	protected Map<String, Column<EzeePayment, ?>> createColumns(final DataGrid<EzeePayment> grid) {
		Map<String, Column<EzeePayment, ?>> columns = new HashMap<>();
		createDateColumn(columns, grid, PAYMENT_DATE, true);
		createTextColumn(columns, grid, PAYMENT_TYPE, true);
		createNumericColumn(columns, grid, PAYMENT_AMOUNT);
		createTextColumn(columns, grid, INVOICES, false);
		return columns;
	}

	@Override
	protected String resolveTextFieldValue(final String fieldName, final EzeePayment payment) {
		switch (fieldName) {
		case PAYMENT_TYPE:
			return payment.getType().toString();
		case PAYMENT_AMOUNT:
			return getAmountFormat().format(payment.getPaymentAmount());
		case INVOICES:
			return getInvoiceNumbers(payment);
		}
		return null;
	}

	@Override
	protected Date resolveDateFieldValue(final String fieldName, final EzeePayment payment) {
		switch (fieldName) {
		case PAYMENT_DATE:
			return DATE_UTILS.fromString(payment.getPaymentDate());
		}
		return null;
	}

	@Override
	protected void addComparators(final Map<String, Column<EzeePayment, ?>> columns) {
		handler.setComparator(columns.get(PAYMENT_DATE), new Comparator<EzeePayment>() {

			@Override
			public int compare(final EzeePayment one, final EzeePayment two) {
				return dateComparator.compare(DATE_UTILS.fromString(one.getPaymentDate()),
						DATE_UTILS.fromString(two.getPaymentDate()));
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

	@Override
	protected String resolveCellStyleNames(final EzeePayment payment) {
		if (payment.getType() == cheque && !payment.isChequePresented()) {
			return INSTANCE.css().lightorangeforeground();
		}
		return INSTANCE.css().greenforeground();
	}
}