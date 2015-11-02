package com.ezee.web.common.ui.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

public class EzeeLogin extends Composite {

	private static EzeeLoginUiBinder uiBinder = GWT.create(EzeeLoginUiBinder.class);

	@UiField
	TextBox txtUsername;

	@UiField
	PasswordTextBox txtPassword;

	@UiField
	Button btnLogin;

	@UiField
	Button btnRegister;

	@UiField
	CheckBox chkRememberMe;
	@UiField HTML lblTitle;
	@UiField Label lblError;

	interface EzeeLoginUiBinder extends UiBinder<Widget, EzeeLogin> {
	}

	public EzeeLogin() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}