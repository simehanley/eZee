package com.ezee.web.common.ui.register;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EzeeRegister extends Composite {

	private static EzeeRegisterUiBinder uiBinder = GWT.create(EzeeRegisterUiBinder.class);

	@UiField
	TextBox txtUsername;

	@UiField
	PasswordTextBox txtPassword;

	@UiField
	TextBox txtFirstname;

	@UiField
	TextBox txtLastname;

	@UiField
	TextBox txtEmail;

	@UiField
	Button btnRegsiter;

	@UiField
	Button btnBack;

	@UiField
	Label lblError;

	interface EzeeRegisterUiBinder extends UiBinder<Widget, EzeeRegister> {
	}

	public EzeeRegister() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
