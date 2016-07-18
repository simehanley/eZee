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

public class EzeeConfirmDialog extends DialogBox {

	private static EzeeConfirmDialogUiBinder uiBinder = GWT.create(EzeeConfirmDialogUiBinder.class);

	interface EzeeConfirmDialogUiBinder extends UiBinder<Widget, EzeeConfirmDialog> {
	}

	@UiField
	Button btnOk;

	@UiField
	Button btnCancel;

	@UiField
	RichTextArea txtMessage;

	private final EzeeConfirmDialogResultHandler handler;

	public EzeeConfirmDialog(final String heading, final String message, final EzeeConfirmDialogResultHandler handler) {
		super(false, true);
		setWidget(uiBinder.createAndBindUi(this));
		setText(heading);
		txtMessage.setText(message);
		this.handler = handler;
	}

	@UiHandler("btnCancel")
	void onCancelClick(ClickEvent event) {
		handler.confrimResult(EzeeConfirmDialogResult.cancel);
		close();
	}

	@UiHandler("btnOk")
	void onOKClick(ClickEvent event) {
		handler.confrimResult(EzeeConfirmDialogResult.ok);
		close();
	}

	public void close() {
		this.hide(true);
	}

	public static void showNew(final String heading, final String message,
			final EzeeConfirmDialogResultHandler handler) {
		new EzeeConfirmDialog(heading, message, handler).center();
	}
}