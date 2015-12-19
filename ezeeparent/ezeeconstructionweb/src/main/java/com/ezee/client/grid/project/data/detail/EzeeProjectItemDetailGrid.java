package com.ezee.client.grid.project.data.detail;

import static com.ezee.client.EzeeConstructionWebConstants.DEFAULT_ITEM_DETAIL;
import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.model.entity.enums.EzeeProjectItemType.expense;
import static com.ezee.model.entity.project.util.EzeeDatabaseEntityUtils.sorted;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;

import java.util.Date;

import com.ezee.client.grid.project.EzeeProjectDetail;
import com.ezee.client.grid.project.data.EzeeProjectDataGrid;
import com.ezee.client.grid.project.data.item.EzeeProjectItemGridListener;
import com.ezee.model.entity.project.EzeeProject;
import com.ezee.model.entity.project.EzeeProjectItem;
import com.ezee.model.entity.project.EzeeProjectItemDetail;
import com.ezee.web.common.ui.grid.EzeeGridModelListener;

public class EzeeProjectItemDetailGrid extends EzeeProjectDataGrid<EzeeProjectItemDetail>
		implements EzeeProjectItemGridListener {

	public EzeeProjectItemDetailGrid(final EzeeProjectDetail projectDetail) {
		super(projectDetail);
	}

	protected void initGrid() {
		super.initGrid();
		model = new EzeeProjectItemDetailGridModel(new EzeeGridModelListener<EzeeProjectItemDetail>() {
			@Override
			public void modelUpdated(final EzeeProjectItemDetail entity) {
				projectDetail.modified();
			}
		});
		model.bind(grid);
	}

	@Override
	protected void loadEntities() {
		EzeeProject project = projectDetail.getProject();
		if (!isEmpty(project.getItems())) {
			loadDetails(project.getItems().iterator().next());
		}
	}

	@Override
	public EzeeProjectItemDetail newEntity() {
		return new EzeeProjectItemDetail(EMPTY_STRING, expense, DEFAULT_ITEM_DETAIL, ZERO_DBL,
				DATE_UTILS.toString(new Date()), null);
	}

	@Override
	public void itemSelected(final EzeeProjectItem item) {
		loadDetails(item);
	}

	private void loadDetails(final EzeeProjectItem item) {
		model.getHandler().getList().clear();
		if (item != null && item.getDetails() != null) {
			if (!isEmpty(item.getDetails())) {
				model.getHandler().getList().addAll(sorted(item.getDetails()));
			}
		}
		grid.redraw();
		setSelected(ZERO);
	}

	@Override
	public void itemDeleted(final EzeeProjectItem item) {
		if (item != null && item.getDetails() != null) {
			if (!isEmpty(item.getDetails())) {
				model.getHandler().getList().clear();
				grid.redraw();
			}
		}
	}
}