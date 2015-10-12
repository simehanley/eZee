package com.ezee.dao;

import org.junit.Test;

import com.ezee.model.entity.EzeePayer;

import junit.framework.TestCase;

/**
 * 
 * @author siborg
 *
 */
public class EzeePayerDaoTest extends AbstractEzeeDaoTest {

	@Test
	public void canPersistAnEzeePayer() {
		EzeePayer payer = new EzeePayer();
		TestCase.assertNull(payer.getId());
		getCtx().getBean(EzeePayerDao.class).save(payer);
		TestCase.assertNotNull(payer.getId());
	}
}
