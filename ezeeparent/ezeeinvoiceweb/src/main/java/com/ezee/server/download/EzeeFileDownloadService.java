package com.ezee.server.download;

import static com.ezee.client.EzeeInvoiceWebConstants.INVOICE_ID;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezee.dao.EzeeInvoiceDao;
import com.ezee.model.entity.EzeeInvoice;

/**
 * 
 * @author siborg
 *
 */
public class EzeeFileDownloadService extends HttpServlet {

	private static final Logger log = LoggerFactory.getLogger(EzeeFileDownloadService.class);

	private static final long serialVersionUID = -176018857407655142L;

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		long invoiceId = Long.parseLong(req.getParameter(INVOICE_ID));
		EzeeInvoice invoice = getInvoiceDao().get(invoiceId, EzeeInvoice.class);
		try {
			if (invoice != null && hasLength(invoice.getFilename())) {
				log.info("Attempting to download '" + invoice.getFilename() + "' for invoice '" + invoice.getInvoiceId()
						+ "'.");
				generateInvoiceFile(invoice, resp);
				log.info("Successfully generated '" + invoice.getFilename() + "' for invoice '" + invoice.getInvoiceId()
						+ "'.");
			}
		} catch (IOException exception) {
			log.info("Error generating '" + invoice.getFilename() + "' for invoice '" + invoice.getInvoiceId() + "'.",
					exception);
		} catch (Throwable t) {
			log.info("Error generating '" + invoice.getFilename() + "' for invoice '" + invoice.getInvoiceId() + "'.",
					t);
		}
	}

	private void generateInvoiceFile(final EzeeInvoice invoice, final HttpServletResponse resp) throws IOException {
		String filename = invoice.getFilename();
		OutputStream stream = createFilenameResponseHeader(resp, filename);
		byte[] file = invoice.getFile();
		if (file != null) {
			resp.setContentLength(file.length);
		} else {
			resp.setContentLength(ZERO);
		}
		stream.write(file);
		stream.close();
	}

	private OutputStream createFilenameResponseHeader(final HttpServletResponse resp, final String filename)
			throws IOException {
		OutputStream stream = resp.getOutputStream();
		resp.setContentType("application/pdf");
		resp.addHeader("Content-Type", "application/pdf");
		resp.setHeader("Content-Disposition", "attachement;filename=" + filename);
		return stream;
	}

	private EzeeInvoiceDao getInvoiceDao() {
		return getWebApplicationContext(getServletContext()).getBean(EzeeInvoiceDao.class);
	}
}