package com.ezee.client.main;

import static com.ezee.client.EzeeInvoiceWebConstants.INVOICE_SERVICE;
import static com.ezee.client.EzeeInvoiceWebConstants.SUPPORT_EMAIL;
import static com.ezee.web.common.EzeeWebCommonConstants.AUTO_LOGIN_HELPER;
import static com.ezee.web.common.ui.utils.EzeeTabLayoutPanelUtils.getFirstInstanceOf;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.grid.EzeeHasGrid;
import com.ezee.client.grid.invoice.EzeeInvoiceGrid;
import com.ezee.client.grid.payee.EzeePayeeGrid;
import com.ezee.client.grid.payer.EzeePayerGrid;
import com.ezee.model.entity.EzeeUser;
import com.ezee.web.common.ui.edit.EzeeEditUser;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class EzeeInvoiceMain extends Composite {

	private static final Logger log = Logger.getLogger("EzeeInvoiceMain");

	private static EzeeInvoiceMainUiBinder uiBinder = GWT.create(EzeeInvoiceMainUiBinder.class);

	@UiField
	HTML user;

	@UiField
	HTML logout;

	@UiField
	HTML version;

	@UiField
	HTML email;

	@UiField
	TabLayoutPanel tab;

	@UiField
	HTML newInvoice;

	@UiField
	HTML editInvoice;

	@UiField
	HTML deleteInvoice;

	@UiField
	HTML makePayment;

	@UiField
	HTML newSupplier;

	@UiField
	HTML editSupplier;

	@UiField
	HTML deleteSupplier;

	@UiField
	HTML newPremises;

	@UiField
	HTML editPremises;

	@UiField
	HTML deletePremises;

	@UiField
	HTML editUser;

	private final EzeeUser ezeeUser;

	interface EzeeInvoiceMainUiBinder extends UiBinder<Widget, EzeeInvoiceMain> {
	}

	public EzeeInvoiceMain(final EzeeUser user) {
		this.ezeeUser = user;
		initWidget(uiBinder.createAndBindUi(this));
		initUser();
		initMain();
		addTabHandler();
	}

	public final TabLayoutPanel getTab() {
		return tab;
	}

	private void addTabHandler() {
		tab.addSelectionHandler(new SelectionHandler<Integer>() {
			public void onSelection(SelectionEvent<Integer> event) {
				int tabId = event.getSelectedItem();
				EzeeHasGrid<?> grid = (EzeeHasGrid<?>) tab.getWidget(tabId);
				grid.getGrid().redraw();
			}
		});
	}

	private void initUser() {
		user.setText("Logged in as : " + ezeeUser.getUsername());
	}

	private void initMain() {
		INVOICE_SERVICE.getVersion(new AsyncCallback<String>() {
			@Override
			public void onSuccess(final String result) {
				version.setText("Version : " + result);
			}

			@Override
			public void onFailure(Throwable caught) {
				log.log(Level.SEVERE, "Unable to resolve software version.", caught);

			}
		});
		email.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.assign(SUPPORT_EMAIL);
			}
		});
		logout.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AUTO_LOGIN_HELPER.unsetRememberMeUser();
				Window.Location.assign(GWT.getHostPageBaseURL());
			}
		});

		ClickHandler mainClickHandler = new EzeeInvoiceMainStackPanelClickHandler();
		newInvoice.addClickHandler(mainClickHandler);
		editInvoice.addClickHandler(mainClickHandler);
		deleteInvoice.addClickHandler(mainClickHandler);
		makePayment.addClickHandler(mainClickHandler);
		newSupplier.addClickHandler(mainClickHandler);
		editSupplier.addClickHandler(mainClickHandler);
		deleteSupplier.addClickHandler(mainClickHandler);
		newPremises.addClickHandler(mainClickHandler);
		editPremises.addClickHandler(mainClickHandler);
		deletePremises.addClickHandler(mainClickHandler);
		editUser.addClickHandler(mainClickHandler);
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
				getFirstInstanceOf(EzeePayeeGrid.class, tab).newEntity();
			} else if (event.getSource().equals(editSupplier)) {
				getFirstInstanceOf(EzeePayeeGrid.class, tab).editEntity();
			} else if (event.getSource().equals(deleteSupplier)) {
				getFirstInstanceOf(EzeePayeeGrid.class, tab).deleteEntity();
			} else if (event.getSource().equals(newPremises)) {
				getFirstInstanceOf(EzeePayerGrid.class, tab).newEntity();
			} else if (event.getSource().equals(editPremises)) {
				getFirstInstanceOf(EzeePayerGrid.class, tab).editEntity();
			} else if (event.getSource().equals(deletePremises)) {
				getFirstInstanceOf(EzeePayerGrid.class, tab).deleteEntity();
			} else if (event.getSource().equals(editUser)) {
				editUser();
			}
		}
	}

	private void editUser() {
		new EzeeEditUser(ezeeUser.getUsername()).center();
	}
}