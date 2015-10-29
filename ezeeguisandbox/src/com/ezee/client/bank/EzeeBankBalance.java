package com.ezee.client.bank;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ListBox;

public class EzeeBankBalance extends Composite {

	private static EzeeBankBalanceUiBinder uiBinder = GWT.create(EzeeBankBalanceUiBinder.class);
	@UiField TextBox txtBalance;
	@UiField Button btnClose;
	@UiField Button btnRefresh;
	@UiField AbsolutePanel gridCheques;
	@UiField ListBox lstPremises;

	interface EzeeBankBalanceUiBinder extends UiBinder<Widget, EzeeBankBalance> {
	}

	public EzeeBankBalance() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
