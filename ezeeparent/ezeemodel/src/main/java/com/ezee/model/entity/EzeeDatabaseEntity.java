package com.ezee.model.entity;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.joda.time.LocalDate;

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
	private final Long id;

	/** date entity was created **/
	@Column(name = "CREATED")
	private LocalDate created;

	/** date entity was updated **/
	@Column(name = "UPDATED")
	private LocalDate updated;

	public EzeeDatabaseEntity() {
		this(new LocalDate(), null);
	}

	public EzeeDatabaseEntity(final LocalDate created, final LocalDate updated) {
		this(NULL_ID, created, updated);
	}

	public EzeeDatabaseEntity(final Long id, final LocalDate created, final LocalDate updated) {
		this.id = id;
		this.created = created;
		this.updated = updated;
	}

	public final Long getId() {
		return id;
	}

	public final LocalDate getCreated() {
		return created;
	}

	public final LocalDate getUpdated() {
		return updated;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
	}

	public void setUpdated(LocalDate updated) {
		this.updated = updated;
	}
}