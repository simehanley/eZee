package com.ezee.model.entity.filter.payment;

import java.util.Date;

import com.ezee.common.EzeeDateUtilities;
import com.ezee.model.entity.EzeePayment;
import com.ezee.model.entity.filter.EzeeDateRangeFilter;
import com.ezee.model.entity.filter.EzeeDelimitedStringFilter;
import com.ezee.model.entity.filter.EzeeEntityFilter;

/**
 * 
 * @author siborg
 *
 */
public class EzeePaymentFilter implements EzeeEntityFilter<EzeePayment> {

	private final EzeeEntityFilter<EzeePayment> invoiceIdFilter;

	private final EzeeEntityFilter<EzeePayment> dateRangeFilter;

	public EzeePaymentFilter(final String invoiceIds, final Date from, final Date to,
			final EzeeDateUtilities dateUtilities) {
		invoiceIdFilter = new EzeeDelimitedStringFilter<>(invoiceIds);
		dateRangeFilter = new EzeeDateRangeFilter<>(dateUtilities, from, to);
	}

	@Override
	public boolean include(final EzeePayment payment) {
		if (!invoiceIdFilter.include(payment)) {
			return false;
		} else if (!dateRangeFilter.include(payment)) {
			return false;
		}
		return true;
	}
}