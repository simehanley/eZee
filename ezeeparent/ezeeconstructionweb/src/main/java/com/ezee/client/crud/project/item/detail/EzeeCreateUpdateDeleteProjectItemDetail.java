package com.ezee.client.crud.project.item.detail;

import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.create;
import static com.ezee.web.common.ui.utils.EzeeListBoxUtils.getItemIndex;
import static com.ezee.web.common.ui.utils.EzeeListBoxUtils.loadEnums;

import java.util.Date;

import com.ezee.model.entity.enums.EzeeProjectItemType;
import com.ezee.model.entity.project.EzeeProjectItemDetail;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteTaxableEntity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.Widget;

public class EzeeCreateUpdateDeleteProjectItemDetail
		extends EzeeCreateUpdateDeleteTaxableEntity<EzeeProjectItemDetail> {

	private static EzeeCreateUpdateDeleteProjectItemDetailUiBinder uiBinder = GWT
			.create(EzeeCreateUpdateDeleteProjectItemDetailUiBinder.class);

	interface EzeeCreateUpdateDeleteProjectItemDetailUiBinder
			extends UiBinder<Widget, EzeeCreateUpdateDeleteProjectItemDetail> {
	}

	@UiField
	Button btnDelete;

	@UiField
	Button btnSave;

	@UiField
	Button btnClose;

	@UiField
	RichTextArea rtxDescription;

	@UiField
	ListBox lstType;

	public EzeeCreateUpdateDeleteProjectItemDetail(EzeeEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<EzeeProjectItemDetail> handler, String[] headers) {
		this(cache, handler, null, create, headers);
	}

	public EzeeCreateUpdateDeleteProjectItemDetail(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeeProjectItemDetail> handler,
			final EzeeProjectItemDetail entity, final EzeeCreateUpdateDeleteEntityType type, final String[] headers) {
		super(cache, handler, entity, type, headers);
		setWidget(uiBinder.createAndBindUi(this));
		setModal(true);
	}

	@Override
	public void show() {
		loadTypes();
		initForm();
		switch (type) {
		case create:
			setText(headers[NEW_HEADER_INDEX]);
			initialiseNew();
			setFocus(txtAmount);
			btnDelete.setEnabled(false);
			break;
		case update:
			setText(headers[EDIT_HEADER_INDEX]);
			setFocus(txtAmount);
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
		super.initialise();
		rtxDescription.setText(entity.getDescription());
		lstType.setItemSelected(getItemIndex(entity.getType().toString(), lstType), true);
	}

	@Override
	protected void bind() {
		if (entity == null) {
			entity = new EzeeProjectItemDetail();
			entity.setCreated(DATE_UTILS.toString(new Date()));
		} else {
			entity.setUpdated(DATE_UTILS.toString(new Date()));
		}
		entity.setDescription(rtxDescription.getText());
		entity.setType(EzeeProjectItemType.get(lstType.getSelectedItemText()));
		super.bind();
	}

	@Override
	protected void disable() {
		super.disable();
		rtxDescription.setEnabled(false);
		lstType.setEnabled(false);
		btnSave.setEnabled(false);
	}

	private void loadTypes() {
		loadEnums(EzeeProjectItemType.values(), lstType);
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
}