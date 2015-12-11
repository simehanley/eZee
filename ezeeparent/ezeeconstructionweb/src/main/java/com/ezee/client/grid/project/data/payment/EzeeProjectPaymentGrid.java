package com.ezee.client.grid.project.data.payment;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.model.entity.enums.EzeePaymentType.cheque;
import static com.ezee.model.entity.project.util.EzeeDatabaseEntityUtils.sorted;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;

import java.util.Date;

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
	public EzeeProjectPayment newEntity() {
		return new EzeeProjectPayment(DATE_UTILS.toString(new Date()), cheque, EMPTY_STRING, ZERO_DBL, ZERO_DBL,
				DATE_UTILS.toString(new Date()), null);
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
				grid.redraw();
			}
		}
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
}