package com.ezee.client.crud.common;

import com.ezee.common.EzeeCommonConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author siborg
 *
 */
public class EzeePayerPayeeCommon extends Composite {

	private static EzeePayerPayeeCommonUiBinder uiBinder = GWT.create(EzeePayerPayeeCommonUiBinder.class);

	interface EzeePayerPayeeCommonUiBinder extends UiBinder<Widget, EzeePayerPayeeCommon> {
	}

	@UiField
	TextBox txtName;

	@UiField
	TextBox txtAddressLine1;

	@UiField
	TextBox txtAddressLine2;

	@UiField
	TextBox txtSuburb;

	@UiField
	TextBox txtCity;

	@UiField
	TextBox txtPostCode;

	@UiField
	ListBox lstState;

	@UiField
	TextBox txtPhone;

	public EzeePayerPayeeCommon() {
		initWidget(uiBinder.createAndBindUi(this));
		initStates();
	}

	public final String getName() {
		return txtName.getText();
	}

	public final String getAddressLine1() {
		return txtAddressLine1.getText();
	}

	public final String getAddressLine2() {
		return txtAddressLine2.getText();
	}

	public final String getSuburb() {
		return txtSuburb.getText();
	}

	public final String getCity() {
		return txtCity.getText();
	}

	public final String getPostCode() {
		return txtPostCode.getText();
	}

	public final String getState() {
		return lstState.getValue(lstState.getSelectedIndex());
	}

	public final String getPhone() {
		return txtPhone.getText();
	}

	private void initStates() {
		lstState.addItem("NSW");
		lstState.addItem("VIC");
		lstState.addItem("QLD");
		lstState.addItem("SA");
		lstState.addItem("WA");
		lstState.addItem("NT");
		lstState.addItem("ACT");
		lstState.setSelectedIndex(EzeeCommonConstants.ZERO);
	}
}