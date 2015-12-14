package com.ezee.client;

import static com.ezee.client.EzeeConstructionWebConstants.LOGIN_USER;
import static com.ezee.client.EzeeConstructionWebConstants.PROJECTS;
import static com.ezee.client.EzeeConstructionWebConstants.PROJECT_CRUD_HEADERS;
import static com.ezee.client.EzeeConstructionWebConstants.REGISTER_USER;
import static com.ezee.client.EzeeConstructionWebConstants.RESOURCES;
import static com.ezee.client.EzeeConstructionWebConstants.RESOURCE_CRUD_HEADERS;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.css.EzeeProjectResources;
import com.ezee.client.grid.project.EzeeProjectGrid;
import com.ezee.client.main.EzeeConstructionMain;
import com.ezee.web.common.ui.entrypoint.EzeeWebEntryPoint;
import com.ezee.web.common.ui.grid.payee.EzeePayeeGrid;
import com.ezee.web.common.ui.main.EzeeWebMain;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class EzeeConstructionWeb extends EzeeWebEntryPoint {

	private static final Logger log = Logger.getLogger("EzeeConstructionWeb");

	public EzeeConstructionWeb() {
		super(LOGIN_USER, REGISTER_USER);
	}

	protected void initApplication() {
		initCache();
		initMain();
	}

	private void initMain() {
		log.log(Level.INFO, "Initialising application.");
		EzeeWebMain main = new EzeeConstructionMain(user, cache);
		EzeeProjectGrid projectGrid = new EzeeProjectGrid(main, cache, PROJECT_CRUD_HEADERS);
		main.getTab().add(projectGrid, PROJECTS);
		main.getTab().add(createResourceGrid(), RESOURCES);
		RootLayoutPanel.get().add(main);
		log.log(Level.INFO, "Application initialised.");
	}

	private EzeePayeeGrid createResourceGrid() {
		return new EzeePayeeGrid(cache, RESOURCE_CRUD_HEADERS);
	}

	@Override
	protected void initResources() {
		super.initResources();
		EzeeProjectResources.INSTANCE.css().ensureInjected();
	}
}