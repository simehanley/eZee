package com.ezee.model.entity.project;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import java.beans.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.ezee.model.entity.enums.EzeePaymentType;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author siborg
 *
 */
@Entity
@Table(name = "EZEE_PROJECT_PAYMENT")
public class EzeeProjectPayment extends EzeeProjectDatabaseEntity implements IsSerializable {

	private static final long serialVersionUID = -3074199701630243689L;

	@Column(name = "PAYMENT_DATE")
	private String paymentDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "PAYMENT_TYPE")
	private EzeePaymentType type;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "AMOUNT")
	private double amount;

	@Column(name = "TAX")
	private double tax;

	@Column(name = "CHEQUE_NUMBER")
	private String chequeNumber;

	public EzeeProjectPayment() {
		super();
	}

	public EzeeProjectPayment(final String paymentDate, final EzeePaymentType type, final String description,
			final double amount, final double tax, final String created, final String updated) {
		this(NULL_ID, paymentDate, type, description, amount, tax, created, updated);
	}

	public EzeeProjectPayment(final Long id, final String paymentDate, final EzeePaymentType type,
			final String description, final double amount, final double tax, final String created,
			final String updated) {
		super(id, created, updated);
		this.paymentDate = paymentDate;
		this.type = type;
		this.description = description;
		this.amount = amount;
		this.tax = tax;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public EzeePaymentType getType() {
		return type;
	}

	public void setType(EzeePaymentType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	@Transient
	public double getTotal() {
		return getAmount() + getTax();
	}

	@Override
	public String toString() {
		return "EzeeProjectPayment [paymentDate=" + paymentDate + ", type=" + type + ", description=" + description
				+ ", amount=" + amount + ", tax=" + tax + ", chequeNumber=" + chequeNumber + ", getGridId()="
				+ getGridId() + ", getId()=" + getId() + "]";
	}
}