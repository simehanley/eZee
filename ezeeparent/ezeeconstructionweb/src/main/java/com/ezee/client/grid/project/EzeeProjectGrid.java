package com.ezee.client.grid.project;

import static com.ezee.client.grid.project.EzeeProjectUtils.getIndexOfProject;
import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.client.crud.project.EzeeCreateUpdateDeleteProject;
import com.ezee.model.entity.project.EzeeProject;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.grid.EzeeFinancialEntityGrid;
import com.ezee.web.common.ui.main.EzeeWebMain;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;

public class EzeeProjectGrid extends EzeeFinancialEntityGrid<EzeeProject> implements EzeeProjectDetailCloseHandler {

	private final EzeeWebMain main;

	public EzeeProjectGrid(final EzeeWebMain main, final EzeeEntityCache cache, final String[] crudHeaders) {
		super(cache, crudHeaders);
		this.main = main;
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

	protected MenuBar createContextMenu() {
		MenuBar menu = super.createContextMenu();
		menu.addSeparator();
		menu.addItem("Edit Project", new Command() {
			@Override
			public void execute() {
				contextMenu.hide();
				editProject();
			}
		});
		return menu;
	}

	@Override
	public void deleteEntity() {
		EzeeProject entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteProject(cache, this, entity, delete, crudHeaders).show();
		}
	}

	@Override
	public void newEntity() {
		new EzeeCreateUpdateDeleteProject(cache, this, crudHeaders).show();
	}

	@Override
	public void editEntity() {
		EzeeProject entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteProject(cache, this, entity, update, crudHeaders).show();
		}
	}

	public void editProject() {
		EzeeProject entity = getSelected();
		if (entity != null) {
			int index = getIndexOfProject(entity, main.getTab());
			if (index >= ZERO) {
				main.getTab().selectTab(index);
			} else {
				main.getTab().add(new EzeeProjectDetail(entity, this), entity.getName());
				main.getTab().selectTab(main.getTab().getWidgetCount() - ONE);
			}
		}
	}

	@Override
	public String getGridClass() {
		return EzeeProject.class.getName();
	}

	@Override
	public void closed(final EzeeProject project) {
		int index = getIndexOfProject(project, main.getTab());
		main.getTab().remove(index);
	}
}