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
import static com.ezee.common.web.EzeeFormatUtils.getCurrencyFormat;
import static com.ezee.common.web.EzeeFormatUtils.getDateBoxFormat;
import static com.ezee.common.web.EzeeFormatUtils.getPercentFormat;
import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;
import static com.ezee.model.entity.lease.EzeeLeaseBondType.none;
import static com.ezee.model.entity.lease.EzeeLeaseConstants.OUTGOINGS;
import static com.ezee.model.entity.lease.EzeeLeaseConstants.PARKING;
import static com.ezee.model.entity.lease.EzeeLeaseConstants.RENT;
import static com.ezee.model.entity.lease.EzeeLeaseConstants.SIGNAGE;
import static com.ezee.model.entity.lease.EzeeLeaseConstants.TOTAL;
import static com.ezee.model.entity.lease.EzeeLeaseMetaDataType.historic_rents;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.EzeeWebCommonConstants.ENTITY_SERVICE;
import static com.ezee.web.common.EzeeWebCommonConstants.ERROR;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.create;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.dialog.EzeeConfirmDialog.showNew;
import static com.ezee.web.common.ui.dialog.EzeeConfirmDialogResult.ok;
import static com.ezee.web.common.ui.dialog.EzeeMessageDialog.showNew;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showDefaultCursor;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showWaitCursor;
import static com.ezee.web.common.ui.utils.EzeeListBoxUtils.getEnum;
import static com.ezee.web.common.ui.utils.EzeeListBoxUtils.getItemIndex;
import static java.util.logging.Level.SEVERE;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.crud.lease.util.EzeeLeaseUtils;
import com.ezee.client.grid.lease.EzeeLeaseSubComponentChangeListener;
import com.ezee.client.grid.leasefile.EzeeLeaseFileGrid;
import com.ezee.client.grid.leasemetadata.EzeeLeaseMetaDataGrid;
import com.ezee.client.grid.leasenote.EzeeLeaseNoteGrid;
import com.ezee.model.entity.EzeeDateSortableDatabaseEntity;
import com.ezee.model.entity.EzeeUser;
import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.model.entity.lease.EzeeLeaseBond;
import com.ezee.model.entity.lease.EzeeLeaseBondType;
import com.ezee.model.entity.lease.EzeeLeaseCategory;
import com.ezee.model.entity.lease.EzeeLeaseFile;
import com.ezee.model.entity.lease.EzeeLeaseIncidental;
import com.ezee.model.entity.lease.EzeeLeaseMetaData;
import com.ezee.model.entity.lease.EzeeLeaseNote;
import com.ezee.model.entity.lease.EzeeLeasePremises;
import com.ezee.model.entity.lease.EzeeLeaseTenant;
import com.ezee.web.common.EzeeWebCommonConstants;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntity;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.dialog.EzeeConfirmDialog;
import com.ezee.web.common.ui.dialog.EzeeConfirmDialogResult;
import com.ezee.web.common.ui.dialog.EzeeConfirmDialogResultHandler;
import com.ezee.web.common.ui.utils.EzeeCursorUtils;
import com.ezee.web.common.ui.utils.EzeeListBoxUtils;
import com.ezee.web.common.ui.utils.EzeeTextBoxUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
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
		implements EzeeLeaseSubComponentChangeListener {

	private static final Logger log = Logger.getLogger("EzeeCreateUpdateDeleteLease");

	private static final int META_DATA_PAGE_SIZE = 15;
	private static final int NOTE_PAGE_SIZE = 15;
	private static final int FILE_PAGE_SIZE = 15;

	private static final int META_DATA_INDEX = 1;
	private static final int NOTE_INDEX = 2;
	private static final int FILE_INDEX = 3;

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
	Label lblNet;

	@UiField
	TextBox txtNet;

	@UiField
	Label lblGst;

	@UiField
	TextBox txtGst;

	@UiField
	Label lblGross;

	@UiField
	TextBox txtGross;

	@UiField
	ListBox lstBondType;

	@UiField
	TextBox txtBondAmount;

	@UiField
	RichTextArea txtBondDetail;

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

	@UiField(provided = true)
	EzeeLeaseNoteGrid noteGrid;

	@UiField(provided = true)
	EzeeLeaseFileGrid fileGrid;

	private final EzeeLeaseUtils leaseUtils = new EzeeLeaseUtils();

	public EzeeCreateUpdateDeleteLease(final EzeeUser user, final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeeLease> handler, final String[] headers) {
		this(user, cache, handler, null, create, headers);
	}

	public EzeeCreateUpdateDeleteLease(final EzeeUser user, final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeeLease> handler, final EzeeLease entity,
			final EzeeCreateUpdateDeleteEntityType type, final String[] headers) {
		super(user, cache, handler, entity, type, headers);
		initGrids();
		setWidget(uiBinder.createAndBindUi(this));
		setModal(true);
	}

	private void initGrids() {
		boolean disableContextMenu = (type == delete) ? true : false;
		metaDataGrid = new EzeeLeaseMetaDataGrid(user, cache, META_DATA_PAGE_SIZE, disableContextMenu, this);
		noteGrid = new EzeeLeaseNoteGrid(user, cache, NOTE_PAGE_SIZE, disableContextMenu, this);
		fileGrid = new EzeeLeaseFileGrid(entity, cache, FILE_PAGE_SIZE, disableContextMenu, this);
	}

	private void initMetaData() {
		if (entity != null) {
			metaDataGrid.setData(entity.getMetaData());
		}
	}

	private void initNotes() {
		if (entity != null) {
			noteGrid.setData(entity.getNotes());
		}
	}

	private void initFiles() {
		if (entity != null) {
			fileGrid.setData(entity.getFiles());
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
			Date optionStart = DATE_UTILS.addDays(DATE_UTILS.fromString(entity.getLeaseEnd()), ONE);
			Date optionEnd = DATE_UTILS.addYearsAsDays(optionStart, TWO);
			dtOptionStart.setValue(optionStart);
			dtOptionEnd.setValue(optionEnd);
		}
		initialiseLeaseIncideantal(RENT, txtRent, txtRentPercent, txtRentAccount, false);
		initialiseLeaseIncideantal(OUTGOINGS, txtOutgoing, txtOutgoingPercent, txtOutgoingAccount, false);
		initialiseLeaseIncideantal(PARKING, txtParking, txtParkingPercent, txtParkingAccount, false);
		initialiseLeaseIncideantal(SIGNAGE, txtSignage, txtSignagePercent, txtSignageAccount, false);
		initialiseLeaseIncideantal(TOTAL, txtNet, null, null, false);
		initialiseLeaseBond();
		initialiseLeaseMetaData();
		txtMyobJobNo.setText(entity.getJobNo());
		chkInactive.setValue(entity.isInactive());
		chkResidential.setValue(entity.isResidential());
		inactive(entity.isInactive());
	}

	private void initialiseLeaseIncideantal(final String type, final TextBox amount, final TextBox percent,
			final TextBox account, final boolean monthly) {
		if (!TOTAL.equals(type)) {
			if (monthly) {
				amount.setEnabled(false);
			} else {
				amount.setEnabled(true);
			}
		}
		if (entity != null) {
			EzeeLeaseIncidental incidental = entity.getIncidental(type);
			if (incidental != null || TOTAL.equals(type)) {
				if (monthly) {
					amount.setValue(getAmountFormat().format(entity.monthlyAmount(type)));
					txtGst.setValue(getAmountFormat().format(entity.monthlyGst(type)));
					txtGross.setValue(getAmountFormat().format(entity.monthlyTotal(type)));
				} else {
					amount.setValue(getAmountFormat().format(entity.yearlyAmount(type)));
					txtGst.setValue(getAmountFormat().format(entity.yearlyGst(type)));
					txtGross.setValue(getAmountFormat().format(entity.yearlyTotal(type)));
				}
				if (!TOTAL.equals(type)) {
					percent.setValue(getPercentFormat().format(incidental.getPercentage()));
					account.setValue(incidental.getAccount());
				}
			} else {
				amount.setValue(getAmountFormat().format(ZERO_DBL));
				if (!TOTAL.equals(type)) {
					percent.setValue(getPercentFormat().format(ZERO_DBL));
					account.setValue(EMPTY_STRING);
				}
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
		bindNotes();
		bindFiles();
		entity.setJobNo(txtMyobJobNo.getText());
		entity.setInactive(chkInactive.getValue());
		entity.setResidential(chkResidential.getValue());
	}

	private void bindIncidental(final String type, final TextBox amount, final TextBox percent, final TextBox account) {
		double actual = getAmountFormat().parse(amount.getText());
		EzeeLeaseIncidental incidental = null;
		if (entity.getIncidental(type) == null) {
			incidental = new EzeeLeaseIncidental();
			incidental.setName(type);
			entity.addIncidental(incidental);
		} else {
			incidental = entity.getIncidental(type);
		}
		incidental.setAmount(actual);
		incidental.setPercentage(getPercentFormat().parse(percent.getText()) / ONE_HUNDRED_DBL);
		incidental.setTaxRate(cache.getConfiguration().getInvoiceTaxRate());
		incidental.setAccount(account.getText());
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
				entity.setBond(bond);
			} else {
				bond = entity.getBond();
			}
			double amount = hasLength(txtBondAmount.getText()) ? getAmountFormat().parse(txtBondAmount.getText())
					: ZERO_DBL;
			bond.setAmount(amount);
			bond.setNotes(txtBondDetail.getText());
			bond.setType(getEnum(EzeeLeaseBondType.class, lstBondType));
		}
	}

	private void bindMetaData() {
		SortedSet<EzeeLeaseMetaData> metaData = new TreeSet<>(metaDataGrid.getModel().getHandler().getList());
		entity.setMetaData(metaData);
	}

	private void bindNotes() {
		SortedSet<EzeeLeaseNote> notes = new TreeSet<>(noteGrid.getModel().getHandler().getList());
		entity.setNotes(notes);
	}

	private void bindFiles() {
		SortedSet<EzeeLeaseFile> files = new TreeSet<>(fileGrid.getModel().getHandler().getList());
		entity.setFiles(files);
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
		txtNet.setValue(getAmountFormat().format(ZERO_DBL));
		txtGst.setValue(getAmountFormat().format(ZERO_DBL));
		txtGross.setValue(getAmountFormat().format(ZERO_DBL));
		txtArea.setValue(getAmountFormat().format(ZERO_DBL));
		txtUnits.setValue(EMPTY_STRING);
		Date start = new Date();
		Date end = DATE_UTILS.addYearsAsDays(start, TWO);
		Date optionStart = DATE_UTILS.addDays(end, ONE);
		Date optionEnd = DATE_UTILS.addYearsAsDays(optionStart, TWO);
		dtStart.setValue(start);
		dtEnd.setValue(end);
		dtUpdate.setValue(start);
		dtOptionStart.setValue(optionStart);
		dtOptionEnd.setValue(optionEnd);
	}

	private void initForm() {
		KeyPressHandler keyPressHandler = new EzeeTextBoxUtils.NumericKeyPressHandler();
		FocusHandler focusHandler = new EzeeTextBoxUtils.TextBoxFocusHandler();
		BlurHandler ezeeUpdateLeaseTotalHandler = new EzeeUpdateLeaseTotalHandler();
		txtRent.addKeyPressHandler(keyPressHandler);
		txtRent.addBlurHandler(ezeeUpdateLeaseTotalHandler);
		txtOutgoing.addKeyPressHandler(keyPressHandler);
		txtOutgoing.addBlurHandler(ezeeUpdateLeaseTotalHandler);
		txtParking.addKeyPressHandler(keyPressHandler);
		txtParking.addBlurHandler(ezeeUpdateLeaseTotalHandler);
		txtSignage.addKeyPressHandler(keyPressHandler);
		txtSignage.addBlurHandler(ezeeUpdateLeaseTotalHandler);
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
					txtBondDetail.setEnabled(false);
					txtBondAmount.setValue(getAmountFormat().format(ZERO_DBL));
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
				if (entity != null && entity.getId() != NULL_ID) {
					if (event.getValue()) {
						showNew("Comfirm Create Vacancy",
								"Would you like to create a 'vacant lease' for this premises? ",
								new EzeeConfirmDialogResultHandler() {
									@Override
									public void confrimResult(EzeeConfirmDialogResult result) {
										switch (result) {
										case ok:
											leaseUtils.createVacantLease(cache, entity, handler);
											break;
										default:
											/* do nothing */
										}

									}
								});
					}
					setEdited();
				}
				inactive(event.getValue());
			}
		});
		dtStart.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date end = DATE_UTILS.addYearsAsDays(event.getValue(), TWO);
				Date optionStart = DATE_UTILS.addDays(end, ONE);
				Date optionEnd = DATE_UTILS.addYearsAsDays(optionStart, TWO);
				dtEnd.setValue(end);
				dtOptionStart.setValue(optionStart);
				dtOptionEnd.setValue(optionEnd);
			}
		});
		dtEnd.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date optionStart = DATE_UTILS.addDays(event.getValue(), ONE);
				Date optionEnd = DATE_UTILS.addYearsAsDays(optionStart, TWO);
				dtOptionStart.setValue(optionStart);
				dtOptionEnd.setValue(optionEnd);
			}
		});
		dtOptionStart.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date optionEnd = DATE_UTILS.addYearsAsDays(event.getValue(), TWO);
				dtOptionEnd.setValue(optionEnd);
			}
		});
		chkToggleMonthly.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				toggleMonthly(event.getValue());
			}
		});
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
				switch (event.getSelectedItem()) {
				case META_DATA_INDEX:
					initMetaData();
					break;
				case NOTE_INDEX:
					initNotes();
					break;
				case FILE_INDEX:
					initFiles();
					break;
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
		case view:
			setText(headers[VIEW_HEADER_INDEX]);
			initialise();
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
		txtMyobJobNo.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnSave.setEnabled(false);
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
		if (entity != null && entity.getBond() != null) {
			lstBondType.setEnabled(!inactive);
			txtBondAmount.setEnabled(!inactive);
			txtBondDetail.setEnabled(!inactive);
		}
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

	private void updateLeaseMetaData(final double historicalRent) {
		EzeeCursorUtils.showWaitCursor();
		btnClose.setEnabled(false);
		EzeeWebCommonConstants.LEASE_UTILITY_SERVICE.getCurrentLeasePeriodString(entity, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				log.log(SEVERE, "Unable to resolve meta data description for historical rent.", caught);
				EzeeCursorUtils.showDefaultCursor();
				btnClose.setEnabled(true);
			}

			@Override
			public void onSuccess(String result) {
				EzeeLeaseMetaData metaData = new EzeeLeaseMetaData();
				metaData.setDate(DATE_UTILS.toString(new Date()));
				metaData.setType(historic_rents.key());
				metaData.setValue(getCurrencyFormat().format(historicalRent));
				metaData.setDescription(result);
				metaDataGrid.onSave(metaData);
				EzeeCursorUtils.showDefaultCursor();
				btnClose.setEnabled(true);
			}
		});
	}

	@UiHandler("btnUpdate")
	void onBtnUpdateClick(final ClickEvent event) {
		EzeeConfirmDialog.showNew("Renew Lease",
				"This action will update the lease amounts by the %'s given.  Are you sure you want to do this?",
				new EzeeConfirmDialogResultHandler() {
					@Override
					public void confrimResult(EzeeConfirmDialogResult result) {
						if (result == ok) {
							bind();
							final double historicalRent = entity.getIncidental(RENT).yearlyAmount();
							updateLeaseMetaData(historicalRent);
							updateIncidental(RENT);
							updateIncidental(OUTGOINGS);
							updateIncidental(PARKING);
							updateIncidental(SIGNAGE);
							setEdited();
						}
					}
				});
	}

	@UiHandler("btnClose")
	public void onCloseClick(ClickEvent event) {
		close();
	}

	@UiHandler("btnSave")
	public void onSaveClick(ClickEvent event) {
		setEdited();
		close();
	}

	@UiHandler("btnDelete")
	public void onDeleteClick(ClickEvent event) {
		if (entity.getId() != NULL_ID) {
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
		} else {
			handler.onDelete(entity);
			close();
		}
	}

	private void setEdited() {
		bind();
		entity.setEdited(true);
		handler.onSave(entity);
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
	public void subComponentSaved(final EzeeDateSortableDatabaseEntity entity) {
		setEdited();
	}

	@Override
	public void subComponentDeleted(final EzeeDateSortableDatabaseEntity entity) {
		setEdited();
	}

	private void toggleMonthly(boolean monthly) {
		if (monthly) {
			lblRent.setText("Rent(M)");
			lblOutgoing.setText("O/gng.(M)");
			lblParking.setText("Park.(M)");
			lblSignage.setText("Sign.(M)");
			lblNet.setText("Net(M)");
			lblGst.setText("Gst(M)");
			lblGross.setText("Gross(M)");
		} else {
			lblRent.setText("Rent(A)");
			lblOutgoing.setText("O/gng.(A)");
			lblParking.setText("Park.(A)");
			lblSignage.setText("Sign.(A)");
			lblNet.setText("Net(A)");
			lblGst.setText("Gst(A)");
			lblGross.setText("Gross(A)");
		}
		initialiseLeaseIncideantal(RENT, txtRent, txtRentPercent, txtRentAccount, monthly);
		initialiseLeaseIncideantal(OUTGOINGS, txtOutgoing, txtOutgoingPercent, txtOutgoingAccount, monthly);
		initialiseLeaseIncideantal(PARKING, txtParking, txtParkingPercent, txtParkingAccount, monthly);
		initialiseLeaseIncideantal(SIGNAGE, txtSignage, txtSignagePercent, txtSignageAccount, monthly);
		initialiseLeaseIncideantal(TOTAL, txtNet, null, null, monthly);
	}

	private class EzeeUpdateLeaseTotalHandler implements BlurHandler {
		@Override
		public void onBlur(BlurEvent event) {
			bind();
			boolean monthly = chkToggleMonthly.getValue();
			initialiseLeaseIncideantal(TOTAL, txtNet, null, null, monthly);
		}
	}
}