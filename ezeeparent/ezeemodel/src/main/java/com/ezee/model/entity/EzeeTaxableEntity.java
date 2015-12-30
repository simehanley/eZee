package com.ezee.model.entity;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

public abstract class EzeeTaxableEntity extends EzeeDatabaseEntity {

	private static final long serialVersionUID = -5617273934977186455L;

	public EzeeTaxableEntity() {
		super();
	}

	public EzeeTaxableEntity(String created, String updated) {
		this(NULL_ID, created, updated);
	}

	public EzeeTaxableEntity(final Long id, final String created, final String updated) {
		super(id, created, updated);
	}

	public abstract double getNet();

	public abstract void setNet(double net);

	public abstract double getTax();

	public abstract void setTax(double tax);

	public abstract double getGross();

	public abstract boolean isManualTax();

	public abstract void setManualTax(boolean manualTax);

	public abstract boolean isReverseTax();

	public abstract void setReverseTax(boolean reverseTax);
}