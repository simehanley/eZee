package com.ezee.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@DiscriminatorValue("Contractor")
public class EzeeContractor extends EzeePayee implements IsSerializable {

	private static final long serialVersionUID = -3382572873460948898L;

	public EzeeContractor() {
		super();
	}

	public EzeeContractor(Long id, String name, String addressLineOne, String addressLineTwo, String suburb,
			String city, String state, String postcode, String phone, String fax, String email, String contact,
			String created, String updated) {
		super(id, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, contact,
				created, updated);
	}

	public EzeeContractor(String name, String addressLineOne, String addressLineTwo, String suburb, String city,
			String state, String postcode, String phone, String fax, String email, String contact, String created,
			String updated) {
		super(name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, contact, created,
				updated);
	}

	@Override
	public String toString() {
		return "EzeeContractor [getName()=" + getName() + "]";
	}
}