package com.ezee.web.common.ui.main;

import static com.ezee.common.web.EzeeFormatUtils.getFullDateTimeFormat;
import static com.ezee.web.common.EzeeWebCommonConstants.AUTO_LOGIN_HELPER;
import static com.ezee.web.common.EzeeWebCommonConstants.CONFIG_SERVICE;
import static com.ezee.web.common.EzeeWebCommonConstants.SUPPORT_EMAIL;
import static java.util.logging.Level.SEVERE;

import java.util.Date;
import java.util.logging.Logger;

import com.ezee.model.entity.EzeeConfiguration;
import com.ezee.model.entity.EzeeUser;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.edit.EzeeEditUser;
import com.ezee.web.common.ui.grid.EzeeHasGrid;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.StackPanel;
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
	public HTML licensedto;

	@UiField
	public HTML date;

	@UiField
	public TabLayoutPanel tab;

	@UiField
	public HTML editUser;

	@UiField
	public StackPanel menu;

	protected final EzeeUser ezeeUser;

	protected final EzeeEntityCache cache;

	public EzeeWebMain(final EzeeUser user, final EzeeEntityCache cache) {
		this.ezeeUser = user;
		this.cache = cache;
	}

	public final TabLayoutPanel getTab() {
		return tab;
	}

	protected void initMain() {
		CONFIG_SERVICE.getConfiguration(new AsyncCallback<EzeeConfiguration>() {

			@Override
			public void onSuccess(final EzeeConfiguration result) {
				version.setText(cache.getConfiguration().getVersion());
				licensedto.setText("Licenced To : " + cache.getConfiguration().getLicensee());
			}

			@Override
			public void onFailure(final Throwable caught) {
				log.log(SEVERE, "Unable to resolve system configuration.", caught);

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
		addTabHandler();
		setDate();
		applyDateTimer();
	}

	protected void initUser() {
		user.setText("Logged in as : " + ezeeUser.getUsername());
	}

	protected void editUser() {
		new EzeeEditUser(ezeeUser.getUsername()).show();
	}

	protected void addTabHandler() {
		tab.addSelectionHandler(new SelectionHandler<Integer>() {
			public void onSelection(SelectionEvent<Integer> event) {
				int tabId = event.getSelectedItem();
				menu.showStack(tabId);
				EzeeHasGrid<?> grid = (EzeeHasGrid<?>) tab.getWidget(tabId);
				grid.getGrid().redraw();
			}
		});
	}

	private void applyDateTimer() {
		Timer dateTimer = new Timer() {
			@Override
			public void run() {
				setDate();
			}
		};
		dateTimer.scheduleRepeating(60 * 1000);
	}

	private void setDate() {
		date.setText("Date : " + getFullDateTimeFormat().format(new Date()));
	}
}