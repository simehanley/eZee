package com.ezee.common.web;

import static com.ezee.common.EzeeCommonConstants.MINUS_ONE;
import static com.ezee.common.EzeeCommonConstants.MONTHS_PER_YEAR;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.common.web.EzeeFormatUtils.getDateFormat;
import static com.google.gwt.user.datepicker.client.CalendarUtil.addDaysToDate;
import static com.google.gwt.user.datepicker.client.CalendarUtil.addMonthsToDate;

import java.util.Date;

import com.ezee.common.EzeeDateUtilities;

public final class EzeeClientDateUtils implements EzeeDateUtilities {

	public static final EzeeClientDateUtils INSTANCE = new EzeeClientDateUtils();

	public final Date fromString(final String date) {
		if (hasLength(date)) {
			return getDateFormat().parse(date);
		}
		return null;
	}

	public final String toString(final Date date) {
		if (date != null) {
			return getDateFormat().format(date);
		}
		return null;
	}

	public Date addDays(final Date date, final int days) {
		if (date != null) {
			Date modified = new Date(date.getTime());
			addDaysToDate(modified, days);
			return modified;
		}
		return null;
	}

	public Date addYears(final Date date, final int years) {
		if (date != null) {
			Date modified = new Date(date.getTime());
			addMonthsToDate(modified, years * MONTHS_PER_YEAR);
			return modified;
		}
		return null;
	}

	@Override
	public Date addYearsAsDays(final Date date, final int years) {
		if (date != null) {
			Date modified = new Date(date.getTime());
			addMonthsToDate(modified, years * MONTHS_PER_YEAR);
			addDaysToDate(modified, MINUS_ONE);
			return modified;
		}
		return null;
	}
}