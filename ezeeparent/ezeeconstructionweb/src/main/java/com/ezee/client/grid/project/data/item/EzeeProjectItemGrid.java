package com.ezee.client.grid.project.data.item;

import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.model.entity.project.util.EzeeDatabaseEntityUtils.sorted;

import java.util.ArrayList;
import java.util.List;

import com.ezee.client.grid.project.EzeeProjectDetail;
import com.ezee.client.grid.project.data.EzeeProjectDataGrid;
import com.ezee.model.entity.project.EzeeProject;
import com.ezee.model.entity.project.EzeeProjectItem;
import com.google.gwt.view.client.SelectionChangeEvent;

public class EzeeProjectItemGrid extends EzeeProjectDataGrid<EzeeProjectItem> {

	private final List<EzeeProjectItemGridListener> listeners = new ArrayList<>();

	public EzeeProjectItemGrid(final EzeeProjectDetail projectDetail) {
		super(projectDetail);
	}

	protected void initGrid() {
		super.initGrid();
		grid.getSelectionModel().addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				EzeeProjectItem item = getSelected();
				if (item != null) {
					notifySelect(item);
				}
			}
		});
		model = new EzeeProjectItemGridModel();
		model.bind(grid);
	}

	@Override
	protected void loadEntities() {
		model.getHandler().getList().clear();
		EzeeProject project = projectDetail.getProject();
		if (!isEmpty(project.getItems())) {
			model.getHandler().getList().addAll(sorted(project.getItems()));
		}
		grid.redraw();
		setSelected(ZERO);
	}

	@Override
	public void addEntity(EzeeProjectItem entity) {
		super.addEntity(entity);
	}

	public void addListener(final EzeeProjectItemGridListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeEntity(final EzeeProjectItem entity) {
		super.removeEntity(entity);
		notifyDelete(entity);
	}

	private void notifySelect(final EzeeProjectItem item) {
		for (EzeeProjectItemGridListener listener : listeners) {
			listener.itemSelected(item, ZERO);
		}
	}

	private void notifyDelete(final EzeeProjectItem item) {
		for (EzeeProjectItemGridListener listener : listeners) {
			listener.itemDeleted(item);
		}
	}

	@Override
	protected void newEntity() {
		projectDetail.newProjectItem();
	}

	@Override
	protected void editEntity() {
		projectDetail.editProjectItem();
	}

	@Override
	protected void deleteEntity() {
		projectDetail.deleteProjectItem();
	}
}