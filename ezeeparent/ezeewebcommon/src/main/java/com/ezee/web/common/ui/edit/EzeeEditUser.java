package com.ezee.web.common.ui.edit;

import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.EzeeWebCommonConstants.EDIT_ERROR;
import static com.ezee.web.common.EzeeWebCommonConstants.EDIT_USER;
import static com.ezee.web.common.EzeeWebCommonConstants.USER_SERVICE;
import static com.ezee.web.common.ui.dialog.EzeeMessageDialog.showNew;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showDefaultCursor;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showWaitCursor;

import java.util.Date;

import com.ezee.model.entity.EzeeUser;
import com.ezee.web.common.ui.EzeeUserResult;
import com.ezee.web.common.ui.dialog.EzeeDialog;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EzeeEditUser extends EzeeDialog {

	private static EzeeEditUserUiBinder uiBinder = GWT.create(EzeeEditUserUiBinder.class);

	@UiField
	TextBox txtFirstname;

	@UiField
	TextBox txtLastname;

	@UiField
	TextBox txtUsername;

	@UiField
	PasswordTextBox txtOldPassword;

	@UiField
	PasswordTextBox txtNewPassword;

	@UiField
	TextBox txtEmail;

	@UiField
	Button btnSave;

	@UiField
	Button btnCancel;

	private final String username;

	private EzeeUser existing;

	interface EzeeEditUserUiBinder extends UiBinder<Widget, EzeeEditUser> {
	}

	public EzeeEditUser(final String username) {
		setText(EDIT_USER);
		setWidget(uiBinder.createAndBindUi(this));
		this.username = username;
		init();
	}

	private void init() {
		txtUsername.setText(username);
		txtUsername.setEnabled(false);
		loadUserDetails();
	}

	private void loadUserDetails() {
		showWaitCursor();
		USER_SERVICE.retrieve(username, new AsyncCallback<EzeeUserResult>() {
			@Override
			public void onSuccess(final EzeeUserResult result) {
				showDefaultCursor();
				if (!hasLength(result.getError()) && result.getUser() != null) {
					existing = result.getUser();
					txtFirstname.setText(existing.getFirstname());
					txtLastname.setText(existing.getLastname());
					txtEmail.setText(existing.getEmail());
				} else {
					showNew(EDIT_ERROR, result.getError());
				}
			}

			@Override
			public void onFailure(final Throwable caught) {
				showDefaultCursor();
				showNew(EDIT_ERROR,
						"Unknown error retrieving user with username '" + username + "'.  See log for details.");
			}
		});
	}

	@UiHandler("btnSave")
	void onSaveClick(ClickEvent event) {
		if (existing != null) {
			if (validateUser()) {
				showWaitCursor();
				EzeeUser edited = new EzeeUser(existing.getId(), txtFirstname.getText(), txtLastname.getText(),
						username, txtNewPassword.getText(), txtEmail.getText(), existing.getType(),
						existing.getCreated(), DATE_UTILS.toString(new Date()));

				USER_SERVICE.edit(existing, edited, txtOldPassword.getText(), new AsyncCallback<EzeeUserResult>() {

					@Override
					public void onFailure(Throwable caught) {
						showDefaultCursor();
						showNew(EDIT_ERROR,
								"Unknown error editing user with username '" + username + "'.  See log for details.");
					}

					@Override
					public void onSuccess(EzeeUserResult result) {
						showDefaultCursor();
						if (!hasLength(result.getError()) && result.getUser() != null) {
							close();
						} else {
							showNew(EDIT_ERROR, result.getError());
						}
					}
				});
			}
		}
	}

	@UiHandler("btnCancel")
	void onCloseClick(ClickEvent event) {
		close();
	}

	private boolean validateUser() {
		if (!hasLength(txtOldPassword.getText())) {
			showNew(EDIT_ERROR, "Existing password cannot be empty.");
			return false;
		} else if (!hasLength(txtNewPassword.getText())) {
			showNew(EDIT_ERROR, "New password cannot be empty.");
			return false;
		} else if (!hasLength(txtEmail.getText())) {
			showNew(EDIT_ERROR, "Email cannot be empty.");
			return false;
		}
		return true;
	}

	@Override
	public void show() {
		setFocus(txtFirstname);
		super.show();
	}
}