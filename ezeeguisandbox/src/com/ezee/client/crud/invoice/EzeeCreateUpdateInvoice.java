package com.ezee.client.crud.invoice;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Button;

public class EzeeCreateUpdateInvoice extends Composite {

	private static EzeeCreateUpdateInvoiceUiBinder uiBinder = GWT.create(EzeeCreateUpdateInvoiceUiBinder.class);

	@UiField
	TextBox txtInvoiceNumber;

	@UiField
	ListBox lstPremises;

	@UiField
	ListBox lstSupplier;

	@UiField
	TextBox txtAmount;

	@UiField
	TextBox txtTax;

	@UiField
	TextBox txtTotal;

	@UiField
	DateBox dtDue;

	@UiField
	DateBox dtPaid;

	@UiField
	ListBox lstDebtAge;

	@UiField
	RichTextArea txtDescription;

	@UiField
	CheckBox chkTaxable;

	@UiField
	Button btnClose;

	@UiField
	Button btnEdit;

	@UiField
	Button btnDelete;

	@UiField
	ListBox lstClassification;

	interface EzeeCreateUpdateInvoiceUiBinder extends UiBinder<Widget, EzeeCreateUpdateInvoice> {
	}

	public EzeeCreateUpdateInvoice() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}