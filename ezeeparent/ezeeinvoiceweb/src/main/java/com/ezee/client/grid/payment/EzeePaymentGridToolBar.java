package com.ezee.client.grid.payment;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;

import java.util.Date;

import com.ezee.client.bank.EzeeBankBalance;
import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.grid.EzeeGridToolbar;
import com.ezee.common.web.EzeeFromatUtils;
import com.ezee.model.entity.EzeePayment;
import com.ezee.model.entity.filter.EzeeEntityFilter;
import com.ezee.model.entity.filter.payment.EzeePaymentFilter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
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

	private final EzeeInvoiceEntityCache cache;

	interface EzeePaymentGridToolBarUiBinder extends UiBinder<Widget, EzeePaymentGridToolBar> {
	}

	public EzeePaymentGridToolBar(final EzeePaymentGrid grid, final EzeeInvoiceEntityCache cache) {
		super(grid);
		this.cache = cache;
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	@Override
	public void init() {
		KeyPressHandler filterHandler = new EzeeToolbarKeyPressHandler();
		txtInvoiceNumber.addKeyPressHandler(filterHandler);
		dtFrom.addDomHandler(filterHandler, KeyPressEvent.getType());
		dtTo.addDomHandler(filterHandler, KeyPressEvent.getType());
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
		return new EzeePaymentFilter(getInvoiceNumber(), getFrom(), getTo(), DATE_UTILS);
	}

	@UiHandler("btnBank")
	void onBankClick(ClickEvent event) {
		EzeeBankBalance bank = new EzeeBankBalance(cache);
		bank.show();
	}
}