package com.ezee.web.common.ui.crud.payer;

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

import com.ezee.model.entity.EzeePayer;
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

public abstract class EzeeCreateUpdateDeletePayer<T extends EzeePayer>
		extends EzeeCreateUpdateDeleteFinancialEntity<T> {

	private static EzeeCreateUpdateDeletePayerUiBinder uiBinder = GWT.create(EzeeCreateUpdateDeletePayerUiBinder.class);

	interface EzeeCreateUpdateDeletePayerUiBinder extends UiBinder<Widget, EzeeCreateUpdateDeletePayer<?>> {
	}

	@UiField
	EzeePayerPayeeCommon payer;

	public EzeeCreateUpdateDeletePayer(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<T> handler, final String[] headers) {
		this(cache, handler, null, create, headers);
	}

	public EzeeCreateUpdateDeletePayer(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<T> handler, final T entity,
			final EzeeCreateUpdateDeleteEntityType type, final String[] headers) {
		super(cache, handler, entity, type, headers);
		setWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void show() {
		switch (type) {
		case create:
			setText(headers[NEW_HEADER_INDEX]);
			setFocus(payer.getTxtName());
			btnDelete.setEnabled(false);
			break;
		case update:
			setText(headers[EDIT_HEADER_INDEX]);
			initialise();
			setFocus(payer.getTxtName());
			btnDelete.setEnabled(false);
			break;
		case delete:
			setText(headers[DELETE_HEADER_INDEX]);
			initialise();
			disable();
			break;
		}
		super.show();
	}

	@Override
	protected void initialise() {
		payer.setName(entity.getName());
		payer.setContact(entity.getContact());
		payer.setAddressLine1(entity.getAddressLineOne());
		payer.setAddressLine2(entity.getAddressLineTwo());
		payer.setSuburb(entity.getSuburb());
		payer.setCity(entity.getCity());
		payer.setState(entity.getState());
		payer.setPostCode(entity.getPostcode());
		payer.setPhone(entity.getPhone());
		payer.setFax(entity.getFax());
		payer.setEmail(entity.getEmail());
	}

	@Override
	protected void bind() {
		if (entity == null) {
			entity = createEntity();
			entity.setCreated(DATE_UTILS.toString(new Date()));
		} else {
			entity.setUpdated(DATE_UTILS.toString(new Date()));
		}
		entity.setName(payer.getName());
		entity.setContact(payer.getContact());
		entity.setAddressLineOne(payer.getAddressLine1());
		entity.setAddressLineTwo(payer.getAddressLine2());
		entity.setSuburb(payer.getSuburb());
		entity.setCity(payer.getCity());
		entity.setState(payer.getState());
		entity.setPostcode(payer.getPostCode());
		entity.setPhone(payer.getPhone());
		entity.setFax(payer.getFax());
		entity.setEmail(payer.getEmail());
	}

	private void disable() {
		payer.disable();
		btnSave.setEnabled(false);
	}
	
	@UiHandler("btnSave")
	public void onSaveClick(ClickEvent event) {
		super.onSaveClick(event);
	}

	@UiHandler("btnDelete")
	public void onDeleteClick(ClickEvent event) {
		super.onDeleteClick(event);
	}

	protected abstract T createEntity();
}