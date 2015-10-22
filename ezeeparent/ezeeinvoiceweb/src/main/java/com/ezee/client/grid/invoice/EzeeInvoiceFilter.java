package com.ezee.client.grid.invoice;

import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.common.string.EzeeStringUtils.hasLength;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ezee.client.grid.EzeeInvoiceNumberFilter;
import com.ezee.model.entity.EzeeInvoice;
import com.google.gwt.regexp.shared.RegExp;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceFilter extends EzeeInvoiceNumberFilter<EzeeInvoice> {

	private final RegExp supplierRegExp;

	private final RegExp premisesRegExp;

	public EzeeInvoiceFilter(final String invoiceText, final String supplierText, final String premisesText) {
		super(invoiceText);
		supplierRegExp = (hasLength(supplierText)) ? RegExp.compile(supplierText, "i") : null;
		premisesRegExp = (hasLength(premisesText)) ? RegExp.compile(premisesText, "i") : null;
	}

	@Override
	public List<EzeeInvoice> apply(final List<EzeeInvoice> unfiltered) {
		Set<EzeeInvoice> filtered = new HashSet<>();
		for (EzeeInvoice invoice : unfiltered) {
			if (empty()) {
				filtered.add(invoice);
			} else {
				checkInvoice(invoice, filtered);
				checkSupplier(invoice, filtered);
				checkPremises(invoice, filtered);
			}
		}
		return new ArrayList<>(filtered);
	}

	@Override
	public boolean empty() {
		return (invoiceRegExp == null && supplierRegExp == null && premisesRegExp == null);
	}

	private void checkInvoice(final EzeeInvoice invoice, final Set<EzeeInvoice> filtered) {
		if (!isEmpty(invoiceRegExp)) {
			for (RegExp regExp : invoiceRegExp) {
				if (regExp.exec(invoice.getInvoiceId()) != null) {
					filtered.add(invoice);
					return;
				}
			}
		}
	}

	private void checkSupplier(final EzeeInvoice invoice, final Set<EzeeInvoice> filtered) {
		if (supplierRegExp != null) {
			if (supplierRegExp.exec(invoice.getPayee().getName()) != null) {
				filtered.add(invoice);
			}
		}
	}

	private void checkPremises(final EzeeInvoice invoice, final Set<EzeeInvoice> filtered) {
		if (premisesRegExp != null) {
			if (premisesRegExp.exec(invoice.getPayer().getName()) != null) {
				filtered.add(invoice);
			}
		}
	}
}