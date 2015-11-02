package com.ezee.client.crud.invoice;

import static com.ezee.client.EzeeInvoiceWebConstants.FILE_UPLOAD_FAIL;
import static com.ezee.client.EzeeInvoiceWebConstants.INVOICE_ID;
import static com.ezee.web.common.EzeeWebCommonConstants.ERROR;
import static com.ezee.web.common.ui.css.EzeeDefaultResources.INSTANCE;
import static com.ezee.web.common.ui.dialog.EzeeMessageDialog.showNew;
import static gwtupload.client.IFileInput.FileInputType.CUSTOM;

import com.ezee.client.grid.invoice.EzeeInvoiceUpLoaderListener;
import com.ezee.model.entity.EzeeInvoice;
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

public class EzeeUploadInvoiceForm extends EzeeDialog {

	private static EzeeUploadInvoiceFormUiBinder uiBinder = GWT.create(EzeeUploadInvoiceFormUiBinder.class);

	private static final String UPLOAD_INVOICE = "Upload Invoice File";

	@UiField
	HorizontalPanel uploadPanel;

	private final EzeeInvoice invoice;

	private final EzeeInvoiceUpLoaderListener listener;

	interface EzeeUploadInvoiceFormUiBinder extends UiBinder<Widget, EzeeUploadInvoiceForm> {
	}

	public EzeeUploadInvoiceForm(final EzeeInvoice invoice, final EzeeInvoiceUpLoaderListener listener) {
		this.invoice = invoice;
		this.listener = listener;
		setWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void center() {
		initForm();
		super.center();
	}

	private void initForm() {
		setText(UPLOAD_INVOICE + " (" + invoice.getInvoiceId() + ")");
		initUploader();
	}

	private void initUploader() {
		Button choose = new Button("Choose File");
		choose.setStyleName(INSTANCE.css().gwtButton());
		final SingleUploader uploader = new SingleUploader(CUSTOM.with(choose), new ModalUploadStatus());
		uploader.setServletPath(uploader.getServletPath() + "?" + INVOICE_ID + "=" + invoice.getId());
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
					listener.invoiceUploaded(result);
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