package com.ezee.client;

import java.util.logging.Level;
import java.util.logging.Logger;

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
	
	EzeeInvoiceServiceAsync service = GWT.create(EzeeInvoiceService.class);

	public void onModuleLoad() {
		init();
	}

	private void init() {
		initMain();
	}

	private void initMain() {
		log.log(Level.INFO, "Initialising application.");
		EzeeInvoiceMain main = new EzeeInvoiceMain();
		main.setService(service);
		RootLayoutPanel.get().add(main);
		log.log(Level.INFO, "Application initialised.");
	}
}