package com.ezee.client.bank;

import static com.ezee.client.EzeeInvoiceWebConstants.BANK;
import static com.ezee.client.css.EzeeInvoiceGwtOverridesResources.INSTANCE;

import com.ezee.client.grid.payment.EzeePaymentGridModel;
import com.ezee.model.entity.EzeePayment;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EzeeBankBalance extends DialogBox {

	private static EzeeBankBalanceUiBinder uiBinder = GWT.create(EzeeBankBalanceUiBinder.class);

	@UiField
	ListBox lstPremises;

	@UiField
	TextBox txtBalance;

	@UiField(provided = true)
	DataGrid<EzeePayment> grdCheques;

	@UiField
	Button btnClose;

	@UiField
	Button btnRefresh;

	private EzeePaymentGridModel model;

	interface EzeeBankBalanceUiBinder extends UiBinder<Widget, EzeeBankBalance> {
	}

	public EzeeBankBalance() {
		super(false, false);
		setText(BANK);
		initGrid();
		setWidget(uiBinder.createAndBindUi(this));
	}

	private void initGrid() {
		grdCheques = new DataGrid<>();
		grdCheques = new DataGrid<EzeePayment>(100, INSTANCE);
		grdCheques.setMinimumTableWidth(600, Style.Unit.PX);
		model = new EzeePaymentGridModel();
		model.bind(grdCheques);
	}
}