package com.ezee.server;

import static com.ezee.common.string.EzeeStringUtils.hasLength;

import java.util.Date;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ezee.common.EzeeDateUtilities;

/**
 * 
 * @author siborg
 *
 */
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
}