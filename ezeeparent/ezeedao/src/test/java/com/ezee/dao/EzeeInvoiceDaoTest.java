package com.ezee.dao;

import static com.ezee.model.entity.enums.EzeeInvoiceClassification.expense;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayer;
import com.ezee.model.entity.EzeeSupplier;

import junit.framework.TestCase;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceDaoTest extends AbstractEzeeDaoTest<EzeeInvoice> {

	@Autowired
	private EzeeSupplierDao supplierDao;

	@Autowired
	private EzeePayerDao payerDao;

	@Autowired
	private EzeeInvoiceDao invoiceDao;

	private final EzeePayer payer = new EzeePayer();
	private final EzeeSupplier supplier = new EzeeSupplier();

	@Override
	@Test
	public void canPersist() {
		init();
		EzeeInvoice invoice = new EzeeInvoice("1", supplier, payer, 100., 10., "TEST", true, "5/11/2015", "31/12/2015",
				null, "5/11/2015", null, expense);
		TestCase.assertNull(invoice.getId());
		invoiceDao.save(invoice);
		TestCase.assertNotNull(invoice.getId());
	}

	@Override
	@Test
	public void canEdit() {
		init();
		EzeeInvoice invoice = new EzeeInvoice("1", supplier, payer, 100., 10., "TEST", true, "5/11/2015", "31/12/2015",
				null, "5/11/2015", null, expense);
		invoiceDao.save(invoice);
		TestCase.assertNull(invoice.getDatePaid());
		invoice.setDatePaid("30/11/2015");
		invoiceDao.save(invoice);
		EzeeInvoice persisted = invoiceDao.get(invoice.getId(), EzeeInvoice.class);
		TestCase.assertNotNull(persisted.getDatePaid());
		TestCase.assertEquals("30/11/2015", persisted.getDatePaid());
	}

	@Override
	@Test
	public void canDelete() {
		init();
		EzeeInvoice invoice = new EzeeInvoice("1", supplier, payer, 100., 10., "TEST", true, "5/11/2015", "31/12/2015",
				null, "5/11/2015", null, expense);
		invoiceDao.save(invoice);
		EzeeInvoice persisted = invoiceDao.get(invoice.getId(), EzeeInvoice.class);
		TestCase.assertNotNull(persisted);
		invoiceDao.delete(persisted);
		persisted = invoiceDao.get(invoice.getId(), EzeeInvoice.class);
		TestCase.assertNull(persisted);
	}

	private void init() {
		payerDao.save(payer);
		supplierDao.save(supplier);
	}
}