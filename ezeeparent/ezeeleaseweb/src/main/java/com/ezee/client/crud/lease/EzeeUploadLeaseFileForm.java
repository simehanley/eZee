package com.ezee.client.crud.lease;

import static com.ezee.client.EzeeLeaseWebConstants.LEASE_ID;
import static com.ezee.web.common.EzeeWebCommonConstants.ERROR;
import static com.ezee.web.common.EzeeWebCommonConstants.FILE_UPLOAD_FAIL;
import static com.ezee.web.common.ui.css.EzeeDefaultResources.INSTANCE;
import static com.ezee.web.common.ui.dialog.EzeeMessageDialog.showNew;
import static gwtupload.client.IFileInput.FileInputType.CUSTOM;

import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.model.entity.lease.EzeeLeaseFile;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.dialog.EzeeDialog;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.ModalUploadStatus;
import gwtupload.client.SingleUploader;

public class EzeeUploadLeaseFileForm extends EzeeDialog {

	private static EzeeUploadLeaseFileFormUiBinder uiBinder = GWT.create(EzeeUploadLeaseFileFormUiBinder.class);

	interface EzeeUploadLeaseFileFormUiBinder extends UiBinder<Widget, EzeeUploadLeaseFileForm> {
	}

	private static final String UPLOAD_FILE = "Upload File";

	@UiField
	HorizontalPanel uploadPanel;

	private final EzeeLease lease;

	private final EzeeCreateUpdateDeleteEntityHandler<EzeeLeaseFile> handler;

	public EzeeUploadLeaseFileForm(final EzeeLease lease,
			final EzeeCreateUpdateDeleteEntityHandler<EzeeLeaseFile> handler) {
		this.lease = lease;
		this.handler = handler;
		setWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void center() {
		initForm();
		super.center();
	}

	private void initForm() {
		setText(UPLOAD_FILE);
		initUploader();
	}

	private void initUploader() {
		Button choose = new Button("Choose File");
		choose.setStyleName(INSTANCE.css().gwtButton());
		final SingleUploader uploader = new SingleUploader(CUSTOM.with(choose), new ModalUploadStatus());
		uploader.setServletPath(uploader.getServletPath() + "?" + LEASE_ID + "=" + lease.getId());
		uploader.setAutoSubmit(true);
		uploader.setValidExtensions("pdf");
		uploader.addOnFinishUploadHandler(new OnFinishUploaderHandler() {
			@Override
			public void onFinish(IUploader iUploader) {
				String result = uploader.getServerMessage().getMessage();
				if (FILE_UPLOAD_FAIL.equals(result)) {
					showNew(ERROR, "File upload failed, please see log for details.");
					close();
				} else {
					handler.onSave(EzeeLeaseFile.getFile(result));
					close();
				}
			}
		});
		uploadPanel.add(uploader);
		Button cancel = new Button("Cancel");
		cancel.setStyleName(INSTANCE.css().gwtButton());
		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				close();
			}
		});
		uploadPanel.add(cancel);
	}
}