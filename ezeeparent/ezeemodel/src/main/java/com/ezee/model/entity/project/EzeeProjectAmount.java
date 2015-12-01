package com.ezee.model.entity.project;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EzeeProjectAmount implements Serializable, IsSerializable {

	private static final long serialVersionUID = -6588825049314920164L;

	private final double amount;

	private final double tax;

	public EzeeProjectAmount(final double amount, final double tax) {
		this.amount = amount;
		this.tax = tax;
	}

	public final double getAmount() {
		return amount;
	}

	public final double getTax() {
		return tax;
	}

	public final double getTotal() {
		return getAmount() + getTax();
	}

	public EzeeProjectAmount minus(EzeeProjectAmount amount) {
		return new EzeeProjectAmount(this.amount - amount.getAmount(), this.tax - amount.getTax());
	}
}