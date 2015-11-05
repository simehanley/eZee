package com.ezee.client.grid;

import static com.ezee.common.web.EzeeClientDateUtils.fromString;

import java.util.Date;
import java.util.Set;

import com.ezee.common.web.EzeeClientDateUtils;
import com.ezee.model.entity.EzeeDatabaseEntity;

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
		this.from = fromString(EzeeClientDateUtils.toString(from));
		this.to = fromString(EzeeClientDateUtils.toString(to));
	}

	protected void checkDates(final T entity, final Set<T> filtered) {
		Date filterDate = fromString(entity.filterDate());
		if (filterDate != null) {
			if (from != null || to != null) {
				boolean fromCheck = (from == null) ? true : (filterDate.after(from) || filterDate.equals(from));
				boolean toCheck = (to == null) ? true : (filterDate.before(to) || filterDate.equals(to));

				if (from != null && to == null) {
					if (fromCheck && showPaid(entity)) {
						filtered.add(entity);
					}
				} else if (from == null && to != null) {
					if (toCheck && showPaid(entity)) {
						filtered.add(entity);
					}
				} else {
					if (fromCheck && toCheck && showPaid(entity)) {
						filtered.add(entity);
					}
				}
			}
		}
	}

	protected abstract boolean showPaid(T entity);
}