package com.ezee.server.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeReportGenerator {

	void generateReport(HttpServletRequest request, HttpServletResponse response);
}
