package com.ezee.server.report.excel.premises;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.dao.EzeePremisesDao;
import com.ezee.model.entity.EzeePremises;
import com.ezee.server.report.excel.payer.EzeePayerReportGenerator;

public class EzeePremisesReportGenerator extends EzeePayerReportGenerator<EzeePremises> {

	@Autowired
	private EzeePremisesDao dao;

	@Override
	protected List<EzeePremises> getEntites(final HttpServletRequest request) {
		return dao.get(resolveFilter(request));
	}
}