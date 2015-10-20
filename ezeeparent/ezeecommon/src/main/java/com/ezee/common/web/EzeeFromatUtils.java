package com.ezee.common.web;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * 
 * @author siborg
 *
 */
public class EzeeFromatUtils {

	private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("dd/MM/yyyy");

	private static final NumberFormat AMOUNT_FORMAT = NumberFormat.getFormat("#,##0.00");

	private static DateBox.Format DATE_BOX_FORMAT = new DateBox.DefaultFormat(DATE_FORMAT);

	public static final DateTimeFormat getDateFormat() {
		return DATE_FORMAT;
	}

	public static final NumberFormat getAmountFormat() {
		return AMOUNT_FORMAT;
	}

	public static final DateBox.Format getDateBoxFormat() {
		return DATE_BOX_FORMAT;
	}
}