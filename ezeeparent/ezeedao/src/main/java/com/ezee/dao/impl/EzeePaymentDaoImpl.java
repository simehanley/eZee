package com.ezee.dao.impl;

import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ezee.dao.EzeeInvoiceDao;
import com.ezee.dao.EzeePaymentDao;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayment;

/**
 * 
 * @author siborg
 *
 */
public class EzeePaymentDaoImpl extends EzeeBaseDaoImpl<EzeePayment> implements EzeePaymentDao {

	@Autowired
	private EzeeInvoiceDao invoiceDao;

	@Override
	@Transactional(propagation = REQUIRED, readOnly = false)
	public void save(final EzeePayment payment) {
		updateInvoices(payment.getInvoices(), payment.getPaymentDate(), true);
		super.save(payment);
	}

	@Override
	@Transactional(propagation = REQUIRED, readOnly = false)
	public void delete(final EzeePayment payment) {
		deleteMappings(payment, "deleteInvoiceMappingsSql");
		updateInvoices(payment.getInvoices(), null, false);
		super.delete(payment);
	}

	private void updateInvoices(final Set<EzeeInvoice> invoices, final Date paymentDate, final boolean paid) {
		if (!isEmpty(invoices)) {
			for (EzeeInvoice invoice : invoices) {
				invoice.setDatePaid(paymentDate);
				invoice.setPaid(paid);
				invoice.setUpdated(new Date());
			}
			invoiceDao.save(invoices);
		}
	}
}