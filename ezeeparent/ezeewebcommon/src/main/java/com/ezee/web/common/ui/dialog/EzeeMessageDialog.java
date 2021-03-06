package com.ezee.web.common.ui.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.Widget;

public class EzeeMessageDialog extends DialogBox {

	private static EzeeMessageDialogUiBinder uiBinder = GWT.create(EzeeMessageDialogUiBinder.class);

	@UiField
	Button btnClose;

	@UiField
	RichTextArea txtMessage;

	interface EzeeMessageDialogUiBinder extends UiBinder<Widget, EzeeMessageDialog> {
	}

	public EzeeMessageDialog(final String heading, final String message) {
		super(false, true);
		setWidget(uiBinder.createAndBindUi(this));
		setText(heading);
		txtMessage.setText(message);
	}

	@UiHandler("btnClose")
	void onCloseClick(ClickEvent event) {
		close();
	}

	public void close() {
		this.hide(true);
	}

	public static void showNew(final String heading, final String message) {
		new EzeeMessageDialog(heading, message).center();
	}
}