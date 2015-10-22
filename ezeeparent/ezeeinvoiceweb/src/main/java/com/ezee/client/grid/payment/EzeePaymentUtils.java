package com.ezee.client.grid.payment;

import java.util.HashSet;
import java.util.Set;

import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayment;

/**
 * 
 * @author siborg
 *
 */
public class EzeePaymentUtils {

	private EzeePaymentUtils() {
	}

	public static final String getInvoiceNumbers(final EzeePayment payment) {
		Set<String> invoiceNumbers = new HashSet<>();
		for (EzeeInvoice invoice : payment.getInvoices()) {
			invoiceNumbers.add(invoice.getInvoiceId());
		}
		return invoiceNumbers.toString();
	}
}
