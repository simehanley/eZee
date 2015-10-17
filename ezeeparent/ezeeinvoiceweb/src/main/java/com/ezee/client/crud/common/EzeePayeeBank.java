package com.ezee.client.crud.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author siborg
 *
 */
public class EzeePayeeBank extends Composite {

	private static EzeePayeeBankUiBinder uiBinder = GWT.create(EzeePayeeBankUiBinder.class);

	interface EzeePayeeBankUiBinder extends UiBinder<Widget, EzeePayeeBank> {
	}

	@UiField
	TextBox txtBank;

	@UiField
	TextBox txtAccountName;

	@UiField
	TextBox txtAccountNumber;

	@UiField
	TextBox txtAccountBsb;

	public EzeePayeeBank() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public final String getBank() {
		return txtBank.getText();
	}

	public final String getAccountName() {
		return txtAccountName.getText();
	}

	public final String getAccountNumber() {
		return txtAccountNumber.getText();
	}

	public final String getAccountBsb() {
		return txtAccountBsb.getText();
	}

	public void setBank(final String bank) {
		txtBank.setText(bank);
	}

	public void setAccountName(final String accountName) {
		txtAccountName.setText(accountName);
	}

	public void setAccountNumber(final String accountNumber) {
		txtAccountNumber.setText(accountNumber);
	}

	public void setAccountBsb(final String accountBsb) {
		txtAccountBsb.setText(accountBsb);
	}

	public void disable() {
		txtBank.setEnabled(false);
		txtAccountName.setEnabled(false);
		txtAccountNumber.setEnabled(false);
		txtAccountBsb.setEnabled(false);
	}
}