package com.ezee.server.util.lease;

import static com.ezee.server.EzeeServerDateUtils.SERVER_DATE_UTILS;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public final class EzeeLeaseDateUtils {

	private static final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormat.forPattern("MMM-yy");

	private static final DateTimeFormatter FULL_DATE_FORMATTER = DateTimeFormat.forPattern("dd MMMM yyyy");

	private static final DateTimeFormatter INVOICE_DATE_FORMATTER = DateTimeFormat.forPattern("MMyy");

	private EzeeLeaseDateUtils() {
	}

	public static LocalDate toDate(final String leaseDate) {
		return new LocalDate(SERVER_DATE_UTILS.fromString(leaseDate));
	}

	public static String format(final LocalDate leaseDate) {
		return SERVER_DATE_UTILS.toString(leaseDate.toDate());
	}

	public static String formatShortDate(final String leaseDate) {
		return formatShortDate(new LocalDate(SERVER_DATE_UTILS.fromString(leaseDate)));
	}

	public static String formatShortDate(final LocalDate leaseDate) {
		return SHORT_DATE_FORMATTER.print(leaseDate);
	}

	public static String formatFullDate(final String leaseDate) {
		return formatFullDate(new LocalDate(SERVER_DATE_UTILS.fromString(leaseDate)));
	}

	public static String formatFullDate(final LocalDate leaseDate) {
		return FULL_DATE_FORMATTER.print(leaseDate);
	}

	public static String formatInvoiceDate(final String leaseDate) {
		return formatInvoiceDate(new LocalDate(SERVER_DATE_UTILS.fromString(leaseDate)));
	}

	public static String formatInvoiceDate(final LocalDate leaseDate) {
		return INVOICE_DATE_FORMATTER.print(leaseDate);
	}

	public static boolean isBeforeOrEqual(final String firstLeaseDate, final String secondLeaseDate) {
		LocalDate firstDate = new LocalDate(SERVER_DATE_UTILS.fromString(firstLeaseDate));
		LocalDate secondDate = new LocalDate(SERVER_DATE_UTILS.fromString(secondLeaseDate));
		return firstDate.isBefore(secondDate) || firstDate.isEqual(secondDate);
	}

	public static boolean isBeforeOrEqual(final LocalDate first, final LocalDate second) {
		return first.isBefore(second) || first.isEqual(second);
	}

	public static boolean isGreaterThan(final String firstLeaseDate, final String secondLeaseDate) {
		return !isBeforeOrEqual(firstLeaseDate, secondLeaseDate);
	}

	public static boolean isGreaterThan(final LocalDate first, final LocalDate second) {
		return !isBeforeOrEqual(first, second);
	}

	public static boolean isGreaterThanOrEqualTo(final String firstLeaseDate, final String secondLeaseDate) {
		LocalDate firstDate = new LocalDate(SERVER_DATE_UTILS.fromString(firstLeaseDate));
		LocalDate secondDate = new LocalDate(SERVER_DATE_UTILS.fromString(secondLeaseDate));
		return isGreaterThanOrEqualTo(firstDate, secondDate);
	}

	public static boolean isGreaterThanOrEqualTo(final LocalDate first, final LocalDate second) {
		return first.isAfter(second) || first.isEqual(second);
	}
}