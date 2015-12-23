package com.ezee.server.report.excel.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.dao.EzeeResourceDao;
import com.ezee.model.entity.EzeeResource;
import com.ezee.server.report.excel.financialentity.EzeeFinancialEntityReportGenerator;

public class EzeeResourceReportGenerator extends EzeeFinancialEntityReportGenerator<EzeeResource> {

	@Autowired
	private EzeeResourceDao dao;

	@Override
	protected List<EzeeResource> getEntites(final HttpServletRequest request) {
		return dao.get(resolveFilter(request));
	}
}