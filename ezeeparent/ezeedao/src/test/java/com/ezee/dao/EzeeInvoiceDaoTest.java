package com.ezee.dao;

import java.util.Date;

import org.junit.Test;

import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayee;
import com.ezee.model.entity.EzeePayer;

import junit.framework.TestCase;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceDaoTest extends AbstractEzeeDaoTest {

	@Test
	public void canPersistAnEzeeInvoice() {
		EzeeInvoice invoice = new EzeeInvoice("123456", new EzeePayee(), new EzeePayer(), 100., 10.,
				"First invoice test.", true, false, null, null, new Date(), null);
		TestCase.assertNull(invoice.getId());
		getCtx().getBean(EzeeInvoiceDao.class).save(invoice);
		TestCase.assertNotNull(invoice.getId());
		TestCase.assertNotNull(invoice.getPayee().getId());
		TestCase.assertNotNull(invoice.getPayer().getId());
	}
}
