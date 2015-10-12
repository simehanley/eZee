package com.ezee.dao;

import java.util.Date;

import org.junit.Test;

import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayee;
import com.ezee.model.entity.EzeePayer;
import com.ezee.model.entity.EzeePayment;

import junit.framework.TestCase;

public class EzeePaymentDaoTest extends AbstractEzeeDaoTest {

	@SuppressWarnings("deprecation")
	@Test
	public void canPersistAnEzeePayment() {
		EzeePayer payer = new EzeePayer("TEST-PAYER", null, null, null, null, null, null, null, new Date(), new Date());
		EzeePayee payee = new EzeePayee("TEST-PAYEE", null, null, null, null, null, null, null, new Date(), new Date());
		EzeeInvoice one = new EzeeInvoice("1234567", payee, payer, 200., 20., "Invoice to payee.", true, false,
				new Date(2015, 6, 25), null, new Date(), new Date());
		EzeeInvoice two = new EzeeInvoice("1234567", payee, payer, 200., 20., "Invoice to payee.", true, false,
				new Date(2015, 6, 25), null, new Date(), new Date());
		getCtx().getBean(EzeePayerDao.class).save(payer);
		getCtx().getBean(EzeePayeeDao.class).save(payee);
		getCtx().getBean(EzeeInvoiceDao.class).save(one);
		getCtx().getBean(EzeeInvoiceDao.class).save(two);
		EzeePayment payment = new EzeePayment(new Date(), null);
		payment.addInvoice(one);
		payment.addInvoice(two);
		TestCase.assertNull(payment.getId());
		getCtx().getBean(EzeePaymentDao.class).save(payment);
		TestCase.assertNotNull(payment.getId());
	}
}
