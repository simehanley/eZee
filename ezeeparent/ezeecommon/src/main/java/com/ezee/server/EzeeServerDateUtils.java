package com.ezee.server;

import static com.ezee.common.string.EzeeStringUtils.hasLength;

import java.util.Date;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 
 * @author siborg
 *
 */
public final class EzeeServerDateUtils {

	private static final DateTimeFormatter DEFAULT = DateTimeFormat.forPattern("dd/MM/yyyy");

	private EzeeServerDateUtils() {
	}

	public static final Date fromString(final String date) {
		if (hasLength(date)) {
			return DEFAULT.parseLocalDateTime(date).toDate();
		}
		return null;
	}

	public static final Date fromString(final String date, final DateTimeFormatter format) {
		if (hasLength(date) && format != null) {
			return format.parseLocalDateTime(date).toDate();
		}
		return fromString(date);
	}

	public static final String toString(final Date date) {
		if (date != null) {
			return DEFAULT.print(new LocalDate(date));
		}
		return null;

	}

	public static final String toString(final Date date, final DateTimeFormatter format) {
		if (date != null && format != null) {
			return format.print(new LocalDate(date));
		}
		return toString(date);
	}
}