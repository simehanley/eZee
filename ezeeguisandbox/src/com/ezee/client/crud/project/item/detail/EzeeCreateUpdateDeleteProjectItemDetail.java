package com.ezee.client.crud.project.item.detail;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.CheckBox;

public class EzeeCreateUpdateDeleteProjectItemDetail extends DialogBox {

	private static EzeeCreateUpdateDeleteProjectItemDetailUiBinder uiBinder = GWT
			.create(EzeeCreateUpdateDeleteProjectItemDetailUiBinder.class);
	@UiField Button btnDelete;
	@UiField Button btnSave;
	@UiField Button btnClose;
	@UiField TextBox txtAmount;
	@UiField TextBox txtTax;
	@UiField TextBox txtTotal;
	@UiField ListBox lstType;
	@UiField CheckBox chkManualTax;
	@UiField CheckBox chkReverseTax;

	interface EzeeCreateUpdateDeleteProjectItemDetailUiBinder
			extends UiBinder<Widget, EzeeCreateUpdateDeleteProjectItemDetail> {
	}

	public EzeeCreateUpdateDeleteProjectItemDetail() {
		setWidget(uiBinder.createAndBindUi(this));
	}

}
