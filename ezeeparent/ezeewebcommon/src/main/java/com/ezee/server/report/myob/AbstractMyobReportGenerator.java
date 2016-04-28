package com.ezee.server.report.myob;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.ezee.server.report.AbstractReportGenerator;

public class AbstractMyobReportGenerator extends AbstractReportGenerator {

	protected OutputStream createMyobResponseHeader(final HttpServletResponse resp, final String filename)
			throws IOException {
		OutputStream stream = resp.getOutputStream();
		resp.setContentType("text/plain");
		resp.addHeader("Content-Type", "text/plain");
		resp.setHeader("Content-Disposition", "attachment;filename=" + filename);
		return stream;
	}
}