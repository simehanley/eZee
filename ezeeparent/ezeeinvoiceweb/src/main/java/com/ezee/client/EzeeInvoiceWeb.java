package com.ezee.client;

import static com.ezee.client.EzeeInvoiceWebConstants.INVOICES;
import static com.ezee.client.EzeeInvoiceWebConstants.PAYMENTS;
import static com.ezee.client.EzeeInvoiceWebConstants.PREMISES;
import static com.ezee.client.EzeeInvoiceWebConstants.SUPPLIERS;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.css.EzeeInvoiceDefaultResources;
import com.ezee.client.css.EzeeInvoiceGwtOverridesResources;
import com.ezee.client.grid.invoice.EzeeInvoiceGrid;
import com.ezee.client.grid.payee.EzeePayeeGrid;
import com.ezee.client.grid.payer.EzeePayerGrid;
import com.ezee.client.grid.payment.EzeePaymentGrid;
import com.ezee.client.main.EzeeInvoiceMain;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayee;
import com.ezee.model.entity.EzeePayer;
import com.ezee.model.entity.EzeePayment;
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

	private EzeeInvoiceEntityCache cache;

	public void onModuleLoad() {
		init();
	}

	private void init() {
		initResources();
		initCache();
		initMain();
	}

	private void initCache() {
		cache = new EzeeInvoiceEntityCache(service);
	}

	private void initResources() {
		EzeeInvoiceDefaultResources.INSTANCE.css().ensureInjected();
		EzeeInvoiceGwtOverridesResources.INSTANCE.dataGridStyle().ensureInjected();
	}

	private void initMain() {
		log.log(Level.INFO, "Initialising application.");
		EzeePayeeGrid payee = new EzeePayeeGrid(service, cache);
		EzeePayerGrid payer = new EzeePayerGrid(service, cache);
		EzeeInvoiceGrid invoice = new EzeeInvoiceGrid(service, cache);
		EzeePaymentGrid payment = new EzeePaymentGrid(service, cache);
		EzeeInvoiceMain main = new EzeeInvoiceMain(service, cache);
		main.addHandler(EzeePayee.class, payee);
		main.addHandler(EzeePayer.class, payer);
		main.addHandler(EzeeInvoice.class, invoice);
		main.addHandler(EzeePayment.class, payment);
		main.getTab().add(invoice, INVOICES);
		main.getTab().add(payment, PAYMENTS);
		main.getTab().add(payee, SUPPLIERS);
		main.getTab().add(payer, PREMISES);
		RootLayoutPanel.get().add(main);
		log.log(Level.INFO, "Application initialised.");
	}
}