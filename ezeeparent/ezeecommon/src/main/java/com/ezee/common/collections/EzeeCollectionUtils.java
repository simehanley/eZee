package com.ezee.common.collections;

import static com.ezee.common.EzeeCommonConstants.ZERO;

import java.util.Collection;

/**
 * 
 * @author siborg
 *
 */
public final class EzeeCollectionUtils {

	private EzeeCollectionUtils() {
	}

	public static boolean isEmpty(Collection<?> collection) {
		if (collection==null || collection.size()==ZERO) {
			return true;
		}
		return false;
	}
}
