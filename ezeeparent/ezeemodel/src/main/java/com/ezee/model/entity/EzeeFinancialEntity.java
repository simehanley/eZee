package com.ezee.model.entity;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 
 * @author siborg
 *
 */
@MappedSuperclass
public abstract class EzeeFinancialEntity extends EzeeDatabaseEntity {

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

	public EzeeFinancialEntity() {
		super();
	}

	public EzeeFinancialEntity(final String name, final String addressLineOne, final String addressLineTwo,
			final String suburb, final String city, final String state, final String postcode, final String phone,
			final Date created, final Date updated) {
		this(NULL_ID, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, created, updated);
	}

	public EzeeFinancialEntity(final Long id, final String name, final String addressLineOne,
			final String addressLineTwo, final String suburb, final String city, final String state,
			final String postcode, final String phone, final Date created, final Date updated) {
		super(id, created, updated);
		this.name = name;
		this.addressLineOne = addressLineOne;
		this.addressLineTwo = addressLineTwo;
		this.suburb = suburb;
		this.city = city;
		this.state = state;
		this.postcode = postcode;
		this.phone = phone;
	}

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

	@Override
	public String toString() {
		return "EzeeFinancialEntity [name=" + name + "]";
	}
}