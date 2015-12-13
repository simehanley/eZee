package com.ezee.model.entity.project;

import static com.ezee.common.uuid.EzeeUuidGenerator.uuid;
import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.ezee.model.entity.EzeeDatabaseEntity;

@MappedSuperclass
public abstract class EzeeProjectDatabaseEntity extends EzeeDatabaseEntity {

	private static final long serialVersionUID = -2764220584875151458L;

	@Transient
	private String gridId;

	public EzeeProjectDatabaseEntity() {
		this(NULL_ID, null, null);
	}

	public EzeeProjectDatabaseEntity(final String created, final String updated) {
		this(NULL_ID, created, updated);
	}

	public EzeeProjectDatabaseEntity(final Long id, final String created, final String updated) {
		super(id, created, updated);
		this.gridId = uuid();
	}

	public final String getGridId() {
		return gridId;
	}

	public void setGridId(final String gridId) {
		this.gridId = gridId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gridId == null) ? 0 : gridId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		EzeeProjectDatabaseEntity other = (EzeeProjectDatabaseEntity) obj;
		if (gridId == null) {
			if (other.gridId != null)
				return false;
		} else if (!gridId.equals(other.gridId))
			return false;
		return true;
	}
}