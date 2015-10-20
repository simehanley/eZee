package com.ezee.client.main;

import java.util.HashMap;
import java.util.Map;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.client.crud.invoice.EzeeCreateUpdateDeleteInvoice;
import com.ezee.client.crud.payee.EzeeCreateUpdateDeletePayee;
import com.ezee.client.crud.payer.EzeeCreateUpdateDeletePayer;
import com.ezee.client.grid.EzeeHasGrid;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayee;
import com.ezee.model.entity.EzeePayer;
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

	private final EzeeInvoiceEntityCache cache;

	private final Map<Class<?>, EzeeCreateUpdateDeleteEntityHandler<?>> handlers = new HashMap<>();

	@UiField
	TabLayoutPanel tab;

	@UiField
	Label newpremises;

	@UiField
	Label newsupplier;

	@UiField
	Label newinvoice;

	interface EzeeInvoiceMainUiBinder extends UiBinder<Widget, EzeeInvoiceMain> {
	}

	public EzeeInvoiceMain(final EzeeInvoiceServiceAsync service, final EzeeInvoiceEntityCache cache) {
		this.service = service;
		this.cache = cache;
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

	public void addHandler(Class<?> clazz, EzeeCreateUpdateDeleteEntityHandler<?> handler) {
		handlers.put(clazz, handler);
	}

	@SuppressWarnings("unchecked")
	@UiHandler("newpremises")
	void onNewPremisesClick(final ClickEvent event) {
		new EzeeCreateUpdateDeletePayer(service, cache,
				(EzeeCreateUpdateDeleteEntityHandler<EzeePayer>) handlers.get(EzeePayer.class)).center();
	}

	@SuppressWarnings("unchecked")
	@UiHandler("newsupplier")
	void onNewSupplierClick(final ClickEvent event) {
		new EzeeCreateUpdateDeletePayee(service, cache,
				(EzeeCreateUpdateDeleteEntityHandler<EzeePayee>) handlers.get(EzeePayee.class)).center();
	}

	@SuppressWarnings("unchecked")
	@UiHandler("newinvoice")
	void onNewInvoiceClick(final ClickEvent event) {
		new EzeeCreateUpdateDeleteInvoice(service, cache,
				(EzeeCreateUpdateDeleteEntityHandler<EzeeInvoice>) handlers.get(EzeeInvoice.class)).center();
	}
}