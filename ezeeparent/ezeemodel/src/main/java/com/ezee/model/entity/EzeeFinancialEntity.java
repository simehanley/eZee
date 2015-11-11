package com.ezee.model.entity;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.ezee.model.entity.filter.EzeeStringFilterable;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author siborg
 *
 */
@MappedSuperclass
public abstract class EzeeFinancialEntity extends EzeeDatabaseEntity
		implements EzeeHasName, IsSerializable, EzeeStringFilterable {

	private static final long serialVersionUID = -6079993940796041714L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "ADDRESS_LINE_1")
	private String addressLineOne;

	@Column(name = "ADDRESS_LINE_2")
	private String addressLineTwo;

	@Column(name = "SUBURB")
	private String suburb;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "POST_CODE")
	private String postcode;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "FAX")
	private String fax;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "CONTACT")
	private String contact;

	public EzeeFinancialEntity() {
		super();
	}

	public EzeeFinancialEntity(final String name, final String addressLineOne, final String addressLineTwo,
			final String suburb, final String city, final String state, final String postcode, final String phone,
			final String fax, final String email, final String contact, final String created, final String updated) {
		this(NULL_ID, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, contact,
				created, updated);
	}

	public EzeeFinancialEntity(final Long id, final String name, final String addressLineOne,
			final String addressLineTwo, final String suburb, final String city, final String state,
			final String postcode, final String phone, final String fax, final String email, final String contact,
			final String created, final String updated) {
		super(id, created, updated);
		this.name = name;
		this.addressLineOne = addressLineOne;
		this.addressLineTwo = addressLineTwo;
		this.suburb = suburb;
		this.city = city;
		this.state = state;
		this.postcode = postcode;
		this.phone = phone;
		this.fax = fax;
		this.email = email;
		this.contact = contact;
	}

	@Override
	public final String getName() {
		return name;
	}

	public final String getAddressLineOne() {
		return addressLineOne;
	}

	public final String getAddressLineTwo() {
		return addressLineTwo;
	}

	public final String getSuburb() {
		return suburb;
	}

	public final String getCity() {
		return city;
	}

	public final String getState() {
		return state;
	}

	public final String getPostcode() {
		return postcode;
	}

	public final String getPhone() {
		return phone;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddressLineOne(String addressLineOne) {
		this.addressLineOne = addressLineOne;
	}

	public void setAddressLineTwo(String addressLineTwo) {
		this.addressLineTwo = addressLineTwo;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "EzeeFinancialEntity [name=" + name + "]";
	}

	@Override
	public String filterString() {
		return getName();
	}
}