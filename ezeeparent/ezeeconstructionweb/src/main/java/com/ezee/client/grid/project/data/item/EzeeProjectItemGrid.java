package com.ezee.client.grid.project.data.item;

import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;

import com.ezee.client.grid.project.data.EzeeProjectDataGrid;
import com.ezee.model.entity.project.EzeeProject;
import com.ezee.model.entity.project.EzeeProjectItem;

public class EzeeProjectItemGrid extends EzeeProjectDataGrid<EzeeProjectItem> {

	public EzeeProjectItemGrid(final EzeeProject project) {
		super(project);
	}

	protected void initGrid() {
		super.initGrid();
		model = new EzeeProjectItemGridModel();
		model.bind(grid);
	}

	@Override
	protected void loadEntities() {
		model.getHandler().getList().clear();
		if (!isEmpty(project.getItems())) {
			model.getHandler().getList().addAll(project.getSortedItems());
		}
		grid.redraw();
	}
}