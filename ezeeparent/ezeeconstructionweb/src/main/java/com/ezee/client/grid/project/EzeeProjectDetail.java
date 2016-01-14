package com.ezee.client.grid.project;

import static com.ezee.client.EzeeConstructionWebConstants.ERROR;
import static com.ezee.client.EzeeConstructionWebConstants.MODIFIED;
import static com.ezee.client.EzeeConstructionWebConstants.PROJECT_ITEM_CRUD_HEADERS;
import static com.ezee.client.EzeeConstructionWebConstants.PROJECT_ITEM_DETAIL_CRUD_HEADERS;
import static com.ezee.client.EzeeConstructionWebConstants.PROJECT_PAYMENT_CRUD_HEADERS;
import static com.ezee.client.EzeeConstructionWebConstants.UNMODIFIED;
import static com.ezee.client.css.EzeeProjectResources.INSTANCE;
import static com.ezee.common.EzeeCommonConstants.MINUS_ONE;
import static com.ezee.common.web.EzeeFormatUtils.getAmountFormat;
import static com.ezee.common.web.EzeeFormatUtils.getPercentFormat;
import static com.ezee.web.common.EzeeWebCommonConstants.ENTITY_SERVICE;
import static com.ezee.web.common.EzeeWebCommonConstants.EXCEL_PROJECT_ID;
import static com.ezee.web.common.EzeeWebCommonConstants.REPORT_SERVICE;
import static com.ezee.web.common.EzeeWebCommonConstants.REPORT_TYPE;
import static com.ezee.web.common.enums.EzeeReportType.detailed_project_report_excel;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;
import static com.ezee.web.common.ui.dialog.EzeeMessageDialog.showNew;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showDefaultCursor;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showWaitCursor;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.crud.project.item.EzeeCreateUpdateDeleteProjectItem;
import com.ezee.client.crud.project.item.detail.EzeeCreateUpdateDeleteProjectItemDetail;
import com.ezee.client.crud.project.item.payment.EzeeCreateUpdateDeleteProjectItemPayment;
import com.ezee.client.grid.project.data.detail.EzeeProjectItemDetailGrid;
import com.ezee.client.grid.project.data.item.EzeeProjectItemGrid;
import com.ezee.client.grid.project.data.payment.EzeeProjectPaymentGrid;
import com.ezee.model.entity.project.EzeeProject;
import com.ezee.model.entity.project.EzeeProjectItem;
import com.ezee.model.entity.project.EzeeProjectItemDetail;
import com.ezee.model.entity.project.EzeeProjectPayment;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.MGWT;

public class EzeeProjectDetail extends Composite {

	private static final Logger log = Logger.getLogger("EzeeProjectDetail");

	private static EzeeProjectDetailUiBinder uiBinder = GWT.create(EzeeProjectDetailUiBinder.class);

	interface EzeeProjectDetailUiBinder extends UiBinder<Widget, EzeeProjectDetail> {
	}

	private EzeeProject project;

	private final EzeeProjectDetailListener listener;

	private EzeeProjectItemGrid projectItemGrid;

	private final EzeeCreateUpdateProjectItemHandler projectItemHandler = new EzeeCreateUpdateProjectItemHandler();

	private EzeeProjectItemDetailGrid projectItemDetailGrid;

	private final EzeeCreateUpdateProjectItemDetailHandler projectItemDetailHandler = new EzeeCreateUpdateProjectItemDetailHandler();

	private EzeeProjectPaymentGrid projectPaymentGrid;

	private final EzeeCreateUpdateProjectPaymentHandler projectPaymentHandler = new EzeeCreateUpdateProjectPaymentHandler();

	private final EzeeEntityCache cache;

	@UiField
	HorizontalPanel projectItems;

	@UiField
	HorizontalPanel projectItemDetails;

	@UiField
	HorizontalPanel projectPayments;

	@UiField
	Label lblStatus;

	@UiField
	Button btnAddItem;

	@UiField
	Button btnEditItem;

	@UiField
	Button btnDeleteItem;

	@UiField
	Button btnAddItemDetail;

	@UiField
	Button btnEditItemDetail;

	@UiField
	Button btnDeleteItemDetail;

	@UiField
	Button btnAddPayment;

	@UiField
	Button btnEditPayment;

	@UiField
	Button btnDeletePayment;

	@UiField
	Button btnSave;

	@UiField
	Button btnClose;

	@UiField
	TextBox txtBudget;

	@UiField
	TextBox txtActual;

	@UiField
	TextBox txtPaid;

	@UiField
	TextBox txtBalance;

	@UiField
	TextBox txtPercent;

	@UiField
	Label lblTotals;

	@UiField
	Label lblBudget;

	@UiField
	Label lblActual;

	@UiField
	Label lblPaid;

	@UiField
	Label lblBalance;

	@UiField
	Label lblPercent;

	@UiField
	StackLayoutPanel panel;

	@UiField
	Button btnReport;

	public EzeeProjectDetail(final EzeeProject project, final EzeeEntityCache cache,
			final EzeeProjectDetailListener listener) {
		this.project = project;
		this.listener = listener;
		this.cache = cache;
		initWidget(uiBinder.createAndBindUi(this));
		initGrids();
	}

	private void initGrids() {
		projectItemGrid = new EzeeProjectItemGrid(this);
		projectItems.add(projectItemGrid);
		projectItemDetailGrid = new EzeeProjectItemDetailGrid(this);
		projectItemDetails.add(projectItemDetailGrid);
		projectItemGrid.addListener(projectItemDetailGrid);
		projectPaymentGrid = new EzeeProjectPaymentGrid(this);
		projectPayments.add(projectPaymentGrid);
		projectItemGrid.addListener(projectPaymentGrid);
		if (!MGWT.getFormFactor().isDesktop()) {
			hideTotals();
		} else {
			updateTotals();
		}
	}

	private void hideTotals() {
		txtBudget.setVisible(false);
		txtActual.setVisible(false);
		txtPaid.setVisible(false);
		txtBalance.setVisible(false);
		txtPercent.setVisible(false);
		lblTotals.setVisible(false);
		lblBudget.setVisible(false);
		lblActual.setVisible(false);
		lblPaid.setVisible(false);
		lblBalance.setVisible(false);
		lblPercent.setVisible(false);
	}

	public final EzeeProject getProject() {
		return project;
	}

	@UiHandler("btnAddItem")
	void onAddItemClick(ClickEvent event) {
		newProjectItem();
	}

	@UiHandler("btnEditItem")
	void onEditItemClick(ClickEvent event) {
		editProjectItem();
	}

	@UiHandler("btnDeleteItem")
	void onDeleteItemClick(ClickEvent event) {
		deleteProjectItem();
	}

	@UiHandler("btnAddItemDetail")
	void onAddItemDetailClick(ClickEvent event) {
		newProjectItemDetail();
	}

	@UiHandler("btnEditItemDetail")
	void onEditItemDetailClick(ClickEvent event) {
		editProjectItemDetail();
	}

	@UiHandler("btnDeleteItemDetail")
	void onDeleteItemDetailClick(ClickEvent event) {
		deleteProjectItemDetail();
	}

	@UiHandler("btnAddPayment")
	void onAddPaymentClick(ClickEvent event) {
		newProjectPayment();
	}

	@UiHandler("btnEditPayment")
	void onEditPaymentClick(ClickEvent event) {
		editProjectPayment();
	}

	@UiHandler("btnDeletePayment")
	void onDeletePaymentClick(ClickEvent event) {
		deleteProjectPayment();
	}

	@UiHandler("btnClose")
	void onCloseClick(ClickEvent event) {
		close();
	}

	@UiHandler("btnSave")
	void onSaveClick(ClickEvent event) {
		save();
	}

	private void close() {
		listener.detailClosed(project);
	}

	@UiHandler("btnReport")
	void onReportClick(ClickEvent event) {
		String reportServiceUrl = GWT.getModuleBaseURL() + resolveReportParamString();
		Window.Location.assign(reportServiceUrl);
	}

	private void save() {
		showWaitCursor();
		ENTITY_SERVICE.saveEntity(EzeeProject.class.getName(), project, new AsyncCallback<EzeeProject>() {

			@Override
			public void onFailure(final Throwable caught) {
				showDefaultCursor();
				String message = "Failed to save project '" + project + "'. See log for details.";
				log.log(Level.SEVERE, message, caught);
				showNew("Error", message);
				error();
			}

			@Override
			public void onSuccess(final EzeeProject result) {
				showDefaultCursor();
				reBindProject(result);
				reloadUi();
				unmodified();
				log.log(Level.INFO, "Saved project '" + project + "'.");
			}
		});
	}

	private void reloadUi() {
		int itemIndex = projectItemGrid.getIndex(projectItemGrid.getSelected());
		int detailIndex = projectItemDetailGrid.getIndex(projectItemDetailGrid.getSelected());
		int paymentIndex = projectPaymentGrid.getIndex(projectPaymentGrid.getSelected());
		projectItemGrid.reloadEntities();
		projectItemGrid.setSelected(itemIndex);
		projectItemDetailGrid.itemSelected(projectItemGrid.getSelected(), detailIndex);
		projectPaymentGrid.itemSelected(projectItemGrid.getSelected(), paymentIndex);
	}

	public void modified() {
		lblStatus.setStyleName(INSTANCE.css().gwtLabelProjectModifyMod());
		lblStatus.setText(MODIFIED);
		updateTotals();
	}

	public void unmodified() {
		lblStatus.setStyleName(INSTANCE.css().gwtLabelProjectModifyUnmod());
		lblStatus.setText(UNMODIFIED);
	}

	public void error() {
		lblStatus.setStyleName(INSTANCE.css().gwtLabelProjectModifyMod());
		lblStatus.setText(ERROR);
	}

	private void reBindProject(final EzeeProject result) {
		this.project = result;
		listener.detailSaved(result);
		updateTotals();
	}

	private void updateTotals() {
		if (MGWT.getFormFactor().isDesktop()) {
			txtBudget.setValue(getAmountFormat().format(project.budgeted().getTotal()));
			txtActual.setValue(getAmountFormat().format(project.actual().getTotal()));
			txtPaid.setValue(getAmountFormat().format(project.paid().getTotal()));
			txtBalance.setValue(getAmountFormat().format(project.balance().getTotal()));
			txtPercent.setValue(getPercentFormat().format(project.percent()));
		}
	}

	private class EzeeCreateUpdateProjectItemHandler implements EzeeCreateUpdateDeleteEntityHandler<EzeeProjectItem> {

		@Override
		public void onSave(final EzeeProjectItem item) {
			int index = projectItemGrid.getIndex(item);
			if (index == MINUS_ONE) {
				project.addItem(item);
				projectItemGrid.addEntity(item);
			}
			projectItemGrid.getGrid().redraw();
			modified();
		}

		@Override
		public void onDelete(final EzeeProjectItem item) {
			int index = projectItemGrid.getIndex(item);
			if (index != MINUS_ONE) {
				project.getItems().remove(item);
				projectItemGrid.removeEntity(item);
				modified();
			}
		}
	}

	private class EzeeCreateUpdateProjectItemDetailHandler
			implements EzeeCreateUpdateDeleteEntityHandler<EzeeProjectItemDetail> {

		@Override
		public void onSave(final EzeeProjectItemDetail detail) {
			int index = projectItemDetailGrid.getIndex(detail);
			if (index == MINUS_ONE) {
				EzeeProjectItem item = project.getItem(projectItemGrid.getSelected().getGridId());
				item.addDetail(detail);
				projectItemDetailGrid.addEntity(detail);
			}
			projectItemDetailGrid.getGrid().redraw();
			replaceItem();
			modified();
		}

		@Override
		public void onDelete(final EzeeProjectItemDetail detail) {
			int index = projectItemDetailGrid.getIndex(detail);
			if (index != MINUS_ONE) {
				EzeeProjectItem item = project.getItem(projectItemGrid.getSelected().getGridId());
				item.getDetails().remove(detail);
				projectItemDetailGrid.removeEntity(detail);
				replaceItem();
				modified();
			}
		}
	}

	private class EzeeCreateUpdateProjectPaymentHandler
			implements EzeeCreateUpdateDeleteEntityHandler<EzeeProjectPayment> {

		@Override
		public void onSave(final EzeeProjectPayment payment) {
			int index = projectPaymentGrid.getIndex(payment);
			if (index == MINUS_ONE) {
				EzeeProjectItem item = project.getItem(projectItemGrid.getSelected().getGridId());
				item.addPayment(payment);
				projectPaymentGrid.addEntity(payment);
			}
			projectPaymentGrid.getGrid().redraw();
			replaceItem();
			modified();
		}

		@Override
		public void onDelete(final EzeeProjectPayment payment) {
			int index = projectPaymentGrid.getIndex(payment);
			if (index != MINUS_ONE) {
				EzeeProjectItem item = project.getItem(projectItemGrid.getSelected().getGridId());
				item.getPayments().remove(payment);
				projectPaymentGrid.removeEntity(payment);
				replaceItem();
				modified();
			}
		}
	}

	public void newProjectItem() {
		new EzeeCreateUpdateDeleteProjectItem(cache, projectItemHandler, PROJECT_ITEM_CRUD_HEADERS).show();
	}

	public void editProjectItem() {
		EzeeProjectItem item = projectItemGrid.getSelected();
		if (item != null) {
			new EzeeCreateUpdateDeleteProjectItem(cache, projectItemHandler, item, update, PROJECT_ITEM_CRUD_HEADERS)
					.show();
		}
	}

	public void deleteProjectItem() {
		EzeeProjectItem item = projectItemGrid.getSelected();
		if (item != null) {
			new EzeeCreateUpdateDeleteProjectItem(cache, projectItemHandler, item, delete, PROJECT_ITEM_CRUD_HEADERS)
					.show();
		}
	}

	public void newProjectItemDetail() {
		EzeeProjectItem item = projectItemGrid.getSelected();
		if (item != null) {
			new EzeeCreateUpdateDeleteProjectItemDetail(cache, projectItemDetailHandler,
					PROJECT_ITEM_DETAIL_CRUD_HEADERS).show();
		}
	}

	public void editProjectItemDetail() {
		EzeeProjectItemDetail detail = projectItemDetailGrid.getSelected();
		if (detail != null) {
			new EzeeCreateUpdateDeleteProjectItemDetail(cache, projectItemDetailHandler, detail, update,
					PROJECT_ITEM_DETAIL_CRUD_HEADERS).show();
		}
	}

	public void deleteProjectItemDetail() {
		EzeeProjectItemDetail detail = projectItemDetailGrid.getSelected();
		if (detail != null) {
			new EzeeCreateUpdateDeleteProjectItemDetail(cache, projectItemDetailHandler, detail, delete,
					PROJECT_ITEM_DETAIL_CRUD_HEADERS).show();
		}
	}

	public void newProjectPayment() {
		EzeeProjectItem item = projectItemGrid.getSelected();
		if (item != null) {
			new EzeeCreateUpdateDeleteProjectItemPayment(cache, projectPaymentHandler, PROJECT_PAYMENT_CRUD_HEADERS)
					.show();
		}
	}

	public void editProjectPayment() {
		EzeeProjectPayment payment = projectPaymentGrid.getSelected();
		if (payment != null) {
			new EzeeCreateUpdateDeleteProjectItemPayment(cache, projectPaymentHandler, payment, update,
					PROJECT_PAYMENT_CRUD_HEADERS).show();
		}
	}

	public void deleteProjectPayment() {
		EzeeProjectPayment payment = projectPaymentGrid.getSelected();
		if (payment != null) {
			new EzeeCreateUpdateDeleteProjectItemPayment(cache, projectPaymentHandler, payment, delete,
					PROJECT_PAYMENT_CRUD_HEADERS).show();
		}
	}

	private int getProjectItemIndex() {
		EzeeProjectItem item = projectItemGrid.getSelected();
		if (item != null) {
			return projectItemGrid.getIndex(item);
		}
		return MINUS_ONE;
	}

	private void replaceItem() {
		int index = getProjectItemIndex();
		EzeeProjectItem item = projectItemGrid.getSelected();
		if (index != MINUS_ONE && item != null) {
			projectItemGrid.getModel().getHandler().getList().remove(index);
			projectItemGrid.getModel().getHandler().getList().add(index, item);
			projectItemGrid.getGrid().redraw();
		}
	}

	private String resolveReportParamString() {
		StringBuilder builder = new StringBuilder();
		builder.append(REPORT_SERVICE + "?" + REPORT_TYPE + "=" + detailed_project_report_excel);
		builder.append("&" + EXCEL_PROJECT_ID + "=" + project.getId());
		return builder.toString();
	}
}