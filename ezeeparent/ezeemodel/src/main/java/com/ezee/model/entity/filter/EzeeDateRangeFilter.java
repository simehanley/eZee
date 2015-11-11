package com.ezee.model.entity.filter;

import static com.ezee.common.string.EzeeStringUtils.hasLength;

import java.util.Date;

import com.ezee.common.EzeeDateUtilities;

public class EzeeDateRangeFilter<T extends EzeeDateFilterable> implements EzeeEntityFilter<T> {

	private final EzeeDateUtilities dateUtilities;

	private final Date fromDate;

	private final Date toDate;

	public EzeeDateRangeFilter(final EzeeDateUtilities dateUtilities, final Date from, final Date to) {
		this.dateUtilities = dateUtilities;
		this.fromDate = from;
		this.toDate = to;
	}

	@Override
	public boolean include(final T entity) {
		if (fromDate == null && toDate == null) {
			return true;
		} else if (!hasLength(entity.filterDate()) && (fromDate != null || toDate != null)) {
			return false;
		} else {
			return checkDates(entity);
		}
	}

	private boolean checkDates(final T entity) {
		Date filterDate = dateUtilities.fromString(entity.filterDate());
		boolean fromCheck = (fromDate == null) ? true : (filterDate.after(fromDate) || filterDate.equals(fromDate));
		boolean toCheck = (toDate == null) ? true : (filterDate.before(toDate) || filterDate.equals(toDate));
		if (fromDate != null && toDate == null) {
			if (fromCheck) {
				return true;
			}
		} else if (fromDate == null && toDate != null) {
			if (toCheck) {
				return true;
			}
		} else {
			if (fromCheck && toCheck) {
				return true;
			}
		}
		return false;
	}
}