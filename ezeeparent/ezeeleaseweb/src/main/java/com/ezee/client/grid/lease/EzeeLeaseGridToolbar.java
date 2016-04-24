package com.ezee.client.grid.lease;

import static com.ezee.client.EzeeLeaseWebConstants.SHOW_INACTIVE_LEASES;
import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.web.common.EzeeWebCommonConstants.LOCAL_STORAGE;

import com.ezee.model.entity.filter.EzeeEntityFilter;
import com.ezee.model.entity.filter.lease.EzeeLeaseFilter;
import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.web.common.localstorage.EzeeLocalStroage;
import com.ezee.web.common.ui.grid.EzeeGridToolbar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EzeeLeaseGridToolbar extends EzeeGridToolbar<EzeeLease> {

	private static EzeeLeaseGridToolbarUiBinder uiBinder = GWT.create(EzeeLeaseGridToolbarUiBinder.class);

	@UiField
	TextBox txtTenant;

	@UiField
	TextBox txtPremises;

	@UiField
	TextBox txtCategory;

	@UiField
	Button btnRefresh;

	@UiField
	Button btnClear;

	@UiField
	CheckBox chkShowInactive;

	interface EzeeLeaseGridToolbarUiBinder extends UiBinder<Widget, EzeeLeaseGridToolbar> {
	}

	public EzeeLeaseGridToolbar(final EzeeLeaseGrid grid) {
		super(grid);
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	@Override
	public void init() {
		KeyPressHandler filterHandler = new EzeeToolbarKeyPressHandler();
		txtTenant.addKeyPressHandler(filterHandler);
		txtPremises.addKeyPressHandler(filterHandler);
		txtCategory.addKeyPressHandler(filterHandler);
		ClickHandler refreshHandler = new EzeeToolbarRefreshHandler();
		btnRefresh.addClickHandler(refreshHandler);
		ClickHandler clearHandler = new EzeeToolbarClearHandler();
		btnClear.addClickHandler(clearHandler);
		ValueChangeHandler<Boolean> valueChangeHandler = new EzeeLocalStorageValueChangeHandler(SHOW_INACTIVE_LEASES);
		chkShowInactive.addValueChangeHandler(valueChangeHandler);
		setShowInactive();
	}

	private void setShowInactive() {
		boolean showPaid = false;
		EzeeLocalStroage localStroage = LOCAL_STORAGE;
		if (localStroage.isSupported() && localStroage.isSet(SHOW_INACTIVE_LEASES)) {
			showPaid = Boolean.valueOf(localStroage.getValue(SHOW_INACTIVE_LEASES));
		}
		chkShowInactive.setValue(showPaid);
	}

	@Override
	public void clearToolbar() {
		txtTenant.setText(EMPTY_STRING);
		txtPremises.setText(EMPTY_STRING);
		txtCategory.setText(EMPTY_STRING);
	}

	public String getTenant() {
		if (hasLength(txtTenant.getText())) {
			return txtTenant.getText();
		}
		return EMPTY_STRING;
	}

	public String getPremises() {
		if (hasLength(txtPremises.getText())) {
			return txtPremises.getText();
		}
		return EMPTY_STRING;
	}

	public String getCategory() {
		if (hasLength(txtCategory.getText())) {
			return txtCategory.getText();
		}
		return EMPTY_STRING;
	}

	public boolean getShowInactive() {
		return chkShowInactive.getValue();
	}

	@Override
	public EzeeEntityFilter<EzeeLease> resolveFilter() {
		return new EzeeLeaseFilter(getTenant(), getPremises(), getCategory(), getShowInactive());
	}
}