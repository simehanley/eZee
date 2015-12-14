package com.ezee.client.main;

import static com.ezee.common.web.EzeeFormatUtils.getFullDateTimeFormat;
import static com.ezee.web.common.ui.utils.EzeeTabLayoutPanelUtils.getFirstInstanceOf;

import java.util.Date;

import com.ezee.client.grid.invoice.EzeeInvoiceGrid;
import com.ezee.client.grid.payee.EzeeInvoicePayeeGrid;
import com.ezee.client.grid.payment.EzeePaymentGrid;
import com.ezee.model.entity.EzeeUser;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.grid.payer.EzeePayerGrid;
import com.ezee.web.common.ui.main.EzeeWebMain;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class EzeeInvoiceMain extends EzeeWebMain {

	private static EzeeInvoiceMainUiBinder uiBinder = GWT.create(EzeeInvoiceMainUiBinder.class);

	@UiField
	HTML newInvoice;

	@UiField
	HTML editInvoice;

	@UiField
	HTML deleteInvoice;

	@UiField
	HTML editPayment;

	@UiField
	HTML deletePayment;

	@UiField
	HTML makePayment;

	@UiField
	HTML newSupplier;

	@UiField
	HTML editSupplier;

	@UiField
	HTML deleteSupplier;

	@UiField
	HTML raiseSupplierInvoice;

	@UiField
	HTML newPremises;

	@UiField
	HTML editPremises;

	@UiField
	HTML deletePremises;

	interface EzeeInvoiceMainUiBinder extends UiBinder<Widget, EzeeInvoiceMain> {
	}

	public EzeeInvoiceMain(final EzeeUser user, final EzeeEntityCache cache) {
		super(user, cache);
		initWidget(uiBinder.createAndBindUi(this));
		initUser();
		initMain();

	}

	@Override
	protected void initMain() {
		super.initMain();
		ClickHandler mainClickHandler = new EzeeInvoiceMainStackPanelClickHandler();
		newInvoice.addClickHandler(mainClickHandler);
		editInvoice.addClickHandler(mainClickHandler);
		deleteInvoice.addClickHandler(mainClickHandler);
		makePayment.addClickHandler(mainClickHandler);
		newSupplier.addClickHandler(mainClickHandler);
		editSupplier.addClickHandler(mainClickHandler);
		deleteSupplier.addClickHandler(mainClickHandler);
		raiseSupplierInvoice.addClickHandler(mainClickHandler);
		editPayment.addClickHandler(mainClickHandler);
		deletePayment.addClickHandler(mainClickHandler);
		newPremises.addClickHandler(mainClickHandler);
		editPremises.addClickHandler(mainClickHandler);
		deletePremises.addClickHandler(mainClickHandler);
		editUser.addClickHandler(mainClickHandler);
		tab.addDomHandler(mainClickHandler, ClickEvent.getType());
	}

	private class EzeeInvoiceMainStackPanelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource().equals(newInvoice)) {
				getFirstInstanceOf(EzeeInvoiceGrid.class, tab).newEntity();
			} else if (event.getSource().equals(editInvoice)) {
				getFirstInstanceOf(EzeeInvoiceGrid.class, tab).editEntity();
			} else if (event.getSource().equals(deleteInvoice)) {
				getFirstInstanceOf(EzeeInvoiceGrid.class, tab).deleteEntity();
			} else if (event.getSource().equals(makePayment)) {
				getFirstInstanceOf(EzeeInvoiceGrid.class, tab).newPayment();
			} else if (event.getSource().equals(newSupplier)) {
				getFirstInstanceOf(EzeeInvoicePayeeGrid.class, tab).newEntity();
			} else if (event.getSource().equals(editSupplier)) {
				getFirstInstanceOf(EzeeInvoicePayeeGrid.class, tab).editEntity();
			} else if (event.getSource().equals(deleteSupplier)) {
				getFirstInstanceOf(EzeeInvoicePayeeGrid.class, tab).deleteEntity();
			} else if (event.getSource().equals(raiseSupplierInvoice)) {
				getFirstInstanceOf(EzeeInvoicePayeeGrid.class, tab).newSupplierInvoice();
			} else if (event.getSource().equals(newPremises)) {
				getFirstInstanceOf(EzeePayerGrid.class, tab).newEntity();
			} else if (event.getSource().equals(editPremises)) {
				getFirstInstanceOf(EzeePayerGrid.class, tab).editEntity();
			} else if (event.getSource().equals(deletePremises)) {
				getFirstInstanceOf(EzeePayerGrid.class, tab).deleteEntity();
			} else if (event.getSource().equals(editUser)) {
				editUser();
			} else if (event.getSource().equals(editPayment)) {
				getFirstInstanceOf(EzeePaymentGrid.class, tab).editEntity();
			} else if (event.getSource().equals(deletePayment)) {
				getFirstInstanceOf(EzeePaymentGrid.class, tab).deleteEntity();
			} else if (event.getSource().equals(tab)) {
				int tabId = tab.getSelectedIndex();
				menu.showStack(tabId);
			}
		}
	}
}