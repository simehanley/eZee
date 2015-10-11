package com.ezee.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.LocalDate;

/**
 * 
 * @author siborg
 *
 */
@Entity
@Table(name = "EZEE_PAYER")
public class EzeePayer extends EzeeFinancialEntity {

	private static final long serialVersionUID = 7366220270189922626L;

	public EzeePayer(final Long id, final String name, final String addressLineOne, final String addressLineTwo,
			final String suburb, final String city, final String state, final String postcode, final String phone,
			final LocalDate created, final LocalDate updated) {
		super(id, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, created, updated);
	}

	public EzeePayer(final String name, final String addressLineOne, final String addressLineTwo, final String suburb,
			final String city, final String state, final String postcode, final String phone, final LocalDate created,
			final LocalDate updated) {
		super(name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, created, updated);
	}
}