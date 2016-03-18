package com.ezee.model.entity.lease;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;
import static javax.persistence.EnumType.STRING;

import javax.persistence.Column;
import javax.persistence.Enumerated;

import com.ezee.model.entity.EzeeDatabaseEntity;
import com.google.gwt.user.client.rpc.IsSerializable;

public class EzeeLeaseBond extends EzeeDatabaseEntity implements IsSerializable {

	private static final long serialVersionUID = 6130332338382084004L;

	@Column(name = "BOND_TYPE")
	@Enumerated(STRING)
	private EzeeLeaseBondType type;

	@Column(name = "BOND_AMOUNT")
	private double amount;

	@Column(name = "BOND_NOTES")
	private String notes;

	public EzeeLeaseBond() {
		super();
	}

	public EzeeLeaseBond(final EzeeLeaseBondType type, final double amount, final String notes, final String created,
			final String updated) {
		this(NULL_ID, type, amount, notes, created, updated);
	}

	public EzeeLeaseBond(final Long id, final EzeeLeaseBondType type, final double amount, final String notes,
			final String created, final String updated) {
		super(id, created, updated);
		this.type = type;
		this.amount = amount;
		this.notes = notes;
	}

	public EzeeLeaseBondType getType() {
		return type;
	}

	public void setType(EzeeLeaseBondType type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}