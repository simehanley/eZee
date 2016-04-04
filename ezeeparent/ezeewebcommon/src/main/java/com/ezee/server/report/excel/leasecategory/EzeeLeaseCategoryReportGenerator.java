package com.ezee.server.report.excel.leasecategory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.dao.EzeeLeaseCategoryDao;
import com.ezee.model.entity.lease.EzeeLeaseCategory;
import com.ezee.server.report.excel.financialentity.EzeeFinancialEntityReportGenerator;

public class EzeeLeaseCategoryReportGenerator extends EzeeFinancialEntityReportGenerator<EzeeLeaseCategory> {

	@Autowired
	private EzeeLeaseCategoryDao dao;

	@Override
	protected List<EzeeLeaseCategory> getEntites(HttpServletRequest request) {
		return dao.get(resolveFilter(request));
	}
}