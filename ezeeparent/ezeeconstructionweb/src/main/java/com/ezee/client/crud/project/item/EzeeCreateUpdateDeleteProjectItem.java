package com.ezee.client.crud.project.item;

import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.create;
import static com.ezee.web.common.ui.utils.EzeeListBoxUtils.getItemIndex;

import java.util.Date;

import com.ezee.model.entity.EzeeContractor;
import com.ezee.model.entity.project.EzeeProjectItem;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntity;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.utils.EzeeListBoxUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EzeeCreateUpdateDeleteProjectItem extends EzeeCreateUpdateDeleteEntity<EzeeProjectItem> {

	private static EzeeCreateUpdateDeleteProjectItemUiBinder uiBinder = GWT
			.create(EzeeCreateUpdateDeleteProjectItemUiBinder.class);

	interface EzeeCreateUpdateDeleteProjectItemUiBinder extends UiBinder<Widget, EzeeCreateUpdateDeleteProjectItem> {
	}

	@UiField
	TextBox txtName;

	@UiField
	ListBox lstContractor;

	@UiField
	Button btnDelete;

	@UiField
	Button btnSave;

	@UiField
	Button btnClose;

	public EzeeCreateUpdateDeleteProjectItem(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeeProjectItem> handler, final String[] headers) {
		this(cache, handler, null, create, headers);
	}

	public EzeeCreateUpdateDeleteProjectItem(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeeProjectItem> handler, final EzeeProjectItem entity,
			final EzeeCreateUpdateDeleteEntityType type, final String[] headers) {
		super(cache, handler, entity, type, headers);
		setWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void show() {
		loadEntities();
		switch (type) {
		case create:
			setText(headers[NEW_HEADER_INDEX]);
			setFocus(txtName);
			btnDelete.setEnabled(false);
			break;
		case update:
			setText(headers[EDIT_HEADER_INDEX]);
			setFocus(txtName);
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
		txtName.setText(entity.getName());
		lstContractor.setItemSelected(getItemIndex(entity.getContractor().getName(), lstContractor), true);
	}

	@Override
	protected void bind() {
		if (entity == null) {
			entity = new EzeeProjectItem();
			entity.setCreated(DATE_UTILS.toString(new Date()));
		} else {
			entity.setUpdated(DATE_UTILS.toString(new Date()));
		}
		entity.setName(txtName.getText());
		entity.setContractor(EzeeListBoxUtils.getEntity(EzeeContractor.class, lstContractor, cache));
	}

	@UiHandler("btnClose")
	void onCloseClick(ClickEvent event) {
		close();
	}

	@UiHandler("btnSave")
	void onSaveClick(ClickEvent event) {
		if (handler != null) {
			bind();
			handler.onSave(entity);
		}
		close();
	}

	@UiHandler("btnDelete")
	void onDeleteClick(ClickEvent event) {
		if (handler != null) {
			handler.onDelete(entity);
		}
		close();
	}

	private void disable() {
		txtName.setEnabled(false);
		lstContractor.setEnabled(false);
		btnSave.setEnabled(false);
	}

	private void loadEntities() {
		EzeeListBoxUtils.loadEntities(EzeeContractor.class, lstContractor, cache);
	}
}