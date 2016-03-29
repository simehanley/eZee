package com.ezee.server.report.excel.leasetenant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.dao.EzeeLeaseTenantDao;
import com.ezee.model.entity.lease.EzeeLeaseTenant;
import com.ezee.server.report.excel.payer.EzeePayerReportGenerator;

public class EzeeLeaseTenantReportGenerator extends EzeePayerReportGenerator<EzeeLeaseTenant> {

	@Autowired
	private EzeeLeaseTenantDao dao;

	@Override
	protected List<EzeeLeaseTenant> getEntites(final HttpServletRequest request) {
		return dao.get(resolveFilter(request));
	}
}
