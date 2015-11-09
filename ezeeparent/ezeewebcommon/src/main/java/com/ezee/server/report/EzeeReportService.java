package com.ezee.server.report;

import static com.ezee.web.common.EzeeWebCommonConstants.REPORT_GENERATORS;
import static com.ezee.web.common.EzeeWebCommonConstants.REPORT_TYPE;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezee.server.AbstractRemoteService;
import com.ezee.web.common.enums.EzeeReportType;

/**
 * 
 * @author siborg
 *
 */
public class EzeeReportService extends AbstractRemoteService {

	private static final long serialVersionUID = 1426459732286098774L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		EzeeReportType type = EzeeReportType.valueOf(req.getParameter(REPORT_TYPE));
		Map<EzeeReportType, EzeeReportGenerator> generators = (Map<EzeeReportType, EzeeReportGenerator>) getSpringBean(
				REPORT_GENERATORS);
		if (generators.containsKey(type)) {
			generators.get(type).generateReport(req, resp);
		}
	}
}