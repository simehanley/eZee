package com.ezee.dao;

import org.junit.Test;

import com.ezee.model.entity.EzeePayee;

import junit.framework.TestCase;

/**
 * 
 * @author siborg
 *
 */
public class EzeePayeeDaoTest extends AbstractEzeeDaoTest {

	@Test
	public void canPersistAnEzeePayee() {
		EzeePayee payee = new EzeePayee();
		TestCase.assertNull(payee.getId());
		getCtx().getBean(EzeePayeeDao.class).save(payee);
		TestCase.assertNotNull(payee.getId());
	}
}