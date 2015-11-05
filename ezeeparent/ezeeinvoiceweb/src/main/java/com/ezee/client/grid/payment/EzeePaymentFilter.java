package com.ezee.client.grid.payment;

import static com.ezee.client.grid.payment.EzeePaymentUtils.getInvoiceNumbers;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ezee.client.grid.EzeeInvoiceNumberAndDateFilter;
import com.ezee.model.entity.EzeePayment;
import com.google.gwt.regexp.shared.RegExp;

/**
 * 
 * @author siborg
 *
 */
public class EzeePaymentFilter extends EzeeInvoiceNumberAndDateFilter<EzeePayment> {

	public EzeePaymentFilter(final String invoiceText, final Date from, final Date to) {
		super(from, to, invoiceText);
	}

	@Override
	public List<EzeePayment> apply(final List<EzeePayment> unfiltered) {
		Set<EzeePayment> filtered = new HashSet<>();
		for (EzeePayment payment : unfiltered) {
			if (empty()) {
				filtered.add(payment);
			} else {
				checkInvoices(payment, filtered);
				checkDates(payment, filtered);
			}
		}
		return new ArrayList<>(filtered);
	}

	@Override
	public boolean empty() {
		return (invoiceRegExp == null && from == null && to == null);
	}

	private void checkInvoices(final EzeePayment payment, final Set<EzeePayment> filtered) {
		if (!isEmpty(invoiceRegExp)) {
			for (RegExp regExp : invoiceRegExp) {
				if (regExp.exec(getInvoiceNumbers(payment)) != null) {
					filtered.add(payment);
					return;
				}
			}
		}
	}

	@Override
	protected boolean showPaid(final EzeePayment entity) {
		return true;
	}
}