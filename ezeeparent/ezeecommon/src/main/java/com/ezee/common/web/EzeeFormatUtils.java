package com.ezee.common.web;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * 
 * @author siborg
 *
 */
public class EzeeFormatUtils {

	private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("dd/MM/yyyy");

	private static final NumberFormat AMOUNT_FORMAT = NumberFormat.getFormat("#,##0.00");
	
	private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getFormat("$#,##0.00");

	private static final NumberFormat PERCENT_FORMAT = NumberFormat.getFormat("##0.00%");

	private static DateBox.Format DATE_BOX_FORMAT = new DateBox.DefaultFormat(DATE_FORMAT);

	private static final DateTimeFormat FULL_DATE_TIME_FORMAT = DateTimeFormat.getFormat(" EEEE, dd MMMM yyyy hh:mm a");

	public static final DateTimeFormat getDateFormat() {
		return DATE_FORMAT;
	}

	public static final DateTimeFormat getFullDateTimeFormat() {
		return FULL_DATE_TIME_FORMAT;
	}

	public static final NumberFormat getAmountFormat() {
		return AMOUNT_FORMAT;
	}
	
	public static final NumberFormat getCurrencyFormat() {
		return CURRENCY_FORMAT;
	}

	public static final NumberFormat getPercentFormat() {
		return PERCENT_FORMAT;
	}

	public static final DateBox.Format getDateBoxFormat() {
		return DATE_BOX_FORMAT;
	}
}