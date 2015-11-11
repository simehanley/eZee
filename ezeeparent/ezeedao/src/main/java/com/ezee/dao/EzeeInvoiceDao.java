package com.ezee.dao;

import java.util.List;
import java.util.Set;

import com.ezee.model.entity.EzeeInvoice;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeInvoiceDao extends EzeeBaseDao<EzeeInvoice> {
	void save(final Set<EzeeInvoice> invoices);

	List<EzeeInvoice> get(String supplier, String premises, String invoiceIds, String dateFrom, String dateTo,
			boolean includePaid);
}