package com.ezee.client.crud.invoice;

import static com.ezee.client.EzeeInvoiceWebConstants.INVOICE_SERVICE;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.common.web.EzeeFormatUtils.getDateBoxFormat;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.EzeeWebCommonConstants.ENTITY_SERVICE;
import static com.ezee.web.common.EzeeWebCommonConstants.ERROR;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.create;
import static com.ezee.web.common.ui.dialog.EzeeMessageDialog.showNew;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showDefaultCursor;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showWaitCursor;
import static com.ezee.web.common.ui.utils.EzeeListBoxUtils.getItemIndex;
import static com.ezee.web.common.ui.utils.EzeeListBoxUtils.setValue;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.grid.invoice.EzeeCreateUpdateDeleteInvoiceHandler;
import com.ezee.model.entity.EzeeConfiguration;
import com.ezee.model.entity.EzeeDebtAgeRule;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayer;
import com.ezee.model.entity.EzeeSupplier;
import com.ezee.model.entity.enums.EzeeInvoiceClassification;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteTaxableEntity;
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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class EzeeCreateUpdateDeleteInvoice extends EzeeCreateUpdateDeleteTaxableEntity<EzeeInvoice> {

	private static final Logger log = Logger.getLogger("EzeeCreateUpdateDeleteInvoice");

	private static EzeeCreateUpdateDeleteInvoiceUiBinder uiBinder = GWT
			.create(EzeeCreateUpdateDeleteInvoiceUiBinder.class);

	interface EzeeCreateUpdateDeleteInvoiceUiBinder extends UiBinder<Widget, EzeeCreateUpdateDeleteInvoice> {
	}

	@UiField
	TextBox txtInvoiceNumber;

	@UiField
	ListBox lstPremises;

	@UiField
	ListBox lstSupplier;

	@UiField
	DateBox dtInvoice;

	@UiField
	DateBox dtDue;

	@UiField
	DateBox dtPaid;

	@UiField
	ListBox lstDebtAge;

	@UiField
	ListBox lstClassification;

	@UiField
	RichTextArea txtDescription;

	@UiField
	Button btnPay;

	@UiField
	Button btnClose;

	@UiField
	Button btnSave;

	@UiField
	Button btnDelete;

	private String supplierName;

	public EzeeCreateUpdateDeleteInvoice(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteInvoiceHandler handler, final String supplierName, final String[] headers) {
		this(cache, handler, null, create, supplierName, headers);
	}

	public EzeeCreateUpdateDeleteInvoice(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteInvoiceHandler handler, final EzeeInvoice entity,
			final EzeeCreateUpdateDeleteEntityType type, final String supplierName, final String[] headers) {
		super(cache, handler, entity, type, headers);
		setWidget(uiBinder.createAndBindUi(this));
		this.supplierName = supplierName;
	}

	@Override
	public void show() {
		loadEntities();
		initForm();
		switch (type) {
		case create:
			setText(headers[NEW_HEADER_INDEX]);
			initialiseNew();
			btnDelete.setEnabled(false);
			setFocus(txtInvoiceNumber);
			break;
		case update:
			setText(headers[EDIT_HEADER_INDEX]);
			initialise();
			setFocus(txtInvoiceNumber);
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

	protected void disable() {
		super.disable();
		txtInvoiceNumber.setEnabled(false);
		lstPremises.setEnabled(false);
		lstSupplier.setEnabled(false);
		dtDue.setEnabled(false);
		dtPaid.setEnabled(false);
		dtInvoice.setEnabled(false);
		txtDescription.setEnabled(false);
		lstDebtAge.setEnabled(false);
		btnSave.setEnabled(false);
		btnPay.setEnabled(false);
		lstClassification.setEnabled(false);
	}

	@Override
	protected void initialise() {
		super.initialise();
		txtInvoiceNumber.setValue(entity.getInvoiceId());
		lstPremises.setItemSelected(getItemIndex(entity.getPayer().getName(), lstPremises), true);
		lstSupplier.setItemSelected(getItemIndex(entity.getSupplier().getName(), lstSupplier), true);
		if (entity.getAgeRule() == null) {
			lstDebtAge.setItemSelected(ZERO, true);
		} else {
			lstDebtAge.setItemSelected(getItemIndex(entity.getAgeRule().getName(), lstDebtAge), true);
		}
		dtInvoice.setValue(DATE_UTILS.fromString(entity.getInvoiceDate()));
		if (entity.getDateDue() != null) {
			dtDue.setValue(DATE_UTILS.fromString(entity.getDateDue()));
		} else {
			resolveDueDate();
		}
		dtPaid.setValue(DATE_UTILS.fromString(entity.getDatePaid()));
		txtDescription.setText(entity.getDescription());
		lstClassification.setItemSelected(getItemIndex(entity.getClassification().name(), lstClassification), true);
		btnPay.setEnabled(entity.getDatePaid() == null);
	}

	protected void initialiseNew() {
		super.initialiseNew();
		resolveDueDate();
	}

	protected void initialiseDefaults() {
		super.initialiseDefaults();
		EzeeConfiguration configuration = cache.getConfiguration();
		if (configuration != null) {
			setValue(configuration.getDefaultDebtAgeRule(), lstDebtAge);
			String supplier = hasLength(supplierName) ? supplierName : configuration.getDefaultInvoiceSupplier();
			setValue(supplier, lstSupplier);
			setValue(configuration.getDefaultInvoicePremises(), lstPremises);
		}
	}

	@Override
	protected void bind() {
		if (entity == null) {
			entity = new EzeeInvoice();
			entity.setCreated(DATE_UTILS.toString(new Date()));
		} else {
			entity.setUpdated(DATE_UTILS.toString(new Date()));
		}
		entity.setInvoiceId(txtInvoiceNumber.getText());
		entity.setPayer(getPremises());
		entity.setSupplier(getSupplier());
		entity.setAgeRule(getAgeRule());
		entity.setInvoiceDate(DATE_UTILS.toString(dtInvoice.getValue()));
		entity.setDateDue(DATE_UTILS.toString(dtDue.getValue()));
		entity.setDatePaid(DATE_UTILS.toString(dtPaid.getValue()));
		entity.setDescription(txtDescription.getText());
		entity.setClassification(EzeeInvoiceClassification.valueOf(lstClassification.getSelectedItemText()));
		super.bind();
	}

	protected void initForm() {
		super.initForm();
		dtInvoice.setFormat(getDateBoxFormat());
		dtInvoice.setValue(new Date());
		dtDue.setFormat(getDateBoxFormat());
		dtPaid.setFormat(getDateBoxFormat());
		dtPaid.setEnabled(false);
		lstDebtAge.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				resolveDueDate();
			}
		});
		dtInvoice.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				resolveDueDate();
			}
		});
	}

	private EzeeDebtAgeRule getAgeRule() {
		return EzeeListBoxUtils.getEntity(EzeeDebtAgeRule.class, lstDebtAge, cache);
	}

	private EzeeSupplier getSupplier() {
		return EzeeListBoxUtils.getEntity(EzeeSupplier.class, lstSupplier, cache);
	}

	private EzeePayer getPremises() {
		return EzeeListBoxUtils.getEntity(EzeePayer.class, lstPremises, cache);
	}

	private void loadEntities() {
		EzeeListBoxUtils.loadEntities(EzeeSupplier.class, lstSupplier, cache);
		EzeeListBoxUtils.loadEntities(EzeePayer.class, lstPremises, cache);
		EzeeListBoxUtils.loadEntities(EzeeDebtAgeRule.class, lstDebtAge, cache);
		EzeeListBoxUtils.loadEnums(EzeeInvoiceClassification.values(), lstClassification);
	}

	@UiHandler("btnClose")
	void onCloseClick(ClickEvent event) {
		close();
	}

	@UiHandler("btnSave")
	void onSaveClick(ClickEvent event) {
		save(false);
	}

	@UiHandler("btnDelete")
	void onDeleteClick(ClickEvent event) {
		btnDelete.setEnabled(false);
		showWaitCursor();
		ENTITY_SERVICE.deleteEntity(EzeeInvoice.class.getName(), entity, new AsyncCallback<EzeeInvoice>() {

			@Override
			public void onFailure(Throwable caught) {
				btnDelete.setEnabled(true);
				showDefaultCursor();
				log.log(Level.SEVERE, "Error deleting invoice '" + entity + "'.", caught);
				showNew(ERROR, "Error deleting invoice '" + entity + "'.  Please see log for details.");
			}

			@Override
			public void onSuccess(EzeeInvoice result) {
				log.log(Level.INFO, "Invoice '" + entity + "' deleted successfully");
				handler.onDelete(result);
				btnDelete.setEnabled(true);
				showDefaultCursor();
				close();
			}
		});
	}

	@UiHandler("btnPay")
	void onPayClick(ClickEvent event) {
		save(true);
	}

	private void resolveDueDate() {
		showWaitCursor();
		EzeeDebtAgeRule rule = (EzeeDebtAgeRule) cache.getEntities(EzeeDebtAgeRule.class)
				.get(lstDebtAge.getSelectedItemText());
		String date = (dtInvoice.getValue() == null) ? DATE_UTILS.toString(new Date())
				: DATE_UTILS.toString(dtInvoice.getValue());
		INVOICE_SERVICE.calculateDueDate(rule, date, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				log.log(Level.SEVERE, "Unable to calculate due date.", caught);
				showDefaultCursor();
			}

			@Override
			public void onSuccess(final String result) {
				dtDue.setValue(DATE_UTILS.fromString(result));
				showDefaultCursor();
			}
		});
	}

	private void save(final boolean makePayment) {
		btnSave.setEnabled(false);
		showWaitCursor();
		bind();
		ENTITY_SERVICE.saveEntity(EzeeInvoice.class.getName(), entity, new AsyncCallback<EzeeInvoice>() {

			@Override
			public void onFailure(final Throwable caught) {
				btnSave.setEnabled(true);
				showDefaultCursor();
				log.log(Level.SEVERE, "Error persisting invoice '" + entity + "'.", caught);
				showNew(ERROR, "Error persisting invoice '" + entity + "'.  Please see log for details.");
			}

			@Override
			public void onSuccess(final EzeeInvoice result) {
				log.log(Level.INFO, "Saved invoice '" + entity + "' successfully");
				handler.onSave(result);
				btnSave.setEnabled(true);
				showDefaultCursor();
				close();
				if (makePayment) {
					((EzeeCreateUpdateDeleteInvoiceHandler) handler).onCreatePaymentFromNewInvoice(result);
				}
			}
		});
	}
}