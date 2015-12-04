package com.ezee.client.main;

import static com.ezee.web.common.ui.utils.EzeeTabLayoutPanelUtils.getFirstInstanceOf;

import com.ezee.model.entity.EzeeUser;
import com.ezee.web.common.ui.grid.payee.EzeePayeeGrid;
import com.ezee.web.common.ui.main.EzeeWebMain;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class EzeeConstructionMain extends EzeeWebMain {

	private static EzeeConstructionMainUiBinder uiBinder = GWT.create(EzeeConstructionMainUiBinder.class);

	interface EzeeConstructionMainUiBinder extends UiBinder<Widget, EzeeConstructionMain> {
	}

	@UiField
	HTML editProject;

	@UiField
	HTML deleteProject;

	@UiField
	HTML newProjectDef;

	@UiField
	HTML editProjectDef;

	@UiField
	HTML deleteProjectDef;

	@UiField
	HTML newResource;

	@UiField
	HTML editResource;

	@UiField
	HTML deleteResource;

	public EzeeConstructionMain(final EzeeUser user) {
		super(user);
		initWidget(uiBinder.createAndBindUi(this));
		initUser();
		initMain();
	}

	@Override
	protected void initMain() {
		super.initMain();
		ClickHandler mainClickHandler = new EzeeConstructionMainStackPanelClickHandler();
		newResource.addClickHandler(mainClickHandler);
		editResource.addClickHandler(mainClickHandler);
		deleteResource.addClickHandler(mainClickHandler);
		editUser.addClickHandler(mainClickHandler);
		tab.addDomHandler(mainClickHandler, ClickEvent.getType());
	}

	private class EzeeConstructionMainStackPanelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource().equals(newResource)) {
				getFirstInstanceOf(EzeePayeeGrid.class, tab).newEntity();
			} else if (event.getSource().equals(editResource)) {
				getFirstInstanceOf(EzeePayeeGrid.class, tab).editEntity();
			} else if (event.getSource().equals(deleteResource)) {
				getFirstInstanceOf(EzeePayeeGrid.class, tab).deleteEntity();
			} else if (event.getSource().equals(editUser)) {
				editUser();
			} else if (event.getSource().equals(tab)) {
				int tabId = tab.getSelectedIndex();
				menu.showStack(tabId);
			}
		}
	}
}