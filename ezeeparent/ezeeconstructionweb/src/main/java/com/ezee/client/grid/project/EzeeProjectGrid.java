package com.ezee.client.grid.project;

import java.util.logging.Logger;

import com.ezee.model.entity.project.EzeeProject;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.grid.EzeeGrid;

public class EzeeProjectGrid extends EzeeGrid<EzeeProject> {

	private static final Logger log = Logger.getLogger("EzeeProjectGrid");

	public EzeeProjectGrid(final EzeeEntityCache cache) {
		super(cache);
	}

	@Override
	protected void initGrid() {
		super.initGrid();
		model = new EzeeProjectGridModel();
		model.bind(grid);
	}

	@Override
	protected void initFilter() {
		super.initFilter();
		toolBar = new EzeeProjectGridToolbar(this);
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
		return EzeeProject.class.getName();
	}
}