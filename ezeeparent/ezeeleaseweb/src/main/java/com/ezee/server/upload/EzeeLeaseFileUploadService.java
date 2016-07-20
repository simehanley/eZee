package com.ezee.server.upload;

import static com.ezee.client.EzeeLeaseWebConstants.LEASE_ID;
import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.server.EzeeServerDateUtils.SERVER_DATE_UTILS;
import static com.ezee.web.common.EzeeWebCommonConstants.FILE_UPLOAD_FAIL;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezee.dao.EzeeLeaseDao;
import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.model.entity.lease.EzeeLeaseFile;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

public class EzeeLeaseFileUploadService extends UploadAction {

	private static final Logger log = LoggerFactory.getLogger(EzeeLeaseFileUploadService.class);

	private static final long serialVersionUID = 9142177563724555385L;

	@Override
	public String executeAction(final HttpServletRequest request, final List<FileItem> sessionFiles)
			throws UploadActionException {
		long leaseId = Long.parseLong(request.getParameter(LEASE_ID));
		EzeeLease lease = getLeaseDao().get(leaseId, EzeeLease.class);
		if (lease != null) {
			if (!isEmpty(sessionFiles)) {
				FileItem file = sessionFiles.get(ZERO);
				String filename = file.getName();
				byte[] content = file.get();
				EzeeLeaseFile leaseFile = new EzeeLeaseFile(resolveOrder(lease), SERVER_DATE_UTILS.toString(new Date()),
						filename);
				leaseFile.setFile(content);
				lease.addFile(leaseFile);
				try {
					getLeaseDao().save(lease);
					log.info("Successfully saved file '" + filename + "' on lease '" + lease + "'.");
					removeSessionFileItems(request);
					return leaseFile.getFileDescrptor();
				} catch (Throwable t) {
					log.error("Saving file '" + filename + "' on lease '" + lease + "' failed.", t);
				}
			} else {
				log.error("Invalid session files object, cannot upload file.");
			}
		} else {
			log.error("Failed to find lease with id '" + leaseId + ".");
		}
		removeSessionFileItems(request);
		return FILE_UPLOAD_FAIL;
	}

	private int resolveOrder(final EzeeLease lease) {
		if (!isEmpty(lease.getFiles())) {
			return lease.getFiles().last().getOrder() + ONE;
		}
		return ZERO;
	}

	private EzeeLeaseDao getLeaseDao() {
		return getWebApplicationContext(getServletContext()).getBean(EzeeLeaseDao.class);
	}
}