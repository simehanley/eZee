package com.ezee.model.entity.lease;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ezee.model.entity.EzeeDateSortableDatabaseEntity;
import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "EZEE_LEASE_META_DATA")
public class EzeeLeaseMetaData extends EzeeDateSortableDatabaseEntity implements IsSerializable {

	private static final long serialVersionUID = -6452618339448182219L;

	@Column(name = "META_DATA_TYPE")
	private String type;

	@Column(name = "META_DATA_DESCRIPTION")
	private String description;

	@Column(name = "META_DATA_VALUE")
	private String value;

	public EzeeLeaseMetaData() {
		super();
	}

	public EzeeLeaseMetaData(final int order, final String date, final String type, final String description,
			final String value) {
		this(NULL_ID, order, date, type, description, value);
	}

	public EzeeLeaseMetaData(final Long id, final int order, final String date, final String type,
			final String description, final String value) {
		super(id, order, date);
		this.type = type;
		this.description = description;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EzeeLeaseMetaData other = (EzeeLeaseMetaData) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}