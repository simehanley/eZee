package com.ezee.client.grid.project.data.payment;

import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.model.entity.project.util.EzeeDatabaseEntityUtils.sorted;

import com.ezee.client.grid.project.EzeeProjectDetail;
import com.ezee.client.grid.project.data.EzeeProjectDataGrid;
import com.ezee.client.grid.project.data.item.EzeeProjectItemGridListener;
import com.ezee.model.entity.project.EzeeProject;
import com.ezee.model.entity.project.EzeeProjectItem;
import com.ezee.model.entity.project.EzeeProjectPayment;
import com.ezee.web.common.ui.grid.EzeeGridModelListener;

public class EzeeProjectPaymentGrid extends EzeeProjectDataGrid<EzeeProjectPayment>
		implements EzeeProjectItemGridListener {

	public EzeeProjectPaymentGrid(final EzeeProjectDetail projectDetail) {
		super(projectDetail);
	}

	protected void initGrid() {
		super.initGrid();
		model = new EzeeProjectPaymentGridModel(new EzeeGridModelListener<EzeeProjectPayment>() {
			@Override
			public void modelUpdated(final EzeeProjectPayment entity) {
				projectDetail.modified();
			}
		});
		model.bind(grid);
	}

	@Override
	protected void loadEntities() {
		EzeeProject project = projectDetail.getProject();
		if (!isEmpty(project.getItems())) {
			loadPayments(project.getItems().iterator().next());
		}
	}

	@Override
	public void itemSelected(final EzeeProjectItem item) {
		loadPayments(item);
	}

	private void loadPayments(final EzeeProjectItem item) {
		model.getHandler().getList().clear();
		if (item != null && item.getPayments() != null) {
			if (!isEmpty(item.getPayments())) {
				model.getHandler().getList().addAll(sorted(item.getPayments()));
			}
		}
		grid.redraw();
		setSelected(ZERO);
	}

	@Override
	public void itemDeleted(final EzeeProjectItem item) {
		if (item != null && item.getPayments() != null) {
			if (!isEmpty(item.getPayments())) {
				model.getHandler().getList().clear();
				grid.redraw();
			}
		}
	}

	@Override
	protected void newEntity() {
	}

	@Override
	protected void editEntity() {
	}

	@Override
	protected void deleteEntity() {
	}
}