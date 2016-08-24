package com.ezee.web.common.ui.register;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.model.entity.enums.EzeeUserType.read_only;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.EzeeWebCommonConstants.USER_SERVICE;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showDefaultCursor;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showWaitCursor;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.model.entity.EzeeUser;
import com.ezee.web.common.ui.EzeeUserResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author siborg
 *
 */
public class EzeeRegister extends Composite {

	private static final Logger log = Logger.getLogger("EzeeRegister");

	private static EzeeRegisterUiBinder uiBinder = GWT.create(EzeeRegisterUiBinder.class);

	@UiField
	HTML lblTitle;

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
	Button btnBack;

	@UiField
	Button btnRegsiter;

	@UiField
	Label lblError;

	private final EzeeRegisterListener listener;

	interface EzeeRegisterUiBinder extends UiBinder<Widget, EzeeRegister> {
	}

	public EzeeRegister(final String text, final EzeeRegisterListener listener) {
		this.listener = listener;
		initWidget(uiBinder.createAndBindUi(this));
		lblTitle.setText(text);
	}

	@UiHandler("btnBack")
	void onBackClick(ClickEvent event) {
		clear();
		listener.cancelRegistration();
	}

	@UiHandler("btnRegsiter")
	void onRegsiterClick(ClickEvent event) {
		clearError();
		if (validRegistration()) {
			showWaitCursor();
			EzeeUser created = new EzeeUser(txtFirstname.getText(), txtLastname.getText(), txtUsername.getText(),
					txtPassword.getText(), txtEmail.getText(), read_only, DATE_UTILS.toString(new Date()), null);
			USER_SERVICE.register(created, new AsyncCallback<EzeeUserResult>() {

				@Override
				public void onSuccess(final EzeeUserResult result) {
					if (result.getUser() != null) {
						clear();
						listener.registrationSuccess(result.getUser());
					} else {
						setError(result.getError());
						showDefaultCursor();
					}
				}

				@Override
				public void onFailure(Throwable caught) {
					String msg = "Unknwon error registering user with username '" + txtUsername.getText() + "'.";
					log.log(Level.SEVERE, msg, caught);
					setError(msg);
					showDefaultCursor();
				}
			});
		}
	}

	private void clear() {
		txtUsername.setText(EMPTY_STRING);
		txtPassword.setText(EMPTY_STRING);
		txtFirstname.setText(EMPTY_STRING);
		txtLastname.setText(EMPTY_STRING);
		txtEmail.setText(EMPTY_STRING);
		clearError();
	}

	private boolean validRegistration() {
		if (!hasLength(txtUsername.getText())) {
			setError("Regsitration username cannot be empty.");
			return false;
		}
		if (!hasLength(txtPassword.getText())) {
			setError("Regsitration password cannot be empty.");
			return false;
		}
		if (!hasLength(txtEmail.getText())) {
			setError("Regsitration email cannot be empty.");
			return false;
		}
		return true;
	}

	private void setError(String text) {
		lblError.setText(text);
	}

	private void clearError() {
		lblError.setText(EMPTY_STRING);
	}
}