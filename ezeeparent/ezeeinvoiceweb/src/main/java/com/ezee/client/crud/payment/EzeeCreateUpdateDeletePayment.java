package com.ezee.client.crud.payment;

import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.common.web.EzeeFormatUtils.getDateBoxFormat;
import static com.ezee.model.entity.enums.EzeePaymentType.cheque;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.EzeeWebCommonConstants.ENTITY_SERVICE;
import static com.ezee.web.common.EzeeWebCommonConstants.ERROR;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.create;
import static com.ezee.web.common.ui.css.EzeeGwtOverridesResources.INSTANCE;
import static com.ezee.web.common.ui.dialog.EzeeMessageDialog.showNew;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showDefaultCursor;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showWaitCursor;
import static com.ezee.web.common.ui.utils.EzeeListBoxUtils.getItemIndex;
import static com.ezee.web.common.ui.utils.EzeeListBoxUtils.loadEnums;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.grid.invoice.EzeeInvoiceGridModel;
import com.ezee.common.numeric.EzeeNumericUtils;
import com.ezee.common.web.EzeeFormatUtils;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayment;
import com.ezee.model.entity.enums.EzeePaymentType;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntity;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.utils.EzeeTextBoxUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class EzeeCreateUpdateDeletePayment extends EzeeCreateUpdateDeleteEntity<EzeePayment> {

	private static final Logger log = Logger.getLogger("EzeeCreateUpdateDeletePayment");

	private static EzeeCreateUpdateDeletePaymentUiBinder uiBinder = GWT
			.create(EzeeCreateUpdateDeletePaymentUiBinder.class);

	interface EzeeCreateUpdateDeletePaymentUiBinder extends UiBinder<Widget, EzeeCreateUpdateDeletePayment> {
	}

	@UiField
	DateBox dtPmtDate;

	@UiField
	ListBox lstPaymentType;

	@UiField
	Label lblChequeNumber;

	@UiField
	TextBox txtChequeNumber;

	@UiField
	CheckBox chkPresented;

	@UiField
	RichTextArea txtDescription;

	@UiField(provided = true)
	DataGrid<EzeeInvoice> grdInvoices;

	@UiField
	TextBox txtAmount;

	@UiField
	TextBox txtTax;

	@UiField
	TextBox txtTotal;

	@UiField
	Button btnDelete;

	@UiField
	Button btnSave;

	@UiField
	Button btnClose;

	private final Set<EzeeInvoice> invoices;

	private EzeeInvoiceGridModel model;

	private EzeePaymentType defaultType;

	public EzeeCreateUpdateDeletePayment(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeePayment> handler, final Set<EzeeInvoice> invoices,
			final String[] headers) {
		this(cache, handler, null, create, invoices, cheque, headers);
	}

	public EzeeCreateUpdateDeletePayment(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeePayment> handler, final EzeePayment entity,
			final EzeeCreateUpdateDeleteEntityType type, final Set<EzeeInvoice> invoices,
			final EzeePaymentType defaultType, final String[] headers) {
		super(cache, handler, entity, type, headers);
		this.defaultType = defaultType;
		initGrid();
		this.invoices = (entity != null) ? entity.getInvoices() : invoices;
		setWidget(uiBinder.createAndBindUi(this));
	}

	private void initGrid() {
		grdInvoices = new DataGrid<EzeeInvoice>(100, INSTANCE);
		grdInvoices.setMinimumTableWidth(600, Style.Unit.PX);
		model = createModel();
		model.bind(grdInvoices);
	}

	@Override
	public void show() {
		loadTypes();
		loadInvoices();
		updateTotals();
		initForm();
		switch (type) {
		case create:
			setText(headers[NEW_HEADER_INDEX]);
			setFocus(lstPaymentType);
			btnDelete.setEnabled(false);
			break;
		case update:
			setText(headers[EDIT_HEADER_INDEX]);
			initialise();
			setFocus(lstPaymentType);
			btnDelete.setEnabled(false);
			break;
		case delete:
			setText(headers[DELETE_HEADER_INDEX]);
			initialise();
			disable();
			break;
		}
		super.show();
	}

	private void disable() {
		btnSave.setEnabled(false);
		lstPaymentType.setEnabled(false);
		txtDescription.setEnabled(false);
		dtPmtDate.setEnabled(false);
	}

	private void initForm() {
		dtPmtDate.setFormat(getDateBoxFormat());
		dtPmtDate.setValue(new Date());
		txtAmount.setEnabled(false);
		txtTax.setEnabled(false);
		txtTotal.setEnabled(false);
		lstPaymentType.setItemSelected(getItemIndex(defaultType.name(), lstPaymentType), true);
		lstPaymentType.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				EzeePaymentType type = EzeePaymentType.valueOf(lstPaymentType.getSelectedItemText());
				boolean enableChequeFields = (type == cheque);
				enableCheckFields(enableChequeFields);
			}
		});
		FocusHandler focusHandler = new EzeeTextBoxUtils.TextBoxFocusHandler();
		txtChequeNumber.addFocusHandler(focusHandler);
	}

	private void enableCheckFields(boolean enable) {
		txtChequeNumber.setEnabled(enable);
		chkPresented.setEnabled(enable);
	}

	private void loadInvoices() {
		if (!isEmpty(invoices)) {
			model.getHandler().getList().addAll(invoices);
			grdInvoices.redraw();
		}
	}

	private void loadTypes() {
		loadEnums(EzeePaymentType.values(), lstPaymentType);
	}

	private void updateTotals() {
		if (!isEmpty(invoices)) {
			double amount = ZERO_DBL, tax = ZERO_DBL, total = ZERO_DBL;
			for (EzeeInvoice invoice : invoices) {
				amount += invoice.getAmount();
				tax += invoice.getTax();
				total += invoice.getInvoiceAmount();
			}
			amount = EzeeNumericUtils.round(amount);
			tax = EzeeNumericUtils.round(tax);
			total = EzeeNumericUtils.round(total);
			txtAmount.setText(EzeeFormatUtils.getAmountFormat().format(amount));
			txtTax.setText(EzeeFormatUtils.getAmountFormat().format(tax));
			txtTotal.setText(EzeeFormatUtils.getAmountFormat().format(total));
		}
	}

	@Override
	protected void initialise() {
		lstPaymentType.setItemSelected(getItemIndex(entity.getType().toString(), lstPaymentType), true);
		txtDescription.setText(entity.getPaymentDescription());
		boolean enableChequeFields = (entity.getType() == cheque);
		enableCheckFields(enableChequeFields);
		txtChequeNumber.setText(entity.getChequeNumber());
		chkPresented.setValue(entity.isChequePresented());
		dtPmtDate.setValue(DATE_UTILS.fromString(entity.getPaymentDate()));
	}

	@Override
	protected void bind() {
		String updateDate = DATE_UTILS.toString(new Date());
		String pmtDate = (dtPmtDate.getValue() == null) ? updateDate : DATE_UTILS.toString(dtPmtDate.getValue());
		if (entity == null) {
			entity = new EzeePayment();
			entity.setCreated(updateDate);
			entity.setInvoices(new HashSet<>(model.getHandler().getList()));
		} else {
			entity.setUpdated(updateDate);
		}
		entity.setType(EzeePaymentType.valueOf(lstPaymentType.getSelectedItemText()));
		entity.setPaymentDescription(txtDescription.getText());
		if (hasLength(txtChequeNumber.getText())) {
			entity.setChequeNumber(txtChequeNumber.getText());
		} else {
			entity.setChequeNumber(null);
		}
		entity.setChequePresented(chkPresented.getValue());
		entity.setPaymentDate(pmtDate);
		bindinvoices(entity.getInvoices(), updateDate, pmtDate);
	}

	private void bindinvoices(final Set<EzeeInvoice> invoices, final String updateDate, final String pmtDate) {
		if (!isEmpty(invoices)) {
			for (EzeeInvoice invoice : invoices) {
				invoice.setDatePaid(pmtDate);
			}
		}
	}

	private void unbindinvoices(final Set<EzeeInvoice> invoices, final String updateDate) {
		if (!isEmpty(invoices)) {
			for (EzeeInvoice invoice : invoices) {
				invoice.setDatePaid(null);
				invoice.setUpdated(updateDate);
			}
		}
	}

	@UiHandler("btnClose")
	void onCloseClick(ClickEvent event) {
		close();
	}

	@UiHandler("btnSave")
	void onSaveClick(ClickEvent event) {
		btnSave.setEnabled(false);
		showWaitCursor();
		bind();
		ENTITY_SERVICE.saveEntity(EzeePayment.class.getName(), entity, new AsyncCallback<EzeePayment>() {

			@Override
			public void onFailure(final Throwable caught) {
				btnSave.setEnabled(true);
				showDefaultCursor();
				log.log(Level.SEVERE, "Error persisting payment '" + entity + "'.", caught);
				showNew(ERROR, "Error persisting payment '" + entity + "'.  Please see log for details.");
			}

			@Override
			public void onSuccess(final EzeePayment result) {
				log.log(Level.INFO, "Saved payment '" + entity + "' successfully");
				handler.onSave(result);
				btnSave.setEnabled(true);
				showDefaultCursor();
				close();
			}
		});
	}

	@UiHandler("btnDelete")
	void onDeleteClick(ClickEvent event) {
		btnDelete.setEnabled(false);
		showWaitCursor();
		unbindinvoices(entity.getInvoices(), DATE_UTILS.toString(new Date()));
		ENTITY_SERVICE.deleteEntity(EzeePayment.class.getName(), entity, new AsyncCallback<EzeePayment>() {

			@Override
			public void onFailure(Throwable caught) {
				btnDelete.setEnabled(true);
				showDefaultCursor();
				log.log(Level.SEVERE, "Error deleting payment '" + entity + "'.", caught);
				showNew(ERROR, "Error deleting invoice '" + entity + "'.  Please see log for details.");
			}

			@Override
			public void onSuccess(EzeePayment result) {
				log.log(Level.INFO, "Payment '" + entity + "' deleted successfully");
				handler.onDelete(result);
				btnDelete.setEnabled(true);
				showDefaultCursor();
				close();
			}
		});
	}

	private EzeeInvoiceGridModel createModel() {
		Set<String> hidden = new HashSet<>();
		hidden.add(EzeeInvoiceGridModel.INVOICE_DATE);
		hidden.add(EzeeInvoiceGridModel.DUE_DATE);
		hidden.add(EzeeInvoiceGridModel.PAID_PATE);
		hidden.add(EzeeInvoiceGridModel.FILE);
		hidden.add(EzeeInvoiceGridModel.PAY);
		return new EzeeInvoiceGridModel(hidden);
	}
}