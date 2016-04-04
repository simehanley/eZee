package com.ezee.web.common.ui.crud.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EzeeLeaseCatgoryFields extends Composite {

	private static EzeeLeaseCatgoryFieldsUiBinder uiBinder = GWT.create(EzeeLeaseCatgoryFieldsUiBinder.class);

	interface EzeeLeaseCatgoryFieldsUiBinder extends UiBinder<Widget, EzeeLeaseCatgoryFields> {
	}

	@UiField
	TextBox txtCompany;

	@UiField
	TextBox txtAbn;

	@UiField
	TextBox txtBank;

	@UiField
	TextBox txtAccountName;

	@UiField
	TextBox txtAccountNumber;

	@UiField
	TextBox txtBsb;

	public EzeeLeaseCatgoryFields() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public final String getCompany() {
		return txtCompany.getText();
	}

	public final String getAbn() {
		return txtAbn.getText();
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

	public final String getBsb() {
		return txtBsb.getText();
	}

	public final void setCompany(String company) {
		this.txtCompany.setText(company);
	}

	public final void setAbn(String abn) {
		this.txtAbn.setText(abn);
	}

	public final void setBank(String bank) {
		this.txtBank.setText(bank);
	}

	public final void setAccountName(String accountName) {
		this.txtAccountName.setText(accountName);
	}

	public final void setAccountNumber(String accountNumber) {
		this.txtAccountNumber.setText(accountNumber);
	}

	public final void setBsb(String bsb) {
		this.txtBsb.setText(bsb);
	}

	public void disable() {
		txtCompany.setEnabled(false);
		txtAbn.setEnabled(false);
		txtBank.setEnabled(false);
		txtAccountName.setEnabled(false);
		txtAccountNumber.setEnabled(false);
		txtBsb.setEnabled(false);
	}
}
