package com.ezee.model.entity;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EzeeDateSortableDatabaseEntity extends EzeeDatabaseEntity {

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
}