package com.ezee.client.grid.lease;

import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.grid.EzeeGrid;
import com.google.gwt.view.client.SingleSelectionModel;

public class EzeeLeaseGrid extends EzeeGrid<EzeeLease> {

	public EzeeLeaseGrid(final EzeeEntityCache cache) {
		super(cache);
	}

	@Override
	protected void initGrid() {
		super.initGrid();
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
	}

	@Override
	public void newEntity() {
	}

	@Override
	public void editEntity() {
	}

	@Override
	public String getGridClass() {
		return EzeeLease.class.getName();
	}
}