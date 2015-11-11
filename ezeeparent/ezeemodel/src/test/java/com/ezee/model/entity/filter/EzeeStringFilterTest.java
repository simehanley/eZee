package com.ezee.model.entity.filter;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;

import org.junit.Test;

import com.ezee.model.entity.EzeePayee;

import junit.framework.TestCase;

/**
 * 
 * @author siborg
 *
 */
public class EzeeStringFilterTest {

	@Test
	public void nullCompareStringPassesFilter() {
		EzeeStringFilter<EzeePayee> filter = new EzeeStringFilter<>(null);
		EzeePayee payee = new EzeePayee("TEST", EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
				EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
		TestCase.assertTrue(filter.include(payee));
	}

	@Test
	public void nonNullCompareStringAndNullNameFailsFilter() {
		EzeeStringFilter<EzeePayee> filter = new EzeeStringFilter<>("TEST");
		EzeePayee payee = new EzeePayee(null, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
				EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
		TestCase.assertFalse(filter.include(payee));
	}

	@Test
	public void caseInSensitiveFilteringWorksAsExpected() {
		EzeeStringFilter<EzeePayee> filter = new EzeeStringFilter<>("es");
		EzeePayee payee = new EzeePayee("TEST", EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
				EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
		TestCase.assertTrue(filter.include(payee));
	}

	@Test
	public void caseSensitiveFilteringWorksAsExpected() {
		EzeeStringFilter<EzeePayee> filter = new EzeeStringFilter<>("es", true);
		EzeePayee payee = new EzeePayee("TEST", EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
				EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
		TestCase.assertFalse(filter.include(payee));
	}
}
