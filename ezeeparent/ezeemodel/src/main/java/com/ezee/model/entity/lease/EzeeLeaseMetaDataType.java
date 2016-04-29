package com.ezee.model.entity.lease;

import com.ezee.model.entity.enums.EzeeKeyedEnum;

public enum EzeeLeaseMetaDataType implements EzeeKeyedEnum {

	historic_rents("Historical Rent"), notices("Notice"), special_conditions("Special Condition"), options("Option");

	private final String key;

	private EzeeLeaseMetaDataType(final String key) {
		this.key = key;
	}

	@Override
	public final String key() {
		return key;
	}
}