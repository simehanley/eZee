package com.ezee.client.grid.lease;

import static com.ezee.client.EzeeLeaseWebConstants.LEASE_CRUD_HEADERS;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.CATEGORY;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.LEASE_ID;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.PREMISES;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.SHOW_INACTIVE;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.TENANT;
import static com.ezee.web.common.EzeeWebCommonConstants.REPORT_SERVICE;
import static com.ezee.web.common.EzeeWebCommonConstants.REPORT_TYPE;
import static com.ezee.web.common.enums.EzeeReportType.lease_invoice_report_excel;
import static com.ezee.web.common.enums.EzeeReportType.lease_list_report_excel;
import static com.ezee.web.common.enums.EzeeReportType.lease_myob_schedule;
import static com.ezee.web.common.enums.EzeeReportType.lease_schedule_report_excel;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;

import java.util.ArrayList;
import java.util.List;

import com.ezee.client.crud.lease.EzeeCreateUpdateDeleteLease;
import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.enums.EzeeReportType;
import com.ezee.web.common.ui.grid.EzeeGrid;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.view.client.SingleSelectionModel;

public class EzeeLeaseGrid extends EzeeGrid<EzeeLease> implements EzeeLeaseSummaryHandler {

	public EzeeLeaseGrid(final EzeeEntityCache cache) {
		super(cache);
	}

	@Override
	protected void initGrid() {
		super.initGrid();
		SingleSelectionModel<EzeeLease> selectModel = new SingleSelectionModel<>();
		grid.setSelectionModel(selectModel);
		model = new EzeeLeaseGridModel(gridToolbar().getShowSummary(), gridToolbar().getShowMonthly());
		model.bind(grid);
	}

	@Override
	protected void initFilter() {
		super.initFilter();
		toolBar = new EzeeLeaseGridToolbar(this, this);
		filterpanel.add(toolBar);
	}

	@Override
	public void deleteEntity() {
		EzeeLease entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteLease(cache, this, entity, delete, LEASE_CRUD_HEADERS).show();
		}
	}

	@Override
	public void newEntity() {
		new EzeeCreateUpdateDeleteLease(cache, this, LEASE_CRUD_HEADERS).show();
	}

	@Override
	public void editEntity() {
		EzeeLease entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteLease(cache, this, entity, update, LEASE_CRUD_HEADERS).show();
		}
	}

	@Override
	public String getGridClass() {
		return EzeeLease.class.getName();
	}

	@Override
	public void summaryValueChanged(boolean summary) {
		gridModel().setSummary(summary);
		redrawGrid();
	}

	@Override
	public void monthlyValueChanged(boolean monthly) {
		gridModel().setMonthly(monthly);
		redrawGrid();
	}

	private void redrawGrid() {
		gridModel().removeColumns(grid);
		gridModel().bind(grid);
		grid.redraw();
	}

	private final EzeeLeaseGridModel gridModel() {
		return (EzeeLeaseGridModel) model;
	}

	private final EzeeLeaseGridToolbar gridToolbar() {
		return (EzeeLeaseGridToolbar) toolBar;
	}

	@Override
	protected MenuBar createContextMenu() {
		MenuBar menu = super.createContextMenu();
		menu.addSeparator();
		menu.addItem("Report", new Command() {
			@Override
			public void execute() {
				createExcelLeaseReport();
				contextMenu.hide();
			}
		});
		menu.addItem("Invoice", new Command() {
			@Override
			public void execute() {
				createExcelLeaseReport(lease_invoice_report_excel);
				contextMenu.hide();
			}
		});
		menu.addItem("Schedule", new Command() {
			@Override
			public void execute() {
				createExcelLeaseReport(lease_schedule_report_excel);
				contextMenu.hide();
			}
		});
		menu.addSeparator();
		menu.addItem("Myob Schedule", new Command() {
			@Override
			public void execute() {
				createExcelLeaseReport(lease_myob_schedule);
				contextMenu.hide();
			}
		});
		return menu;
	}

	private void createExcelLeaseReport() {
		EzeeLeaseGridToolbar toolbar = gridToolbar();
		StringBuilder builder = new StringBuilder();
		builder.append(REPORT_SERVICE + "?" + REPORT_TYPE + "=" + lease_list_report_excel);
		builder.append("&" + TENANT + "=" + toolbar.getTenant());
		builder.append("&" + PREMISES + "=" + toolbar.getPremises());
		builder.append("&" + CATEGORY + "=" + toolbar.getCategory());
		builder.append("&" + SHOW_INACTIVE + "=" + toolbar.getShowInactive());
		String reportServiceUrl = GWT.getModuleBaseURL() + builder.toString();
		Window.Location.assign(reportServiceUrl);
	}

	private void createExcelLeaseReport(final EzeeReportType type) {
		EzeeLease lease = getSelected();
		if (lease != null) {
			if (lease.getId() != null) {
				if (!lease.isInactive()) {
					StringBuilder builder = new StringBuilder();
					builder.append(REPORT_SERVICE + "?" + REPORT_TYPE + "=" + type);
					builder.append("&" + LEASE_ID + "=" + lease.getId());
					String reportServiceUrl = GWT.getModuleBaseURL() + builder.toString();
					Window.Location.assign(reportServiceUrl);
				}
			}
		}
	}

	public List<EzeeLease> getEdited() {
		List<EzeeLease> all = model.getHandler().getList();
		List<EzeeLease> edited = new ArrayList<>();
		if (!isEmpty(all)) {
			for (EzeeLease lease : all) {
				if (lease.isEdited()) {
					edited.add(lease);
				}
			}
		}
		return edited;
	}
}