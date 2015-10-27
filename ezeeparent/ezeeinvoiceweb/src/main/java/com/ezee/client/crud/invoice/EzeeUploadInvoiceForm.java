package com.ezee.client.crud.invoice;

import static com.ezee.client.EzeeInvoiceWebConstants.FILE_UPLOAD_SERVICE;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.google.gwt.user.client.ui.FormPanel.ENCODING_MULTIPART;
import static com.google.gwt.user.client.ui.FormPanel.METHOD_POST;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EzeeUploadInvoiceForm extends PopupPanel {

	private static EzeeUploadInvoiceFormUiBinder uiBinder = GWT.create(EzeeUploadInvoiceFormUiBinder.class);

	interface EzeeUploadInvoiceFormUiBinder extends UiBinder<Widget, EzeeUploadInvoiceForm> {
	}

	// @UiField
	FormPanel formPanel;

	@UiField
	Button btnUpload;

	@UiField
	Button btnCancel;

	// @UiField
	TextBox txtInvoiceNumber;

	// @UiField
	TextBox txtPremises;

	// @UiField
	TextBox txtSupplier;

	// @UiField
	FileUpload fileUpload;

	@UiField
	FormPanel docForm;

	@UiField
	FlowPanel inputPane;

	@UiField
	FileUpload DocPath;

	private final String invoiceNumber;

	private final String premises;

	private final String supplier;

	public EzeeUploadInvoiceForm(final String invoiceNumber, final String premises, final String supplier) {
		setWidget(uiBinder.createAndBindUi(this));
		this.invoiceNumber = invoiceNumber;
		this.premises = premises;
		this.supplier = supplier;
	}

	@Override
	public void center() {
		if (validate()) {
			init();
			super.center();
		}
	}

	private void init() {
		// formPanel = new FormPanel();
		// formPanel.setAction(GWT.getModuleBaseURL() + FILE_UPLOAD_SERVICE);
		// formPanel.setEncoding(ENCODING_MULTIPART);
		// formPanel.setMethod(METHOD_POST);
		// setText("Upload Invoice");
		// txtInvoiceNumber.setText(invoiceNumber);
		// txtInvoiceNumber.setEnabled(false);
		// txtPremises.setText(premises);
		// txtPremises.setEnabled(false);
		// txtSupplier.setText(supplier);
		// txtSupplier.setEnabled(false);

		docForm.setAction(GWT.getModuleBaseURL() + FILE_UPLOAD_SERVICE);
		docForm.setEncoding(ENCODING_MULTIPART);
		docForm.setMethod(METHOD_POST);
	}

	private boolean validate() {
		if (!hasLength(invoiceNumber)) {
			Window.alert("You must enter a valid invoice number.");
			return false;
		} else if (!hasLength(premises)) {
			Window.alert("You must enter a valid premises.");
			return false;
		} else if (!hasLength(supplier)) {
			Window.alert("You must enter a valid supplier.");
			return false;
		}
		return true;
	}

	@UiHandler("btnCancel")
	void onDeleteClick(ClickEvent event) {
		this.hide(true);
	}

	@UiHandler("btnUpload")
	void onUploadClick(ClickEvent event) {
		String filename = DocPath.getFilename();
		if (hasLength(filename)) {

			docForm.submit();
		}
	}
}