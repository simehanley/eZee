package com.ezee.client;

import static com.ezee.client.EzeeConstructionWebConstants.LOGIN_USER;
import static com.ezee.client.EzeeConstructionWebConstants.REGISTER_USER;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.main.EzeeConstructionMain;
import com.ezee.web.common.ui.entrypoint.EzeeWebEntryPoint;
import com.ezee.web.common.ui.main.EzeeWebMain;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class EzeeConstructionWeb extends EzeeWebEntryPoint {

	private static final Logger log = Logger.getLogger("EzeeConstructionWeb");

	public EzeeConstructionWeb() {
		super(LOGIN_USER, REGISTER_USER);
	}

	protected void initApplication() {
		initMain();
	}

	private void initMain() {
		log.log(Level.INFO, "Initialising application.");
		EzeeWebMain main = new EzeeConstructionMain(user);
		RootLayoutPanel.get().add(main);
		log.log(Level.INFO, "Application initialised.");
	}
}