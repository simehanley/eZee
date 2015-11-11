package com.ezee.server.util;

import org.joda.time.LocalDate;

import com.ezee.common.EzeeDateUtilities;
import com.ezee.model.entity.EzeeDebtAgeRule;
import com.ezee.server.EzeeServerDateUtils;

public class EzeeDueDateCalculator {

	private final EzeeDateUtilities dateUtilities = new EzeeServerDateUtils();

	public String calculate(final EzeeDebtAgeRule rule, final String today) {
		if (rule == null) {
			return null;
		}
		LocalDate date = new LocalDate(dateUtilities.fromString(today));
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
		return dateUtilities.toString(date.toDate());
	}
}