package com.ezee.client.crud.lease;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.common.EzeeCommonConstants.TWO;
import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.web.EzeeFormatUtils.getDateBoxFormat;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.ui.utils.EzeeListBoxUtils.getEnum;

import java.util.Date;

import com.ezee.common.web.EzeeFormatUtils;
import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.model.entity.lease.EzeeLeaseBondType;
import com.ezee.model.entity.lease.EzeeLeaseCategory;
import com.ezee.model.entity.lease.EzeeLeasePremises;
import com.ezee.model.entity.lease.EzeeLeaseTenant;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntity;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.utils.EzeeListBoxUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class EzeeCreateUpdateDeleteLease extends EzeeCreateUpdateDeleteEntity<EzeeLease> {

	private static EzeeCreateUpdateDeleteLeaseUiBinder uiBinder = GWT.create(EzeeCreateUpdateDeleteLeaseUiBinder.class);

	interface EzeeCreateUpdateDeleteLeaseUiBinder extends UiBinder<Widget, EzeeCreateUpdateDeleteLease> {
	}

	@UiField
	ListBox lstTenant;

	@UiField
	ListBox lstCategory;

	@UiField
	TabPanel tab;

	@UiField
	ListBox lstPremises;

	@UiField
	TextBox txtUnits;

	@UiField
	TextBox txtArea;

	@UiField
	DateBox dtStart;

	@UiField
	DateBox dtEnd;

	@UiField
	DateBox dtUpdate;

	@UiField
	DateBox dtOptionStart;

	@UiField
	DateBox dtOptionEnd;

	@UiField
	CheckBox chkOption;

	@UiField
	TextBox txtRent;

	@UiField
	TextBox txtRentPercent;

	@UiField
	TextBox txtRentAccount;

	@UiField
	TextBox txtOutgoing;

	@UiField
	TextBox txtOutgoingPercent;

	@UiField
	TextBox txtOutgoingAccount;

	@UiField
	TextBox txtParking;

	@UiField
	TextBox txtParkingPercent;

	@UiField
	TextBox txtParkingAccount;

	@UiField
	TextBox txtSignage;

	@UiField
	TextBox txtSignagePercent;

	@UiField
	TextBox txtSignageAccount;

	@UiField
	ListBox lstBondType;

	@UiField
	TextBox txtBondAmount;

	@UiField
	RichTextArea txtBondDetail;

	@UiField
	RichTextArea txtNotes;

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
	Button btnClose;

	public EzeeCreateUpdateDeleteLease(EzeeEntityCache cache, EzeeCreateUpdateDeleteEntityHandler<EzeeLease> handler,
			String[] headers) {
		this(cache, handler, null, EzeeCreateUpdateDeleteEntityType.create, headers);
	}

	public EzeeCreateUpdateDeleteLease(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeeLease> handler, final EzeeLease entity,
			final EzeeCreateUpdateDeleteEntityType type, final String[] headers) {
		super(cache, handler, entity, type, headers);
		setWidget(uiBinder.createAndBindUi(this));
		setModal(true);
	}

	@Override
	protected void initialise() {
	}

	@Override
	protected void bind() {
	}

	private void initialiseNew() {
		txtRent.setValue(EzeeFormatUtils.getAmountFormat().format(ZERO_DBL));
		txtOutgoing.setValue(EzeeFormatUtils.getAmountFormat().format(ZERO_DBL));
		txtParking.setValue(EzeeFormatUtils.getAmountFormat().format(ZERO_DBL));
		txtSignage.setValue(EzeeFormatUtils.getAmountFormat().format(ZERO_DBL));
		txtBondAmount.setValue(EzeeFormatUtils.getAmountFormat().format(ZERO_DBL));
		Date start = new Date();
		Date end = DATE_UTILS.addYears(start, TWO);
		Date optionStart = DATE_UTILS.addDays(end, ONE);
		Date optionEnd = DATE_UTILS.addYears(optionStart, TWO);
		dtStart.setValue(start);
		dtEnd.setValue(end);
		dtUpdate.setValue(start);
		dtOptionStart.setValue(optionStart);
		dtOptionEnd.setValue(optionEnd);
	}

	private void initForm() {
		dtStart.setFormat(getDateBoxFormat());
		dtEnd.setFormat(getDateBoxFormat());
		dtUpdate.setFormat(getDateBoxFormat());
		dtOptionStart.setFormat(getDateBoxFormat());
		dtOptionEnd.setFormat(getDateBoxFormat());
		lstBondType.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				EzeeLeaseBondType type = getEnum(EzeeLeaseBondType.class, lstBondType);
				switch (type) {
				case none:
					txtBondAmount.setEnabled(false);
					txtBondAmount.setValue(EzeeFormatUtils.getAmountFormat().format(ZERO_DBL));
					txtBondDetail.setEnabled(false);
					txtBondDetail.setText(EMPTY_STRING);
					break;
				default:
					txtBondAmount.setEnabled(true);
					txtBondDetail.setEnabled(true);
					break;
				}
			}
		});
		chkOption.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				dtOptionStart.setEnabled(chkOption.getValue());
				dtOptionEnd.setEnabled(chkOption.getValue());
			}
		});
		dtStart.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date end = DATE_UTILS.addYears(event.getValue(), TWO);
				Date optionStart = DATE_UTILS.addDays(end, ONE);
				Date optionEnd = DATE_UTILS.addYears(optionStart, TWO);
				dtEnd.setValue(end);
				dtOptionStart.setValue(optionStart);
				dtOptionEnd.setValue(optionEnd);
			}
		});
		dtEnd.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date optionStart = DATE_UTILS.addDays(event.getValue(), ONE);
				Date optionEnd = DATE_UTILS.addYears(optionStart, TWO);
				dtOptionStart.setValue(optionStart);
				dtOptionEnd.setValue(optionEnd);
			}
		});
		dtOptionStart.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date optionEnd = DATE_UTILS.addYears(event.getValue(), TWO);
				dtOptionEnd.setValue(optionEnd);
			}
		});
	}

	@Override
	public void show() {
		loadEntities();
		initForm();
		switch (type) {
		case create:
			setText(headers[NEW_HEADER_INDEX]);
			initialiseNew();
			break;
		case update:
			setText(headers[EDIT_HEADER_INDEX]);
			break;
		case delete:
			setText(headers[EDIT_HEADER_INDEX]);
			break;
		}
		super.show();
	}

	@UiHandler("btnClose")
	public void onCloseClick(ClickEvent event) {
		close();
	}

	private void loadEntities() {
		EzeeListBoxUtils.loadEntities(EzeeLeaseTenant.class, lstTenant, cache);
		EzeeListBoxUtils.loadEntities(EzeeLeaseCategory.class, lstCategory, cache);
		EzeeListBoxUtils.loadEntities(EzeeLeasePremises.class, lstPremises, cache);
		EzeeListBoxUtils.loadEnums(EzeeLeaseBondType.values(), lstBondType);
	}
}