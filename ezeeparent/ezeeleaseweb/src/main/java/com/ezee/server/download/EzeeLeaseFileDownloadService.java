package com.ezee.server.download;

import static com.ezee.client.EzeeLeaseWebConstants.LEASE_FILE_NAME;
import static com.ezee.client.EzeeLeaseWebConstants.LEASE_ID;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezee.dao.EzeeLeaseDao;
import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.model.entity.lease.EzeeLeaseFile;

public class EzeeLeaseFileDownloadService extends HttpServlet {

	private static final Logger log = LoggerFactory.getLogger(EzeeLeaseFileDownloadService.class);

	private static final long serialVersionUID = 4506300572090941870L;

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		long leaseId = Long.parseLong(req.getParameter(LEASE_ID));
		String filename = req.getParameter(LEASE_FILE_NAME);
		EzeeLease lease = getLeaseDao().get(leaseId, EzeeLease.class);
		try {
			if (lease != null && hasLength(filename)) {
				log.info("Attempting to download '" + filename + "' for lease '" + lease + "'.");
				EzeeLeaseFile file = resolveFile(lease, filename);
				if (file != null) {
					generateLeaseFile(file, resp);
					log.info("Successfully generated '" + filename + "' for lease '" + lease + "'.");
				} else {
					log.error(filename + " not found on lease '" + lease + "'.");
				}
			}
		} catch (IOException exception) {
			log.info("Error generating '" + filename + "' for lease '" + lease + "'.", exception);
		} catch (Throwable t) {
			log.info("Error generating '" + filename + "' for lease '" + lease + "'.", t);
		}
	}

	private EzeeLeaseFile resolveFile(final EzeeLease lease, final String filename) {
		if (!isEmpty(lease.getFiles())) {
			for (EzeeLeaseFile file : lease.getFiles()) {
				if (filename.equals(file.getFilename())) {
					return file;
				}
			}
		}
		return null;
	}

	private void generateLeaseFile(final EzeeLeaseFile file, final HttpServletResponse resp) throws IOException {
		OutputStream stream = createFilenameResponseHeader(resp, file);
		byte[] filebytes = file.getFile();
		if (file != null) {
			resp.setContentLength(filebytes.length);
		}
		stream.write(filebytes);
		stream.close();
	}

	private OutputStream createFilenameResponseHeader(final HttpServletResponse resp, final EzeeLeaseFile file)
			throws IOException {
		OutputStream stream = resp.getOutputStream();
		String contentType = hasLength(file.getContentType()) ? file.getContentType() : "application/pdf";
		resp.setContentType(contentType);
		resp.addHeader("Content-Type", contentType);
		resp.setHeader("Content-Disposition", "attachement;filename=" + file.getFilename());
		return stream;
	}

	private EzeeLeaseDao getLeaseDao() {
		return getWebApplicationContext(getServletContext()).getBean(EzeeLeaseDao.class);
	}
}