package com.ezee.common.numeric;

import static com.ezee.common.EzeeCommonConstants.ONE_DBL;
import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static java.math.RoundingMode.HALF_EVEN;

import java.math.BigDecimal;

/**
 * 
 * @author siborg
 *
 */
public class EzeeNumericUtils {

	private static final double ZERO_TOLERANCE = 1e-12;

	public static double round(final double value) {
		return round(value, 2);
	}

	public static double round(final double value, final int places) {
		return new BigDecimal(value).setScale(places, HALF_EVEN).doubleValue();
	}

	public static boolean isCloseToZero(final double value) {
		return Math.abs(value) < ZERO_TOLERANCE;
	}

	public static double percentComplete(double starting, double balance) {
		double percentComplete = ZERO_DBL;
		if (isCloseToZero(balance)) {
			percentComplete = ONE_DBL;
		} else {
			percentComplete = ((starting - balance) / starting);
		}
		return percentComplete;
	}
}