package com.ezee.client.crud.lease;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.cellview.client.DataGrid;

public class EzeeCreateUpdateLease extends Composite {

	private static EzeeCreateUpdateLeaseUiBinder uiBinder = GWT.create(EzeeCreateUpdateLeaseUiBinder.class);
	
	@UiField
	ListBox cmbTenant;
	@UiField
	ListBox cmbCategory;
	@UiField
	CheckBox chkResidential;
	@UiField
	CheckBox chkInactive;
	@UiField
	TextBox txtMyobJobNo;
	@UiField
	Button btnUpdate;
	@UiField
	Button btnSave;
	@UiField
	Button btnCancel;
	@UiField TextBox txtUnits;
	@UiField Label txtArea;
	@UiField Button btnDelete;
	@UiField(provided=true) DataGrid<Object> dataGrid = new DataGrid<Object>();

	interface EzeeCreateUpdateLeaseUiBinder extends UiBinder<Widget, EzeeCreateUpdateLease> {
	}

	public EzeeCreateUpdateLease() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
