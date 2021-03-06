package com.ezee.model.entity;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;
import static javax.persistence.DiscriminatorType.STRING;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Table;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author siborg
 *
 */
@Entity
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "PAYEE_TYPE", discriminatorType = STRING)
@Table(name = "EZEE_PAYEE")
public abstract class EzeePayee extends EzeeFinancialEntity implements IsSerializable {

	private static final long serialVersionUID = -7361680029940311470L;

	@Column(name = "BANK")
	private String bank;

	@Column(name = "ACCOUNT_NAME")
	private String accountName;

	@Column(name = "ACCOUNT_NUMBER")
	private String accountNumber;

	@Column(name = "ACCOUNT_BSB")
	private String accountBsb;

	public EzeePayee() {
		super();
	}

	public EzeePayee(final String name, final String addressLineOne, final String addressLineTwo, final String suburb,
			final String city, final String state, final String postcode, final String phone, final String fax,
			final String email, final String contact, final String created, final String updated) {
		this(NULL_ID, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, contact,
				created, updated);
	}

	public EzeePayee(final Long id, final String name, final String addressLineOne, final String addressLineTwo,
			final String suburb, final String city, final String state, final String postcode, final String phone,
			final String fax, final String email, final String contact, final String created, final String updated) {
		super(id, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, contact,
				created, updated);
	}

	public final String getBank() {
		return bank;
	}

	public final String getAccountName() {
		return accountName;
	}

	public final String getAccountNumber() {
		return accountNumber;
	}

	public final String getAccountBsb() {
		return accountBsb;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setAccountBsb(String accountBsb) {
		this.accountBsb = accountBsb;
	}

	@Override
	public String toString() {
		return "EzeePayee [getName()=" + getName() + "]";
	}
}