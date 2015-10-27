package com.ezee.client.grid;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EzeeFinancialEntityToolbar extends Composite {

	private static EzeeFinancialEntityToolbarUiBinder uiBinder = GWT.create(EzeeFinancialEntityToolbarUiBinder.class);

	interface EzeeFinancialEntityToolbarUiBinder extends UiBinder<Widget, EzeeFinancialEntityToolbar> {
	}

	@UiField
	TextBox txtNameText;

	@UiField
	Button btnRefresh;

	@UiField
	Button btnClear;

	public EzeeFinancialEntityToolbar() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
