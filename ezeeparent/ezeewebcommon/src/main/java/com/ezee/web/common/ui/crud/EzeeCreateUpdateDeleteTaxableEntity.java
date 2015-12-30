package com.ezee.web.common.ui.crud;

import static com.ezee.common.EzeeCommonConstants.ONE_DBL;
import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.numeric.EzeeNumericUtils.round;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.common.web.EzeeFormatUtils.getAmountFormat;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.create;

import com.ezee.common.web.EzeeFormatUtils;
import com.ezee.model.entity.EzeeConfiguration;
import com.ezee.model.entity.EzeeTaxableEntity;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.utils.EzeeTextBoxUtils;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextBox;

public abstract class EzeeCreateUpdateDeleteTaxableEntity<T extends EzeeTaxableEntity>
		extends EzeeCreateUpdateDeleteEntity<T> {

	@UiField
	public TextBox txtAmount;

	@UiField
	public TextBox txtTax;

	@UiField
	public TextBox txtTotal;

	@UiField
	public CheckBox chkManualTax;

	@UiField
	public CheckBox chkReverseTax;

	private double taxRate;

	public EzeeCreateUpdateDeleteTaxableEntity(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<T> handler, final String[] headers) {
		this(cache, handler, null, create, headers);
	}

	public EzeeCreateUpdateDeleteTaxableEntity(final EzeeEntityCache cache,
			final EzeeCreateUpdateDeleteEntityHandler<T> handler, final T entity,
			final EzeeCreateUpdateDeleteEntityType type, final String[] headers) {
		super(cache, handler, entity, type, headers);
	}

	protected void initForm() {
		if (cache.getConfiguration() != null) {
			taxRate = cache.getConfiguration().getInvoiceTaxRate();
		} else {
			taxRate = ZERO_DBL;
		}
		KeyPressHandler keyPressHandler = new EzeeTextBoxUtils.NumericKeyPressHandler();
		BlurHandler blurHandler = new EzeeAmountBlurHandler();
		txtAmount.addKeyPressHandler(keyPressHandler);
		txtAmount.addBlurHandler(blurHandler);
		txtTax.addKeyPressHandler(keyPressHandler);
		txtTax.addBlurHandler(blurHandler);
		txtTotal.addKeyPressHandler(keyPressHandler);
		txtTotal.addBlurHandler(blurHandler);
		ValueChangeHandler<Boolean> taxChangeHandler = new EzeeTaxCalculationChangeHandler();
		chkManualTax.addValueChangeHandler(taxChangeHandler);
		chkReverseTax.addValueChangeHandler(taxChangeHandler);
		FocusHandler focusHandler = new EzeeTextBoxUtils.TextBoxFocusHandler();
		txtAmount.addFocusHandler(focusHandler);
		txtTax.addFocusHandler(focusHandler);
		txtTotal.addFocusHandler(focusHandler);
	}

	protected void initialiseAmountFields() {
		txtTax.setEnabled(chkManualTax.getValue());
		txtAmount.setEnabled(!chkReverseTax.getValue());
		txtTotal.setEnabled(chkReverseTax.getValue());
	}

	protected void initialiseNew() {
		txtAmount.setValue(EzeeFormatUtils.getAmountFormat().format(ZERO_DBL));
		txtTax.setValue(EzeeFormatUtils.getAmountFormat().format(ZERO_DBL));
		txtTotal.setValue(EzeeFormatUtils.getAmountFormat().format(ZERO_DBL));
		initialiseDefaults();
	}

	protected void initialiseDefaults() {
		EzeeConfiguration configuration = cache.getConfiguration();
		if (configuration != null) {
			chkManualTax.setValue(configuration.getDefaultManualTax());
			chkReverseTax.setValue(configuration.getDefaultReverseTax());
			initialiseAmountFields();
		}
	}

	@Override
	protected void initialise() {
		txtAmount.setValue(getAmountFormat().format(entity.getNet()));
		txtTax.setValue(getAmountFormat().format(entity.getTax()));
		txtTax.setEnabled(entity.isManualTax());
		txtTotal.setValue(getAmountFormat().format(entity.getGross()));
		chkManualTax.setValue(entity.isManualTax());
		chkReverseTax.setValue(entity.isReverseTax());
		initialiseAmountFields();
	}

	@Override
	protected void bind() {
		entity.setNet(getAmountFormat().parse(txtAmount.getText()));
		entity.setTax(getAmountFormat().parse(txtTax.getText()));
		entity.setManualTax(chkManualTax.getValue());
		entity.setReverseTax(chkReverseTax.getValue());
	}

	protected void disable() {
		txtAmount.setEnabled(false);
		txtTax.setEnabled(false);
		txtTotal.setEnabled(false);
		chkManualTax.setEnabled(false);
		chkReverseTax.setEnabled(false);
	}

	private void resolveInvoiceAmount() {
		double gross = ZERO_DBL, net = ZERO_DBL, tax = ZERO_DBL;
		if (chkReverseTax.getValue()) {
			gross = hasLength(txtTotal.getText()) ? round(getAmountFormat().parse(txtTotal.getValue())) : ZERO_DBL;
			net = resolveNetAmount(gross);
			tax = gross - net;
		} else {
			net = hasLength(txtAmount.getText()) ? round(getAmountFormat().parse(txtAmount.getValue())) : ZERO_DBL;
			tax = resolveTaxAmount(net);
			gross = net + tax;
		}
		txtAmount.setValue(getAmountFormat().format(net));
		txtTax.setValue(getAmountFormat().format(tax));
		txtTotal.setValue(getAmountFormat().format(gross));
	}

	private double resolveTaxAmount(double netAmount) {
		if (!hasLength(txtTax.getText())) {
			return ZERO_DBL;
		}
		return chkManualTax.getValue() ? round(getAmountFormat().parse(txtTax.getValue())) : round(netAmount * taxRate);
	}

	private double resolveNetAmount(double grossAmount) {
		if (chkManualTax.getValue()) {
			double tax = (hasLength(txtTax.getText())) ? round(getAmountFormat().parse(txtTax.getValue())) : ZERO_DBL;
			return grossAmount - tax;
		} else {
			return round(grossAmount / (ONE_DBL + taxRate));
		}
	}

	private final class EzeeAmountBlurHandler implements BlurHandler {

		@Override
		public void onBlur(final BlurEvent event) {
			resolveInvoiceAmount();
		}
	}

	private class EzeeTaxCalculationChangeHandler implements ValueChangeHandler<Boolean> {

		@Override
		public void onValueChange(ValueChangeEvent<Boolean> event) {
			initialiseAmountFields();
			resolveInvoiceAmount();
		}
	}
}