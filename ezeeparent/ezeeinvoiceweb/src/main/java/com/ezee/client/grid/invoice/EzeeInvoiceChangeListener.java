package com.ezee.client.grid.invoice;

import java.util.Set;

import com.ezee.model.entity.EzeeInvoice;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeInvoiceChangeListener {

	void onInvoicesChanged(Set<EzeeInvoice> invoices);
}
