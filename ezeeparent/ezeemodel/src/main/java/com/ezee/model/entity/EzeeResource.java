package com.ezee.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@DiscriminatorValue("EzeeConstructionWeb")
public class EzeeResource extends EzeePayee implements IsSerializable {

	private static final long serialVersionUID = -3382572873460948898L;

	public EzeeResource() {
		super();
	}

	public EzeeResource(Long id, String name, String addressLineOne, String addressLineTwo, String suburb, String city,
			String state, String postcode, String phone, String fax, String email, String contact, String created,
			String updated) {
		super(id, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, contact,
				created, updated);
	}

	public EzeeResource(String name, String addressLineOne, String addressLineTwo, String suburb, String city,
			String state, String postcode, String phone, String fax, String email, String contact, String created,
			String updated) {
		super(name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, contact, created,
				updated);
	}

	@Override
	public String toString() {
		return "EzeeResource [getName()=" + getName() + "]";
	}
}