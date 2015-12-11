package com.ezee.model.entity.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author siborg
 *
 */
public enum EzeeProjectItemType {
	expense, variation;

	public static List<String> types() {
		List<String> types = new ArrayList<>();
		for (EzeeProjectItemType type : values()) {
			types.add(type.name());
		}
		return types;
	}

	public static EzeeProjectItemType get(final String type) {
		for (EzeeProjectItemType value : values()) {
			if (type.equals(value.name())) {
				return value;
			}
		}
		return null;
	}
}
