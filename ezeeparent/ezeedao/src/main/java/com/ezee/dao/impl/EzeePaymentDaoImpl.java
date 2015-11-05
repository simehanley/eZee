package com.ezee.dao.impl;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ezee.dao.EzeeInvoiceDao;
import com.ezee.dao.EzeePaymentDao;
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
		invoiceDao.save(payment.getInvoices());
		super.save(payment);
	}

	@Override
	@Transactional(propagation = REQUIRED, readOnly = false)
	public void delete(final EzeePayment payment) {
		deleteMappings(payment, "deleteInvoiceMappingsSql");
		invoiceDao.save(payment.getInvoices());
		super.delete(payment);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EzeePayment> getOutstandingCheques(Long premisesId) {
		if (premisesId == null) {
			return (List<EzeePayment>) getHibernateTemplate().findByNamedQuery("selectOutstandingChequesSql",
					new Object[] {});
		} else {
			return (List<EzeePayment>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam("selectOutstandingChequesByPremisesSql", "payerId", premisesId);
		}
	}

	@Override
	public EzeePayment get(long id) {
		return super.get(id, EzeePayment.class);
	}

	@Override
	public List<EzeePayment> get() {
		return super.get(EzeePayment.class);
	}
}