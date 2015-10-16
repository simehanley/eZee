package com.ezee.client.crud.payer;

import com.ezee.client.crud.common.EzeePayerPayeeCommon;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;

public class EzeeCreateUpdatePayer extends DialogBox {

	private static EzeeCreateUpdatePayerUiBinder uiBinder = GWT.create(EzeeCreateUpdatePayerUiBinder.class);

	@UiField
	EzeePayerPayeeCommon payer;

	@UiField
	Button btnSave;

	@UiField
	Button btnClose;

	interface EzeeCreateUpdatePayerUiBinder extends UiBinder<Widget, EzeeCreateUpdatePayer> {
	}

	public EzeeCreateUpdatePayer() {
		setWidget(uiBinder.createAndBindUi(this));
	}
}