package com.ezee.client.crud.leasecategory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;

public class EzeeCreateUpdateLeaseCategory extends DialogBox {

	private static EzeeCreateUpdateLeaseCategoryUiBinder uiBinder = GWT
			.create(EzeeCreateUpdateLeaseCategoryUiBinder.class);

	interface EzeeCreateUpdateLeaseCategoryUiBinder extends UiBinder<Widget, EzeeCreateUpdateLeaseCategory> {
	}

	public EzeeCreateUpdateLeaseCategory() {
		setWidget(uiBinder.createAndBindUi(this));
	}
}
