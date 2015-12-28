package com.ezee.server.report.excel.payee;

import com.ezee.model.entity.EzeePayee;
import com.ezee.server.report.excel.financialentity.EzeeFinancialEntityReportGenerator;

public abstract class EzeePayeeReportGenerator<T extends EzeePayee> extends EzeeFinancialEntityReportGenerator<T> {

	// @Autowired
	// private EzeePayeeDao dao;
	//
	// @Override
	// protected List<EzeePayee> getEntites(final HttpServletRequest request) {
	// return dao.get(resolveFilter(request));
	// }
}