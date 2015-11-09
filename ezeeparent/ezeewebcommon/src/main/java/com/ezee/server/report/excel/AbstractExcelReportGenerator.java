package com.ezee.server.report.excel;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.ezee.server.report.AbstractReportGenerator;

/**
 * 
 * @author siborg
 *
 */
public abstract class AbstractExcelReportGenerator extends AbstractReportGenerator {

	protected OutputStream createExcelFilenameResponseHeader(final HttpServletResponse resp, final String filename)
			throws IOException {
		OutputStream stream = resp.getOutputStream();
		resp.setContentType("application/vnd.ms-excel");
		resp.addHeader("Content-Type", "application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachement;filename=" + filename);
		return stream;
	}
}