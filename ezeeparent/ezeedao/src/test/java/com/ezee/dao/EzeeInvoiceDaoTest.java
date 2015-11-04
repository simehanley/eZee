package com.ezee.dao;

import static com.ezee.model.entity.enums.EzeeInvoiceClassification.expense;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

	@Autowired
	private EzeePayeeDao payeeDao;

	@Autowired
	private EzeePayerDao payerDao;

	@Test
	public void canPersistAnEzeeInvoice() {
		EzeePayee payee = new EzeePayee();
		EzeePayer payer = new EzeePayer();
		payeeDao.save(payee);
		payerDao.save(payer);
		Date date = new Date();
		EzeeInvoice invoice = new EzeeInvoice("123456", payee, payer, 100., 10., "First invoice test.", true, false,
				date, date, date, date, null, expense);
		TestCase.assertNull(invoice.getId());
		getCtx().getBean(EzeeInvoiceDao.class).save(invoice);
		TestCase.assertNotNull(invoice.getId());
		TestCase.assertNotNull(invoice.getPayee().getId());
		TestCase.assertNotNull(invoice.getPayer().getId());
	}
}
