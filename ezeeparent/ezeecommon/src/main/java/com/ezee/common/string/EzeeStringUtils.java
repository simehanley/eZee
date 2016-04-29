package com.ezee.common.string;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;

/**
 * 
 * @author siborg
 *
 */
public class EzeeStringUtils {

	private EzeeStringUtils() {
	}

	public static boolean hasLength(final String string) {
		return (string != null && !string.trim().isEmpty());
	}

	public static String getStringValue(final String value) {
		if (hasLength(value)) {
			return value;
		}
		return EMPTY_STRING;
	}
}