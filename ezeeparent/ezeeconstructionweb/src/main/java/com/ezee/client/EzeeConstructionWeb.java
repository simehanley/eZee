package com.ezee.client;

import static com.ezee.client.EzeeConstructionWebConstants.CONTRACTORS;
import static com.ezee.client.EzeeConstructionWebConstants.CONTRACTOR_CRUD_HEADERS;
import static com.ezee.client.EzeeConstructionWebConstants.LOGIN_USER;
import static com.ezee.client.EzeeConstructionWebConstants.PROJECTS;
import static com.ezee.client.EzeeConstructionWebConstants.PROJECT_CRUD_HEADERS;
import static com.ezee.client.EzeeConstructionWebConstants.REGISTER_USER;
import static java.util.logging.Level.SEVERE;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.css.EzeeProjectResources;
import com.ezee.client.grid.project.EzeeProjectGrid;
import com.ezee.client.main.EzeeConstructionMain;
import com.ezee.model.entity.EzeeContractor;
import com.ezee.web.common.ui.entrypoint.EzeeWebEntryPoint;
import com.ezee.web.common.ui.grid.contractor.EzeeContractorGrid;
import com.ezee.web.common.ui.main.EzeeWebMain;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class EzeeConstructionWeb extends EzeeWebEntryPoint {

	private static final Logger log = Logger.getLogger("EzeeConstructionWeb");

	public EzeeConstructionWeb() {
		super(LOGIN_USER, REGISTER_USER);
	}

	protected void initApplication() {
		initCache();
	}

	private void initMain() {
		log.log(Level.INFO, "Initialising application.");
		EzeeWebMain main = new EzeeConstructionMain(user, cache);
		EzeeProjectGrid projectGrid = new EzeeProjectGrid(main, cache, PROJECT_CRUD_HEADERS);
		main.getTab().add(projectGrid, PROJECTS);
		main.getTab().add(createContractorGrid(), CONTRACTORS);
		RootLayoutPanel.get().add(main);
		log.log(Level.INFO, "Application initialised.");
	}

	private EzeeContractorGrid createContractorGrid() {
		return new EzeeContractorGrid(cache, CONTRACTOR_CRUD_HEADERS);
	}

	@Override
	protected void initResources() {
		super.initResources();
		EzeeProjectResources.INSTANCE.css().ensureInjected();
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

	@Override
	public List<Class<?>> resolveCacheEntityTypes() {
		List<Class<?>> types = new ArrayList<>();
		types.add(EzeeContractor.class);
		return types;
	}
}