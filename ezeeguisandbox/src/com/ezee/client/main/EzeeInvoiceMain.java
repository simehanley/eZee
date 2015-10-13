package com.ezee.client.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class EzeeInvoiceMain extends Composite {

	private static EzeeInvoiceMainUiBinder uiBinder = GWT.create(EzeeInvoiceMainUiBinder.class);

	interface EzeeInvoiceMainUiBinder extends UiBinder<Widget, EzeeInvoiceMain> {
	}

	public EzeeInvoiceMain() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("btnRefresh")
	void onBtnRefreshClick(ClickEvent event) {
	}
}
