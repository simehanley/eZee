package com.ezee.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "EZEE_CONFIGURATION")
public class EzeeConfiguration extends EzeeDatabaseEntity implements IsSerializable {

	private static final long serialVersionUID = -4938729762631503373L;

	@Column(name = "VERSION")
	private String version;

	@Column(name = "INVOICE_TAX_RATE")
	private double invoiceTaxRate;

	@Column(name = "DEFAULT_DEBT_AGE_RULE")
	private String defaultDebtAgeRule;

	@Column(name = "DEFAULT_INVOICE_SUPPLIER")
	private String defaultInvoiceSupplier;

	@Column(name = "DEFAULT_INVOICE_PREMISES")
	private String defaultInvoicePremises;

	@Column(name = "DEFAULT_MANUAL_TAX")
	private boolean defaultManualTax;

	public EzeeConfiguration() {
		super();
	}

	public EzeeConfiguration(final String created, final String updated) {
		super(created, updated);
	}

	public EzeeConfiguration(final Long id, final String created, final String updated) {
		super(id, created, updated);
	}

	public final String getVersion() {
		return version;
	}

	public final double getInvoiceTaxRate() {
		return invoiceTaxRate;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setInvoiceTaxRate(double invoiceTaxRate) {
		this.invoiceTaxRate = invoiceTaxRate;
	}

	public final String getDefaultDebtAgeRule() {
		return defaultDebtAgeRule;
	}

	public void setDefaultDebtAgeRule(String defaultDebtAgeRule) {
		this.defaultDebtAgeRule = defaultDebtAgeRule;
	}

	public final String getDefaultInvoiceSupplier() {
		return defaultInvoiceSupplier;
	}

	public void setDefaultInvoiceSupplier(String defaultInvoiceSupplier) {
		this.defaultInvoiceSupplier = defaultInvoiceSupplier;
	}

	public final boolean getDefaultManualTax() {
		return defaultManualTax;
	}

	public void setDefaultManualTax(boolean defaultManualTax) {
		this.defaultManualTax = defaultManualTax;
	}

	public final String getDefaultInvoicePremises() {
		return defaultInvoicePremises;
	}

	public void setDefaultInvoicePremises(String defaultInvoicePremises) {
		this.defaultInvoicePremises = defaultInvoicePremises;
	}
}