package com.ezee.client.main;

import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.web.common.ui.utils.EzeeTabLayoutPanelUtils.getFirstInstanceOf;

import com.ezee.client.grid.project.EzeeProjectGrid;
import com.ezee.model.entity.EzeeUser;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.grid.EzeeHasGrid;
import com.ezee.web.common.ui.grid.contractor.EzeeContractorGrid;
import com.ezee.web.common.ui.main.EzeeWebMain;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
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
	HTML newProjectDef;

	@UiField
	HTML editProjectDef;

	@UiField
	HTML deleteProjectDef;

	@UiField
	HTML newContractor;

	@UiField
	HTML editContractor;

	@UiField
	HTML deleteContractor;

	public EzeeConstructionMain(final EzeeUser user, final EzeeEntityCache cache) {
		super(user, cache);
		initWidget(uiBinder.createAndBindUi(this));
		initUser();
		initMain();
	}

	@Override
	protected void initMain() {
		super.initMain();
		ClickHandler mainClickHandler = new EzeeConstructionMainStackPanelClickHandler();
		editProject.addClickHandler(mainClickHandler);
		newProjectDef.addClickHandler(mainClickHandler);
		editProjectDef.addClickHandler(mainClickHandler);
		deleteProjectDef.addClickHandler(mainClickHandler);
		newContractor.addClickHandler(mainClickHandler);
		editContractor.addClickHandler(mainClickHandler);
		deleteContractor.addClickHandler(mainClickHandler);
		editUser.addClickHandler(mainClickHandler);
		tab.addDomHandler(mainClickHandler, ClickEvent.getType());
	}

	@Override
	protected void addTabHandler() {
		tab.addSelectionHandler(new SelectionHandler<Integer>() {
			public void onSelection(SelectionEvent<Integer> event) {
				int tabId = event.getSelectedItem();
				if (tabId <= ONE) {
					menu.showStack(tabId);
					EzeeHasGrid<?> grid = (EzeeHasGrid<?>) tab.getWidget(tabId);
					grid.getGrid().redraw();
				}
			}
		});
	}

	private class EzeeConstructionMainStackPanelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource().equals(editProject)) {
				getFirstInstanceOf(EzeeProjectGrid.class, tab).editProject();
			} else if (event.getSource().equals(newProjectDef)) {
				getFirstInstanceOf(EzeeProjectGrid.class, tab).newEntity();
			} else if (event.getSource().equals(editProjectDef)) {
				getFirstInstanceOf(EzeeProjectGrid.class, tab).editEntity();
			} else if (event.getSource().equals(deleteProjectDef)) {
				getFirstInstanceOf(EzeeProjectGrid.class, tab).deleteEntity();
			} else if (event.getSource().equals(newContractor)) {
				getFirstInstanceOf(EzeeContractorGrid.class, tab).newEntity();
			} else if (event.getSource().equals(editContractor)) {
				getFirstInstanceOf(EzeeContractorGrid.class, tab).editEntity();
			} else if (event.getSource().equals(deleteContractor)) {
				getFirstInstanceOf(EzeeContractorGrid.class, tab).deleteEntity();
			} else if (event.getSource().equals(editUser)) {
				editUser();
			} else if (event.getSource().equals(tab)) {
				int tabId = tab.getSelectedIndex();
				menu.showStack(tabId);
			}
		}
	}
}