package com.ezee.server;

import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.common.string.EzeeStringUtils.hasLength;

import java.util.Date;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ezee.common.EzeeDateUtilities;

public final class EzeeServerDateUtils implements EzeeDateUtilities {

	public static final EzeeServerDateUtils SERVER_DATE_UTILS = new EzeeServerDateUtils();

	private static final DateTimeFormatter DEFAULT = DateTimeFormat.forPattern("dd/MM/yyyy");

	@Override
	public final Date fromString(final String date) {
		if (hasLength(date)) {
			return DEFAULT.parseLocalDateTime(date).toDate();
		}
		return null;
	}

	@Override
	public final String toString(final Date date) {
		if (date != null) {
			return DEFAULT.print(new LocalDate(date));
		}
		return null;
	}

	public Date addDays(final Date date, final int days) {
		if (date != null) {
			LocalDate modified = new LocalDate(date);
			return modified.plusDays(days).toDate();
		}
		return null;
	}

	public Date addYears(final Date date, final int years) {
		if (date != null) {
			LocalDate modified = new LocalDate(date);
			return modified.plusYears(years).toDate();
		}
		return null;
	}

	@Override
	public Date addYearsAsDays(final Date date, final int years) {
		if (date != null) {
			LocalDate modified = new LocalDate(date);
			return modified.plusYears(years).minusDays(ONE).toDate();
		}
		return null;
	}
}