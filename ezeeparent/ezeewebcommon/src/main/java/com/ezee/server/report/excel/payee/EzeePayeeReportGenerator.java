package com.ezee.server.report.excel.payee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.dao.EzeePayeeDao;
import com.ezee.model.entity.EzeePayee;
import com.ezee.server.report.excel.financialentity.EzeeFinancialEntityReportGenerator;

public class EzeePayeeReportGenerator extends EzeeFinancialEntityReportGenerator<EzeePayee> {

	@Autowired
	private EzeePayeeDao dao;

	@Override
	protected List<EzeePayee> getEntites(final HttpServletRequest request) {
		return dao.get(resolveFilter(request));
	}
}