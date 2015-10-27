package com.ezee.client.crud.payment;

import static com.ezee.client.EzeeInvoiceWebConstants.DELETE_PAYMENT;
import static com.ezee.client.EzeeInvoiceWebConstants.EDIT_PAYMENT;
import static com.ezee.client.EzeeInvoiceWebConstants.NEW_PAYMENT;
import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.create;
import static com.ezee.client.css.EzeeInvoiceGwtOverridesResources.INSTANCE;
import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.model.entity.enums.EzeePaymentType.cheque;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.crud.EzeeCreateUpdateDeleteEntity;
import com.ezee.client.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.client.grid.invoice.EzeeInvoiceGridModel;
import com.ezee.common.numeric.EzeeNumericUtils;
import com.ezee.common.web.EzeeFromatUtils;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayment;
import com.ezee.model.entity.enums.EzeePaymentType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EzeeCreateUpdateDeletePayment extends EzeeCreateUpdateDeleteEntity<EzeePayment> {

	private static final Logger log = Logger.getLogger("EzeeCreateUpdateDeletePayment");

	private static EzeeCreateUpdateDeletePaymentUiBinder uiBinder = GWT
			.create(EzeeCreateUpdateDeletePaymentUiBinder.class);

	interface EzeeCreateUpdateDeletePaymentUiBinder extends UiBinder<Widget, EzeeCreateUpdateDeletePayment> {
	}

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

	public EzeeCreateUpdateDeletePayment(final EzeeInvoiceServiceAsync service, final EzeeInvoiceEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeePayment> handler, final Set<EzeeInvoice> invoices) {
		this(service, cache, handler, null, create, invoices, cheque);
	}

	public EzeeCreateUpdateDeletePayment(final EzeeInvoiceServiceAsync service, final EzeeInvoiceEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<EzeePayment> handler, final EzeePayment entity,
			final EzeeCreateUpdateDeleteEntityType type, final Set<EzeeInvoice> invoices,
			final EzeePaymentType defaultType) {
		super(service, cache, handler, entity, type);
		this.defaultType = defaultType;
		initGrid();
		this.invoices = (entity != null) ? entity.getInvoices() : invoices;
		setWidget(uiBinder.createAndBindUi(this));
	}

	private void initGrid() {
		grdInvoices = new DataGrid<EzeeInvoice>(100, INSTANCE);
		grdInvoices.setMinimumTableWidth(600, Style.Unit.PX);
		model = new EzeeInvoiceGridModel();
		model.bind(grdInvoices);
	}

	@Override
	public void center() {
		loadTypes();
		loadInvoices();
		updateTotals();
		initForm();
		switch (type) {
		case create:
			setText(NEW_PAYMENT);
			btnDelete.setEnabled(false);
			break;
		case update:
			setText(EDIT_PAYMENT);
			initialise();
			btnDelete.setEnabled(false);
			break;
		case delete:
			setText(DELETE_PAYMENT);
			initialise();
			disable();
			break;
		}
		super.center();
	}

	private void disable() {
		btnSave.setEnabled(false);
		lstPaymentType.setEnabled(false);
		txtDescription.setEnabled(false);
	}

	private void initForm() {
		txtAmount.setEnabled(false);
		txtTax.setEnabled(false);
		txtTotal.setEnabled(false);
		lstPaymentType.setItemSelected(getItemIndex(defaultType.name(), lstPaymentType), true);
		lstPaymentType.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				EzeePaymentType type = EzeePaymentType.valueOf(lstPaymentType.getSelectedItemText());
				boolean showChequeFields = (type == cheque);
				showCheckFields(showChequeFields);
			}
		});
	}

	private void showCheckFields(boolean show) {
		lblChequeNumber.setVisible(show);
		txtChequeNumber.setVisible(show);
		chkPresented.setVisible(show);
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
			txtAmount.setText(EzeeFromatUtils.getAmountFormat().format(amount));
			txtTax.setText(EzeeFromatUtils.getAmountFormat().format(tax));
			txtTotal.setText(EzeeFromatUtils.getAmountFormat().format(total));
		}
	}

	@Override
	protected void initialise() {
		lstPaymentType.setItemSelected(getItemIndex(entity.getType().toString(), lstPaymentType), true);
		txtDescription.setText(entity.getPaymentDescription());
		boolean showChequeFields = (entity.getType() == cheque);
		showCheckFields(showChequeFields);
		txtChequeNumber.setText(entity.getChequeNumber());
		chkPresented.setValue(entity.isChequePresented());
	}

	@Override
	protected void bind() {
		if (entity == null) {
			entity = new EzeePayment();
			entity.setCreated(new Date());
		}
		entity.setPaymentDate(new Date());
		entity.setType(EzeePaymentType.valueOf(lstPaymentType.getSelectedItemText()));
		entity.setPaymentDescription(txtDescription.getText());
		entity.setInvoices(new HashSet<>(model.getHandler().getList()));
		entity.setUpdated(new Date());
		if (hasLength(txtChequeNumber.getText())) {
			entity.setChequeNumber(txtChequeNumber.getText());
		} else {
			entity.setChequeNumber(null);
		}
		entity.setChequePresented(chkPresented.getValue());
	}

	@UiHandler("btnClose")
	void onCloseClick(ClickEvent event) {
		close();
	}

	@UiHandler("btnSave")
	void onSaveClick(ClickEvent event) {
		bind();

		service.saveEntity(EzeePayment.class.getName(), entity, new AsyncCallback<EzeePayment>() {

			@Override
			public void onFailure(final Throwable caught) {
				log.log(Level.SEVERE, "Error persisting payment '" + entity + "'.", caught);
				Window.alert("Error persisting payment '" + entity + "'.  Please see log for details.");
			}

			@Override
			public void onSuccess(final EzeePayment result) {
				log.log(Level.INFO, "Saved payment '" + entity + "' successfully");
				handler.onSave(result);
				close();
			}
		});
	}

	@UiHandler("btnDelete")
	void onDeleteClick(ClickEvent event) {
		service.deleteEntity(EzeePayment.class.getName(), entity, new AsyncCallback<EzeePayment>() {

			@Override
			public void onFailure(Throwable caught) {
				log.log(Level.SEVERE, "Error deleting payment '" + entity + "'.", caught);
				Window.alert("Error deleting invoice '" + entity + "'.  Please see log for details.");
			}

			@Override
			public void onSuccess(EzeePayment result) {
				log.log(Level.INFO, "Payment '" + entity + "' deleted successfully");
				handler.onDelete(result);
				close();
			}
		});
	}
}