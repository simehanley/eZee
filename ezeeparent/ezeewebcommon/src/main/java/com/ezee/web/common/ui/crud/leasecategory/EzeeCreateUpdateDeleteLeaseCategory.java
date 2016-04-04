package com.ezee.web.common.ui.crud.leasecategory;

import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.EzeeWebCommonConstants.ENTITY_SERVICE;
import static com.ezee.web.common.EzeeWebCommonConstants.ERROR;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.create;
import static com.ezee.web.common.ui.dialog.EzeeMessageDialog.showNew;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showDefaultCursor;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showWaitCursor;

import java.util.Date;
import java.util.logging.Level;

import com.ezee.model.entity.lease.EzeeLeaseCategory;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.crud.common.EzeeCreateUpdateDeleteFinancialEntity;
import com.ezee.web.common.ui.crud.common.EzeeLeaseCatgoryFields;
import com.ezee.web.common.ui.crud.common.EzeePayerPayeeCommon;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class EzeeCreateUpdateDeleteLeaseCategory extends EzeeCreateUpdateDeleteFinancialEntity<EzeeLeaseCategory> {

	private static EzeeCreateUpdateDeleteLeaseCategoryUiBinder uiBinder = GWT
			.create(EzeeCreateUpdateDeleteLeaseCategoryUiBinder.class);

	interface EzeeCreateUpdateDeleteLeaseCategoryUiBinder
			extends UiBinder<Widget, EzeeCreateUpdateDeleteLeaseCategory> {
	}

	@UiField
	EzeePayerPayeeCommon payee;

	@UiField
	EzeeLeaseCatgoryFields category;

	public EzeeCreateUpdateDeleteLeaseCategory(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeeLeaseCategory> handler, final String[] headers) {
		this(cache, handler, null, create, headers);
	}

	public EzeeCreateUpdateDeleteLeaseCategory(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeeLeaseCategory> handler, final EzeeLeaseCategory entity,
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
		category.setCompany(entity.getCategoryCompany());
		category.setAbn(entity.getAbn());
		category.setBank(entity.getBank());
		category.setAccountName(entity.getAccountName());
		category.setAccountNumber(entity.getAccountNumber());
		category.setBsb(entity.getAccountBsb());

	}

	@Override
	protected void bind() {
		if (entity == null) {
			entity = new EzeeLeaseCategory();
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
		entity.setCategoryCompany(category.getCompany());
		entity.setAbn(category.getAbn());
		entity.setBank(category.getBank());
		entity.setAccountName(category.getAccountName());
		entity.setAccountNumber(category.getAccountNumber());
		entity.setAccountBsb(category.getBsb());
	}

	@UiHandler("btnSave")
	public void onSaveClick(ClickEvent event) {
		super.onSaveClick(event);
	}

	@UiHandler("btnDelete")
	public void onDeleteClick(ClickEvent event) {
		super.onDeleteClick(event);
	}

	private void disable() {
		payee.disable();
		category.disable();
		btnSave.setEnabled(false);
	}
}