package com.ezee.client.grid.invoice;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.EzeeWebCommonConstants.REPORT_SERVICE;
import static com.ezee.web.common.EzeeWebCommonConstants.REPORT_TYPE;
import static com.ezee.web.common.enums.EzeeReportType.detailed_payee_invoice_excel;

import java.util.Date;

import com.ezee.client.grid.EzeeGridToolbar;
import com.ezee.common.web.EzeeFromatUtils;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.filter.EzeeEntityFilter;
import com.ezee.model.entity.filter.invoice.EzeeInvoiceFilter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class EzeeInvoiceGridToolBar extends EzeeGridToolbar<EzeeInvoice> {

	private static EzeeInvoiceGridToolBarUiBinder uiBinder = GWT.create(EzeeInvoiceGridToolBarUiBinder.class);

	@UiField
	DateBox dtFrom;

	@UiField
	DateBox dtTo;

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
	Button btnReport;

	@UiField
	CheckBox chkShowPaid;

	interface EzeeInvoiceGridToolBarUiBinder extends UiBinder<Widget, EzeeInvoiceGridToolBar> {
	}

	public EzeeInvoiceGridToolBar(final EzeeInvoiceGrid grid) {
		super(grid);
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	@Override
	public void init() {
		KeyPressHandler filterHandler = new EzeeToolbarKeyPressHandler();
		dtFrom.setFormat(EzeeFromatUtils.getDateBoxFormat());
		dtTo.setFormat(EzeeFromatUtils.getDateBoxFormat());
		dtFrom.addDomHandler(filterHandler, KeyPressEvent.getType());
		dtTo.addDomHandler(filterHandler, KeyPressEvent.getType());
		txtInvoiceNumber.addKeyPressHandler(filterHandler);
		txtSupplier.addKeyPressHandler(filterHandler);
		txtPremises.addKeyPressHandler(filterHandler);
		ClickHandler refreshHandler = new EzeeToolbarRefreshHandler();
		btnRefresh.addClickHandler(refreshHandler);
		ClickHandler clearHandler = new EzeeToolbarClearHandler();
		btnClear.addClickHandler(clearHandler);
		ValueChangeHandler<Boolean> valueChangeHandler = new EzeeToolbarValueChangeHandler();
		chkShowPaid.addValueChangeHandler(valueChangeHandler);
	}

	@Override
	public void clearToolbar() {
		dtFrom.setValue(null);
		dtTo.setValue(null);
		txtInvoiceNumber.setText(EMPTY_STRING);
		txtSupplier.setText(EMPTY_STRING);
		txtPremises.setText(EMPTY_STRING);
	}

	public Date getFrom() {
		return dtFrom.getValue();
	}

	public Date getTo() {
		return dtTo.getValue();
	}

	public String getInvoiceNumber() {
		if (hasLength(txtInvoiceNumber.getText())) {
			return txtInvoiceNumber.getText();
		}
		return EMPTY_STRING;
	}

	public String getSupplier() {
		if (hasLength(txtSupplier.getText())) {
			return txtSupplier.getText();
		}
		return EMPTY_STRING;
	}

	public String getPremises() {
		if (hasLength(txtPremises.getText())) {
			return txtPremises.getText();
		}
		return EMPTY_STRING;
	}

	public boolean getShowPaid() {
		return chkShowPaid.getValue();
	}

	@Override
	public EzeeEntityFilter<EzeeInvoice> resolveFilter() {
		return new EzeeInvoiceFilter(getSupplier(), getPremises(), getInvoiceNumber(), getFrom(), getTo(), DATE_UTILS,
				getShowPaid());
	}

	@UiHandler("btnReport")
	void onReportClick(ClickEvent event) {
		String reportServiceUrl = GWT.getModuleBaseURL() + REPORT_SERVICE + "?" + REPORT_TYPE + "="
				+ detailed_payee_invoice_excel;
		Window.Location.replace(reportServiceUrl);
	}
}