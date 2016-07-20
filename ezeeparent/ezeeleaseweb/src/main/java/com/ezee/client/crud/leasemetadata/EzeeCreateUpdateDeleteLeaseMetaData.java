package com.ezee.client.crud.leasemetadata;

import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.create;
import static com.ezee.web.common.ui.utils.EzeeListBoxUtils.getItemIndex;

import java.util.Date;

import com.ezee.model.entity.lease.EzeeLeaseMetaData;
import com.ezee.model.entity.lease.EzeeLeaseMetaDataType;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntity;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.utils.EzeeListBoxUtils;
import com.ezee.web.common.ui.utils.EzeeRichTextAreaUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EzeeCreateUpdateDeleteLeaseMetaData extends EzeeCreateUpdateDeleteEntity<EzeeLeaseMetaData> {

	private static EzeeCreateUpdateDeleteLeaseMetaDataUiBinder uiBinder = GWT
			.create(EzeeCreateUpdateDeleteLeaseMetaDataUiBinder.class);

	interface EzeeCreateUpdateDeleteLeaseMetaDataUiBinder
			extends UiBinder<Widget, EzeeCreateUpdateDeleteLeaseMetaData> {
	}

	@UiField
	ListBox lstType;

	@UiField
	TextBox txtValue;

	@UiField
	RichTextArea txtDescription;

	@UiField
	Button btnClose;

	@UiField
	Button btnSave;

	@UiField
	Button btnDelete;

	public EzeeCreateUpdateDeleteLeaseMetaData(EzeeEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<EzeeLeaseMetaData> handler, String[] headers) {
		this(cache, handler, null, create, headers);
	}

	public EzeeCreateUpdateDeleteLeaseMetaData(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeeLeaseMetaData> handler, final EzeeLeaseMetaData entity,
			final EzeeCreateUpdateDeleteEntityType type, final String[] headers) {
		super(cache, handler, entity, type, headers);
		setWidget(uiBinder.createAndBindUi(this));
		setModal(true);
	}

	@Override
	protected void initialise() {
		lstType.setItemSelected(getItemIndex(entity.getType(), lstType), true);
		txtValue.setText(entity.getValue());
		txtDescription.setText(entity.getDescription());
	}

	@Override
	protected void bind() {
		if (entity == null) {
			entity = new EzeeLeaseMetaData();
			entity.setDate(DATE_UTILS.toString(new Date()));
		}
		entity.setType(lstType.getSelectedValue());
		entity.setValue(txtValue.getText());
		entity.setDescription(txtDescription.getText());
	}

	@Override
	public void show() {
		initForm();
		loadEntities();
		switch (type) {
		case create:
			setText(headers[NEW_HEADER_INDEX]);
			setFocus(lstType);
			break;
		case update:
			setText(headers[EDIT_HEADER_INDEX]);
			initialise();
			setFocus(lstType);
			break;
		case delete:
			setText(headers[EDIT_HEADER_INDEX]);
			initialise();
			btnDelete.setEnabled(true);
			disable();
			break;
		}
		super.show();
	}

	private void initForm() {
		KeyPressHandler handler = new EzeeRichTextAreaUtils.TabKeyPressHandler(new Widget[] { txtValue },
				new Widget[] { btnSave });
		txtDescription.addKeyPressHandler(handler);

	}

	private void disable() {
		lstType.setEnabled(false);
		txtValue.setEnabled(false);
		txtDescription.setEnabled(false);
		btnSave.setEnabled(false);
	}

	private void loadEntities() {
		EzeeListBoxUtils.loadKeyedEnums(EzeeLeaseMetaDataType.values(), lstType);
	}

	@UiHandler("btnClose")
	public void onCloseClick(ClickEvent event) {
		close();
	}

	@UiHandler("btnSave")
	public void onSaveClick(ClickEvent event) {
		bind();
		handler.onSave(entity);
		close();
	}

	@UiHandler("btnDelete")
	public void onDeleteClick(ClickEvent event) {
		bind();
		handler.onDelete(entity);
		close();
	}
}