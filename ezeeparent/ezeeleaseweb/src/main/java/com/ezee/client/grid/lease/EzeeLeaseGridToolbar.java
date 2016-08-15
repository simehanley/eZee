package com.ezee.client.grid.lease;

import static com.ezee.client.EzeeLeaseWebConstants.LEASE_SUMMARY_IN_MONTHS;
import static com.ezee.client.EzeeLeaseWebConstants.SHOW_INACTIVE_LEASES;
import static com.ezee.client.EzeeLeaseWebConstants.SHOW_LEASE_SUMMARY;
import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.web.common.EzeeWebCommonConstants.ENTITY_SERVICE;
import static com.ezee.web.common.EzeeWebCommonConstants.ERROR;
import static com.ezee.web.common.EzeeWebCommonConstants.LEASE_UTILITY_SERVICE;
import static com.ezee.web.common.EzeeWebCommonConstants.LOCAL_STORAGE;
import static com.ezee.web.common.ui.dialog.EzeeMessageDialog.showNew;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showDefaultCursor;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showWaitCursor;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.model.entity.filter.EzeeEntityFilter;
import com.ezee.model.entity.filter.lease.EzeeLeaseFilter;
import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.web.common.localstorage.EzeeLocalStroage;
import com.ezee.web.common.ui.grid.EzeeGridToolbar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EzeeLeaseGridToolbar extends EzeeGridToolbar<EzeeLease> {

	private static final Logger log = Logger.getLogger("EzeeLeaseGridToolbar");

	private static EzeeLeaseGridToolbarUiBinder uiBinder = GWT.create(EzeeLeaseGridToolbarUiBinder.class);

	@UiField
	TextBox txtTenant;

	@UiField
	TextBox txtPremises;

	@UiField
	TextBox txtCategory;

	@UiField
	Button btnSave;

	@UiField
	Button btnRefresh;

	@UiField
	Button btnClear;

	@UiField
	Button btnEmail;

	@UiField
	CheckBox chkShowInactive;

	@UiField
	CheckBox chkSummary;

	@UiField
	CheckBox chkMonthly;

	private final EzeeLeaseSummaryHandler handler;

	interface EzeeLeaseGridToolbarUiBinder extends UiBinder<Widget, EzeeLeaseGridToolbar> {
	}

	public EzeeLeaseGridToolbar(final EzeeLeaseGrid grid, final EzeeLeaseSummaryHandler handler) {
		super(grid);
		initWidget(uiBinder.createAndBindUi(this));
		init();
		this.handler = handler;
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
		ValueChangeHandler<Boolean> inactiveChangeHandler = new EzeeLocalStorageValueChangeHandler(
				SHOW_INACTIVE_LEASES);
		chkShowInactive.addValueChangeHandler(inactiveChangeHandler);
		ValueChangeHandler<Boolean> summaryChangeHandler = new EzeeLocalStorageValueChangeHandler(SHOW_LEASE_SUMMARY);
		chkSummary.addValueChangeHandler(summaryChangeHandler);
		chkSummary.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				handler.summaryValueChanged(event.getValue());
				chkMonthly.setEnabled(event.getValue());
			}
		});
		ValueChangeHandler<Boolean> monthlyChangeHandler = new EzeeLocalStorageValueChangeHandler(
				LEASE_SUMMARY_IN_MONTHS);
		chkMonthly.addValueChangeHandler(monthlyChangeHandler);
		chkMonthly.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				handler.monthlyValueChanged(event.getValue());
			}
		});
		setLeaseCheckBoxes();
	}

	private void setLeaseCheckBoxes() {
		EzeeLocalStroage localStroage = LOCAL_STORAGE;
		boolean showPaid = false;
		if (localStroage.isSupported() && localStroage.isSet(SHOW_INACTIVE_LEASES)) {
			showPaid = Boolean.valueOf(localStroage.getValue(SHOW_INACTIVE_LEASES));
		}
		chkShowInactive.setValue(showPaid);
		boolean summary = false;
		if (localStroage.isSupported() && localStroage.isSet(SHOW_LEASE_SUMMARY)) {
			summary = Boolean.valueOf(localStroage.getValue(SHOW_LEASE_SUMMARY));
		}
		chkSummary.setValue(summary);
		boolean monthly = false;
		if (localStroage.isSupported() && localStroage.isSet(LEASE_SUMMARY_IN_MONTHS)) {
			monthly = Boolean.valueOf(localStroage.getValue(LEASE_SUMMARY_IN_MONTHS));
		}
		chkMonthly.setEnabled(summary);
		chkMonthly.setValue(monthly);
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

	public boolean getShowSummary() {
		return chkSummary.getValue();
	}

	public boolean getShowMonthly() {
		return chkMonthly.getValue();
	}

	@Override
	public EzeeEntityFilter<EzeeLease> resolveFilter() {
		return new EzeeLeaseFilter(getTenant(), getPremises(), getCategory(), getShowInactive());
	}

	@UiHandler("btnSave")
	public void onSaveClick(final ClickEvent event) {
		List<EzeeLease> edited = ((EzeeLeaseGrid) grid).getEdited();
		if (!isEmpty(edited)) {
			btnSave.setEnabled(false);
			showWaitCursor();
			ENTITY_SERVICE.saveEntities(EzeeLease.class.getName(), edited, new AsyncCallback<List<EzeeLease>>() {
				@Override
				public void onFailure(final Throwable caught) {
					btnSave.setEnabled(true);
					showDefaultCursor();
					log.log(Level.SEVERE, "Error saving leases.", caught);
					showNew(ERROR, "Error saving leases. Please see log for details.");
				}

				@Override
				public void onSuccess(final List<EzeeLease> result) {
					if (!isEmpty(result)) {
						refreshLeases(result);
					}
					btnSave.setEnabled(true);
					showDefaultCursor();
					log.log(Level.INFO, "Leases saved successfully.");
				}
			});
		}
	}

	@UiHandler("btnEmail")
	public void onEmailClick(final ClickEvent event) {
		btnEmail.setEnabled(false);
		showWaitCursor();
		LEASE_UTILITY_SERVICE.sendEmail(new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				btnEmail.setEnabled(true);
				showDefaultCursor();
				log.log(Level.INFO, "Email sent successfully.");
			}

			@Override
			public void onFailure(final Throwable caught) {
				btnEmail.setEnabled(true);
				showDefaultCursor();
				log.log(Level.SEVERE, "Error sending email.", caught);
			}
		});
	}

	private void refreshLeases(final List<EzeeLease> result) {
		for (int i = ZERO; i < result.size(); i++) {
			grid.onSave(result.get(i), false);
		}
		grid.getGrid().redraw();
	}
}