package com.ezee.dao;

import java.util.List;
import java.util.Set;

import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.filter.invoice.EzeeInvoiceFilter;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeInvoiceDao extends EzeeBaseDao<EzeeInvoice> {
	void save(final Set<EzeeInvoice> invoices);
	List<EzeeInvoice> get(EzeeInvoiceFilter filter);
}