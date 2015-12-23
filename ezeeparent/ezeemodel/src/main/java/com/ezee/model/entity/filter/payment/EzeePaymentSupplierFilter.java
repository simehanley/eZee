package com.ezee.model.entity.filter.payment;

import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.model.entity.EzeePaymentUtils.getInvoiceSuppliers;

import com.ezee.model.entity.EzeePayment;
import com.ezee.model.entity.filter.EzeeStringFilter;

public class EzeePaymentSupplierFilter extends EzeeStringFilter<EzeePayment> {

	public EzeePaymentSupplierFilter(final String compareString) {
		this(compareString, false);
	}

	public EzeePaymentSupplierFilter(final String compareString, final boolean caseSensitive) {
		super(compareString, caseSensitive);
	}

	@Override
	public boolean include(final EzeePayment payment) {
		String filterString = getInvoiceSuppliers(payment);
		if (!hasLength(compareString)) {
			return true;
		} else {
			String name = (caseSensitive) ? filterString : filterString.toUpperCase();
			return name.contains(compareString);
		}
	}
}