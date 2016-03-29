package com.ezee.web.common.ui.grid.leasetenant;

import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.model.entity.lease.EzeeLeaseTenant;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.leasetenant.EzeeCreateUpdateDeleteLeaseTenant;
import com.ezee.web.common.ui.grid.payer.EzeePayerGrid;

public class EzeeLeaseTenantGrid extends EzeePayerGrid<EzeeLeaseTenant> {

	public EzeeLeaseTenantGrid(final EzeeEntityCache cache, final String[] crudHeaders) {
		super(cache, crudHeaders);
	}

	@Override
	protected void createToolbar() {
		toolBar = new EzeeLeaseTenantToolbar(this);
		filterpanel.add(toolBar);
	}

	@Override
	public void deleteEntity() {
		EzeeLeaseTenant entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteLeaseTenant(cache, this, entity, delete, crudHeaders).show();
		}
	}

	@Override
	public void newEntity() {
		new EzeeCreateUpdateDeleteLeaseTenant(cache, this, crudHeaders).show();
	}

	@Override
	public void editEntity() {
		EzeeLeaseTenant entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteLeaseTenant(cache, this, entity, update, crudHeaders).show();
		}
	}

	@Override
	public String getGridClass() {
		return EzeeLeaseTenant.class.getName();
	}
}