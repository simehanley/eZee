package com.ezee.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@DiscriminatorValue("Supplier")
public class EzeeSupplier extends EzeePayee implements IsSerializable {

	private static final long serialVersionUID = -582535496865296514L;

	public EzeeSupplier() {
		super();
	}

	public EzeeSupplier(Long id, String name, String addressLineOne, String addressLineTwo, String suburb, String city,
			String state, String postcode, String phone, String fax, String email, String contact, String created,
			String updated) {
		super(id, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, contact,
				created, updated);
	}

	public EzeeSupplier(String name, String addressLineOne, String addressLineTwo, String suburb, String city,
			String state, String postcode, String phone, String fax, String email, String contact, String created,
			String updated) {
		super(name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, contact, created,
				updated);
	}

	@Override
	public String toString() {
		return "EzeeSupplier [getName()=" + getName() + "]";
	}
}