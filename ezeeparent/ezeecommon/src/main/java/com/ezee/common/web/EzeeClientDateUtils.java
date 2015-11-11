package com.ezee.common.web;

import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.common.web.EzeeFromatUtils.getDateFormat;

import java.util.Date;

import com.ezee.common.EzeeDateUtilities;

public final class EzeeClientDateUtils implements EzeeDateUtilities {

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
}