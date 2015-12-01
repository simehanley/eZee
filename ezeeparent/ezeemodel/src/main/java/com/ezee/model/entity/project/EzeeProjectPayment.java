package com.ezee.model.entity.project;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import java.beans.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.model.entity.enums.EzeePaymentType;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author siborg
 *
 */
@Entity
@Table(name = "EZEE_PROJECT_PAYMENT")
public class EzeeProjectPayment extends EzeeDatabaseEntity implements IsSerializable {

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((chequeNumber == null) ? 0 : chequeNumber.hashCode());
		result = prime * result + ((paymentDate == null) ? 0 : paymentDate.hashCode());
		temp = Double.doubleToLongBits(tax);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		EzeeProjectPayment other = (EzeeProjectPayment) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (chequeNumber == null) {
			if (other.chequeNumber != null)
				return false;
		} else if (!chequeNumber.equals(other.chequeNumber))
			return false;
		if (paymentDate == null) {
			if (other.paymentDate != null)
				return false;
		} else if (!paymentDate.equals(other.paymentDate))
			return false;
		if (Double.doubleToLongBits(tax) != Double.doubleToLongBits(other.tax))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}