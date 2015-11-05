package com.ezee.dao;

import static com.ezee.model.entity.enums.EzeeInvoiceClassification.expense;
import static com.ezee.model.entity.enums.EzeePaymentType.cheque;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayee;
import com.ezee.model.entity.EzeePayer;
import com.ezee.model.entity.EzeePayment;

import junit.framework.TestCase;

public class EzeePaymentDaoTest extends AbstractEzeeDaoTest<EzeePayment> {

	@Autowired
	private EzeePayeeDao payeeDao;

	@Autowired
	private EzeePayerDao payerDao;

	@Autowired
	private EzeeInvoiceDao invoiceDao;

	@Autowired
	private EzeePaymentDao paymentDao;

	private final EzeePayer payer = new EzeePayer();
	private final EzeePayee payee = new EzeePayee();

	private final EzeeInvoice one = new EzeeInvoice("1", payee, payer, 100., 10., "TEST 1", true, "5/11/2015",
			"30/11/2015", null, "5/11/2015", null, expense);
	private final EzeeInvoice two = new EzeeInvoice("2", payee, payer, 200., 20., "TEST 2", true, "5/11/2015",
			"30/11/2015", null, "5/11/2015", null, expense);

	@Override
	@Test
	public void canPersist() {
		init();
		EzeePayment payment = new EzeePayment("5/11/2015", null);
		payment.addInvoice(one);
		payment.addInvoice(two);
		payment.setPaymentDate("30/11/2015");
		TestCase.assertNull(payment.getId());
		paymentDao.save(payment);
		TestCase.assertNotNull(payment.getId());
	}

	@Override
	@Test
	public void canEdit() {
		init();
		EzeePayment payment = new EzeePayment("5/11/2015", null);
		payment.addInvoice(one);
		payment.addInvoice(two);
		payment.setPaymentDate("30/11/2015");
		paymentDao.save(payment);
		TestCase.assertNull(payment.getType());
		payment.setType(cheque);
		paymentDao.save(payment);
		EzeePayment persisted = paymentDao.get(payment.getId(), EzeePayment.class);
		TestCase.assertNotNull(persisted.getType());
	}

	@Override
	@Test
	public void canDelete() {
		init();
		EzeePayment payment = new EzeePayment("5/11/2015", null);
		payment.addInvoice(one);
		payment.addInvoice(two);
		payment.setPaymentDate("30/11/2015");
		paymentDao.save(payment);
		EzeePayment persisted = paymentDao.get(payment.getId(), EzeePayment.class);
		TestCase.assertNotNull(persisted);
		paymentDao.delete(persisted);
		persisted = paymentDao.get(payment.getId(), EzeePayment.class);
		TestCase.assertNull(persisted);
	}

	private void init() {
		payerDao.save(payer);
		payeeDao.save(payee);
		invoiceDao.save(one);
		invoiceDao.save(two);
	}
}