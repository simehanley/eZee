package com.ezee.web.common.ui.entrypoint;

import static com.ezee.web.common.EzeeWebCommonConstants.AUTO_LOGIN_HELPER;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showDefaultCursor;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showWaitCursor;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.model.entity.EzeeUser;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.css.EzeeDefaultResources;
import com.ezee.web.common.ui.css.EzeeGwtOverridesResources;
import com.ezee.web.common.ui.login.EzeeLogin;
import com.ezee.web.common.ui.login.EzeeLoginListener;
import com.ezee.web.common.ui.register.EzeeRegister;
import com.ezee.web.common.ui.register.EzeeRegisterListener;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public abstract class EzeeWebEntryPoint implements EntryPoint, EzeeLoginListener, EzeeRegisterListener {

	private static final Logger log = Logger.getLogger("EzeeWebEntryPoint");

	protected final EzeeLogin login;

	protected final EzeeRegister register;

	protected EzeeUser user;

	protected EzeeEntityCache cache;

	public EzeeWebEntryPoint(final String loginHeader, final String registerHeader) {
		login = new EzeeLogin(loginHeader, this, this);
		register = new EzeeRegister(registerHeader, this);
	}

	@Override
	public void onModuleLoad() {
		init();
	}

	private void init() {
		initResources();
		if (AUTO_LOGIN_HELPER.doAutoLogin()) {
			showWaitCursor();
			user = AUTO_LOGIN_HELPER.getAutoLoginUser();
			initApplication();
			showDefaultCursor();
		} else {
			initLogin();
		}
	}

	protected void initCache() {
		cache = new EzeeEntityCache();
	}

	protected void initResources() {
		EzeeDefaultResources.INSTANCE.css().ensureInjected();
		EzeeGwtOverridesResources.INSTANCE.dataGridStyle().ensureInjected();
	}

	private void initLogin() {
		RootLayoutPanel.get().add(login);
	}

	protected abstract void initApplication();

	@Override
	public void loginSuccessful(EzeeUser user) {
		RootLayoutPanel.get().remove(login);
		log.log(Level.INFO, "Successfully logged in as user '" + user + "'.");
		this.user = user;
		initApplication();
		showDefaultCursor();
	}

	@Override
	public void requestNewRegistration() {
		RootLayoutPanel.get().remove(login);
		RootLayoutPanel.get().add(register);
	}

	@Override
	public void cancelRegistration() {
		RootLayoutPanel.get().remove(register);
		RootLayoutPanel.get().add(login);
	}

	@Override
	public void registrationSuccess(final EzeeUser user) {
		RootLayoutPanel.get().remove(register);
		log.log(Level.INFO, "Successfully registered user '" + user + "'.");
		this.user = user;
		initApplication();
		showDefaultCursor();
	}
}