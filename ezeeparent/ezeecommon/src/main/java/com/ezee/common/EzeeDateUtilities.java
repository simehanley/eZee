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
}