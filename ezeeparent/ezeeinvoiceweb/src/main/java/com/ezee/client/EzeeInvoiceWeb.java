package com.ezee.client;

import static com.ezee.client.EzeeInvoiceWebConstants.INVOICES;
import static com.ezee.client.EzeeInvoiceWebConstants.PAYMENTS;
import static com.ezee.client.EzeeInvoiceWebConstants.PREMISES;
import static com.ezee.client.EzeeInvoiceWebConstants.SUPPLIERS;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.css.EzeeInvoiceDefaultResources;
import com.ezee.client.css.EzeeInvoiceGwtOverridesResources;
import com.ezee.client.grid.invoice.EzeeInvoiceGrid;
import com.ezee.client.grid.payee.EzeePayeeGrid;
import com.ezee.client.grid.payer.EzeePayerGrid;
import com.ezee.client.grid.payment.EzeePaymentGrid;
import com.ezee.client.main.EzeeInvoiceMain;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceWeb implements EntryPoint {

	private static final Logger log = Logger.getLogger("EzeeInvoiceWeb");

	private final EzeeInvoiceServiceAsync service = GWT.create(EzeeInvoiceService.class);

	public void onModuleLoad() {
		init();
	}

	private void init() {
		initResources();
		initMain();
	}

	private void initResources() {
		EzeeInvoiceDefaultResources.INSTANCE.css().ensureInjected();
		EzeeInvoiceGwtOverridesResources.INSTANCE.dataGridStyle().ensureInjected();
	}

	private void initMain() {
		log.log(Level.INFO, "Initialising application.");
		EzeeInvoiceMain main = new EzeeInvoiceMain(service);
		main.getTab().add(new EzeeInvoiceGrid(service), INVOICES);
		main.getTab().add(new EzeePaymentGrid(service), PAYMENTS);
		main.getTab().add(new EzeePayeeGrid(service), SUPPLIERS);
		main.getTab().add(new EzeePayerGrid(service), PREMISES);
		RootLayoutPanel.get().add(main);
		log.log(Level.INFO, "Application initialised.");
	}
}