package com.ezee.client.grid.project.data.item;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.model.entity.project.util.EzeeDatabaseEntityUtils.sorted;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ezee.client.grid.project.EzeeProjectDetail;
import com.ezee.client.grid.project.data.EzeeProjectDataGrid;
import com.ezee.model.entity.EzeeResource;
import com.ezee.model.entity.project.EzeeProject;
import com.ezee.model.entity.project.EzeeProjectItem;
import com.ezee.web.common.ui.grid.EzeeGridModelListener;
import com.google.gwt.view.client.SelectionChangeEvent;

public class EzeeProjectItemGrid extends EzeeProjectDataGrid<EzeeProjectItem> {

	private final List<EzeeProjectItemGridListener> listeners = new ArrayList<>();

	public EzeeProjectItemGrid(final EzeeProjectDetail projectDetail, final Map<String, EzeeResource> resources) {
		super(projectDetail, resources);
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

		model = new EzeeProjectItemGridModel(new EzeeGridModelListener<EzeeProjectItem>() {
			@Override
			public void modelUpdated(final EzeeProjectItem entity) {
				projectDetail.modified();
			}
		}, resources);
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

	@Override
	public EzeeProjectItem newEntity() {
		EzeeProjectItem item = new EzeeProjectItem(EMPTY_STRING, resources.values().iterator().next(),
				DATE_UTILS.toString(new Date()), null);
		return item;
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
			listener.itemSelected(item);
		}
	}

	private void notifyDelete(final EzeeProjectItem item) {
		for (EzeeProjectItemGridListener listener : listeners) {
			listener.itemDeleted(item);
		}
	}
}