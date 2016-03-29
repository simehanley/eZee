package com.ezee.model.entity;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@DiscriminatorValue(value = "Premises")
public class EzeePremises extends EzeePayer implements IsSerializable {

	private static final long serialVersionUID = -7184365616494000908L;

	public EzeePremises() {
		super();
	}

	public EzeePremises(Long id, String name, String addressLineOne, String addressLineTwo, String suburb, String city,
			String state, String postcode, String phone, String fax, String email, String contact, String created,
			String updated) {
		super(id, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, contact,
				created, updated);
	}

	public EzeePremises(String name, String addressLineOne, String addressLineTwo, String suburb, String city,
			String state, String postcode, String phone, String fax, String email, String contact, String created,
			String updated) {
		this(NULL_ID, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, contact,
				created, updated);
	}

	@Override
	public String toString() {
		return "EzeePremises [getName()=" + getName() + "]";
	}
}