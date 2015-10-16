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
}