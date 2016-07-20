package com.ezee.client.crud.leasenote;

import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.create;

import java.util.Date;

import com.ezee.model.entity.lease.EzeeLeaseNote;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntity;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.utils.EzeeRichTextAreaUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.Widget;

public class EzeeCreateUpdateDeleteLeaseNote extends EzeeCreateUpdateDeleteEntity<EzeeLeaseNote> {

	private static EzeeCreateUpdateDeleteLeaseNoteUiBinder uiBinder = GWT
			.create(EzeeCreateUpdateDeleteLeaseNoteUiBinder.class);

	interface EzeeCreateUpdateDeleteLeaseNoteUiBinder extends UiBinder<Widget, EzeeCreateUpdateDeleteLeaseNote> {
	}

	@UiField
	RichTextArea txtNoteValue;

	@UiField
	Button btnClose;

	@UiField
	Button btnSave;

	@UiField
	Button btnDelete;

	public EzeeCreateUpdateDeleteLeaseNote(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeeLeaseNote> handler, final String[] headers) {
		this(cache, handler, null, create, headers);
	}

	public EzeeCreateUpdateDeleteLeaseNote(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeeLeaseNote> handler, final EzeeLeaseNote entity,
			final EzeeCreateUpdateDeleteEntityType type, final String[] headers) {
		super(cache, handler, entity, type, headers);
		setWidget(uiBinder.createAndBindUi(this));
		setModal(true);
	}

	@Override
	protected void initialise() {
		txtNoteValue.setText(entity.getNote());
	}

	@Override
	protected void bind() {
		if (entity == null) {
			entity = new EzeeLeaseNote();
			entity.setDate(DATE_UTILS.toString(new Date()));
		}
		entity.setNote(txtNoteValue.getText());
	}

	@Override
	public void show() {
		initForm();
		switch (type) {
		case create:
			setText(headers[NEW_HEADER_INDEX]);
			setFocus(txtNoteValue);
			break;
		case update:
			setText(headers[EDIT_HEADER_INDEX]);
			initialise();
			setFocus(txtNoteValue);
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
		KeyPressHandler handler = new EzeeRichTextAreaUtils.TabKeyPressHandler(null, new Widget[] { btnSave });
		txtNoteValue.addKeyPressHandler(handler);

	}

	private void disable() {
		txtNoteValue.setEnabled(false);
		btnSave.setEnabled(false);
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