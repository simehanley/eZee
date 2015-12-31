package com.ezee.client.crud.project.item.payment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;

public class EzeeCreateUpdateDeleteProjectItemPayment extends DialogBox {

	private static EzeeCreateUpdateDeleteProjectItemPaymentUiBinder uiBinder = GWT
			.create(EzeeCreateUpdateDeleteProjectItemPaymentUiBinder.class);

	@UiField
	TextBox txtInvoiceNumber;

	@UiField
	TextBox txtChequeNumber;

	@UiField
	ListBox lstPaymentTypes;

	@UiField
	Button btnDelete;

	@UiField
	Button btnSave;

	@UiField
	Button btnClose;

	interface EzeeCreateUpdateDeleteProjectItemPaymentUiBinder
			extends UiBinder<Widget, EzeeCreateUpdateDeleteProjectItemPayment> {
	}

	public EzeeCreateUpdateDeleteProjectItemPayment() {
		setWidget(uiBinder.createAndBindUi(this));
	}

}
