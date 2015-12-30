package com.ezee.model.entity.project;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import java.beans.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.ezee.model.entity.enums.EzeeProjectItemType;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author siborg
 *
 */
@Entity
@Table(name = "EZEE_PROJECT_ITEM_DETAIL")
public class EzeeProjectItemDetail extends EzeeProjectDatabaseEntity implements IsSerializable {

	private static final long serialVersionUID = 4722296927992884033L;

	@Column(name = "DESCRIPTION")
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "ITEM_TYPE")
	private EzeeProjectItemType type;

	@Column(name = "AMOUNT")
	private double amount;

	@Column(name = "TAX")
	private double tax;

	@Column(name = "MANUAL_TAX")
	private boolean manualtax = Boolean.TRUE;

	@Column(name = "REVERSE_TAX")
	private boolean reversetax = Boolean.FALSE;

	public EzeeProjectItemDetail() {
		super();
	}

	public EzeeProjectItemDetail(final String description, final EzeeProjectItemType type, final double amount,
			final double tax, final String created, final String updated) {
		this(NULL_ID, description, type, amount, tax, created, updated);
	}

	public EzeeProjectItemDetail(final Long id, final String description, final EzeeProjectItemType type,
			final double amount, final double tax, final String created, final String updated) {
		super(id, created, updated);
		this.description = description;
		this.type = type;
		this.amount = amount;
		this.tax = tax;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EzeeProjectItemType getType() {
		return type;
	}

	public void setType(EzeeProjectItemType type) {
		this.type = type;
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

	@Transient
	public double getTotal() {
		return getAmount() + getTax();
	}

	@Override
	public final boolean isManualTax() {
		return manualtax;
	}

	public void setManualTax(boolean manualtax) {
		this.manualtax = manualtax;
	}

	@Override
	public final boolean isReverseTax() {
		return reversetax;
	}

	public void setReverseTax(boolean reversetax) {
		this.reversetax = reversetax;
	}

	@Override
	public String toString() {
		return "EzeeProjectItemDetail [description=" + description + ", type=" + type + ", amount=" + amount + ", tax="
				+ tax + ", getGridId()=" + getGridId() + ", getId()=" + getId() + "]";
	}

	@Override
	public double getNet() {
		return getAmount();
	}

	@Override
	public double getGross() {
		return getTotal();
	}

	@Override
	public void setNet(double net) {
		setAmount(net);
	}
}