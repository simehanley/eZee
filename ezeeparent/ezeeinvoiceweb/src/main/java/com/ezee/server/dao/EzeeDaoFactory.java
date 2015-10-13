package com.ezee.server.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.dao.EzeeDebtAgeRuleDao;
import com.ezee.dao.EzeeInvoiceDao;
import com.ezee.dao.EzeePayeeDao;
import com.ezee.dao.EzeePayerDao;
import com.ezee.dao.EzeePaymentDao;

/**
 * 
 * @author siborg
 *
 */
public class EzeeDaoFactory {

	@Autowired
	private EzeePayeeDao payeeDao;

	@Autowired
	private EzeePayerDao payerDao;

	@Autowired
	private EzeePaymentDao paymentDao;

	@Autowired
	private EzeeInvoiceDao invoiceDao;

	@Autowired
	private EzeeDebtAgeRuleDao debtAgeRuleDao;

	public final EzeePayeeDao getPayeeDao() {
		return payeeDao;
	}

	public final EzeePayerDao getPayerDao() {
		return payerDao;
	}

	public final EzeePaymentDao getPaymentDao() {
		return paymentDao;
	}

	public final EzeeInvoiceDao getInvoiceDao() {
		return invoiceDao;
	}

	public final EzeeDebtAgeRuleDao getDebtAgeRuleDao() {
		return debtAgeRuleDao;
	}
}