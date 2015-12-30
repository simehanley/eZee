package com.ezee.client.crud.project.item.payment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;

public class EzeeCreateUpdateDeleteProjectItemPayment extends DialogBox {

	private static EzeeCreateUpdateDeleteProjectItemPaymentUiBinder uiBinder = GWT
			.create(EzeeCreateUpdateDeleteProjectItemPaymentUiBinder.class);

	interface EzeeCreateUpdateDeleteProjectItemPaymentUiBinder
			extends UiBinder<Widget, EzeeCreateUpdateDeleteProjectItemPayment> {
	}

	public EzeeCreateUpdateDeleteProjectItemPayment() {
		setWidget(uiBinder.createAndBindUi(this));
	}

}
