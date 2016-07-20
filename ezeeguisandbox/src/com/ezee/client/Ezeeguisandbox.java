package com.ezee.client;

import src.com.ezee.client.css.EzeeInvoiceDefaultResources;
import src.com.ezee.client.main.EzeeInvoiceMain;

public class Ezeeguisandbox implements EntryPoint {

	private static final Logger log = Logger.getLogger("EzeeInvoiceWeb");

	public void onModuleLoad() {
		init();
	}

	private void init() {
		initResources();
		initMain();
	}

	private void initResources() {
		EzeeInvoiceDefaultResources.INSTANCE.css().ensureInjected();
	}

	private void initMain() {
		log.log(Level.INFO, "Initialising application.");
		EzeeInvoiceMain main = new EzeeInvoiceMain();
		RootLayoutPanel.get().add(main);
		log.log(Level.INFO, "Application initialised.");
	}
}
