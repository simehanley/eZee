package com.ezee.model.entity.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author siborg
 *
 */
public enum EzeePaymentType {
	bpay, bank_transfer, cheque, credit_card, cash;

	public static List<String> types() {
		List<String> types = new ArrayList<>();
		for (EzeePaymentType type : values()) {
			types.add(type.name());
		}
		return types;
	}

	public static EzeePaymentType get(final String type) {
		for (EzeePaymentType value : values()) {
			if (type.equals(value.name())) {
				return value;
			}
		}
		return null;
	}
}