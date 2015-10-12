package com.ezee.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EZEE_CONFIGURATION")
public class EzeeConfiguration extends EzeeDatabaseEntity {

	private static final long serialVersionUID = -4938729762631503373L;

	@Column(name = "VERSION")
	private String version;

	@Column(name = "INVOICE_TAX_RATE")
	private double invoiceTaxRate;

	public EzeeConfiguration() {
		super();
	}

	public EzeeConfiguration(Date created, Date updated) {
		super(created, updated);
	}

	public EzeeConfiguration(Long id, Date created, Date updated) {
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
}