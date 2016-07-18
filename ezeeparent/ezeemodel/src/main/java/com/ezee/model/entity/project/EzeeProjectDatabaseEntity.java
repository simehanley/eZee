package com.ezee.model.entity.project;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import javax.persistence.MappedSuperclass;

import com.ezee.model.entity.EzeeTaxableEntity;

@MappedSuperclass
public abstract class EzeeProjectDatabaseEntity extends EzeeTaxableEntity {

	private static final long serialVersionUID = -2764220584875151458L;

	public EzeeProjectDatabaseEntity() {
		this(NULL_ID, null, null);
	}

	public EzeeProjectDatabaseEntity(final String created, final String updated) {
		this(NULL_ID, created, updated);
	}

	public EzeeProjectDatabaseEntity(final Long id, final String created, final String updated) {
		super(id, created, updated);
	}
}