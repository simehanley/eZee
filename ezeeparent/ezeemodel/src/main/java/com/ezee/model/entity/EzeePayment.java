package com.ezee.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author siborg
 *
 */
@Entity
@Table(name = "EZEE_PAYMENT")
public class EzeePayment extends EzeeDatabaseEntity {

	private static final long serialVersionUID = 607584929798342009L;

	public EzeePayment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EzeePayment(final Date created, final Date updated) {
		super(created, updated);
	}

	public EzeePayment(final Long id, final Date created, final Date updated) {
		super(id, created, updated);
	}
}