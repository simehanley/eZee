package com.ezee.client.crud.project.item.payment;

import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.common.web.EzeeFormatUtils.getDateBoxFormat;
import static com.ezee.model.entity.enums.EzeePaymentType.cheque;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.create;
import static com.ezee.web.common.ui.utils.EzeeListBoxUtils.getItemIndex;
import static com.ezee.web.common.ui.utils.EzeeListBoxUtils.loadEnums;

import java.util.Date;

import com.ezee.model.entity.enums.EzeePaymentType;
import com.ezee.model.entity.project.EzeeProjectPayment;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteTaxableEntity;
import com.ezee.web.common.ui.utils.EzeeTextBoxUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class EzeeCreateUpdateDeleteProjectItemPayment extends EzeeCreateUpdateDeleteTaxableEntity<EzeeProjectPayment> {

	private static EzeeCreateUpdateDeleteProjectItemPaymentUiBinder uiBinder = GWT
			.create(EzeeCreateUpdateDeleteProjectItemPaymentUiBinder.class);

	@UiField
	DateBox dtPaymentDate;

	@UiField
	TextBox txtInvoiceNumber;

	@UiField
	TextBox txtChequeNumber;

	@UiField
	ListBox lstPaymentType;

	@UiField
	RichTextArea rtxDescription;

	@UiField
	Button btnDelete;

	@UiField
	Button btnSave;

	@UiField
	Button btnClose;

	interface EzeeCreateUpdateDeleteProjectItemPaymentUiBinder
			extends UiBinder<Widget, EzeeCreateUpdateDeleteProjectItemPayment> {
	}

	public EzeeCreateUpdateDeleteProjectItemPayment(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeeProjectPayment> handler, final String[] headers) {
		this(cache, handler, null, create, headers);
	}

	public EzeeCreateUpdateDeleteProjectItemPayment(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeeProjectPayment> handler, final EzeeProjectPayment entity,
			final EzeeCreateUpdateDeleteEntityType type, final String[] headers) {
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
	protected void initForm() {
		super.initForm();
		dtPaymentDate.setFormat(getDateBoxFormat());
		dtPaymentDate.setValue(new Date());
		lstPaymentType.setItemSelected(getItemIndex(EzeePaymentType.cheque.name(), lstPaymentType), true);
		lstPaymentType.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				EzeePaymentType type = EzeePaymentType.valueOf(lstPaymentType.getSelectedItemText());
				boolean enableChequeFields = (type == cheque);
				enableCheckFields(enableChequeFields);
			}
		});
		FocusHandler focusHandler = new EzeeTextBoxUtils.TextBoxFocusHandler();
		txtChequeNumber.addFocusHandler(focusHandler);
		txtInvoiceNumber.addFocusHandler(focusHandler);
	}

	@Override
	protected void initialise() {
		super.initialise();
		dtPaymentDate.setValue(DATE_UTILS.fromString(entity.getPaymentDate()));
		lstPaymentType.setItemSelected(getItemIndex(entity.getType().toString(), lstPaymentType), true);
		rtxDescription.setText(entity.getDescription());
		txtChequeNumber.setText(entity.getChequeNumber());
		txtInvoiceNumber.setText(entity.getInvoiceRef());
		boolean enableChequeFields = (entity.getType() == cheque);
		enableCheckFields(enableChequeFields);
	}

	@Override
	protected void bind() {
		if (entity == null) {
			entity = new EzeeProjectPayment();
			entity.setCreated(DATE_UTILS.toString(new Date()));
		} else {
			entity.setUpdated(DATE_UTILS.toString(new Date()));
		}
		entity.setPaymentDate(DATE_UTILS.toString(dtPaymentDate.getValue()));
		entity.setDescription(rtxDescription.getText());
		entity.setType(EzeePaymentType.get(lstPaymentType.getSelectedItemText()));
		if (hasLength(txtChequeNumber.getText())) {
			entity.setChequeNumber(txtChequeNumber.getText());
		} else {
			entity.setChequeNumber(null);
		}
		if (hasLength(txtInvoiceNumber.getText())) {
			entity.setInvoiceRef(txtInvoiceNumber.getText());
		} else {
			entity.setInvoiceRef(null);
		}
		super.bind();
	}

	@Override
	protected void disable() {
		super.disable();
		txtChequeNumber.setEnabled(false);
		txtInvoiceNumber.setEnabled(false);
		dtPaymentDate.setEnabled(false);
		rtxDescription.setEnabled(false);
		btnSave.setEnabled(false);
	}

	private void loadTypes() {
		loadEnums(EzeePaymentType.values(), lstPaymentType);
	}

	private void enableCheckFields(boolean enableChequeFields) {
		txtChequeNumber.setEnabled(enableChequeFields);
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