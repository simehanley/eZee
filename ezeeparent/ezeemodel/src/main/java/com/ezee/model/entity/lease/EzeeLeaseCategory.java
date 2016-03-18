package com.ezee.model.entity.lease;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ezee.model.entity.EzeeDatabaseEntity;
import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "EZEE_LEASE_CATEGORY")
public class EzeeLeaseCategory extends EzeeDatabaseEntity implements IsSerializable {

	private static final long serialVersionUID = -4128079589689346278L;

	@Column(name = "CATEGORY_NAME")
	private String category;

	@Column(name = "CATEGORY_COMPANY")
	private String categoryCompany;

	@Column(name = "CATEGORY_ABN")
	private String categoryAbn;

	@Column(name = "CATEGORY_ADDRESS")
	private String categoryAddress;

	@Column(name = "CATEGORY_PHONE")
	private String categoryPhone;

	@Column(name = "CATEGORY_ACCOUNT_NAME")
	private String categoryAccountName;

	@Column(name = "CATEGORY_BANK")
	private String categoryBank;

	@Column(name = "CATEGORY_BSB")
	private String categoryBsb;

	@Column(name = "CATEGORY_ACCOUNT_NUMBER")
	private String categoryAccountNumber;

	public EzeeLeaseCategory() {
		super();
	}

	public EzeeLeaseCategory(final String category, final String created, final String updated) {
		this(NULL_ID, category, created, updated);
	}

	protected EzeeLeaseCategory(final Long id, final String category, final String created, final String updated) {
		super(id, created, updated);
		this.category = category;
	}

	public final String getCategory() {
		return category;
	}

	public void setCategory(final String category) {
		this.category = category;
	}

	public String getCategoryCompany() {
		return categoryCompany;
	}

	public void setCategoryCompany(String categoryCompany) {
		this.categoryCompany = categoryCompany;
	}

	public String getCategoryAbn() {
		return categoryAbn;
	}

	public void setCategoryAbn(String categoryAbn) {
		this.categoryAbn = categoryAbn;
	}

	public String getCategoryAddress() {
		return categoryAddress;
	}

	public void setCategoryAddress(String categoryAddress) {
		this.categoryAddress = categoryAddress;
	}

	public String getCategoryPhone() {
		return categoryPhone;
	}

	public void setCategoryPhone(String categoryPhone) {
		this.categoryPhone = categoryPhone;
	}

	public String getCategoryAccountName() {
		return categoryAccountName;
	}

	public void setCategoryAccountName(String categoryAccountName) {
		this.categoryAccountName = categoryAccountName;
	}

	public String getCategoryBank() {
		return categoryBank;
	}

	public void setCategoryBank(String categoryBank) {
		this.categoryBank = categoryBank;
	}

	public String getCategoryBsb() {
		return categoryBsb;
	}

	public void setCategoryBsb(String categoryBsb) {
		this.categoryBsb = categoryBsb;
	}

	public String getCategoryAccountNumber() {
		return categoryAccountNumber;
	}

	public void setCategoryAccountNumber(String categoryAccountNumber) {
		this.categoryAccountNumber = categoryAccountNumber;
	}

	@Override
	public String toString() {
		return "EzeeLeaseCategory [category=" + category + "]";
	}
}