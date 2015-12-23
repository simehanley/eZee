package com.ezee.client.grid.invoice;

import static com.ezee.client.EzeeInvoiceWebConstants.SHOW_PAID_INVOICES;
import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.EzeeWebCommonConstants.EXCEL_INVOICE_DATE_FROM_FILTER;
import static com.ezee.web.common.EzeeWebCommonConstants.EXCEL_INVOICE_DATE_TO_FILTER;
import static com.ezee.web.common.EzeeWebCommonConstants.EXCEL_INVOICE_INCLUDE_PAID_FILTER;
import static com.ezee.web.common.EzeeWebCommonConstants.EXCEL_INVOICE_INVOICES_FILTER;
import static com.ezee.web.common.EzeeWebCommonConstants.EXCEL_INVOICE_PREMISES_FILTER;
import static com.ezee.web.common.EzeeWebCommonConstants.EXCEL_INVOICE_SUPPLIER_FILTER;
import static com.ezee.web.common.EzeeWebCommonConstants.LOCAL_STORAGE;
import static com.ezee.web.common.EzeeWebCommonConstants.REPORT_SERVICE;
import static com.ezee.web.common.EzeeWebCommonConstants.REPORT_TYPE;
import static com.ezee.web.common.enums.EzeeReportType.detailed_payee_invoice_excel;

import java.util.Date;

import com.ezee.common.web.EzeeFormatUtils;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.filter.EzeeEntityFilter;
import com.ezee.model.entity.filter.invoice.EzeeInvoiceFilter;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.localstorage.EzeeLocalStroage;
import com.ezee.web.common.ui.grid.EzeeGridToolbar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
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

	private final EzeeEntityCache cache;

	interface EzeeInvoiceGridToolBarUiBinder extends UiBinder<Widget, EzeeInvoiceGridToolBar> {
	}

	public EzeeInvoiceGridToolBar(final EzeeInvoiceGrid grid, final EzeeEntityCache cache) {
		super(grid);
		this.cache = cache;
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	@Override
	public void init() {
		KeyPressHandler filterHandler = new EzeeToolbarKeyPressHandler();
		dtFrom.setFormat(EzeeFormatUtils.getDateBoxFormat());
		dtTo.setFormat(EzeeFormatUtils.getDateBoxFormat());
		dtFrom.addDomHandler(filterHandler, KeyPressEvent.getType());
		dtTo.addDomHandler(filterHandler, KeyPressEvent.getType());
		txtInvoiceNumber.addKeyPressHandler(filterHandler);
		txtSupplier.addKeyPressHandler(filterHandler);
		txtPremises.addKeyPressHandler(filterHandler);
		ClickHandler refreshHandler = new EzeeToolbarRefreshHandler();
		btnRefresh.addClickHandler(refreshHandler);
		ClickHandler clearHandler = new EzeeToolbarClearHandler();
		btnClear.addClickHandler(clearHandler);
		ValueChangeHandler<Boolean> valueChangeHandler = new EzeeShowPaidValueChangeHandler();
		chkShowPaid.addValueChangeHandler(valueChangeHandler);
		setShowPaid();
	}

	private void setShowPaid() {
		boolean showPaid = false;
		EzeeLocalStroage localStroage = LOCAL_STORAGE;
		if (localStroage.isSupported() && localStroage.isSet(SHOW_PAID_INVOICES)) {
			showPaid = Boolean.valueOf(localStroage.getValue(SHOW_PAID_INVOICES));
		} else {
			if (cache.getConfiguration() != null) {
				showPaid = cache.getConfiguration().getDefaultShowPaid();
			}
		}
		chkShowPaid.setValue(showPaid);
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
		String reportServiceUrl = GWT.getModuleBaseURL() + resolveReportParamString();
		Window.Location.assign(reportServiceUrl);
	}

	private String resolveReportParamString() {
		StringBuilder builder = new StringBuilder();
		builder.append(REPORT_SERVICE + "?" + REPORT_TYPE + "=" + detailed_payee_invoice_excel);
		builder.append("&" + EXCEL_INVOICE_SUPPLIER_FILTER + "=" + getSupplier());
		builder.append("&" + EXCEL_INVOICE_PREMISES_FILTER + "=" + getPremises());
		builder.append("&" + EXCEL_INVOICE_INVOICES_FILTER + "=" + getInvoiceNumber());
		if (getFrom() != null) {
			builder.append("&" + EXCEL_INVOICE_DATE_FROM_FILTER + "=" + DATE_UTILS.toString(getFrom()));
		}
		if (getTo() != null) {
			builder.append("&" + EXCEL_INVOICE_DATE_TO_FILTER + "=" + DATE_UTILS.toString(getTo()));
		}
		builder.append("&" + EXCEL_INVOICE_INCLUDE_PAID_FILTER + "=" + Boolean.toString(getShowPaid()));
		return builder.toString();
	}

	private class EzeeShowPaidValueChangeHandler extends EzeeToolbarValueChangeHandler {

		@Override
		public void onValueChange(ValueChangeEvent<Boolean> event) {
			super.onValueChange(event);
			LOCAL_STORAGE.setValue(SHOW_PAID_INVOICES, Boolean.toString(event.getValue()));
		}
	}
}