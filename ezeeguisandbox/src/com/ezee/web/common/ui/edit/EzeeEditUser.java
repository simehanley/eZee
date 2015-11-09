package com.ezee.web.common.ui.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Button;

public class EzeeEditUser extends Composite {

	private static EzeeEditUserUiBinder uiBinder = GWT.create(EzeeEditUserUiBinder.class);
	@UiField TextBox txtFirstname;
	@UiField TextBox txtLastname;
	@UiField TextBox txtUsername;
	@UiField PasswordTextBox txtOldPassword;
	@UiField PasswordTextBox txtNewPassword;
	@UiField TextBox txtEmail;
	@UiField Button btnSave;
	@UiField Button btnCancel;

	interface EzeeEditUserUiBinder extends UiBinder<Widget, EzeeEditUser> {
	}

	public EzeeEditUser() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
