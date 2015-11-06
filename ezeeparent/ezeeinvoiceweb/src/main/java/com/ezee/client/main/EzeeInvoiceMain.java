package com.ezee.client.main;

import static com.ezee.client.EzeeInvoiceWebConstants.INVOICE_SERVICE;
import static com.ezee.client.EzeeInvoiceWebConstants.SUPPORT_EMAIL;
import static com.ezee.web.common.EzeeWebCommonConstants.AUTO_LOGIN_HELPER;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showDefaultCursor;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showPointerCursor;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.grid.EzeeHasGrid;
import com.ezee.model.entity.EzeeUser;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class EzeeInvoiceMain extends Composite {

	private static final Logger log = Logger.getLogger("EzeeInvoiceMain");

	private static EzeeInvoiceMainUiBinder uiBinder = GWT.create(EzeeInvoiceMainUiBinder.class);

	@UiField
	HTML user;

	@UiField
	HTML logout;

	@UiField
	HTML version;

	@UiField
	HTML email;

	@UiField
	TabLayoutPanel tab;

	interface EzeeInvoiceMainUiBinder extends UiBinder<Widget, EzeeInvoiceMain> {
	}

	public EzeeInvoiceMain(final EzeeUser user) {
		initWidget(uiBinder.createAndBindUi(this));
		initUser(user);
		initMain();
		addTabHandler();
	}

	public final TabLayoutPanel getTab() {
		return tab;
	}

	private void addTabHandler() {
		tab.addSelectionHandler(new SelectionHandler<Integer>() {
			public void onSelection(SelectionEvent<Integer> event) {
				int tabId = event.getSelectedItem();
				EzeeHasGrid<?> grid = (EzeeHasGrid<?>) tab.getWidget(tabId);
				grid.getGrid().redraw();
			}
		});
	}

	private void initUser(final EzeeUser loggedInUser) {
		user.setText("Logged in as : " + loggedInUser.getUsername());
	}

	private void initMain() {
		INVOICE_SERVICE.getVersion(new AsyncCallback<String>() {
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
		MouseOverHandler mouseOverHandler = new EzeeMainMouseOverHandler();
		logout.addMouseOverHandler(mouseOverHandler);
		email.addMouseOverHandler(mouseOverHandler);
		MouseOutHandler mouseOutHandler = new EzeeMainMouseOutHandler();
		logout.addMouseOutHandler(mouseOutHandler);
		email.addMouseOutHandler(mouseOutHandler);
	}

	private class EzeeMainMouseOverHandler implements MouseOverHandler {

		@Override
		public void onMouseOver(MouseOverEvent event) {
			showPointerCursor();
		}
	}

	private class EzeeMainMouseOutHandler implements MouseOutHandler {

		@Override
		public void onMouseOut(MouseOutEvent event) {
			showDefaultCursor();
		}
	}
}