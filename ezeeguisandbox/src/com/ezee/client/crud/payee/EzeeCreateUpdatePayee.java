package com.ezee.client.crud.payee;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;

public class EzeeCreateUpdatePayee extends Composite {

	private static EzeeCreateUpdatePayeeUiBinder uiBinder = GWT.create(EzeeCreateUpdatePayeeUiBinder.class);
	@UiField Button btnClose;
	@UiField Button btnSave;

	interface EzeeCreateUpdatePayeeUiBinder extends UiBinder<Widget, EzeeCreateUpdatePayee> {
	}

	public EzeeCreateUpdatePayee() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
