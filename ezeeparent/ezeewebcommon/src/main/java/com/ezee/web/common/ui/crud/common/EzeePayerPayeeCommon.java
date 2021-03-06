package com.ezee.web.common.ui.crud.common;

import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.string.EzeeStringUtils.hasLength;

import com.ezee.common.EzeeCommonConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
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
	TextBox txtContact;

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

	@UiField
	TextBox txtFax;

	@UiField
	TextBox txtEmail;

	public EzeePayerPayeeCommon() {
		initWidget(uiBinder.createAndBindUi(this));
		initStates();
		initEmail();
	}

	public TextBox getTxtName() {
		return txtName;
	}

	public final String getName() {
		return txtName.getText();
	}

	public final String getContact() {
		return txtContact.getText();
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

	public final String getFax() {
		return txtFax.getText();
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

	public void setName(final String name) {
		txtName.setText(name);
	}

	public void setContact(final String contact) {
		txtContact.setText(contact);
	}

	public void setAddressLine1(final String addressLine1) {
		txtAddressLine1.setText(addressLine1);
	}

	public void setAddressLine2(final String addressLine2) {
		txtAddressLine2.setText(addressLine2);
	}

	public void setSuburb(final String suburb) {
		txtSuburb.setText(suburb);
	}

	public void setCity(final String city) {
		txtCity.setText(city);
	}

	public void setPostCode(final String postCode) {
		txtPostCode.setText(postCode);
	}

	public void setState(final String state) {
		lstState.setSelectedIndex(getStateIndex(state));
	}

	public void setPhone(final String phone) {
		txtPhone.setText(phone);
	}

	public void setFax(final String fax) {
		txtFax.setText(fax);
	}

	private int getStateIndex(final String state) {
		for (int i = ZERO; i < lstState.getItemCount(); i++) {
			if (state.equals(lstState.getItemText(i))) {
				return i;
			}
		}
		return ZERO;
	}

	public final String getEmail() {
		return txtEmail.getText();
	}

	public void setEmail(final String eMail) {
		this.txtEmail.setText(eMail);
	}

	public void disable() {
		txtName.setEnabled(false);
		txtContact.setEnabled(false);
		txtAddressLine1.setEnabled(false);
		txtAddressLine2.setEnabled(false);
		txtSuburb.setEnabled(false);
		txtCity.setEnabled(false);
		txtPostCode.setEnabled(false);
		lstState.setEnabled(false);
		txtPhone.setEnabled(false);
		txtFax.setEnabled(false);
		txtEmail.setEnabled(false);
	}

	private void initEmail() {
		txtEmail.addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				if (hasLength(txtEmail.getText())) {
					Window.Location.assign("mailto:" + txtEmail.getText());
				}
			}
		});
	}
}