package com.ezee.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author siborg
 *
 */
@Entity
@Table(name = "EZEE_PAYEE")
public class EzeePayee extends EzeeFinancialEntity {

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

	public EzeePayee(final Long id, final String name, final String addressLineOne, final String addressLineTwo,
			final String suburb, final String city, final String state, final String postcode, final String phone,
			final Date created, final Date updated) {
		super(id, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, created, updated);
	}

	public EzeePayee(final String name, final String addressLineOne, final String addressLineTwo, final String suburb,
			final String city, final String state, final String postcode, final String phone, final Date created,
			final Date updated) {
		super(name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, created, updated);
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
}