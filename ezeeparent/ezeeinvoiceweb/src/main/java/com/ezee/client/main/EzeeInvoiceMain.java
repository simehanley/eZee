package com.ezee.client.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class EzeeInvoiceMain extends Composite {

	private static EzeeInvoiceMainUiBinder uiBinder = GWT.create(EzeeInvoiceMainUiBinder.class);

	interface EzeeInvoiceMainUiBinder extends UiBinder<Widget, EzeeInvoiceMain> {
	}

	public EzeeInvoiceMain() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
