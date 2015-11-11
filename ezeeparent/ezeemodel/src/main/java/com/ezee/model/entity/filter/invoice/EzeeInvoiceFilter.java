package com.ezee.model.entity.filter.invoice;

import java.util.Date;

import com.ezee.common.EzeeDateUtilities;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayee;
import com.ezee.model.entity.EzeePayer;
import com.ezee.model.entity.filter.EzeeDateRangeFilter;
import com.ezee.model.entity.filter.EzeeDelimitedStringFilter;
import com.ezee.model.entity.filter.EzeeEntityFilter;
import com.ezee.model.entity.filter.EzeeStringFilter;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceFilter implements EzeeEntityFilter<EzeeInvoice> {

	private final EzeeEntityFilter<EzeePayee> supplierFilter;

	private final EzeeEntityFilter<EzeePayer> premisesFilter;

	private final EzeeEntityFilter<EzeeInvoice> invoiceIdFilter;

	private final EzeeEntityFilter<EzeeInvoice> dateRangeFilter;

	private final boolean includePaid;

	public EzeeInvoiceFilter(final String supplier, final String premises, final String invoiceIds, final Date from,
			final Date to, final EzeeDateUtilities dateUtilities, final boolean includePaid) {
		supplierFilter = new EzeeStringFilter<>(supplier);
		premisesFilter = new EzeeStringFilter<>(premises);
		invoiceIdFilter = new EzeeDelimitedStringFilter<>(invoiceIds);
		dateRangeFilter = new EzeeDateRangeFilter<>(dateUtilities, from, to);
		this.includePaid = includePaid;
	}

	@Override
	public boolean include(final EzeeInvoice invoice) {
		if (!supplierFilter.include(invoice.getPayee())) {
			return false;
		} else if (!premisesFilter.include(invoice.getPayer())) {
			return false;
		} else if (!invoiceIdFilter.include(invoice)) {
			return false;
		} else if (!dateRangeFilter.include(invoice)) {
			return false;
		} else if (!includePaid && invoice.getDatePaid() != null) {
			return false;
		}
		return true;
	}
}