package com.ezee.client.crud.project.item.payment;

import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.create;
import static com.ezee.web.common.ui.utils.EzeeListBoxUtils.loadEnums;

import com.ezee.model.entity.enums.EzeePaymentType;
import com.ezee.model.entity.project.EzeeProjectPayment;
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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EzeeCreateUpdateDeleteProjectItemPayment extends EzeeCreateUpdateDeleteTaxableEntity<EzeeProjectPayment> {

	private static EzeeCreateUpdateDeleteProjectItemPaymentUiBinder uiBinder = GWT
			.create(EzeeCreateUpdateDeleteProjectItemPaymentUiBinder.class);

	@UiField
	TextBox txtInvoiceNumber;

	@UiField
	TextBox txtChequeNumber;

	@UiField
	ListBox lstPaymentTypes;

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

	private void loadTypes() {
		loadEnums(EzeePaymentType.values(), lstPaymentTypes);
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