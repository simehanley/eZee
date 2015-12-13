package com.ezee.client.grid.project;

import static com.ezee.client.grid.project.EzeeProjectUtils.getIndexOfProject;
import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;
import static com.ezee.web.common.ui.dialog.EzeeMessageDialog.showNew;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ezee.client.crud.project.EzeeCreateUpdateDeleteProject;
import com.ezee.model.entity.EzeePayee;
import com.ezee.model.entity.project.EzeeProject;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.grid.EzeeFinancialEntityGrid;
import com.ezee.web.common.ui.main.EzeeWebMain;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;

public class EzeeProjectGrid extends EzeeFinancialEntityGrid<EzeeProject> implements EzeeProjectDetailListener {

	private final EzeeWebMain main;

	public EzeeProjectGrid(final EzeeWebMain main, final EzeeEntityCache cache, final String[] crudHeaders) {
		super(cache, crudHeaders);
		this.main = main;
	}

	@Override
	protected void initGrid() {
		super.initGrid();
		grid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
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
				Map<String, EzeePayee> resources = resolveResources();
				if (!isEmpty(resources)) {
					main.getTab().add(new EzeeProjectDetail(entity, this, resources).asWidget(), entity.getName());
					main.getTab().selectTab(main.getTab().getWidgetCount() - ONE);
				} else {
					showNew("Error", "Resources need to be added prior to editing project detail.");
				}
			}
		}
	}

	@Override
	public String getGridClass() {
		return EzeeProject.class.getName();
	}

	@Override
	public void detailSaved(final EzeeProject project) {
		onSave(project);
		grid.redraw();
	}

	@Override
	public void detailClosed(final EzeeProject project) {
		int index = getIndexOfProject(project, main.getTab());
		if (index >= ZERO) {
			main.getTab().remove(index);
		}
	}

	private Map<String, EzeePayee> resolveResources() {
		Map<String, EzeePayee> resources = cache.getEntitiesOfType(EzeePayee.class);
		List<EzeePayee> sorted = new ArrayList<>(resources.values());
		Collections.sort(sorted);
		Map<String, EzeePayee> sortedMap = new LinkedHashMap<>();
		for (EzeePayee payee : sorted) {
			sortedMap.put(payee.getName(), payee);
		}
		return sortedMap;
	}
}