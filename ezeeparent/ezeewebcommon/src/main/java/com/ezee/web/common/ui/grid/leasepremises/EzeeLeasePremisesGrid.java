package com.ezee.web.common.ui.grid.leasepremises;

import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.model.entity.lease.EzeeLeasePremises;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.leasepremises.EzeeCreateUpdateDeleteLeasePremises;
import com.ezee.web.common.ui.grid.payee.EzeePayeeGrid;

public class EzeeLeasePremisesGrid extends EzeePayeeGrid<EzeeLeasePremises> {

	public EzeeLeasePremisesGrid(final EzeeEntityCache cache, final String[] crudHeaders) {
		super(cache, crudHeaders);
	}

	@Override
	protected void createToolbar() {
		toolBar = new EzeeLeasePremisesToolbar(this);
		filterpanel.add(toolBar);
	}

	@Override
	public void deleteEntity() {
		EzeeLeasePremises entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteLeasePremises(cache, this, entity, delete, crudHeaders).show();
		}
	}

	@Override
	public void newEntity() {
		new EzeeCreateUpdateDeleteLeasePremises(cache, this, crudHeaders).show();
	}

	@Override
	public void editEntity() {
		EzeeLeasePremises entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteLeasePremises(cache, this, entity, update, crudHeaders).show();
		}
	}
	
	@Override
	public void viewEntity() {
		// TODO Implement read-only functionality
	}

	@Override
	public String getGridClass() {
		return EzeeLeasePremises.class.getName();
	}
}