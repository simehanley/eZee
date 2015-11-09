package com.ezee.web.common.ui.login;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.web.common.EzeeWebCommonConstants.AUTO_LOGIN_HELPER;
import static com.ezee.web.common.EzeeWebCommonConstants.USER_SERVICE;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showDefaultCursor;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showWaitCursor;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ENTER;
import static java.util.logging.Level.SEVERE;

import java.util.logging.Logger;

import com.ezee.web.common.ui.EzeeUserResult;
import com.ezee.web.common.ui.register.EzeeRegisterListener;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
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
public class EzeeLogin extends Composite {

	private static final Logger log = Logger.getLogger("EzeeLogin");

	private static EzeeLoginUiBinder uiBinder = GWT.create(EzeeLoginUiBinder.class);

	@UiField
	HTML lblTitle;

	@UiField
	TextBox txtUsername;

	@UiField
	PasswordTextBox txtPassword;

	@UiField
	CheckBox chkRememberMe;

	@UiField
	Button btnLogin;

	@UiField
	Button btnRegister;

	@UiField
	Label lblError;

	private final EzeeLoginListener loginListener;

	private final EzeeRegisterListener registerListener;

	interface EzeeLoginUiBinder extends UiBinder<Widget, EzeeLogin> {
	}

	public EzeeLogin(final String text, final EzeeLoginListener loginListener,
			final EzeeRegisterListener registerListener) {
		this.loginListener = loginListener;
		this.registerListener = registerListener;
		initWidget(uiBinder.createAndBindUi(this));
		lblTitle.setText(text);
		init();
	}

	private void init() {
		if (AUTO_LOGIN_HELPER.isSupported()) {
			chkRememberMe.setVisible(true);
		} else {
			chkRememberMe.setVisible(false);
		}
		initForm();
	}

	private void initForm() {
		chkRememberMe.setValue(AUTO_LOGIN_HELPER.isRememberMeSet());
		txtPassword.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(final KeyPressEvent event) {
				if (event.getNativeEvent().getKeyCode() == KEY_ENTER) {
					login();
				}
			}
		});
		chkRememberMe.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(final ValueChangeEvent<Boolean> event) {
				if (AUTO_LOGIN_HELPER.isSupported()) {
					if (event.getValue()) {
						AUTO_LOGIN_HELPER.setRememberMe();
					} else {
						AUTO_LOGIN_HELPER.clearAutoLogin();
					}
				}
			}
		});
	}

	@UiHandler("btnLogin")
	void onLoginClick(ClickEvent event) {
		login();
	}

	@UiHandler("btnRegister")
	void onRegsiterClick(ClickEvent event) {
		clear();
		registerListener.requestNewRegistration();
	}

	private void clear() {
		txtUsername.setText(EMPTY_STRING);
		txtPassword.setText(EMPTY_STRING);
		clearError();
	}

	private boolean validLogin() {
		if (!hasLength(txtUsername.getText())) {
			setError("Login username cannot be empty.");
			return false;
		}
		if (!hasLength(txtPassword.getText())) {
			setError("Login password cannot be empty.");
			return false;
		}
		return true;
	}

	private void login() {
		clearError();
		if (validLogin()) {
			showWaitCursor();
			USER_SERVICE.authenticate(txtUsername.getText(), txtPassword.getText(),
					new AsyncCallback<EzeeUserResult>() {
						@Override
						public void onFailure(Throwable caught) {
							String msg = "Unknwon error authenticating user with username '" + txtUsername.getText()
									+ "'.";
							log.log(SEVERE, msg, caught);
							setError(msg);
							showDefaultCursor();
						}

						@Override
						public void onSuccess(final EzeeUserResult result) {
							if (result.getUser() != null) {
								clear();
								if (AUTO_LOGIN_HELPER.isRememberMeSet()) {
									AUTO_LOGIN_HELPER.setRememberMeUser(result.getUser().getUsername());
								}
								loginListener.loginSuccessful(result.getUser());
							} else {
								setError(result.getError());
								showDefaultCursor();
							}
						}
					});
		}
	}

	private void setError(String text) {
		lblError.setText(text);
	}

	private void clearError() {
		lblError.setText(EMPTY_STRING);
	}
}