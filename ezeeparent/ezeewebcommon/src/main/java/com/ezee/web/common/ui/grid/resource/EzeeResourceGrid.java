package com.ezee.web.common.ui.grid.resource;

import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.model.entity.EzeeResource;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.resource.EzeeCreateUpdateDeleteResource;
import com.ezee.web.common.ui.grid.payee.EzeePayeeGrid;

public class EzeeResourceGrid extends EzeePayeeGrid<EzeeResource> {

	public EzeeResourceGrid(EzeeEntityCache cache, String[] crudHeaders) {
		super(cache, crudHeaders);
	}

	@Override
	protected void createToolbar() {
		toolBar = new EzeeResourceToolbar(this);
		filterpanel.add(toolBar);
	}

	@Override
	public String getGridClass() {
		return EzeeResource.class.getName();
	}

	@Override
	public void deleteEntity() {
		EzeeResource entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteResource(cache, this, entity, delete, crudHeaders).show();
		}
	}

	@Override
	public void newEntity() {
		new EzeeCreateUpdateDeleteResource(cache, this, crudHeaders).show();
	}

	@Override
	public void editEntity() {
		EzeeResource entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteResource(cache, this, entity, update, crudHeaders).show();
		}
	}
}