package com.ezee.client.grid.payment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.TextBox;

public class EzeePaymentGridToolBar extends Composite {

	private static EzeePaymentGridToolBarUiBinder uiBinder = GWT.create(EzeePaymentGridToolBarUiBinder.class);
	@UiField DateBox dtFrom;
	@UiField DateBox dtTo;
	@UiField TextBox txtInvoiceText;

	interface EzeePaymentGridToolBarUiBinder extends UiBinder<Widget, EzeePaymentGridToolBar> {
	}

	public EzeePaymentGridToolBar() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
