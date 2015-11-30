package com.ezee.web.common.ui.main;

import static com.ezee.web.common.EzeeWebCommonConstants.AUTO_LOGIN_HELPER;
import static com.ezee.web.common.EzeeWebCommonConstants.SUPPORT_EMAIL;
import static com.ezee.web.common.EzeeWebCommonConstants.UTILITY_SERVICE;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.model.entity.EzeeUser;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class EzeeWebMain extends Composite {

	private static final Logger log = Logger.getLogger("EzeeWebMain");

	@UiField
	public HTML user;

	@UiField
	public HTML logout;

	@UiField
	public HTML version;

	@UiField
	public HTML email;

	@UiField
	public TabLayoutPanel tab;

	protected final EzeeUser ezeeUser;

	public EzeeWebMain(final EzeeUser user) {
		this.ezeeUser = user;
	}

	public final TabLayoutPanel getTab() {
		return tab;
	}

	protected void initMain() {
		UTILITY_SERVICE.getVersion(new AsyncCallback<String>() {
			@Override
			public void onSuccess(final String result) {
				version.setText("Version : " + result);
			}

			@Override
			public void onFailure(Throwable caught) {
				log.log(Level.SEVERE, "Unable to resolve software version.", caught);

			}
		});
		email.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.assign(SUPPORT_EMAIL);
			}
		});
		logout.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AUTO_LOGIN_HELPER.unsetRememberMeUser();
				Window.Location.assign(GWT.getHostPageBaseURL());
			}
		});
	}

	protected void initUser() {
		user.setText("Logged in as : " + ezeeUser.getUsername());
	}
}