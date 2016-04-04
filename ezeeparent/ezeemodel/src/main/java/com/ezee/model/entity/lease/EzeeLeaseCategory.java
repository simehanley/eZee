package com.ezee.model.entity.lease;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ezee.model.entity.EzeeFinancialEntity;
import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "EZEE_LEASE_CATEGORY")
public class EzeeLeaseCategory extends EzeeFinancialEntity implements IsSerializable {

	private static final long serialVersionUID = -4128079589689346278L;

	@Column(name = "CATEGORY_COMPANY")
	private String categoryCompany;

	@Column(name = "BANK")
	private String bank;

	@Column(name = "ACCOUNT_NAME")
	private String accountName;

	@Column(name = "ACCOUNT_NUMBER")
	private String accountNumber;

	@Column(name = "ACCOUNT_BSB")
	private String accountBsb;

	@Column(name = "ABN")
	private String abn;

	public EzeeLeaseCategory() {
		super();
	}

	public EzeeLeaseCategory(Long id, String name, String categoryCompany, String addressLineOne, String addressLineTwo,
			String suburb, String city, String state, String postcode, String phone, String fax, String email,
			String contact, String bank, String accountName, String accountNumber, String accountBsb, String abn,
			String created, String updated) {
		super(id, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, contact,
				created, updated);
		this.categoryCompany = categoryCompany;
		this.bank = bank;
		this.accountName = accountName;
		this.accountNumber = accountNumber;
		this.accountBsb = accountBsb;
		this.abn = abn;
	}

	public EzeeLeaseCategory(String name, String categoryCompany, String addressLineOne, String addressLineTwo,
			String suburb, String city, String state, String postcode, String phone, String fax, String email,
			String contact, String bank, String accountName, String accountNumber, String accountBsb, String abn,
			String created, String updated) {
		this(NULL_ID, name, categoryCompany, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax,
				email, contact, bank, accountName, accountNumber, accountBsb, abn, created, updated);
	}

	public final String getCategoryCompany() {
		return categoryCompany;
	}

	public final void setCategoryCompany(String categoryCompany) {
		this.categoryCompany = categoryCompany;
	}

	public final String getBank() {
		return bank;
	}

	public final void setBank(String bank) {
		this.bank = bank;
	}

	public final String getAccountName() {
		return accountName;
	}

	public final void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public final String getAccountNumber() {
		return accountNumber;
	}

	public final void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public final String getAccountBsb() {
		return accountBsb;
	}

	public final void setAccountBsb(String accountBsb) {
		this.accountBsb = accountBsb;
	}

	public final String getAbn() {
		return abn;
	}

	public final void setAbn(String abn) {
		this.abn = abn;
	}

	@Override
	public String toString() {
		return "EzeeLeaseCategory [category=" + getName() + "]";
	}
}