package com.ezee.client.main;

import static com.ezee.web.common.ui.utils.EzeeTabLayoutPanelUtils.getFirstInstanceOf;

import com.ezee.client.grid.lease.EzeeLeaseGrid;
import com.ezee.model.entity.EzeeUser;
import com.ezee.model.entity.enums.EzeeUserType;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.grid.leasecategory.EzeeLeaseCategoryGrid;
import com.ezee.web.common.ui.grid.leasepremises.EzeeLeasePremisesGrid;
import com.ezee.web.common.ui.grid.leasetenant.EzeeLeaseTenantGrid;
import com.ezee.web.common.ui.main.EzeeWebMain;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class EzeeLeaseMain extends EzeeWebMain {

	private static EzeeLeaseMainUiBinder uiBinder = GWT.create(EzeeLeaseMainUiBinder.class);

	@UiField
	HTML newLease;

	@UiField
	HTML editLease;

	@UiField
	HTML deleteLease;

	@UiField
	HTML viewLease;

	@UiField
	HTML newTenant;

	@UiField
	HTML editTenant;

	@UiField
	HTML deleteTenant;

	@UiField
	HTML newPremises;

	@UiField
	HTML editPremises;

	@UiField
	HTML deletePremises;

	@UiField
	HTML newCategory;

	@UiField
	HTML editCategory;

	@UiField
	HTML deleteCategory;

	interface EzeeLeaseMainUiBinder extends UiBinder<Widget, EzeeLeaseMain> {
	}

	public EzeeLeaseMain(final EzeeUser user, final EzeeEntityCache cache) {
		super(user, cache);
		initWidget(uiBinder.createAndBindUi(this));
		initUser();
		initMain();
	}

	@Override
	protected void initMain() {
		super.initMain();
		ClickHandler mainClickHandler = new EzeeLeaseMainStackPanelClickHandler();
		if (ezeeUser.getType() == EzeeUserType.admin) {
			newLease.setVisible(true);
			editLease.addClickHandler(mainClickHandler);
			newLease.setVisible(true);
			editLease.addClickHandler(mainClickHandler);
			deleteLease.setVisible(true);
			deleteLease.addClickHandler(mainClickHandler);
			viewLease.setVisible(false);
		} else {
			newLease.setVisible(false);
			editLease.setVisible(false);
			deleteLease.setVisible(false);
			viewLease.addClickHandler(mainClickHandler);
			viewLease.setVisible(true);
		}
		newTenant.addClickHandler(mainClickHandler);
		editTenant.addClickHandler(mainClickHandler);
		deleteTenant.addClickHandler(mainClickHandler);
		newPremises.addClickHandler(mainClickHandler);
		editPremises.addClickHandler(mainClickHandler);
		deletePremises.addClickHandler(mainClickHandler);
		newCategory.addClickHandler(mainClickHandler);
		deleteCategory.addClickHandler(mainClickHandler);
		editCategory.addClickHandler(mainClickHandler);
		editUser.addClickHandler(mainClickHandler);
		tab.addDomHandler(mainClickHandler, ClickEvent.getType());
	}

	private class EzeeLeaseMainStackPanelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource().equals(newLease)) {
				getFirstInstanceOf(EzeeLeaseGrid.class, tab).newEntity();
			} else if (event.getSource().equals(editLease)) {
				getFirstInstanceOf(EzeeLeaseGrid.class, tab).editEntity();
			} else if (event.getSource().equals(deleteLease)) {
				getFirstInstanceOf(EzeeLeaseGrid.class, tab).deleteEntity();
			} else if (event.getSource().equals(viewLease)) {
				getFirstInstanceOf(EzeeLeaseGrid.class, tab).viewEntity();
			} else if (event.getSource().equals(newTenant)) {
				getFirstInstanceOf(EzeeLeaseTenantGrid.class, tab).newEntity();
			} else if (event.getSource().equals(editTenant)) {
				getFirstInstanceOf(EzeeLeaseTenantGrid.class, tab).editEntity();
			} else if (event.getSource().equals(deleteTenant)) {
				getFirstInstanceOf(EzeeLeaseTenantGrid.class, tab).deleteEntity();
			} else if (event.getSource().equals(newPremises)) {
				getFirstInstanceOf(EzeeLeasePremisesGrid.class, tab).newEntity();
			} else if (event.getSource().equals(editPremises)) {
				getFirstInstanceOf(EzeeLeasePremisesGrid.class, tab).editEntity();
			} else if (event.getSource().equals(deletePremises)) {
				getFirstInstanceOf(EzeeLeasePremisesGrid.class, tab).deleteEntity();
			} else if (event.getSource().equals(newCategory)) {
				getFirstInstanceOf(EzeeLeaseCategoryGrid.class, tab).newEntity();
			} else if (event.getSource().equals(editCategory)) {
				getFirstInstanceOf(EzeeLeaseCategoryGrid.class, tab).editEntity();
			} else if (event.getSource().equals(deleteCategory)) {
				getFirstInstanceOf(EzeeLeaseCategoryGrid.class, tab).deleteEntity();
			} else if (event.getSource().equals(editUser)) {
				editUser();
			} else if (event.getSource().equals(tab)) {
				int tabId = tab.getSelectedIndex();
				menu.showStack(tabId);
			}
		}
	}
}