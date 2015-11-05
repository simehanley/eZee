package com.ezee.common.web;

import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.common.web.EzeeFromatUtils.getDateFormat;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public final class EzeeClientDateUtils {

	private EzeeClientDateUtils() {
	}

	public static final Date fromString(final String date) {
		if (hasLength(date)) {
			return getDateFormat().parse(date);
		}
		return null;
	}

	public static final Date fromString(final String date, final DateTimeFormat format) {
		if (hasLength(date) && format != null) {
			return format.parse(date);
		}
		return fromString(date);
	}

	public static final String toString(final Date date) {
		if (date != null) {
			return getDateFormat().format(date);
		}
		return null;

	}

	public static final String toString(final Date date, final DateTimeFormat format) {
		if (date != null && format != null) {
			return format.format(date);
		}
		return toString(date);
	}
}