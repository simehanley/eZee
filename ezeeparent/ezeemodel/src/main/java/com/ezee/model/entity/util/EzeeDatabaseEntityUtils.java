package com.ezee.model.entity.util;

import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.ezee.model.entity.EzeeDatabaseEntity;

public final class EzeeDatabaseEntityUtils {

	private EzeeDatabaseEntityUtils() {
	}

	public static <T extends EzeeDatabaseEntity> Set<T> sorted(Set<T> unsorted) {
		if (!isEmpty(unsorted)) {
			List<T> sorted = new ArrayList<>(unsorted);
			Collections.sort(sorted);
			return new LinkedHashSet<>(sorted);
		}
		return null;
	}
}
