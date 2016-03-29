package com.ezee.model.entity;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;
import static javax.persistence.DiscriminatorType.STRING;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Table;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author siborg
 *
 */
@Entity
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "PAYER_TYPE", discriminatorType = STRING)
@Table(name = "EZEE_PAYER")
public abstract class EzeePayer extends EzeeFinancialEntity implements IsSerializable {

	private static final long serialVersionUID = 7366220270189922626L;

	public EzeePayer() {
		super();
	}

	public EzeePayer(final String name, final String addressLineOne, final String addressLineTwo, final String suburb,
			final String city, final String state, final String postcode, final String phone, final String fax,
			final String email, final String contact, final String created, final String updated) {
		this(NULL_ID, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, contact,
				created, updated);
	}

	public EzeePayer(final Long id, final String name, final String addressLineOne, final String addressLineTwo,
			final String suburb, final String city, final String state, final String postcode, final String phone,
			final String fax, final String email, final String contact, final String created, final String updated) {
		super(id, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, contact,
				created, updated);
	}

	@Override
	public String toString() {
		return "EzeePayer [getName()=" + getName() + "]";
	}
}