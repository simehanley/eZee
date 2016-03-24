package com.ezee.model.entity.lease;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.ezee.model.entity.EzeePayer;
import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@DiscriminatorValue("LeaseTenant")
public class EzeeLeaseTenant extends EzeePayer implements IsSerializable {

	private static final long serialVersionUID = 2951764816085742366L;

	public EzeeLeaseTenant(String name, String addressLineOne, String addressLineTwo, String suburb, String city,
			String state, String postcode, String phone, String fax, String email, String contact, String created,
			String updated) {
		this(NULL_ID, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, contact,
				created, updated);
	}

	public EzeeLeaseTenant(Long id, String name, String addressLineOne, String addressLineTwo, String suburb,
			String city, String state, String postcode, String phone, String fax, String email, String contact,
			String created, String updated) {
		super(id, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, contact,
				created, updated);
	}

	public EzeeLeaseTenant() {
		super();
	}

	@Override
	public String toString() {
		return "EzeeLeaseTenant [getName()=" + getName() + "]";
	}
}