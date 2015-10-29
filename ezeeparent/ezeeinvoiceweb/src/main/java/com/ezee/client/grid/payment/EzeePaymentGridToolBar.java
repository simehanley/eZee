package com.ezee.client.grid.payment;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.string.EzeeStringUtils.hasLength;

import java.util.Date;

import com.ezee.client.bank.EzeeBankBalance;
import com.ezee.client.grid.EzeeGridToolbar;
import com.ezee.common.web.EzeeFromatUtils;
import com.ezee.model.entity.EzeePayment;
import com.ezee.model.entity.filter.EzeeEntityFilter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class EzeePaymentGridToolBar extends EzeeGridToolbar<EzeePayment> {

	private static EzeePaymentGridToolBarUiBinder uiBinder = GWT.create(EzeePaymentGridToolBarUiBinder.class);

	@UiField
	DateBox dtFrom;

	@UiField
	DateBox dtTo;

	@UiField
	TextBox txtInvoiceNumber;

	@UiField
	Button btnRefresh;

	@UiField
	Button btnClear;

	@UiField
	Button btnBank;

	interface EzeePaymentGridToolBarUiBinder extends UiBinder<Widget, EzeePaymentGridToolBar> {
	}

	public EzeePaymentGridToolBar(final EzeePaymentGrid grid) {
		super(grid);
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	@Override
	public void init() {
		KeyPressHandler filterHandler = new EzeeToolbarKeyPressHandler();
		txtInvoiceNumber.addKeyPressHandler(filterHandler);
		ClickHandler refreshHandler = new EzeeToolbarRefreshHandler();
		btnRefresh.addClickHandler(refreshHandler);
		ClickHandler clearHandler = new EzeeToolbarClearHandler();
		btnClear.addClickHandler(clearHandler);
		dtFrom.setFormat(EzeeFromatUtils.getDateBoxFormat());
		dtTo.setFormat(EzeeFromatUtils.getDateBoxFormat());
	}

	public String getInvoiceNumber() {
		if (hasLength(txtInvoiceNumber.getText())) {
			return txtInvoiceNumber.getText();
		}
		return EMPTY_STRING;
	}

	public Date getFrom() {
		return dtFrom.getValue();
	}

	public Date getTo() {
		return dtTo.getValue();
	}

	@Override
	public void clearToolbar() {
		txtInvoiceNumber.setText(EMPTY_STRING);
		dtFrom.setValue(null);
		dtTo.setValue(null);
	}

	@Override
	public EzeeEntityFilter<EzeePayment> resolveFilter() {
		return new EzeePaymentFilter(getInvoiceNumber(), getFrom(), getTo());
	}

	@UiHandler("btnBank")
	void onBankClick(ClickEvent event) {
		EzeeBankBalance bank = new EzeeBankBalance();
		bank.center();
	}
}