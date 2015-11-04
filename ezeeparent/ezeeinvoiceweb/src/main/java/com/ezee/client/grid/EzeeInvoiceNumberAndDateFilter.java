package com.ezee.client.grid;

import static com.ezee.common.EzeeCommonConstants.MINUS_ONE;
import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.google.gwt.user.datepicker.client.CalendarUtil.addDaysToDate;

import java.util.Date;
import java.util.Set;

import com.ezee.model.entity.EzeeDatabaseEntity;
import com.google.gwt.user.datepicker.client.CalendarUtil;

/**
 * 
 * @author siborg
 *
 */
public abstract class EzeeInvoiceNumberAndDateFilter<T extends EzeeDatabaseEntity> extends EzeeInvoiceNumberFilter<T> {

	protected final Date from;

	protected final Date to;

	public EzeeInvoiceNumberAndDateFilter(final Date from, final Date to, final String invoiceText) {
		super(invoiceText);
		/* this is a hack - want to remove GMT offset */
		this.from = resolveFilterDate(from, MINUS_ONE);
		this.to = resolveFilterDate(to, ONE);
		/* this is a hack - want to remove GMT offset */
	}

	protected void checkDates(final T entity, final Set<T> filtered) {
		if (from != null || to != null) {
			boolean fromCheck = (from == null) ? true
					: entity.filterDate().after(from) || entity.filterDate().equals(from);
			boolean toCheck = (to == null) ? true : entity.filterDate().before(to) || entity.filterDate().equals(to);
			if (from != null && to == null) {
				if (fromCheck) {
					filtered.add(entity);
				}
			} else if (from == null && to != null) {
				if (toCheck) {
					filtered.add(entity);
				}
			} else {
				if (fromCheck && toCheck) {
					filtered.add(entity);
				}
			}
		}
	}

	private Date resolveFilterDate(final Date date, final int offset) {
		if (date == null) {
			return date;
		}
		Date candidate = CalendarUtil.copyDate(date);
		addDaysToDate(candidate, offset);
		return candidate;
	}
}