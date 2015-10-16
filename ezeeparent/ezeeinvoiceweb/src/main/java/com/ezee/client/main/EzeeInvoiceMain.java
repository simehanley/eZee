package com.ezee.client.main;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.crud.payee.EzeeCreateUpdateDeletePayee;
import com.ezee.client.crud.payer.EzeeCreateUpdateDeletePayer;
import com.ezee.client.grid.EzeeHasGrid;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class EzeeInvoiceMain extends Composite {

	private static EzeeInvoiceMainUiBinder uiBinder = GWT.create(EzeeInvoiceMainUiBinder.class);

	private final EzeeInvoiceServiceAsync service;

	@UiField
	TabLayoutPanel tab;

	@UiField
	Label newpremises;

	@UiField
	Label newsupplier;

	interface EzeeInvoiceMainUiBinder extends UiBinder<Widget, EzeeInvoiceMain> {
	}

	public EzeeInvoiceMain(final EzeeInvoiceServiceAsync service) {
		this.service = service;
		initWidget(uiBinder.createAndBindUi(this));
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

	@UiHandler("newpremises")
	void onNewPremisesClick(final ClickEvent event) {
		new EzeeCreateUpdateDeletePayer(service).center();
	}

	@UiHandler("newsupplier")
	void onNewSupplierClick(final ClickEvent event) {
		new EzeeCreateUpdateDeletePayee(service).center();
	}
}