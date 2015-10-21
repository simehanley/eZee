package com.ezee.client.grid.payment;

import java.util.Set;

import com.ezee.model.entity.EzeeInvoice;

/**
 * 
 * @author siborg
 *
 */
public interface EzeePaymentCreationListener {

	void onCreatePayment(Set<EzeeInvoice> invoices);
}
