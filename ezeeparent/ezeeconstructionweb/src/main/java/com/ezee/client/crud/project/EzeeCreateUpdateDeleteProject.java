package com.ezee.client.crud.project;

import static com.ezee.common.web.EzeeFromatUtils.getDateBoxFormat;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.EzeeWebCommonConstants.ENTITY_SERVICE;
import static com.ezee.web.common.EzeeWebCommonConstants.ERROR;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.create;
import static com.ezee.web.common.ui.dialog.EzeeMessageDialog.showNew;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showDefaultCursor;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showWaitCursor;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.model.entity.project.EzeeProject;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.crud.common.EzeeCreateUpdateDeleteFinancialEntity;
import com.ezee.web.common.ui.crud.common.EzeePayerPayeeCommon;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class EzeeCreateUpdateDeleteProject extends EzeeCreateUpdateDeleteFinancialEntity<EzeeProject> {

	private static final Logger log = Logger.getLogger("EzeeCreateUpdateProject");

	private static EzeeCreateUpdateDeleteProjectUiBinder uiBinder = GWT
			.create(EzeeCreateUpdateDeleteProjectUiBinder.class);

	interface EzeeCreateUpdateDeleteProjectUiBinder extends UiBinder<Widget, EzeeCreateUpdateDeleteProject> {
	};

	@UiField
	DateBox dtStart;

	@UiField
	DateBox dtEnd;

	@UiField
	Button btnDelete;

	@UiField
	Button btnClose;

	@UiField
	Button btnSave;

	@UiField
	EzeePayerPayeeCommon common;

	public EzeeCreateUpdateDeleteProject(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeeProject> handler, final String[] headers) {
		this(cache, handler, null, create, headers);
	}

	public EzeeCreateUpdateDeleteProject(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeeProject> handler, final EzeeProject entity,
			final EzeeCreateUpdateDeleteEntityType type, final String[] headers) {
		super(cache, handler, entity, type, headers);
		setWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void show() {
		dtStart.setFormat(getDateBoxFormat());
		dtEnd.setFormat(getDateBoxFormat());
		switch (type) {
		case create:
			setText(headers[NEW_HEADER_INDEX]);
			setFocus(common.getTxtName());
			btnDelete.setEnabled(false);
			break;
		case update:
			setText(headers[EDIT_HEADER_INDEX]);
			setFocus(common.getTxtName());
			initialise();
			btnDelete.setEnabled(false);
			break;
		case delete:
			setText(headers[EDIT_HEADER_INDEX]);
			initialise();
			disable();
			break;
		}
		super.show();
	}

	@Override
	protected void initialise() {
		dtStart.setValue(DATE_UTILS.fromString(entity.getStartDate()));
		dtEnd.setValue(DATE_UTILS.fromString(entity.getEndDate()));
		common.setName(entity.getName());
		common.setContact(entity.getContact());
		common.setAddressLine1(entity.getAddressLineOne());
		common.setAddressLine2(entity.getAddressLineTwo());
		common.setSuburb(entity.getSuburb());
		common.setCity(entity.getCity());
		common.setState(entity.getState());
		common.setPostCode(entity.getPostcode());
		common.setPhone(entity.getPhone());
		common.setFax(entity.getFax());
		common.setEmail(entity.getEmail());
	}

	@Override
	protected void bind() {
		if (entity == null) {
			entity = new EzeeProject();
			entity.setCreated(DATE_UTILS.toString(new Date()));
			entity.setUpdated(null);
		} else {
			entity.setUpdated(DATE_UTILS.toString(new Date()));
		}
		entity.setStartDate(DATE_UTILS.toString(dtStart.getValue()));
		entity.setEndDate(DATE_UTILS.toString(dtEnd.getValue()));
		entity.setName(common.getName());
		entity.setContact(common.getContact());
		entity.setAddressLineOne(common.getAddressLine1());
		entity.setAddressLineTwo(common.getAddressLine2());
		entity.setSuburb(common.getSuburb());
		entity.setCity(common.getCity());
		entity.setState(common.getState());
		entity.setPostcode(common.getPostCode());
		entity.setPhone(common.getPhone());
		entity.setFax(common.getFax());
		entity.setEmail(common.getEmail());
	}

	private void disable() {
		common.disable();
		dtStart.setEnabled(false);
		dtEnd.setEnabled(false);
		btnSave.setEnabled(false);
	}

	@UiHandler("btnClose")
	void onCloseClick(ClickEvent event) {
		close();
	}

	@UiHandler("btnSave")
	void onSaveClick(ClickEvent event) {
		btnSave.setEnabled(false);
		showWaitCursor();
		bind();
		ENTITY_SERVICE.saveEntity(EzeeProject.class.getName(), entity, new AsyncCallback<EzeeProject>() {

			@Override
			public void onFailure(final Throwable caught) {
				btnSave.setEnabled(true);
				showDefaultCursor();
				log.log(Level.SEVERE, "Error persisting project '" + entity + "'.", caught);
				showNew(ERROR, "Error persisting project '" + entity + "'.  Please see log for details.");
			}

			@Override
			public void onSuccess(final EzeeProject result) {
				log.log(Level.INFO, "Saved project '" + entity + "' successfully");
				handler.onSave(result);
				btnSave.setEnabled(true);
				showDefaultCursor();
				close();
			}
		});
	}

	@UiHandler("btnDelete")
	void onDeleteClick(ClickEvent event) {
		btnDelete.setEnabled(false);
		showWaitCursor();
		ENTITY_SERVICE.deleteEntity(EzeeProject.class.getName(), entity, new AsyncCallback<EzeeProject>() {

			@Override
			public void onFailure(Throwable caught) {
				btnDelete.setEnabled(true);
				showDefaultCursor();
				log.log(Level.SEVERE, "Error deleting project '" + entity + "'.", caught);
				showNew(ERROR, "Error deleting project '" + entity + "'.  Please see log for details");
			}

			@Override
			public void onSuccess(EzeeProject result) {
				log.log(Level.INFO, "Project '" + entity + "' deleted successfully");
				handler.onDelete(result);
				updateCache(result, type);
				btnDelete.setEnabled(true);
				showDefaultCursor();
				close();
			}
		});
	}
}