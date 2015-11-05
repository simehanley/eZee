package com.ezee.client.util;

import static com.ezee.common.EzeeCommonConstants.MINUS_ONE;
import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.common.EzeeCommonConstants.ZERO;

import java.util.Comparator;
import java.util.Date;

public class EzeeDateComparator implements Comparator<Date> {

	@Override
	public int compare(final Date one, final Date two) {
		if (one != null && two != null) {
			return one.compareTo(two);
		} else if (one == null && two != null) {
			return MINUS_ONE;
		} else if (one != null && two == null) {
			return ONE;
		} else {
			return ZERO;
		}
	}
}