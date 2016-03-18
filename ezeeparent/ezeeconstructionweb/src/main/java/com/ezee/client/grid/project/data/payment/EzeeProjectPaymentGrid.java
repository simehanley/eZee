package com.ezee.client.grid.project.data.payment;

import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.model.entity.util.EzeeDatabaseEntityUtils.sorted;

import com.ezee.client.grid.project.EzeeProjectDetail;
import com.ezee.client.grid.project.data.EzeeProjectDataGrid;
import com.ezee.client.grid.project.data.item.EzeeProjectItemGridListener;
import com.ezee.model.entity.project.EzeeProject;
import com.ezee.model.entity.project.EzeeProjectItem;
import com.ezee.model.entity.project.EzeeProjectPayment;

public class EzeeProjectPaymentGrid extends EzeeProjectDataGrid<EzeeProjectPayment>
		implements EzeeProjectItemGridListener {

	public EzeeProjectPaymentGrid(final EzeeProjectDetail projectDetail) {
		super(projectDetail);
	}

	protected void initGrid() {
		super.initGrid();
		model = new EzeeProjectPaymentGridModel();
		model.bind(grid);
	}

	@Override
	protected void loadEntities() {
		EzeeProject project = projectDetail.getProject();
		if (!isEmpty(project.getItems())) {
			loadPayments(project.getItems().iterator().next(), ZERO);
		}
	}

	@Override
	public void itemSelected(final EzeeProjectItem item, final int selected) {
		loadPayments(item, selected);
	}

	private void loadPayments(final EzeeProjectItem item, final int selected) {
		model.getHandler().getList().clear();
		if (item != null && item.getPayments() != null) {
			if (!isEmpty(item.getPayments())) {
				model.getHandler().getList().addAll(sorted(item.getPayments()));
			}
		}
		grid.redraw();
		setSelected(selected);
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
		projectDetail.newProjectPayment();
	}

	@Override
	protected void editEntity() {
		projectDetail.editProjectPayment();
	}

	@Override
	protected void deleteEntity() {
		projectDetail.deleteProjectPayment();
	}
}