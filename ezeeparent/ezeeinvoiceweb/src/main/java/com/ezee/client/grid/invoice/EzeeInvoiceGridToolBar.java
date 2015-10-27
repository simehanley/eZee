package com.ezee.client.grid.invoice;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.string.EzeeStringUtils.hasLength;

import com.ezee.client.grid.EzeeGridToolbar;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.filter.EzeeEntityFilter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EzeeInvoiceGridToolBar extends EzeeGridToolbar<EzeeInvoice> {

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

	public EzeeInvoiceGridToolBar(final EzeeInvoiceGrid grid) {
		super(grid);
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	@Override
	public void init() {
		KeyPressHandler filterHandler = new EzeeToolbarKeyPressHandler();
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
		txtInvoiceNumber.setText(EMPTY_STRING);
		txtSupplier.setText(EMPTY_STRING);
		txtPremises.setText(EMPTY_STRING);
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
		return new EzeeInvoiceFilter(getInvoiceNumber(), getSupplier(), getPremises(), getShowPaid());
	}
}