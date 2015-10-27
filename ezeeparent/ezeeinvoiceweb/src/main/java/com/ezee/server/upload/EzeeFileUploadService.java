package com.ezee.server.upload;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author siborg
 *
 */
public class EzeeFileUploadService extends HttpServlet {

	private static final Logger log = LoggerFactory.getLogger(EzeeFileUploadService.class);

	private static final long serialVersionUID = 9142177563724555385L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean multipart = ServletFileUpload.isMultipartContent(new ServletRequestContext(request));

		if (multipart) {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List<FileItem> items = upload.parseRequest(request);

				for (FileItem item : items) {
					if (item.isFormField()) {
						log.info(item.getFieldName());
						log.info(item.getString());
					} else {
						log.info(item.getName());
					}
				}
			} catch (FileUploadException e) {
				log.error("Error uploading file.", e);
			}
		}
	}
}