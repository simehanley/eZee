package com.ezee.model.entity.filter;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;

import org.junit.Test;

import com.ezee.model.entity.EzeePayee;
import com.ezee.model.entity.EzeeSupplier;

import junit.framework.TestCase;

/**
 * 
 * @author siborg
 *
 */
public class EzeeDelimitedStringFilterTest {

	@Test
	public void nullCompareStringPassesFilter() {
		EzeeDelimitedStringFilter<EzeePayee> filter = new EzeeDelimitedStringFilter<>(null);
		EzeePayee payee = new EzeeSupplier("TEST", EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
				EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
		TestCase.assertTrue(filter.include(payee));
	}

	@Test
	public void nonNullCompareStringAndNullNameFailsFilter() {
		EzeeDelimitedStringFilter<EzeePayee> filter = new EzeeDelimitedStringFilter<>("TEST,BLAH");
		EzeePayee payee = new EzeeSupplier(null, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
				EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
		TestCase.assertFalse(filter.include(payee));
	}

	@Test
	public void caseInSensitiveFilteringWorksAsExpected() {
		EzeeDelimitedStringFilter<EzeePayee> filter = new EzeeDelimitedStringFilter<>("es,blah");
		EzeeSupplier payee = new EzeeSupplier("BLAH", EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
				EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
				EMPTY_STRING);
		TestCase.assertTrue(filter.include(payee));
	}

	@Test
	public void caseSensitiveFilteringWorksAsExpected() {
		EzeeDelimitedStringFilter<EzeePayee> filter = new EzeeDelimitedStringFilter<>("es,blah",
				EzeeDelimitedStringFilter.DEFAULT_DELIMITER, true);
		EzeePayee payee = new EzeeSupplier("BLAH", EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
				EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
		TestCase.assertFalse(filter.include(payee));
	}
}
