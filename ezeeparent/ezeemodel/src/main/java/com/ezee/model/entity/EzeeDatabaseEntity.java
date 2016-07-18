package com.ezee.model.entity;

import static com.ezee.common.EzeeCommonConstants.MINUS_ONE;
import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.ezee.common.uuid.EzeeUuidGenerator;
import com.google.gwt.user.client.rpc.IsSerializable;

@MappedSuperclass
public abstract class EzeeDatabaseEntity implements Serializable, IsSerializable, Comparable<EzeeDatabaseEntity> {

	private static final long serialVersionUID = -273464647432397798L;

	/** unique identifier **/
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "GRIDID")
	private String gridId = EzeeUuidGenerator.uuid();

	public EzeeDatabaseEntity() {
		this(NULL_ID);
	}

	public EzeeDatabaseEntity(final Long id) {
		this.id = id;
	}

	public final Long getId() {
		return id;
	}

	public String getGridId() {
		return gridId;
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EzeeDatabaseEntity other = (EzeeDatabaseEntity) obj;
		if (gridId == null) {
			if (other.gridId != null)
				return false;
		} else if (!gridId.equals(other.gridId))
			return false;
		return true;
	}

	@Override
	public int compareTo(final EzeeDatabaseEntity entity) {
		if (getId() == NULL_ID && entity.getId() == NULL_ID) {
			return ZERO;
		} else if (getId() != NULL_ID && entity.getId() == NULL_ID) {
			return ONE;
		} else if (getId() == NULL_ID && entity.getId() != NULL_ID) {
			return MINUS_ONE;
		}
		return getId().compareTo(entity.getId());
	}
}