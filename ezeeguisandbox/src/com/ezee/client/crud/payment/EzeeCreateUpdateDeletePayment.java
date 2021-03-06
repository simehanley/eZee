package com.ezee.client.crud.payment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.datepicker.client.DateBox;

public class EzeeCreateUpdateDeletePayment extends DialogBox {

	private static EzeeCreateUpdateDeletePaymentUiBinder uiBinder = GWT
			.create(EzeeCreateUpdateDeletePaymentUiBinder.class);
	
	@UiField
	ListBox lstPaymentType;

	@UiField
	RichTextArea txtDescription;

	@UiField(provided = true)
	DataGrid<Object> grdInvoices = new DataGrid<Object>();
	@UiField TextBox txtAmount;
	@UiField TextBox txtTax;
	@UiField TextBox txtTotal;
	@UiField Button btnDelete;
	@UiField Button btnSave;
	@UiField Button btnClose;
	@UiField Label lblChequeNumber;
	@UiField TextBox txtChequeNumber;
	@UiField CheckBox chkPresented;
	@UiField DateBox dtPmtDate;

	interface EzeeCreateUpdateDeletePaymentUiBinder extends UiBinder<Widget, EzeeCreateUpdateDeletePayment> {
	}

	public EzeeCreateUpdateDeletePayment() {
		setWidget(uiBinder.createAndBindUi(this));
	}
}
