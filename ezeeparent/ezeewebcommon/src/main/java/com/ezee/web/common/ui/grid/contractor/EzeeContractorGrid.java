package com.ezee.web.common.ui.grid.contractor;

import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.model.entity.EzeeContractor;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.contractor.EzeeCreateUpdateDeleteContractor;
import com.ezee.web.common.ui.grid.payee.EzeePayeeGrid;

public class EzeeContractorGrid extends EzeePayeeGrid<EzeeContractor> {

	public EzeeContractorGrid(EzeeEntityCache cache, String[] crudHeaders) {
		super(cache, crudHeaders);
	}

	@Override
	protected void createToolbar() {
		toolBar = new EzeeContractorToolbar(this);
		filterpanel.add(toolBar);
	}

	@Override
	public String getGridClass() {
		return EzeeContractor.class.getName();
	}

	@Override
	public void deleteEntity() {
		EzeeContractor entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteContractor(cache, this, entity, delete, crudHeaders).show();
		}
	}

	@Override
	public void newEntity() {
		new EzeeCreateUpdateDeleteContractor(cache, this, crudHeaders).show();
	}

	@Override
	public void editEntity() {
		EzeeContractor entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteContractor(cache, this, entity, update, crudHeaders).show();
		}
	}
	
	@Override
	public void viewEntity() {
		// TODO Implement read-only functionality
	}
}