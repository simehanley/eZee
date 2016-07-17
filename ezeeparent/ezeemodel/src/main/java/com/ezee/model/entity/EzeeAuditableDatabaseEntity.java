package com.ezee.model.entity;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author siborg
 *
 */
@MappedSuperclass
public abstract class EzeeAuditableDatabaseEntity extends EzeeDatabaseEntity implements IsSerializable {

	private static final long serialVersionUID = -1523917928877244252L;

	/** date entity was created **/
	@Column(name = "CREATED")
	private String created;

	/** date entity was updated **/
	@Column(name = "UPDATED")
	private String updated;

	public EzeeAuditableDatabaseEntity() {
		this(null, null);
	}

	public EzeeAuditableDatabaseEntity(final String created, final String updated) {
		this(NULL_ID, created, updated);
	}

	public EzeeAuditableDatabaseEntity(final Long id, final String created, final String updated) {
		super(id);
		this.created = created;
		this.updated = updated;
	}

	public final String getCreated() {
		return created;
	}

	public final String getUpdated() {
		return updated;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String filterDate() {
		return getCreated();
	}
}