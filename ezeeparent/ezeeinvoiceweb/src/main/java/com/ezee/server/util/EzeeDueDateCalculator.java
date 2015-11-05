package com.ezee.server.util;

import static com.ezee.server.EzeeServerDateUtils.fromString;

import org.joda.time.LocalDate;

import com.ezee.model.entity.EzeeDebtAgeRule;
import com.ezee.server.EzeeServerDateUtils;

public class EzeeDueDateCalculator {

	public String calculate(final EzeeDebtAgeRule rule, final String today) {
		if (rule == null) {
			return null;
		}
		LocalDate date = new LocalDate(fromString(today));
		if (rule.isEom()) {
			date = date.dayOfMonth().withMaximumValue();
		}
		switch (rule.getType()) {
		case days:
			date = date.plusDays(rule.getInterval());
			break;
		case weeks:
			date = date.plusWeeks(rule.getInterval());
			break;
		case months:
			date = date.plusMonths(rule.getInterval());
			break;
		}
		if (rule.isEom()) {
			date = date.dayOfMonth().withMaximumValue();
		}
		return EzeeServerDateUtils.toString(date.toDate());
	}
}