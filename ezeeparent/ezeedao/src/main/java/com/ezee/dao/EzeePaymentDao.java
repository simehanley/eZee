package com.ezee.dao;

import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import org.springframework.transaction.annotation.Transactional;

import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayment;

/**
 * 
 * @author siborg
 *
 */
public class EzeePaymentDao extends EzeeBaseDao<EzeePayment> {

	@Override
	@Transactional(propagation = REQUIRED, readOnly = false)
	public void save(final EzeePayment payment) {
		if (!isEmpty(payment.getInvoices())) {
			for (EzeeInvoice invoice : payment.getInvoices()) {
				invoice.setDatePaid(payment.getPaymentDate());
				invoice.setUpdated(payment.getPaymentDate());
				invoice.setPaid(true);
			}
		}
		super.save(payment);
	}
}