package com.ezee.client.crud.invoice;

import static com.ezee.client.EzeeInvoiceWebConstants.DELETE_INVOICE;
import static com.ezee.client.EzeeInvoiceWebConstants.EDIT_INVOICE;
import static com.ezee.client.EzeeInvoiceWebConstants.NEW_INVOICE;
import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.create;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.numeric.EzeeNumericUtils.round;
import static com.ezee.common.web.EzeeFromatUtils.getAmountFormat;
import static com.ezee.common.web.EzeeFromatUtils.getDateBoxFormat;
import static com.ezee.web.common.ui.utils.EzeeListBoxUtils.getItemIndex;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.crud.EzeeCreateUpdateDeleteEntity;
import com.ezee.client.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.client.ui.EzeeInvoiceUiUtils;
import com.ezee.client.util.EzeeDueDateCalculator;
import com.ezee.common.web.EzeeFromatUtils;
import com.ezee.model.entity.EzeeDebtAgeRule;
import com.ezee.model.entity.EzeeHasName;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayee;
import com.ezee.model.entity.EzeePayer;
import com.ezee.model.entity.enums.EzeeInvoiceClassification;
import com.ezee.web.common.ui.utils.EzeeListBoxUtils;
import com.ezee.web.common.ui.utils.EzeeTextBoxUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class EzeeCreateUpdateDeleteInvoice extends EzeeCreateUpdateDeleteEntity<EzeeInvoice> {

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
	TextBox txtAmount;

	@UiField
	TextBox txtTax;

	@UiField
	TextBox txtTotal;

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
	CheckBox chkTaxable;

	@UiField
	Button btnClose;

	@UiField
	Button btnSave;

	@UiField
	Button btnDelete;

	private double taxRate;

	public EzeeCreateUpdateDeleteInvoice(final EzeeInvoiceServiceAsync service, final EzeeInvoiceEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<EzeeInvoice> handler) {
		this(service, cache, handler, null, create);
	}

	public EzeeCreateUpdateDeleteInvoice(final EzeeInvoiceServiceAsync service, final EzeeInvoiceEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<EzeeInvoice> handler, final EzeeInvoice entity,
			final EzeeCreateUpdateDeleteEntityType type) {
		super(service, cache, handler, entity, type);
		setWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void center() {
		loadEntities();
		initForm();
		switch (type) {
		case create:
			setText(NEW_INVOICE);
			initialiseNew();
			btnDelete.setEnabled(false);
			break;
		case update:
			setText(EDIT_INVOICE);
			initialise();
			btnDelete.setEnabled(false);
			if (entity.getDatePaid() != null) {
				disable();
			}
			break;
		case delete:
			setText(DELETE_INVOICE);
			initialise();
			disable();
			break;
		}
		super.center();
	}

	private void disable() {
		txtInvoiceNumber.setEnabled(false);
		lstPremises.setEnabled(false);
		lstSupplier.setEnabled(false);
		txtAmount.setEnabled(false);
		dtDue.setEnabled(false);
		dtPaid.setEnabled(false);
		txtDescription.setEnabled(false);
		chkTaxable.setEnabled(false);
		lstDebtAge.setEnabled(false);
		btnSave.setEnabled(false);
	}

	@Override
	protected void initialise() {
		txtInvoiceNumber.setValue(entity.getInvoiceId());
		lstPremises.setItemSelected(getItemIndex(entity.getPayer().getName(), lstPremises), true);
		lstSupplier.setItemSelected(getItemIndex(entity.getPayee().getName(), lstSupplier), true);
		if (entity.getAgeRule() == null) {
			lstDebtAge.setItemSelected(ZERO, true);
		} else {
			lstDebtAge.setItemSelected(getItemIndex(entity.getAgeRule().getName(), lstDebtAge), true);
		}
		txtAmount.setValue(getAmountFormat().format(entity.getAmount()));
		txtTax.setValue(getAmountFormat().format(entity.getTax()));
		txtTotal.setValue(getAmountFormat().format(entity.getInvoiceAmount()));
		dtDue.setValue(entity.getDateDue());
		dtPaid.setValue(entity.getDatePaid());
		txtDescription.setText(entity.getDescription());
		lstClassification.setItemSelected(getItemIndex(entity.getClassification().name(), lstClassification), true);
	}

	private void initialiseNew() {
		txtAmount.setValue(EzeeFromatUtils.getAmountFormat().format(ZERO_DBL));
		txtTax.setValue(EzeeFromatUtils.getAmountFormat().format(ZERO_DBL));
		txtTotal.setValue(EzeeFromatUtils.getAmountFormat().format(ZERO_DBL));
	}

	@Override
	protected void bind() {
		if (entity == null) {
			entity = new EzeeInvoice();
			entity.setCreated(new Date());
		}
		entity.setInvoiceId(txtInvoiceNumber.getText());
		entity.setPayer(getPremises());
		entity.setPayee(getSupplier());
		entity.setAgeRule(getAgeRule());
		entity.setAmount(getAmountFormat().parse(txtAmount.getText()));
		entity.setTax(getAmountFormat().parse(txtTax.getText()));
		entity.setDateDue(dtDue.getValue());
		entity.setDatePaid(dtPaid.getValue());
		entity.setTaxable(chkTaxable.getValue());
		entity.setPaid(dtPaid.getValue() != null);
		entity.setDescription(txtDescription.getText());
		entity.setUpdated(new Date());
		entity.setClassification(EzeeInvoiceClassification.valueOf(lstClassification.getSelectedItemText()));
	}

	private void initForm() {
		dtDue.setFormat(getDateBoxFormat());
		dtPaid.setFormat(getDateBoxFormat());
		dtPaid.setEnabled(false);
		txtTax.setEnabled(false);
		txtTotal.setEnabled(false);
		taxRate = cache.getConfiguration().getInvoiceTaxRate();
		txtAmount.addKeyPressHandler(new EzeeTextBoxUtils.NumericKeyPressHandler());
		txtAmount.addBlurHandler(new NumericBlurHandler());
		chkTaxable.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				resolveInvoiceAmounts();
			}
		});
		lstDebtAge.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				resolveDueDate();

			}
		});
		resolveDueDate();
	}

	private EzeeDebtAgeRule getAgeRule() {
		return getEntity(EzeeDebtAgeRule.class, lstDebtAge);
	}

	private EzeePayee getSupplier() {
		return getEntity(EzeePayee.class, lstSupplier);
	}

	private EzeePayer getPremises() {
		return getEntity(EzeePayer.class, lstPremises);
	}

	@SuppressWarnings("unchecked")
	private <T extends EzeeHasName> T getEntity(final Class<T> clazz, final ListBox listBox) {
		String name = listBox.getSelectedValue();
		if (name != null) {
			return (T) cache.getEntities(clazz).get(name);
		}
		return null;
	}

	private void loadEntities() {
		EzeeInvoiceUiUtils.loadEntities(EzeePayee.class, lstSupplier, cache);
		EzeeInvoiceUiUtils.loadEntities(EzeePayer.class, lstPremises, cache);
		EzeeInvoiceUiUtils.loadEntities(EzeeDebtAgeRule.class, lstDebtAge, cache);
		EzeeListBoxUtils.loadEnums(EzeeInvoiceClassification.values(), lstClassification);
	}

	@UiHandler("btnClose")
	void onCloseClick(ClickEvent event) {
		close();
	}

	@UiHandler("btnSave")
	void onSaveClick(ClickEvent event) {
		bind();

		service.saveEntity(EzeeInvoice.class.getName(), entity, new AsyncCallback<EzeeInvoice>() {

			@Override
			public void onFailure(final Throwable caught) {
				log.log(Level.SEVERE, "Error persisting invoice '" + entity + "'.", caught);
				Window.alert("Error persisting invoice '" + entity + "'.  Please see log for details.");
			}

			@Override
			public void onSuccess(final EzeeInvoice result) {
				log.log(Level.INFO, "Saved invoice '" + entity + "' successfully");
				handler.onSave(result);
				close();
			}
		});
	}

	@UiHandler("btnDelete")
	void onDeleteClick(ClickEvent event) {
		service.deleteEntity(EzeeInvoice.class.getName(), entity, new AsyncCallback<EzeeInvoice>() {

			@Override
			public void onFailure(Throwable caught) {
				log.log(Level.SEVERE, "Error deleting invoice '" + entity + "'.", caught);
				Window.alert("Error deleting invoice '" + entity + "'.  Please see log for details.");
			}

			@Override
			public void onSuccess(EzeeInvoice result) {
				log.log(Level.INFO, "Invoice '" + entity + "' deleted successfully");
				handler.onDelete(result);
				close();
			}
		});
	}

	private final class NumericBlurHandler implements BlurHandler {

		@Override
		public void onBlur(final BlurEvent event) {
			resolveInvoiceAmounts();
		}
	}

	private void resolveInvoiceAmounts() {
		double amount = round(getAmountFormat().parse(txtAmount.getValue()));
		double tax = (chkTaxable.getValue()) ? round(amount * taxRate) : ZERO_DBL;
		double total = amount + tax;
		txtAmount.setValue(getAmountFormat().format(amount));
		txtTax.setValue(getAmountFormat().format(tax));
		txtTotal.setValue(getAmountFormat().format(total));
	}

	private void resolveDueDate() {
		EzeeDebtAgeRule rule = (EzeeDebtAgeRule) cache.getEntities(EzeeDebtAgeRule.class)
				.get(lstDebtAge.getSelectedItemText());
		dtDue.setValue(EzeeDueDateCalculator.calculate(rule));
	}
}