package com.ezee.client.grid.project;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.EzeeWebCommonConstants.ENTITY_SERVICE;
import static com.ezee.web.common.ui.dialog.EzeeMessageDialog.showNew;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showDefaultCursor;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showWaitCursor;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.grid.project.data.item.EzeeProjectItemGrid;
import com.ezee.model.entity.project.EzeeProject;
import com.ezee.model.entity.project.EzeeProjectItem;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class EzeeProjectDetail extends Composite {

	private static final Logger log = Logger.getLogger("EzeeProjectDetail");

	private static EzeeProjectDetailUiBinder uiBinder = GWT.create(EzeeProjectDetailUiBinder.class);

	interface EzeeProjectDetailUiBinder extends UiBinder<Widget, EzeeProjectDetail> {
	}

	private final EzeeProject project;

	private final EzeeProjectDetailCloseHandler handler;

	private EzeeProjectItemGrid projectItemGrid;

	@UiField
	HorizontalPanel projectItems;

	@UiField
	HorizontalPanel projectItemsDetails;

	@UiField
	HorizontalPanel projectItemsPayments;

	@UiField
	Button btnCloseItem;

	@UiField
	Button btnAddItem;

	@UiField
	Button btnDeleteItem;

	@UiField
	Button btnSaveItem;

	public EzeeProjectDetail(final EzeeProject project, final EzeeProjectDetailCloseHandler handler) {
		this.project = project;
		this.handler = handler;
		initWidget(uiBinder.createAndBindUi(this));
		initGrids();
	}

	private void initGrids() {
		projectItemGrid = new EzeeProjectItemGrid(project);
		projectItems.add(projectItemGrid);

	}

	public final EzeeProject getProject() {
		return project;
	}

	@UiHandler("btnAddItem")
	void onAddItemClick(ClickEvent event) {
		EzeeProjectItem item = new EzeeProjectItem(EMPTY_STRING, DATE_UTILS.toString(new Date()), null);
		project.addItem(item);
		projectItemGrid.getModel().getHandler().getList().add(item);
		projectItemGrid.getGrid().getSelectionModel().setSelected(item, true);
		projectItemGrid.getGrid().getRowElement(projectItemGrid.getGrid().getVisibleItems().indexOf(item))
				.scrollIntoView();
	}

	@UiHandler("btnDeleteItem")
	void onDeleteItemClick(ClickEvent event) {
		EzeeProjectItem item = projectItemGrid.getSelected();
		if (item != null) {
			project.getItems().remove(item);
			projectItemGrid.getModel().getHandler().getList().remove(item);
		}
	}

	@UiHandler("btnSaveItem")
	void onSaveItemClick(ClickEvent event) {
		save();
	}

	private void save() {
		showWaitCursor();
		ENTITY_SERVICE.saveEntity(EzeeProject.class.getName(), project, new AsyncCallback<EzeeProject>() {

			@Override
			public void onFailure(final Throwable caught) {
				showDefaultCursor();
				String message = "Failed to save project '" + project + "'.  See log for details.";
				log.log(Level.SEVERE, message, caught);
				showNew("Error", message);
			}

			@Override
			public void onSuccess(final EzeeProject result) {
				showDefaultCursor();
				/* propogate changes */
			}
		});
	}
}