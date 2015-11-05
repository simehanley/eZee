package com.ezee.model.entity;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author siborg
 *
 */
@MappedSuperclass
public abstract class EzeeDatabaseEntity implements Serializable, IsSerializable {

	private static final long serialVersionUID = -1523917928877244252L;

	/** unique identifier **/
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	/** date entity was created **/
	@Column(name = "CREATED")
	private String created;

	/** date entity was updated **/
	@Column(name = "UPDATED")
	private String updated;

	public EzeeDatabaseEntity() {
		this(null, null);
	}

	public EzeeDatabaseEntity(final String created, final String updated) {
		this(NULL_ID, created, updated);
	}

	public EzeeDatabaseEntity(final Long id, final String created, final String updated) {
		this.id = id;
		this.created = created;
		this.updated = updated;
	}

	public final Long getId() {
		return id;
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