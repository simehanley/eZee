package com.ezee.web.common.ui.main;

import static com.ezee.web.common.EzeeWebCommonConstants.AUTO_LOGIN_HELPER;
import static com.ezee.web.common.EzeeWebCommonConstants.SUPPORT_EMAIL;
import static com.ezee.web.common.EzeeWebCommonConstants.UTILITY_SERVICE;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.model.entity.EzeeUser;
import com.ezee.web.common.ui.edit.EzeeEditUser;
import com.ezee.web.common.ui.grid.EzeeHasGrid;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiField;
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
	public TabLayoutPanel tab;

	@UiField
	public HTML editUser;

	@UiField
	public StackPanel menu;

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
		addTabHandler();
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
}