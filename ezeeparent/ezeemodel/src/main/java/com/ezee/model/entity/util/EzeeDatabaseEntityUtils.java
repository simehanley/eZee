package com.ezee.model.entity.util;

import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.ezee.model.entity.EzeeAuditableDatabaseEntity;

public final class EzeeDatabaseEntityUtils {

	private EzeeDatabaseEntityUtils() {
	}

	public static <T extends EzeeAuditableDatabaseEntity> Set<T> sorted(Set<T> unsorted) {
		if (!isEmpty(unsorted)) {
			List<T> sorted = new ArrayList<>(unsorted);
			Collections.sort(sorted);
			return new LinkedHashSet<>(sorted);
		}
		return null;
	}
}
