package com.ezee.client.grid.invoice;

import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.common.string.EzeeStringUtils.hasLength;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ezee.client.grid.EzeeInvoiceNumberAndDateFilter;
import com.ezee.model.entity.EzeeInvoice;
import com.google.gwt.regexp.shared.RegExp;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceFilter extends EzeeInvoiceNumberAndDateFilter<EzeeInvoice> {

	private final RegExp supplierRegExp;

	private final RegExp premisesRegExp;

	private final boolean showPaid;

	public EzeeInvoiceFilter(final Date from, final Date to, final String invoiceText, final String supplierText,
			final String premisesText, final boolean showPaid) {
		super(from, to, invoiceText);
		supplierRegExp = (hasLength(supplierText)) ? RegExp.compile(supplierText, "i") : null;
		premisesRegExp = (hasLength(premisesText)) ? RegExp.compile(premisesText, "i") : null;
		this.showPaid = showPaid;
	}

	@Override
	public List<EzeeInvoice> apply(final List<EzeeInvoice> unfiltered) {
		Set<EzeeInvoice> filtered = new HashSet<>();
		for (EzeeInvoice invoice : unfiltered) {
			if (empty()) {
				if (showPaid(invoice)) {
					filtered.add(invoice);
				}
			} else {
				checkDates(invoice, filtered);
				checkInvoice(invoice, filtered);
				checkSupplier(invoice, filtered);
				checkPremises(invoice, filtered);
			}
		}
		return new ArrayList<>(filtered);
	}

	@Override
	public boolean empty() {
		return (from == null && to == null && invoiceRegExp == null && supplierRegExp == null
				&& premisesRegExp == null);
	}

	private void checkInvoice(final EzeeInvoice invoice, final Set<EzeeInvoice> filtered) {
		if (!isEmpty(invoiceRegExp)) {
			for (RegExp regExp : invoiceRegExp) {
				if (regExp.exec(invoice.getInvoiceId()) != null && showPaid(invoice)) {
					filtered.add(invoice);
					return;
				}
			}
		}
	}

	private void checkSupplier(final EzeeInvoice invoice, final Set<EzeeInvoice> filtered) {
		if (supplierRegExp != null) {
			if (supplierRegExp.exec(invoice.getPayee().getName()) != null && showPaid(invoice)) {
				filtered.add(invoice);
			}
		}
	}

	private void checkPremises(final EzeeInvoice invoice, final Set<EzeeInvoice> filtered) {
		if (premisesRegExp != null) {
			if (premisesRegExp.exec(invoice.getPayer().getName()) != null && showPaid(invoice)) {
				filtered.add(invoice);
			}
		}
	}

	@Override
	protected boolean showPaid(final EzeeInvoice invoice) {
		boolean paid = (invoice.getDatePaid() != null);
		if (!paid || (showPaid && paid)) {
			return true;
		}
		return false;
	}
}