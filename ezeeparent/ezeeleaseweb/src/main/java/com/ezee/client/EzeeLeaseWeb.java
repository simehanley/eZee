package com.ezee.client;

import static com.ezee.client.EzeeLeaseWebConstants.LOGIN_USER;
import static com.ezee.client.EzeeLeaseWebConstants.REGISTER_USER;
import static java.util.logging.Level.SEVERE;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.main.EzeeLeaseMain;
import com.ezee.web.common.ui.entrypoint.EzeeWebEntryPoint;
import com.ezee.web.common.ui.main.EzeeWebMain;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class EzeeLeaseWeb extends EzeeWebEntryPoint {

	private static final Logger log = Logger.getLogger("EzeeLeaseWeb");

	public EzeeLeaseWeb() {
		super(LOGIN_USER, REGISTER_USER);
	}

	@Override
	protected void initApplication() {
		initCache();
	}

	@Override
	public void configurationLoaded() {
		initMain();
	}

	@Override
	public void configurationLoadFailed() {
		log.log(SEVERE, "Configuration failed to load.  Will start application without defaults.");
		initMain();
	}

	private void initMain() {
		log.log(Level.INFO, "Initialising application.");
		EzeeWebMain main = new EzeeLeaseMain(user, cache);
		RootLayoutPanel.get().add(main);
		log.log(Level.INFO, "Application initialised.");
	}
}