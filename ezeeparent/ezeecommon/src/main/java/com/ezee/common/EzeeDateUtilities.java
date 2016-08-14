package com.ezee.common;

import java.util.Date;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeDateUtilities {

	Date fromString(final String date);

	String toString(final Date date);

	Date addDays(Date date, int days);

	Date addYears(Date date, int years);
	
	Date addYearsAsDays(Date date, int years);
}