package com.ezee.client.grid.lease;

import static com.ezee.client.EzeeLeaseWebConstants.LEASE_CRUD_HEADERS;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.client.crud.lease.EzeeCreateUpdateDeleteLease;
import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.grid.EzeeGrid;
import com.google.gwt.view.client.SingleSelectionModel;

public class EzeeLeaseGrid extends EzeeGrid<EzeeLease> {

	public EzeeLeaseGrid(final EzeeEntityCache cache) {
		super(cache);
	}

	@Override
	protected void initGrid(final int pageSize) {
		super.initGrid(pageSize);
		SingleSelectionModel<EzeeLease> selectModel = new SingleSelectionModel<>();
		grid.setSelectionModel(selectModel);
		model = new EzeeLeaseGridModel();
		model.bind(grid);
	}

	@Override
	protected void initFilter() {
		super.initFilter();
		toolBar = new EzeeLeaseGridToolbar(this);
		filterpanel.add(toolBar);
	}

	@Override
	public void deleteEntity() {
		EzeeLease entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteLease(cache, this, entity, delete, LEASE_CRUD_HEADERS).show();
		}
	}

	@Override
	public void newEntity() {
		new EzeeCreateUpdateDeleteLease(cache, this, LEASE_CRUD_HEADERS).show();
	}

	@Override
	public void editEntity() {
		EzeeLease entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteLease(cache, this, entity, update, LEASE_CRUD_HEADERS).show();
		}
	}

	@Override
	public String getGridClass() {
		return EzeeLease.class.getName();
	}
}