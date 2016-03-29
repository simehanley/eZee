package com.ezee.client;

import static com.ezee.client.EzeeLeaseWebConstants.LEASE_PREMISES_CRUD_HEADERS;
import static com.ezee.client.EzeeLeaseWebConstants.LEASE_TENANT_CRUD_HEADERS;
import static com.ezee.client.EzeeLeaseWebConstants.LOGIN_USER;
import static com.ezee.client.EzeeLeaseWebConstants.PREMISES;
import static com.ezee.client.EzeeLeaseWebConstants.REGISTER_USER;
import static com.ezee.client.EzeeLeaseWebConstants.TENANTS;
import static java.util.logging.Level.SEVERE;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.main.EzeeLeaseMain;
import com.ezee.model.entity.lease.EzeeLeaseCategory;
import com.ezee.model.entity.lease.EzeeLeasePremises;
import com.ezee.model.entity.lease.EzeeLeaseTenant;
import com.ezee.web.common.ui.entrypoint.EzeeWebEntryPoint;
import com.ezee.web.common.ui.grid.leasepremises.EzeeLeasePremisesGrid;
import com.ezee.web.common.ui.grid.leasetenant.EzeeLeaseTenantGrid;
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
		main.getTab().add(createTenantGrid(), TENANTS);
		main.getTab().add(createPremisesGrid(), PREMISES);
		RootLayoutPanel.get().add(main);
		log.log(Level.INFO, "Application initialised.");
	}

	private EzeeLeaseTenantGrid createTenantGrid() {
		return new EzeeLeaseTenantGrid(cache, LEASE_TENANT_CRUD_HEADERS);
	}

	private EzeeLeasePremisesGrid createPremisesGrid() {
		return new EzeeLeasePremisesGrid(cache, LEASE_PREMISES_CRUD_HEADERS);
	}

	@Override
	public List<Class<?>> resolveCacheEntityTypes() {
		List<Class<?>> types = new ArrayList<>();
		types.add(EzeeLeaseTenant.class);
		types.add(EzeeLeaseCategory.class);
		types.add(EzeeLeasePremises.class);
		return types;
	}
}