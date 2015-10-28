package com.ezee.server.upload;

import static com.ezee.client.EzeeInvoiceWebConstants.FILE_UPLOAD_FAIL;
import static com.ezee.client.EzeeInvoiceWebConstants.FILE_UPLOAD_SUCCESS;
import static com.ezee.client.EzeeInvoiceWebConstants.INVOICE_ID;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezee.dao.EzeeInvoiceDao;
import com.ezee.model.entity.EzeeInvoice;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

/**
 * 
 * @author siborg
 *
 */
public class EzeeFileUploadService extends UploadAction {

	private static final Logger log = LoggerFactory.getLogger(EzeeFileUploadService.class);

	private static final long serialVersionUID = 9142177563724555385L;

	@Override
	public String executeAction(final HttpServletRequest request, final List<FileItem> sessionFiles)
			throws UploadActionException {
		long invoiceId = Long.parseLong(request.getParameter(INVOICE_ID));
		EzeeInvoice invoice = getInvoiceDao().get(invoiceId, EzeeInvoice.class);
		if (invoice != null) {
			if (!isEmpty(sessionFiles)) {
				FileItem file = sessionFiles.get(ZERO);
				String filename = file.getName();
				byte[] content = file.get();
				invoice.setFilename(filename);
				invoice.setFile(content);
				try {
					getInvoiceDao().save(invoice);
					log.info("Successfully saved file '" + filename + "' against invoice '" + invoice.getInvoiceId()
							+ "'.");

					removeSessionFileItems(request);
					return FILE_UPLOAD_SUCCESS;
				} catch (Throwable t) {
					log.error("Saving file '" + filename + "' against invoice '" + invoice.getInvoiceId() + "' failed.",
							t);
				}
			} else {
				log.error("Invalid session files object, cannot upload file.");
			}
		} else {
			log.error("Failed to find invoice with id '" + invoiceId + ".");
		}
		removeSessionFileItems(request);
		return FILE_UPLOAD_FAIL;
	}

	private EzeeInvoiceDao getInvoiceDao() {
		return getWebApplicationContext(getServletContext()).getBean(EzeeInvoiceDao.class);
	}
}