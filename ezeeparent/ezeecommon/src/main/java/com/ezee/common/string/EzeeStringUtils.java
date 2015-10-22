package com.ezee.common.string;

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
}