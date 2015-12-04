package com.ezee.server;

import java.util.List;

import com.ezee.client.EzeeInvoiceService;
import com.ezee.model.entity.EzeeDebtAgeRule;
import com.ezee.model.entity.EzeePayment;
import com.ezee.server.dao.EzeeEntitiesDao;
import com.ezee.server.util.EzeeDueDateCalculator;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceServiceImpl extends AbstractRemoteService implements EzeeInvoiceService {

	private static final long serialVersionUID = -9137671586522654165L;

	@Override
	public List<EzeePayment> getOutstandingCheques(final Long premisesId) {
		return getSpringBean(EzeeEntitiesDao.class).getOutstandingCheques(premisesId);
	}

	@Override
	public String calculateDueDate(final EzeeDebtAgeRule rule, final String today) {
		return getSpringBean(EzeeDueDateCalculator.class).calculate(rule, today);
	}
}