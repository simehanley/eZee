package com.ezee.common.collections;

import static com.ezee.common.EzeeCommonConstants.ZERO;

import java.util.Collection;
import java.util.Map;

/**
 * 
 * @author siborg
 *
 */
public final class EzeeCollectionUtils {

	private EzeeCollectionUtils() {
	}

	public static boolean isEmpty(Collection<?> collection) {
		if (collection == null || collection.size() == ZERO) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Map<?, ?> map) {
		if (map == null || map.size() == ZERO) {
			return true;
		}
		return false;
	}
}
