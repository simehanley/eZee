package com.ezee.web.common.ui.crud.payee;

import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.create;

import java.util.Date;

import com.ezee.model.entity.EzeePayee;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.crud.common.EzeeCreateUpdateDeleteFinancialEntity;
import com.ezee.web.common.ui.crud.common.EzeePayeeBank;
import com.ezee.web.common.ui.crud.common.EzeePayerPayeeCommon;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

public abstract class EzeeCreateUpdateDeletePayee<T extends EzeePayee>
		extends EzeeCreateUpdateDeleteFinancialEntity<T> {

	private static EzeeCreateUpdateDeletePayeeUiBinder uiBinder = GWT.create(EzeeCreateUpdateDeletePayeeUiBinder.class);

	interface EzeeCreateUpdateDeletePayeeUiBinder extends UiBinder<Widget, EzeeCreateUpdateDeletePayee<?>> {
	}

	@UiField
	EzeePayerPayeeCommon payee;

	@UiField
	EzeePayeeBank payeebank;

	public EzeeCreateUpdateDeletePayee(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<T> handler, final String[] headers) {
		this(cache, handler, null, create, headers);
	}

	public EzeeCreateUpdateDeletePayee(final EzeeEntityCache cache,
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
			setFocus(payee.getTxtName());
			btnDelete.setEnabled(false);
			break;
		case update:
			setText(headers[EDIT_HEADER_INDEX]);
			setFocus(payee.getTxtName());
			initialise();
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
		payee.setName(entity.getName());
		payee.setContact(entity.getContact());
		payee.setAddressLine1(entity.getAddressLineOne());
		payee.setAddressLine2(entity.getAddressLineTwo());
		payee.setSuburb(entity.getSuburb());
		payee.setCity(entity.getCity());
		payee.setState(entity.getState());
		payee.setPostCode(entity.getPostcode());
		payee.setPhone(entity.getPhone());
		payee.setFax(entity.getFax());
		payee.setEmail(entity.getEmail());
		payeebank.setBank(entity.getBank());
		payeebank.setAccountName(entity.getAccountName());
		payeebank.setAccountNumber(entity.getAccountNumber());
		payeebank.setAccountBsb(entity.getAccountBsb());
	}

	@Override
	protected void bind() {
		if (entity == null) {
			entity = createEntity();
			entity.setCreated(DATE_UTILS.toString(new Date()));
			entity.setUpdated(null);
		} else {
			entity.setUpdated(DATE_UTILS.toString(new Date()));
		}
		entity.setName(payee.getName());
		entity.setContact(payee.getContact());
		entity.setAddressLineOne(payee.getAddressLine1());
		entity.setAddressLineTwo(payee.getAddressLine2());
		entity.setSuburb(payee.getSuburb());
		entity.setCity(payee.getCity());
		entity.setState(payee.getState());
		entity.setPostcode(payee.getPostCode());
		entity.setPhone(payee.getPhone());
		entity.setFax(payee.getFax());
		entity.setEmail(payee.getEmail());
		entity.setBank(payeebank.getBank());
		entity.setAccountName(payeebank.getAccountName());
		entity.setAccountNumber(payeebank.getAccountNumber());
		entity.setAccountBsb(payeebank.getAccountBsb());
	}

	private void disable() {
		payee.disable();
		payeebank.disable();
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