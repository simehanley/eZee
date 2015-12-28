package com.ezee.model.entity;

import java.util.HashSet;
import java.util.Set;

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

	public static final String getInvoiceSuppliers(final EzeePayment payment) {
		Set<String> suppliers = new HashSet<>();
		for (EzeeInvoice invoice : payment.getInvoices()) {
			suppliers.add(invoice.getSupplier().getName());
		}
		return suppliers.toString();
	}
}
