package com.ezee.model.entity.project;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import java.beans.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.model.entity.enums.EzeeProjectItemType;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author siborg
 *
 */
@Entity
@Table(name = "EZEE_PROJECT_ITEM_DETAIL")
public class EzeeProjectItemDetail extends EzeeDatabaseEntity implements IsSerializable {

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		EzeeProjectItemDetail other = (EzeeProjectItemDetail) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (Double.doubleToLongBits(tax) != Double.doubleToLongBits(other.tax))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}