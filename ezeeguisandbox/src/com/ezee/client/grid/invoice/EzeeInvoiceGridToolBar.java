package com.ezee.client.grid.invoice;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;

public class EzeeInvoiceGridToolBar extends Composite {

	private static EzeeInvoiceGridToolBarUiBinder uiBinder = GWT.create(EzeeInvoiceGridToolBarUiBinder.class);
	
	@UiField
	TextBox txtInvoiceNumber;
	
	@UiField
	TextBox txtSupplier;
	
	@UiField
	TextBox txtPremises;
	
	@UiField
	Button btnRefresh;
	
	@UiField
	Button btnClear;
	
	@UiField
	CheckBox chkShowPaid;

	interface EzeeInvoiceGridToolBarUiBinder extends UiBinder<Widget, EzeeInvoiceGridToolBar> {
	}

	public EzeeInvoiceGridToolBar() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
