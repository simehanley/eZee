package com.ezee.model.entity.lease;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import javax.persistence.Column;

import com.ezee.model.entity.EzeeDatabaseEntity;
import com.google.gwt.user.client.rpc.IsSerializable;

public class EzeeLeaseMetaData extends EzeeDatabaseEntity implements IsSerializable {

	private static final long serialVersionUID = -6452618339448182219L;

	@Column(name = "META_DATA_TYPE")
	private String type;

	@Column(name = "META_DATA_DESCRIPTION")
	private String description;

	@Column(name = "META_DATA_VALUE")
	private String value;

	@Column(name = "META_DATA_ORDER")
	private Integer order;

	public EzeeLeaseMetaData() {
		super();
	}

	public EzeeLeaseMetaData(final String type, final String description, final String value, final Integer order,
			final String created, final String updated) {
		this(NULL_ID, type, description, value, order, created, updated);
	}

	public EzeeLeaseMetaData(final Long id, final String type, final String description, final String value,
			final Integer order, final String created, final String updated) {
		super(id, created, updated);
		this.type = type;
		this.description = description;
		this.value = value;
		this.order = order;
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

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
}