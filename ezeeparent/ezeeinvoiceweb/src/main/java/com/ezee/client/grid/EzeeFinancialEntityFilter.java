package com.ezee.client.grid;

import static com.ezee.common.string.EzeeStringUtils.hasLength;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ezee.model.entity.EzeeFinancialEntity;
import com.ezee.model.entity.filter.EzeeEntityFilter;
import com.google.gwt.regexp.shared.RegExp;

public class EzeeFinancialEntityFilter<T extends EzeeFinancialEntity> implements EzeeEntityFilter<T> {

	private final RegExp nameRegExp;

	public EzeeFinancialEntityFilter(final String nameText) {
		nameRegExp = (hasLength(nameText)) ? RegExp.compile(nameText, "i") : null;
	}

	@Override
	public List<T> apply(List<T> unfiltered) {
		Set<T> filtered = new HashSet<>();
		for (T entity : unfiltered) {
			if (empty()) {
				filtered.add(entity);
			} else {
				checkName(entity, filtered);
			}
		}
		return new ArrayList<>(filtered);
	}

	@Override
	public boolean empty() {
		return (nameRegExp == null);
	}

	private void checkName(T entity, Set<T> filtered) {
		if (nameRegExp != null) {
			if (nameRegExp.exec(entity.getName()) != null) {
				filtered.add(entity);
			}
		}
	}
}