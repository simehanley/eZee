package com.ezee.web.common.ui.grid.leasecategory;

import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.model.entity.lease.EzeeLeaseCategory;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.leasecategory.EzeeCreateUpdateDeleteLeaseCategory;
import com.ezee.web.common.ui.grid.EzeeFinancialEntityGrid;

public class EzeeLeaseCategoryGrid extends EzeeFinancialEntityGrid<EzeeLeaseCategory> {

	public EzeeLeaseCategoryGrid(final EzeeEntityCache cache, final String[] crudHeaders) {
		super(cache, crudHeaders);
	}

	@Override
	protected void initGrid() {
		super.initGrid();
		model = new EzeeLeaseCategoryGridModel();
		model.bind(grid);
	}

	@Override
	protected void initFilter() {
		super.initFilter();
		createToolbar();
	}

	@Override
	public void deleteEntity() {
		EzeeLeaseCategory entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteLeaseCategory(cache, this, entity, delete, crudHeaders).show();
		}
	}

	@Override
	public void newEntity() {
		new EzeeCreateUpdateDeleteLeaseCategory(cache, this, crudHeaders).show();
	}

	@Override
	public void editEntity() {
		EzeeLeaseCategory entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteLeaseCategory(cache, this, entity, update, crudHeaders).show();
		}
	}
	
	@Override
	public void viewEntity() {
		// TODO Implement read-only functionality
	}

	@Override
	public String getGridClass() {
		return EzeeLeaseCategory.class.getName();
	}

	private void createToolbar() {
		toolBar = new EzeeLeaseCategoryToolbar(this);
		filterpanel.add(toolBar);
	}
}