package com.ezee.client.util;

import java.util.Date;

import com.ezee.model.entity.EzeeDebtAgeRule;
import com.google.gwt.user.datepicker.client.CalendarUtil;

/**
 * 
 * @author siborg
 *
 */
public class EzeeDueDateCalculator {

	private EzeeDueDateCalculator() {
	}

	public static Date calculate(final EzeeDebtAgeRule rule) {
		Date date = new Date();
		if (rule == null) {
			return date;
		}
		if (rule.isEom()) {
			CalendarUtil.addMonthsToDate(date, 1);
			CalendarUtil.setToFirstDayOfMonth(date);
			CalendarUtil.addDaysToDate(date, -1);
		}
		switch (rule.getType()) {
		case days:
			CalendarUtil.addDaysToDate(date, rule.getInterval());
			break;
		case weeks:
			CalendarUtil.addDaysToDate(date, rule.getInterval() * 7);
			break;
		case months:
			CalendarUtil.addMonthsToDate(date, rule.getInterval());
			break;
		}
		return date;
	}
}