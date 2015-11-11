package com.ezee.model.entity.filter;

import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.string.EzeeStringUtils.hasLength;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author siborg
 *
 */
public class EzeeDelimitedStringFilter<T extends EzeeStringFilterable> implements EzeeEntityFilter<T> {

	public static final String DEFAULT_DELIMITER = ",";

	/** comma delimited string of invoice (partial or full) ids **/
	protected final String delimitedString;

	/** presumed delimiter **/
	protected final String delimiter;

	/** whether comparison is case sensitive or not **/
	protected final boolean caseSensitive;

	protected final List<EzeeStringFilter<T>> filters = new ArrayList<>();

	public EzeeDelimitedStringFilter(final String delimitedString) {
		this(delimitedString, DEFAULT_DELIMITER, false);
	}

	public EzeeDelimitedStringFilter(final String delimitedString, final String delimiter) {
		this(delimitedString, delimiter, false);
	}

	public EzeeDelimitedStringFilter(final String delimitedString, final String delimiter,
			final boolean caseSensitive) {
		this.delimitedString = delimitedString;
		this.delimiter = delimiter;
		this.caseSensitive = caseSensitive;
		init();
	}

	@Override
	public boolean include(final T entity) {
		if (!hasLength(delimitedString)) {
			return true;
		} else if (hasLength(delimitedString) && !hasLength(entity.filterString())) {
			return false;
		} else {
			for (EzeeStringFilter<T> filter : filters) {
				if (filter.include(entity)) {
					return true;
				}
			}
		}
		return false;
	}

	private void init() {
		if (hasLength(delimitedString)) {
			String[] strs = delimitedString.split(delimiter);
			if (strs != null && strs.length > ZERO) {
				for (String str : strs) {
					filters.add(new EzeeStringFilter<T>(str, caseSensitive));
				}
			}
		}
	}
}