package com.ezee.client.main;

import com.ezee.model.entity.EzeeUser;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.main.EzeeWebMain;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class EzeeLeaseMain extends EzeeWebMain {

	private static EzeeLeaseMainUiBinder uiBinder = GWT.create(EzeeLeaseMainUiBinder.class);
	
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

	interface EzeeLeaseMainUiBinder extends UiBinder<Widget, EzeeLeaseMain> {
	}

	public EzeeLeaseMain(final EzeeUser user, final EzeeEntityCache cache) {
		super(user, cache);
		initWidget(uiBinder.createAndBindUi(this));
		initUser();
		initMain();
	}

	@Override
	protected void initMain() {
		super.initMain();
//		ClickHandler mainClickHandler = new EzeeInvoiceMainStackPanelClickHandler();
//		newInvoice.addClickHandler(mainClickHandler);
//		editInvoice.addClickHandler(mainClickHandler);
//		deleteInvoice.addClickHandler(mainClickHandler);
//		makePayment.addClickHandler(mainClickHandler);
//		newSupplier.addClickHandler(mainClickHandler);
//		editSupplier.addClickHandler(mainClickHandler);
//		deleteSupplier.addClickHandler(mainClickHandler);
//		raiseSupplierInvoice.addClickHandler(mainClickHandler);
//		editPayment.addClickHandler(mainClickHandler);
//		deletePayment.addClickHandler(mainClickHandler);
//		newPremises.addClickHandler(mainClickHandler);
//		editPremises.addClickHandler(mainClickHandler);
//		deletePremises.addClickHandler(mainClickHandler);
//		editUser.addClickHandler(mainClickHandler);
//		tab.addDomHandler(mainClickHandler, ClickEvent.getType());
	}

//	private class EzeeInvoiceMainStackPanelClickHandler implements ClickHandler {
//
//		@Override
//		public void onClick(ClickEvent event) {
//			if (event.getSource().equals(newInvoice)) {
//				getFirstInstanceOf(EzeeInvoiceGrid.class, tab).newEntity();
//			} else if (event.getSource().equals(editInvoice)) {
//				getFirstInstanceOf(EzeeInvoiceGrid.class, tab).editEntity();
//			} else if (event.getSource().equals(deleteInvoice)) {
//				getFirstInstanceOf(EzeeInvoiceGrid.class, tab).deleteEntity();
//			} else if (event.getSource().equals(makePayment)) {
//				getFirstInstanceOf(EzeeInvoiceGrid.class, tab).newPayment();
//			} else if (event.getSource().equals(newSupplier)) {
//				getFirstInstanceOf(EzeeInvoiceSupplierGrid.class, tab).newEntity();
//			} else if (event.getSource().equals(editSupplier)) {
//				getFirstInstanceOf(EzeeInvoiceSupplierGrid.class, tab).editEntity();
//			} else if (event.getSource().equals(deleteSupplier)) {
//				getFirstInstanceOf(EzeeInvoiceSupplierGrid.class, tab).deleteEntity();
//			} else if (event.getSource().equals(raiseSupplierInvoice)) {
//				getFirstInstanceOf(EzeeInvoiceSupplierGrid.class, tab).newSupplierInvoice();
//			} else if (event.getSource().equals(newPremises)) {
//				getFirstInstanceOf(EzeePayerGrid.class, tab).newEntity();
//			} else if (event.getSource().equals(editPremises)) {
//				getFirstInstanceOf(EzeePayerGrid.class, tab).editEntity();
//			} else if (event.getSource().equals(deletePremises)) {
//				getFirstInstanceOf(EzeePayerGrid.class, tab).deleteEntity();
//			} else if (event.getSource().equals(editUser)) {
//				editUser();
//			} else if (event.getSource().equals(editPayment)) {
//				getFirstInstanceOf(EzeePaymentGrid.class, tab).editEntity();
//			} else if (event.getSource().equals(deletePayment)) {
//				getFirstInstanceOf(EzeePaymentGrid.class, tab).deleteEntity();
//			} else if (event.getSource().equals(tab)) {
//				int tabId = tab.getSelectedIndex();
//				menu.showStack(tabId);
//			}
//		}
//	}
}