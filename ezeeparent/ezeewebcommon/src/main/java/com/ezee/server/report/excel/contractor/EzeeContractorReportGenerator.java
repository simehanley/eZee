package com.ezee.server.report.excel.contractor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.dao.EzeeContractorDao;
import com.ezee.model.entity.EzeeContractor;
import com.ezee.server.report.excel.payee.EzeePayeeReportGenerator;

public class EzeeContractorReportGenerator extends EzeePayeeReportGenerator<EzeeContractor> {

	@Autowired
	private EzeeContractorDao dao;

	@Override
	protected List<EzeeContractor> getEntites(final HttpServletRequest request) {
		return dao.get(resolveFilter(request));
	}
}