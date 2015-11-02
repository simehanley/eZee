package com.ezee.web.common.ui.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;

public class EzeeMessageDialog extends DialogBox {

	private static EzeeMessageDialogUiBinder uiBinder = GWT.create(EzeeMessageDialogUiBinder.class);
	@UiField Button btnClose;
	@UiField Label lblMessage;

	interface EzeeMessageDialogUiBinder extends UiBinder<Widget, EzeeMessageDialog> {
	}

	public EzeeMessageDialog(final String heading, final String message) {
		super(false, true);
		setWidget(uiBinder.createAndBindUi(this));
		setText(heading);
	}

	public void close() {
		this.hide(true);
	}
}