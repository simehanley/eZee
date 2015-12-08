package com.ezee.client.grid.project.data.detail;

import com.ezee.client.grid.project.EzeeProjectItemChangedHandler;
import com.ezee.client.grid.project.data.EzeeProjectDataGrid;
import com.ezee.model.entity.project.EzeeProject;
import com.ezee.model.entity.project.EzeeProjectItem;
import com.ezee.model.entity.project.EzeeProjectItemDetail;

public class EzeeProjectItemDetailGrid extends EzeeProjectDataGrid<EzeeProjectItemDetail>
		implements EzeeProjectItemChangedHandler {

	public EzeeProjectItemDetailGrid(final EzeeProject project) {
		super(project);
	}

	protected void initGrid() {
		super.initGrid();
		model = new EzeeProjectItemDetailGridModel();
		model.bind(grid);
	}

	@Override
	protected void loadEntities() {

	}

	@Override
	public void onChange(final EzeeProjectItem item) {
	}
}