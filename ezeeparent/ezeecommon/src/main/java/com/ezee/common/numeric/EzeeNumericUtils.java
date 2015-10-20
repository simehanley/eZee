package com.ezee.common.numeric;

import static java.math.RoundingMode.HALF_EVEN;

import java.math.BigDecimal;

/**
 * 
 * @author siborg
 *
 */
public class EzeeNumericUtils {

	public static double round(final double value) {
		return round(value, 2);
	}

	public static double round(final double value, final int places) {
		return new BigDecimal(value).setScale(places, HALF_EVEN).doubleValue();
	}
}