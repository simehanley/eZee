package com.ezee.client.crud.invoice;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class EzeeUploadInvoiceForm extends Composite {

	private static EzeeUploadInvoiceFormUiBinder uiBinder = GWT.create(EzeeUploadInvoiceFormUiBinder.class);
	@UiField HorizontalPanel uploadPanel;

	interface EzeeUploadInvoiceFormUiBinder extends UiBinder<Widget, EzeeUploadInvoiceForm> {
	}

	public EzeeUploadInvoiceForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
