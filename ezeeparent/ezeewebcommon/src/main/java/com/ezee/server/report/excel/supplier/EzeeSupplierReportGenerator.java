package com.ezee.server.report.excel.supplier;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.dao.EzeeSupplierDao;
import com.ezee.model.entity.EzeeSupplier;
import com.ezee.server.report.excel.payee.EzeePayeeReportGenerator;

public class EzeeSupplierReportGenerator extends EzeePayeeReportGenerator<EzeeSupplier> {

	@Autowired
	private EzeeSupplierDao dao;

	@Override
	protected List<EzeeSupplier> getEntites(final HttpServletRequest request) {
		return dao.get(resolveFilter(request));
	}
}
