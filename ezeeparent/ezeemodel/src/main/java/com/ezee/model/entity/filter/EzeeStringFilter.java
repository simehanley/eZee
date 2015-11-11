package com.ezee.model.entity.filter;

import static com.ezee.common.string.EzeeStringUtils.hasLength;

/**
 * 
 * @author siborg
 *
 */
public class EzeeStringFilter<T extends EzeeStringFilterable> implements EzeeEntityFilter<T> {

	/** string to compare against **/
	protected final String compareString;

	/** whether comparison is case sensitive or not **/
	protected final boolean caseSensitive;

	public EzeeStringFilter(final String compareString) {
		this(compareString, false);
	}

	public EzeeStringFilter(final String compareString, final boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
		if (hasLength(compareString)) {
			this.compareString = (this.caseSensitive) ? compareString : compareString.toUpperCase();
		} else {
			this.compareString = null;
		}
	}

	public String getCompareString() {
		return compareString;
	}

	public final boolean isCaseSensitive() {
		return caseSensitive;
	}

	@Override
	public boolean include(final T entity) {
		if (!hasLength(compareString)) {
			return true;
		} else if (hasLength(compareString) && !hasLength(entity.filterString())) {
			return false;
		} else {
			String name = (caseSensitive) ? entity.filterString() : entity.filterString().toUpperCase();
			return name.contains(compareString);
		}
	}
}