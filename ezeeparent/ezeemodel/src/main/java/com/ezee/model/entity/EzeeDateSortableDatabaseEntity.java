package com.ezee.model.entity;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.google.gwt.user.client.rpc.IsSerializable;

@MappedSuperclass
public abstract class EzeeDateSortableDatabaseEntity extends EzeeDatabaseEntity implements IsSerializable {

	private static final long serialVersionUID = 8161380026337084145L;

	@Column(name = "SORT_ORDER")
	protected Integer order;

	@Column(name = "CREATED")
	protected String date;

	public EzeeDateSortableDatabaseEntity() {
		super();
	}

	public EzeeDateSortableDatabaseEntity(final int order, final String date) {
		this(NULL_ID, order, date);
	}

	public EzeeDateSortableDatabaseEntity(final Long id, final int order, final String date) {
		super(id);
		this.order = order;
		this.date = date;
	}

	public final Integer getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public final String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
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
		EzeeDateSortableDatabaseEntity other = (EzeeDateSortableDatabaseEntity) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		return true;
	}

	@Override
	public int compareTo(final EzeeDatabaseEntity entity) {
		EzeeDateSortableDatabaseEntity sortable = (EzeeDateSortableDatabaseEntity) entity;
		return this.order.compareTo(sortable.order);
	}
}