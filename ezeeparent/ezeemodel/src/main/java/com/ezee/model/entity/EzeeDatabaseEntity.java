package com.ezee.model.entity;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 
 * @author siborg
 *
 */
@MappedSuperclass
public abstract class EzeeDatabaseEntity implements Serializable {

	private static final long serialVersionUID = -1523917928877244252L;

	/** unique identifier **/
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	/** date entity was created **/
	@Column(name = "CREATED")
	private Date created;

	/** date entity was updated **/
	@Column(name = "UPDATED")
	private Date updated;

	public EzeeDatabaseEntity() {
		this(new Date(), null);
	}

	public EzeeDatabaseEntity(final Date created, final Date updated) {
		this(NULL_ID, created, updated);
	}

	public EzeeDatabaseEntity(final Long id, final Date created, final Date updated) {
		this.id = id;
		this.created = created;
		this.updated = updated;
	}

	public final Long getId() {
		return id;
	}

	public final Date getCreated() {
		return created;
	}

	public final Date getUpdated() {
		return updated;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EzeeDatabaseEntity other = (EzeeDatabaseEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}