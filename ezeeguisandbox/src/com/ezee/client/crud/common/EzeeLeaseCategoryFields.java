package com.ezee.client.crud.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class EzeeLeaseCategoryFields extends Composite {

	private static EzeeLeaseCategoryFieldsUiBinder uiBinder = GWT.create(EzeeLeaseCategoryFieldsUiBinder.class);

	interface EzeeLeaseCategoryFieldsUiBinder extends UiBinder<Widget, EzeeLeaseCategoryFields> {
	}

	public EzeeLeaseCategoryFields() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
