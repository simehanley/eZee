package com.ezee.model.entity.lease;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ezee.model.entity.EzeeDateSortableDatabaseEntity;
import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "EZEE_LEASE_NOTE")
public class EzeeLeaseNote extends EzeeDateSortableDatabaseEntity implements IsSerializable {

	private static final long serialVersionUID = 5794707600663563097L;

	@Column(name = "NOTE")
	private String note;

	public EzeeLeaseNote() {
		super();
	}

	public EzeeLeaseNote(final int order, final String notedate, final String note) {
		this(NULL_ID, order, notedate, note);
	}

	public EzeeLeaseNote(final Long id, final int order, final String notedate, final String note) {
		super(id, order, notedate);
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}