package com.ezee.server.report.excel.payer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.dao.EzeePayerDao;
import com.ezee.model.entity.EzeePayer;
import com.ezee.server.report.excel.financialentity.EzeeFinancialEntityReportGenerator;

public class EzeePayerReportGenerator extends EzeeFinancialEntityReportGenerator<EzeePayer> {

	@Autowired
	private EzeePayerDao dao;

	@Override
	protected List<EzeePayer> getEntites(final HttpServletRequest request) {
		return dao.get(resolveFilter(request));
	}
}