package com.ezee.server.report.excel.leasepremises;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.dao.EzeeLeasePremisesDao;
import com.ezee.model.entity.lease.EzeeLeasePremises;
import com.ezee.server.report.excel.payee.EzeePayeeReportGenerator;

public class EzeeLeasePremisesReportGenerator extends EzeePayeeReportGenerator<EzeeLeasePremises> {

	@Autowired
	private EzeeLeasePremisesDao dao;

	@Override
	protected List<EzeeLeasePremises> getEntites(HttpServletRequest request) {
		return dao.get(resolveFilter(request));
	}
}