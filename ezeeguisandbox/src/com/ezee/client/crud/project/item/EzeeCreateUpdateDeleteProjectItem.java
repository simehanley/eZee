package com.ezee.client.crud.project.item;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;

public class EzeeCreateUpdateDeleteProjectItem extends DialogBox {

	private static EzeeCreateUpdateDeleteProjectItemUiBinder uiBinder = GWT
			.create(EzeeCreateUpdateDeleteProjectItemUiBinder.class);
	@UiField TextBox txtName;

	interface EzeeCreateUpdateDeleteProjectItemUiBinder extends UiBinder<Widget, EzeeCreateUpdateDeleteProjectItem> {
	}

	public EzeeCreateUpdateDeleteProjectItem() {
		setWidget(uiBinder.createAndBindUi(this));
	}

}
