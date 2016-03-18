package com.ezee.model.entity.lease;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ezee.model.entity.EzeeFinancialEntity;
import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "EZEE_LEASE_TENANT")
public class EzeeLeaseTenant extends EzeeFinancialEntity implements IsSerializable {

	private static final long serialVersionUID = -7511603234655151026L;

	public EzeeLeaseTenant() {
		super();
	}

	public EzeeLeaseTenant(final String name, final String addressLineOne, final String addressLineTwo,
			final String suburb, final String city, final String state, final String postcode, final String phone,
			final String fax, final String email, final String contact, final String created, final String updated) {
		this(NULL_ID, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, contact,
				created, updated);
	}

	public EzeeLeaseTenant(final Long id, final String name, final String addressLineOne, final String addressLineTwo,
			final String suburb, final String city, final String state, final String postcode, final String phone,
			final String fax, final String email, final String contact, final String created, final String updated) {
		super(id, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, contact,
				created, updated);
	}

	@Override
	public String toString() {
		return "EzeeLeaseTenant [getName()=" + getName() + "]";
	}
}