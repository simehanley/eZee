package com.ezee.client.crud.lease;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.common.EzeeCommonConstants.ONE_HUNDRED_DBL;
import static com.ezee.common.EzeeCommonConstants.TWO;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.common.numeric.EzeeNumericUtils.isCloseToZero;
import static com.ezee.common.numeric.EzeeNumericUtils.round;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.common.web.EzeeFormatUtils.getAmountFormat;
import static com.ezee.common.web.EzeeFormatUtils.getDateBoxFormat;
import static com.ezee.common.web.EzeeFormatUtils.getPercentFormat;
import static com.ezee.model.entity.lease.EzeeLeaseBondType.none;
import static com.ezee.model.entity.lease.EzeeLeaseConstants.OUTGOINGS;
import static com.ezee.model.entity.lease.EzeeLeaseConstants.PARKING;
import static com.ezee.model.entity.lease.EzeeLeaseConstants.RENT;
import static com.ezee.model.entity.lease.EzeeLeaseConstants.SIGNAGE;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.EzeeWebCommonConstants.ENTITY_SERVICE;
import static com.ezee.web.common.EzeeWebCommonConstants.ERROR;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.create;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.dialog.EzeeMessageDialog.showNew;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showDefaultCursor;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showWaitCursor;
import static com.ezee.web.common.ui.utils.EzeeListBoxUtils.getEnum;
import static com.ezee.web.common.ui.utils.EzeeListBoxUtils.getItemIndex;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.grid.leasemetadata.EzeeLeaseMetaDataChangeListener;
import com.ezee.client.grid.leasemetadata.EzeeLeaseMetaDataGrid;
import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.model.entity.lease.EzeeLeaseBond;
import com.ezee.model.entity.lease.EzeeLeaseBondType;
import com.ezee.model.entity.lease.EzeeLeaseCategory;
import com.ezee.model.entity.lease.EzeeLeaseIncidental;
import com.ezee.model.entity.lease.EzeeLeaseMetaData;
import com.ezee.model.entity.lease.EzeeLeasePremises;
import com.ezee.model.entity.lease.EzeeLeaseTenant;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntity;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.utils.EzeeListBoxUtils;
import com.ezee.web.common.ui.utils.EzeeRichTextAreaUtils;
import com.ezee.web.common.ui.utils.EzeeTextBoxUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class EzeeCreateUpdateDeleteLease extends EzeeCreateUpdateDeleteEntity<EzeeLease>
		implements EzeeLeaseMetaDataChangeListener {

	private static final Logger log = Logger.getLogger("EzeeCreateUpdateDeleteLease");

	private static final int META_DATA_PAGE_SIZE = 15;

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
	CheckBox chkExecised;

	@UiField
	Label lblRent;

	@UiField
	TextBox txtRent;

	@UiField
	TextBox txtRentPercent;

	@UiField
	TextBox txtRentAccount;

	@UiField
	Label lblOutgoing;

	@UiField
	TextBox txtOutgoing;

	@UiField
	TextBox txtOutgoingPercent;

	@UiField
	TextBox txtOutgoingAccount;

	@UiField
	Label lblParking;

	@UiField
	TextBox txtParking;

	@UiField
	TextBox txtParkingPercent;

	@UiField
	TextBox txtParkingAccount;

	@UiField
	Label lblSignage;

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
	CheckBox chkToggleMonthly;

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

	@UiField
	Button btnDelete;

	@UiField(provided = true)
	EzeeLeaseMetaDataGrid metaDataGrid;

	public EzeeCreateUpdateDeleteLease(EzeeEntityCache cache, EzeeCreateUpdateDeleteEntityHandler<EzeeLease> handler,
			String[] headers) {
		this(cache, handler, null, create, headers);
	}

	public EzeeCreateUpdateDeleteLease(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeeLease> handler, final EzeeLease entity,
			final EzeeCreateUpdateDeleteEntityType type, final String[] headers) {
		super(cache, handler, entity, type, headers);
		initMetaDataGrid();
		setWidget(uiBinder.createAndBindUi(this));
		setModal(true);
	}

	private void initMetaDataGrid() {
		boolean disableContextMenu = (type == delete) ? true : false;
		metaDataGrid = new EzeeLeaseMetaDataGrid(cache, META_DATA_PAGE_SIZE, disableContextMenu, this);
	}

	private void initMetaData() {
		if (entity != null) {
			metaDataGrid.setMetaData(entity.getMetaData());
		}
	}

	@Override
	protected void initialise() {
		lstTenant.setItemSelected(getItemIndex(entity.getTenant().getName(), lstTenant), true);
		lstCategory.setItemSelected(getItemIndex(entity.getCategory().getName(), lstCategory), true);
		lstPremises.setItemSelected(getItemIndex(entity.getPremises().getName(), lstPremises), true);
		txtUnits.setValue(entity.getLeasedUnits());
		txtArea.setValue(getAmountFormat().format(entity.getLeasedArea()));
		dtStart.setValue(DATE_UTILS.fromString(entity.getLeaseStart()));
		dtEnd.setValue(DATE_UTILS.fromString(entity.getLeaseEnd()));
		dtUpdate.setValue(DATE_UTILS.fromString(entity.getUpdated()));
		chkOption.setValue(entity.hasOption());
		chkExecised.setEnabled(entity.hasOption());
		chkExecised.setValue(entity.isOptionExercised());
		dtEnd.setEnabled(!entity.isOptionExercised());
		dtOptionEnd.setEnabled(entity.hasOption());
		if (entity.hasOption()) {
			dtOptionStart.setValue(DATE_UTILS.fromString(entity.getOptionStartDate()));
			dtOptionEnd.setValue(DATE_UTILS.fromString(entity.getOptionEndDate()));
		} else {
			Date optionStart = DATE_UTILS.fromString(entity.getLeaseEnd());
			Date optionEnd = DATE_UTILS.addYears(optionStart, TWO);
			dtOptionStart.setValue(optionStart);
			dtOptionEnd.setValue(optionEnd);
		}
		initialiseLeaseIncideantal(RENT, txtRent, txtRentPercent, txtRentAccount, false);
		initialiseLeaseIncideantal(OUTGOINGS, txtOutgoing, txtOutgoingPercent, txtOutgoingAccount, false);
		initialiseLeaseIncideantal(PARKING, txtParking, txtParkingPercent, txtParkingAccount, false);
		initialiseLeaseIncideantal(SIGNAGE, txtSignage, txtSignagePercent, txtSignageAccount, false);
		initialiseLeaseBond();
		initialiseLeaseMetaData();
		txtNotes.setText(entity.getNotes());
		txtMyobJobNo.setText(entity.getJobNo());
		chkInactive.setValue(entity.isInactive());
		chkResidential.setValue(entity.isResidential());
		inactive(entity.isInactive());
	}

	private void initialiseLeaseIncideantal(final String type, final TextBox amount, final TextBox percent,
			final TextBox account, final boolean monthly) {
		if (monthly) {
			amount.setEnabled(false);
		} else {
			amount.setEnabled(true);
		}
		if (entity != null) {
			EzeeLeaseIncidental incidental = entity.getIncidental(type);
			if (incidental != null) {
				if (monthly) {
					amount.setValue(getAmountFormat().format(entity.monthlyTotal(type)));
				} else {
					amount.setValue(getAmountFormat().format(entity.yearlyTotal(type)));
				}
				percent.setValue(getPercentFormat().format(incidental.getPercentage()));
				account.setValue(incidental.getAccount());
			} else {
				amount.setValue(getAmountFormat().format(ZERO_DBL));
				percent.setValue(getPercentFormat().format(ZERO_DBL));
				account.setValue(EMPTY_STRING);
			}
		}
	}

	private void initialiseLeaseBond() {
		if (entity.getBond() != null) {
			EzeeLeaseBond bond = entity.getBond();
			lstBondType.setItemSelected(getItemIndex(bond.getType().toString(), lstBondType), true);
			txtBondAmount.setValue(getAmountFormat().format(bond.getAmount()));
			txtBondDetail.setText(bond.getNotes());
		} else {
			txtBondAmount.setEnabled(false);
			txtBondDetail.setEnabled(false);
		}
	}

	private void initialiseLeaseMetaData() {
		if (!isEmpty(entity.getMetaData())) {
			metaDataGrid.getModel().getHandler().getList().addAll(entity.getMetaData());
		}
	}

	@Override
	protected void bind() {
		if (entity == null) {
			entity = new EzeeLease();
			entity.setCreated(DATE_UTILS.toString(new Date()));
			entity.setUpdated(entity.getCreated());
		} else {
			entity.setUpdated(DATE_UTILS.toString(new Date()));
		}
		entity.setTenant(getTenant());
		entity.setCategory(getCategory());
		entity.setPremises(getPremises());
		entity.setLeasedUnits(txtUnits.getText());
		entity.setLeasedArea(getAmountFormat().parse(txtArea.getText()));
		entity.setLeaseStart(DATE_UTILS.toString(dtStart.getValue()));
		entity.setLeaseEnd(DATE_UTILS.toString(dtEnd.getValue()));
		bindLeaseOption();
		bindLeaseBond();
		bindIncidental(RENT, txtRent, txtRentPercent, txtRentAccount);
		bindIncidental(OUTGOINGS, txtOutgoing, txtOutgoingPercent, txtOutgoingAccount);
		bindIncidental(PARKING, txtParking, txtParkingPercent, txtParkingAccount);
		bindIncidental(SIGNAGE, txtSignage, txtSignagePercent, txtSignageAccount);
		bindMetaData();
		entity.setNotes(txtNotes.getText());
		entity.setJobNo(txtMyobJobNo.getText());
		entity.setInactive(chkInactive.getValue());
		entity.setResidential(chkResidential.getValue());
	}

	private void bindIncidental(final String type, final TextBox amount, final TextBox percent, final TextBox account) {
		double actual = getAmountFormat().parse(amount.getText());
		if (actual != ZERO_DBL) {
			EzeeLeaseIncidental incidental = null;
			if (entity.getIncidental(type) == null) {
				incidental = new EzeeLeaseIncidental();
				incidental.setName(type);
				incidental.setCreated(DATE_UTILS.toString(new Date()));
				incidental.setUpdated(incidental.getCreated());
				entity.addIncidental(incidental);
			} else {
				incidental = entity.getIncidental(type);
				incidental.setUpdated(DATE_UTILS.toString(new Date()));
			}
			incidental.setAmount(actual);
			incidental.setPercentage(getPercentFormat().parse(percent.getText()) / ONE_HUNDRED_DBL);
			incidental.setTaxRate(cache.getConfiguration().getInvoiceTaxRate());
			incidental.setAccount(account.getText());
		} else {
			if (entity.getIncidental(type) != null) {
				entity.removeIncidental(type);
			}
		}
	}

	private void bindLeaseOption() {
		entity.setHasOption(chkOption.getValue());
		entity.setOptionExercised(chkExecised.getValue());
		if (chkOption.getValue()) {
			entity.setOptionStartDate(DATE_UTILS.toString(dtOptionStart.getValue()));
			entity.setOptionEndDate(DATE_UTILS.toString(dtOptionEnd.getValue()));
		} else {
			entity.setOptionStartDate(null);
			entity.setOptionEndDate(null);
		}
	}

	private void bindLeaseBond() {
		if (getEnum(EzeeLeaseBondType.class, lstBondType) == none) {
			if (entity.getBond() != null) {
				entity.setBond(null);
			}
		} else {
			EzeeLeaseBond bond = null;
			if (entity.getBond() == null) {
				bond = new EzeeLeaseBond();
				bond.setCreated(DATE_UTILS.toString(new Date()));
				bond.setUpdated(bond.getCreated());
				entity.setBond(bond);
			} else {
				bond = entity.getBond();
				bond.setUpdated(DATE_UTILS.toString(new Date()));
			}
			double amount = hasLength(txtBondAmount.getText()) ? getAmountFormat().parse(txtBondAmount.getText())
					: ZERO_DBL;
			bond.setAmount(amount);
			bond.setNotes(txtBondDetail.getText());
			bond.setType(getEnum(EzeeLeaseBondType.class, lstBondType));
		}
	}

	private void bindMetaData() {
		if (type == create) {
			SortedSet<EzeeLeaseMetaData> metaData = new TreeSet<>(metaDataGrid.getModel().getHandler().getList());
			if (!isEmpty(metaData)) {
				entity.setMetaData(metaData);
			}
		}
	}

	private void initialiseNew() {
		txtRent.setValue(getAmountFormat().format(ZERO_DBL));
		txtOutgoing.setValue(getAmountFormat().format(ZERO_DBL));
		txtParking.setValue(getAmountFormat().format(ZERO_DBL));
		txtSignage.setValue(getAmountFormat().format(ZERO_DBL));
		txtBondAmount.setValue(getAmountFormat().format(ZERO_DBL));
		txtRentPercent.setValue(getPercentFormat().format(ZERO_DBL));
		txtOutgoingPercent.setValue(getPercentFormat().format(ZERO_DBL));
		txtParkingPercent.setValue(getPercentFormat().format(ZERO_DBL));
		txtSignagePercent.setValue(getPercentFormat().format(ZERO_DBL));
		txtArea.setValue(getAmountFormat().format(ZERO_DBL));
		txtUnits.setValue(EMPTY_STRING);
		Date start = new Date();
		Date end = DATE_UTILS.addYears(start, TWO);
		Date optionStart = DATE_UTILS.addYears(start, TWO);
		Date optionEnd = DATE_UTILS.addYears(optionStart, TWO);
		dtStart.setValue(start);
		dtEnd.setValue(end);
		dtUpdate.setValue(start);
		dtOptionStart.setValue(optionStart);
		dtOptionEnd.setValue(optionEnd);
	}

	private void initForm() {
		KeyPressHandler keyPressHandler = new EzeeTextBoxUtils.NumericKeyPressHandler();
		FocusHandler focusHandler = new EzeeTextBoxUtils.TextBoxFocusHandler();
		txtRent.addKeyPressHandler(keyPressHandler);
		txtOutgoing.addKeyPressHandler(keyPressHandler);
		txtParking.addKeyPressHandler(keyPressHandler);
		txtSignage.addKeyPressHandler(keyPressHandler);
		txtBondAmount.addKeyPressHandler(keyPressHandler);
		txtArea.addKeyPressHandler(keyPressHandler);
		txtRent.addFocusHandler(focusHandler);
		txtOutgoing.addFocusHandler(focusHandler);
		txtParking.addFocusHandler(focusHandler);
		txtSignage.addFocusHandler(focusHandler);
		txtBondAmount.addFocusHandler(focusHandler);
		txtUnits.addFocusHandler(focusHandler);
		txtArea.addFocusHandler(focusHandler);
		txtMyobJobNo.addFocusHandler(focusHandler);
		dtStart.setFormat(getDateBoxFormat());
		dtEnd.setFormat(getDateBoxFormat());
		dtUpdate.setFormat(getDateBoxFormat());
		dtOptionStart.setFormat(getDateBoxFormat());
		dtOptionEnd.setFormat(getDateBoxFormat());
		txtBondAmount.setValue(getAmountFormat().format(ZERO_DBL));
		txtBondDetail.setText(EMPTY_STRING);
		lstBondType.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				EzeeLeaseBondType type = getEnum(EzeeLeaseBondType.class, lstBondType);
				switch (type) {
				case none:
					txtBondAmount.setEnabled(false);
					txtBondAmount.setValue(getAmountFormat().format(ZERO_DBL));
					txtBondDetail.setEnabled(false);
					txtBondDetail.setText(EMPTY_STRING);
					break;
				default:
					txtBondAmount.setEnabled(true);
					txtBondDetail.setEnabled(true);
					txtBondAmount.setValue(getAmountFormat().format(ZERO_DBL));
					txtBondDetail.setText(EMPTY_STRING);
					break;
				}
			}
		});
		chkOption.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				dtOptionEnd.setEnabled(event.getValue());
				chkExecised.setEnabled(event.getValue());
			}
		});
		chkExecised.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				dtEnd.setEnabled(!event.getValue());
			}
		});
		chkInactive.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				inactive(event.getValue());
			}
		});
		dtStart.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date end = DATE_UTILS.addYears(event.getValue(), TWO);
				Date optionStart = end;
				Date optionEnd = DATE_UTILS.addYears(optionStart, TWO);
				dtEnd.setValue(end);
				dtOptionStart.setValue(optionStart);
				dtOptionEnd.setValue(optionEnd);
			}
		});
		dtEnd.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date optionStart = event.getValue();
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
		chkToggleMonthly.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				toggleMonthly(event.getValue());
			}
		});
		KeyPressHandler bondDetailHandler = new EzeeRichTextAreaUtils.TabKeyPressHandler(new Widget[] { txtBondAmount },
				new Widget[] { txtNotes, txtMyobJobNo });
		txtBondDetail.addKeyPressHandler(bondDetailHandler);
		KeyPressHandler notesHandler = new EzeeRichTextAreaUtils.TabKeyPressHandler(
				new Widget[] { txtNotes, txtSignageAccount }, new Widget[] { txtMyobJobNo });
		txtNotes.addKeyPressHandler(notesHandler);
		EzeeLeaseAmountChangeHandler amountChangeHandler = new EzeeLeaseAmountChangeHandler();
		txtRent.addValueChangeHandler(amountChangeHandler);
		txtOutgoing.addValueChangeHandler(amountChangeHandler);
		txtParking.addValueChangeHandler(amountChangeHandler);
		txtSignage.addValueChangeHandler(amountChangeHandler);
		txtBondAmount.addValueChangeHandler(amountChangeHandler);
		txtArea.addValueChangeHandler(amountChangeHandler);
		EzeeLeasePerecntChangeHandler perecntChangeHandler = new EzeeLeasePerecntChangeHandler();
		txtRentPercent.addValueChangeHandler(perecntChangeHandler);
		txtOutgoingPercent.addValueChangeHandler(perecntChangeHandler);
		txtParkingPercent.addValueChangeHandler(perecntChangeHandler);
		txtSignagePercent.addValueChangeHandler(perecntChangeHandler);
		tab.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				if (event.getSelectedItem() == ONE) {
					initMetaData();
				}
			}
		});
		tab.selectTab(ZERO);
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
			initialise();
			break;
		case delete:
			setText(headers[EDIT_HEADER_INDEX]);
			initialise();
			btnDelete.setEnabled(true);
			disable();
			break;
		}
		super.show();
	}

	private void disable() {
		lstTenant.setEnabled(false);
		lstCategory.setEnabled(false);
		lstPremises.setEnabled(false);
		txtUnits.setEnabled(false);
		txtArea.setEnabled(false);
		dtStart.setEnabled(false);
		dtEnd.setEnabled(false);
		dtOptionStart.setEnabled(false);
		dtOptionEnd.setEnabled(false);
		chkOption.setEnabled(false);
		chkExecised.setEnabled(false);
		disableIncidental(txtRent, txtRentPercent, txtRentAccount, false);
		disableIncidental(txtOutgoing, txtOutgoingPercent, txtOutgoingAccount, false);
		disableIncidental(txtSignage, txtSignagePercent, txtSignageAccount, false);
		disableIncidental(txtParking, txtParkingPercent, txtParkingAccount, false);
		lstBondType.setEnabled(false);
		txtBondAmount.setEnabled(false);
		txtBondDetail.setEnabled(false);
		txtNotes.setEnabled(false);
		txtMyobJobNo.setEnabled(false);
		btnUpdate.setEnabled(false);
		chkResidential.setEnabled(false);
		chkInactive.setEnabled(false);
	}

	private void inactive(boolean inactive) {
		lstTenant.setEnabled(!inactive);
		lstCategory.setEnabled(!inactive);
		lstPremises.setEnabled(!inactive);
		txtUnits.setEnabled(!inactive);
		txtArea.setEnabled(!inactive);
		dtStart.setEnabled(!inactive);
		dtEnd.setEnabled(!inactive);
		chkOption.setEnabled(!inactive);
		if (entity != null) {
			if (chkOption.getValue()) {
				dtOptionEnd.setEnabled(!inactive);
				chkExecised.setEnabled(!inactive);
			}
		}
		disableIncidental(txtRent, txtRentPercent, txtRentAccount, !inactive);
		disableIncidental(txtOutgoing, txtOutgoingPercent, txtOutgoingAccount, !inactive);
		disableIncidental(txtSignage, txtSignagePercent, txtSignageAccount, !inactive);
		disableIncidental(txtParking, txtParkingPercent, txtParkingAccount, !inactive);
		lstBondType.setEnabled(!inactive);
		txtBondAmount.setEnabled(!inactive);
		txtBondDetail.setEnabled(!inactive);
		txtNotes.setEnabled(!inactive);
		txtMyobJobNo.setEnabled(!inactive);
		btnUpdate.setEnabled(!inactive);
		chkResidential.setEnabled(!inactive);
	}

	private void disableIncidental(final TextBox amount, final TextBox percent, final TextBox account,
			final boolean enable) {
		amount.setEnabled(enable);
		percent.setEnabled(enable);
		account.setEnabled(enable);
	}

	private void updateIncidental(final String type) {
		toggleMonthly(false);
		if (entity != null) {
			EzeeLeaseIncidental incidental = entity.getIncidental(type);
			if (incidental != null) {
				double amount = incidental.getAmount();
				double percent = incidental.getPercentage();
				double newAmount = round(amount * (ONE + percent));
				if (!isCloseToZero(newAmount - amount)) {
					incidental.setAmount(newAmount);
					switch (type) {
					case OUTGOINGS:
						txtOutgoing.setValue(getAmountFormat().format(newAmount));
						break;
					case PARKING:
						txtParking.setValue(getAmountFormat().format(newAmount));
						break;
					case SIGNAGE:
						txtSignage.setValue(getAmountFormat().format(newAmount));
						break;
					default:
						txtRent.setValue(getAmountFormat().format(newAmount));
						break;
					}
				}
			}
		}
	}

	@UiHandler("btnUpdate")
	void onBtnUpdateClick(final ClickEvent event) {
		updateIncidental(RENT);
		updateIncidental(OUTGOINGS);
		updateIncidental(PARKING);
		updateIncidental(SIGNAGE);
	}

	@UiHandler("btnClose")
	public void onCloseClick(ClickEvent event) {
		close();
	}

	@UiHandler("btnSave")
	public void onSaveClick(ClickEvent event) {
		btnSave.setEnabled(false);
		showWaitCursor();
		bind();
		ENTITY_SERVICE.saveEntity(EzeeLease.class.getName(), entity, new AsyncCallback<EzeeLease>() {

			@Override
			public void onFailure(final Throwable caught) {
				btnSave.setEnabled(true);
				showDefaultCursor();
				log.log(Level.SEVERE, "Error persisting invoice '" + entity + "'.", caught);
				showNew(ERROR, "Error persisting invoice '" + entity + "'.  Please see log for details.");
			}

			@Override
			public void onSuccess(final EzeeLease lease) {
				log.log(Level.INFO, "Saved lease '" + entity + "' successfully");
				handler.onSave(lease);
				btnSave.setEnabled(true);
				showDefaultCursor();
				close();
			}
		});
	}

	@UiHandler("btnDelete")
	public void onDeleteClick(ClickEvent event) {
		btnDelete.setEnabled(false);
		showWaitCursor();
		ENTITY_SERVICE.deleteEntity(EzeeLease.class.getName(), entity, new AsyncCallback<EzeeLease>() {
			@Override
			public void onFailure(Throwable caught) {
				btnDelete.setEnabled(true);
				showDefaultCursor();
				log.log(Level.SEVERE, "Error deleting lease '" + entity + "'.", caught);
				showNew(ERROR, "Error deleting lease '" + entity + "'.  Please see log for details.");
			}

			@Override
			public void onSuccess(EzeeLease result) {
				log.log(Level.INFO, "Lease '" + entity + "' deleted successfully");
				handler.onDelete(result);
				btnDelete.setEnabled(true);
				showDefaultCursor();
				close();
			}
		});
	}

	private EzeeLeaseTenant getTenant() {
		return EzeeListBoxUtils.getEntity(EzeeLeaseTenant.class, lstTenant, cache);
	}

	private EzeeLeaseCategory getCategory() {
		return EzeeListBoxUtils.getEntity(EzeeLeaseCategory.class, lstCategory, cache);
	}

	private EzeeLeasePremises getPremises() {
		return EzeeListBoxUtils.getEntity(EzeeLeasePremises.class, lstPremises, cache);
	}

	private void loadEntities() {
		EzeeListBoxUtils.loadEntities(EzeeLeaseTenant.class, lstTenant, cache);
		EzeeListBoxUtils.loadEntities(EzeeLeaseCategory.class, lstCategory, cache);
		EzeeListBoxUtils.loadEntities(EzeeLeasePremises.class, lstPremises, cache);
		EzeeListBoxUtils.loadEnums(EzeeLeaseBondType.values(), lstBondType);
	}

	private final class EzeeLeaseAmountChangeHandler implements ValueChangeHandler<String> {

		@Override
		public void onValueChange(ValueChangeEvent<String> event) {
			TextBox textBox = (TextBox) event.getSource();
			textBox.setValue(getAmountFormat().format(getAmountFormat().parse(textBox.getText())));
		}
	}

	private final class EzeeLeasePerecntChangeHandler implements ValueChangeHandler<String> {

		@Override
		public void onValueChange(ValueChangeEvent<String> event) {
			TextBox textBox = (TextBox) event.getSource();
			textBox.setValue(getPercentFormat().format(Double.valueOf(textBox.getText())));
		}
	}

	@Override
	public void metaDataSaved(final EzeeLeaseMetaData metaData) {
		if (entity != null) {
			entity.addMetaData(metaData);
		}
	}

	@Override
	public void metaDataDeleted(final EzeeLeaseMetaData metaData) {
		if (entity != null) {
			entity.removeMetaData(metaData);
		}
	}

	private void toggleMonthly(boolean monthly) {
		if (monthly) {
			lblRent.setText("Rent(M)");
			lblOutgoing.setText("O/gng.(M)");
			lblParking.setText("Park.(M)");
			lblSignage.setText("Sign.(M)");
		} else {
			lblRent.setText("Rent(A)");
			lblOutgoing.setText("O/gng.(A)");
			lblParking.setText("Park.(A)");
			lblSignage.setText("Sign.(A)");
		}
		initialiseLeaseIncideantal(RENT, txtRent, txtRentPercent, txtRentAccount, monthly);
		initialiseLeaseIncideantal(OUTGOINGS, txtOutgoing, txtOutgoingPercent, txtOutgoingAccount, monthly);
		initialiseLeaseIncideantal(PARKING, txtParking, txtParkingPercent, txtParkingAccount, monthly);
		initialiseLeaseIncideantal(SIGNAGE, txtSignage, txtSignagePercent, txtSignageAccount, monthly);
	}
}