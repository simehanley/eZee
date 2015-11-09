package com.ezee.server.report.excel.invoice;

import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.dao.EzeeInvoiceDao;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.server.report.EzeeReportGenerator;
import com.ezee.server.report.excel.AbstractExcelReportGenerator;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceReportGenerator extends AbstractExcelReportGenerator implements EzeeReportGenerator {

	private static final Logger log = LoggerFactory.getLogger(EzeeInvoiceReportGenerator.class);

	@Autowired
	private EzeeInvoiceDao dao;

	@Override
	public void generateReport(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			generateEzeeInvoiceReport(request, response);
		} catch (IOException exception) {
			log.error("Error generating ezee invoice report.", exception);
		}

	}

	private void generateEzeeInvoiceReport(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<EzeeInvoice> invoices = dao.get(EzeeInvoice.class);
		if (!isEmpty(invoices)) {
			Map<String, List<EzeeInvoice>> supplierInvoices = resolveInvoicesBySupplier(invoices);
			generateEzeeInvoiceReport(supplierInvoices, response);
		}
	}

	private void generateEzeeInvoiceReport(final Map<String, List<EzeeInvoice>> supplierInvoices,
			final HttpServletResponse response) throws IOException {
		String filename = UUID.randomUUID().toString() + ".xls";
		OutputStream stream = createExcelFilenameResponseHeader(response, filename);
		byte[] content = generateEzeeInvoiceReport(supplierInvoices);
		response.setContentLength(content.length);
		stream.write(content);
		stream.close();
	}

	private byte[] generateEzeeInvoiceReport(final Map<String, List<EzeeInvoice>> supplierInvoices) {
		/* implement me */
		return new byte[] {};
	}

	private Map<String, List<EzeeInvoice>> resolveInvoicesBySupplier(final List<EzeeInvoice> invoices) {
		return null;
	}
}