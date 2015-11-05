package com.ezee.model.entity;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author siborg
 *
 */
@Entity
@Table(name = "EZEE_PAYER")
public class EzeePayer extends EzeeFinancialEntity implements IsSerializable {

	private static final long serialVersionUID = 7366220270189922626L;

	public EzeePayer() {
		super();
	}

	public EzeePayer(final String name, final String addressLineOne, final String addressLineTwo, final String suburb,
			final String city, final String state, final String postcode, final String phone, final String fax,
			final String email, final String created, final String updated) {
		this(NULL_ID, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, created,
				updated);
	}

	public EzeePayer(final Long id, final String name, final String addressLineOne, final String addressLineTwo,
			final String suburb, final String city, final String state, final String postcode, final String phone,
			final String fax, final String email, final String created, final String updated) {
		super(id, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, created,
				updated);
	}

	@Override
	public String toString() {
		return "EzeePayer [getName()=" + getName() + "]";
	}
}