package com.ezee.client.grid.project;

import static com.ezee.client.EzeeConstructionWebConstants.ERROR;
import static com.ezee.client.EzeeConstructionWebConstants.MODIFIED;
import static com.ezee.client.EzeeConstructionWebConstants.UNMODIFIED;
import static com.ezee.client.css.EzeeProjectResources.INSTANCE;
import static com.ezee.common.web.EzeeFormatUtils.getAmountFormat;
import static com.ezee.common.web.EzeeFormatUtils.getPercentFormat;
import static com.ezee.web.common.EzeeWebCommonConstants.ENTITY_SERVICE;
import static com.ezee.web.common.ui.dialog.EzeeMessageDialog.showNew;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showDefaultCursor;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showWaitCursor;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.grid.project.data.detail.EzeeProjectItemDetailGrid;
import com.ezee.client.grid.project.data.item.EzeeProjectItemGrid;
import com.ezee.client.grid.project.data.payment.EzeeProjectPaymentGrid;
import com.ezee.model.entity.EzeeResource;
import com.ezee.model.entity.project.EzeeProject;
import com.ezee.model.entity.project.EzeeProjectItem;
import com.ezee.model.entity.project.EzeeProjectItemDetail;
import com.ezee.model.entity.project.EzeeProjectPayment;
import com.ezee.web.common.ui.dialog.EzeeMessageDialog;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EzeeProjectDetail extends Composite {

	private static final Logger log = Logger.getLogger("EzeeProjectDetail");

	private static EzeeProjectDetailUiBinder uiBinder = GWT.create(EzeeProjectDetailUiBinder.class);

	interface EzeeProjectDetailUiBinder extends UiBinder<Widget, EzeeProjectDetail> {
	}

	private EzeeProject project;

	private final EzeeProjectDetailListener listener;

	private EzeeProjectItemGrid projectItemGrid;

	private EzeeProjectItemDetailGrid projectItemDetailGrid;

	private EzeeProjectPaymentGrid projectPaymentGrid;

	private final Map<String, EzeeResource> resources;

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
	Button btnDeleteItem;

	@UiField
	Button btnAddItemDetail;

	@UiField
	Button btnDeleteItemDetail;

	@UiField
	Button btnAddPayment;

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

	public EzeeProjectDetail(final EzeeProject project, final EzeeProjectDetailListener listener,
			final Map<String, EzeeResource> resources) {
		this.project = project;
		this.listener = listener;
		this.resources = resources;
		initWidget(uiBinder.createAndBindUi(this));
		initGrids();
	}

	private void initGrids() {
		projectItemGrid = new EzeeProjectItemGrid(this, resources);
		projectItems.add(projectItemGrid);
		projectItemDetailGrid = new EzeeProjectItemDetailGrid(this);
		projectItemDetails.add(projectItemDetailGrid);
		projectItemGrid.addListener(projectItemDetailGrid);
		projectPaymentGrid = new EzeeProjectPaymentGrid(this);
		projectPayments.add(projectPaymentGrid);
		projectItemGrid.addListener(projectPaymentGrid);
		updateTotals();
	}

	public final EzeeProject getProject() {
		return project;
	}

	@UiHandler("btnAddItem")
	void onAddItemClick(ClickEvent event) {
		EzeeProjectItem item = projectItemGrid.newEntity();
		project.addItem(item);
		projectItemGrid.addEntity(item);
		modified();

	}

	@UiHandler("btnDeleteItem")
	void onDeleteItemClick(ClickEvent event) {
		EzeeProjectItem item = projectItemGrid.getSelected();
		if (item != null) {
			project.getItems().remove(item);
			projectItemGrid.removeEntity(item);
			modified();
		}
	}

	@UiHandler("btnAddItemDetail")
	void onAddItemDetailClick(ClickEvent event) {
		if (projectItemGrid.getSelected() != null) {
			EzeeProjectItem item = project.getItem(projectItemGrid.getSelected().getGridId());
			if (item != null) {
				EzeeProjectItemDetail detail = projectItemDetailGrid.newEntity();
				item.addDetail(detail);
				projectItemDetailGrid.addEntity(detail);
				modified();
			}
		} else {
			EzeeMessageDialog.showNew("Add Detail", "Select and/or add an item to allocate a detail item to.");
		}
	}

	@UiHandler("btnDeleteItemDetail")
	void onDeleteItemDetailClick(ClickEvent event) {
		EzeeProjectItemDetail detail = projectItemDetailGrid.getSelected();
		if (detail != null && projectItemGrid.getSelected() != null) {
			EzeeProjectItem item = project.getItem(projectItemGrid.getSelected().getGridId());
			if (item != null) {
				item.getDetails().remove(detail);
				projectItemDetailGrid.removeEntity(detail);
				modified();
			}
		}
	}

	@UiHandler("btnAddPayment")
	void onAddPaymentClick(ClickEvent event) {
		if (projectItemGrid.getSelected() != null) {
			EzeeProjectItem item = project.getItem(projectItemGrid.getSelected().getGridId());
			if (item != null) {
				EzeeProjectPayment payment = projectPaymentGrid.newEntity();
				item.addPayment(payment);
				projectPaymentGrid.addEntity(payment);
				modified();
			}
		} else {
			EzeeMessageDialog.showNew("Add Payment", "Select and/or add an item to allocate a payment to.");
		}
	}

	@UiHandler("btnDeletePayment")
	void onDeletePaymentClick(ClickEvent event) {
		EzeeProjectPayment payment = projectPaymentGrid.getSelected();
		if (payment != null && projectItemGrid.getSelected() != null) {
			EzeeProjectItem item = project.getItem(projectItemGrid.getSelected().getGridId());
			if (item != null) {
				item.getPayments().remove(payment);
				projectPaymentGrid.removeEntity(payment);
				modified();
			}
		}
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
				unmodified();
				log.log(Level.INFO, "Saved project '" + project + "'.");
			}
		});
	}

	public void modified() {
		lblStatus.setStyleName(INSTANCE.css().gwtLabelProjectModifyMod());
		lblStatus.setText(MODIFIED);
		updateTotals();
		projectItemDetailGrid.getGrid().redraw();
		projectPaymentGrid.getGrid().redraw();
		projectItemGrid.getGrid().redraw();
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
		txtBudget.setValue(getAmountFormat().format(project.budgeted().getTotal()));
		txtActual.setValue(getAmountFormat().format(project.actual().getTotal()));
		txtPaid.setValue(getAmountFormat().format(project.paid().getTotal()));
		txtBalance.setValue(getAmountFormat().format(project.balance().getTotal()));
		txtPercent.setValue(getPercentFormat().format(project.percent()));
	}
}