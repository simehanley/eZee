package com.ezee.model.entity.project;

import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;
import static com.ezee.model.entity.project.EzeeProjectItemUtilities.resolveActual;
import static com.ezee.model.entity.project.EzeeProjectItemUtilities.resolveBudgeted;
import static com.ezee.model.entity.project.EzeeProjectItemUtilities.resolvePaid;
import static javax.persistence.CascadeType.ALL;

import java.beans.Transient;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ezee.model.entity.EzeeDatabaseEntity;

/**
 * 
 * @author siborg
 *
 */
@Entity
@Table(name = "EZEE_PROJECT_ITEM")
public class EzeeProjectItem extends EzeeDatabaseEntity {

	private static final long serialVersionUID = -3908799031337740246L;

	@Column(name = "ITEM_NAME")
	private String name;

	@OneToMany(cascade = ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ITEM_ID")
	private Set<EzeeProjectItemDetail> details;

	@OneToMany(cascade = ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ITEM_ID")
	private Set<EzeeProjectPayment> payments;

	public EzeeProjectItem() {
		super();
	}

	public EzeeProjectItem(final String name, final String created, final String updated) {
		this(NULL_ID, name, created, updated);

	}

	public EzeeProjectItem(final Long id, final String name, final String created, final String updated) {
		super(id, created, updated);
		this.name = name;
	}

	public void addDetail(final EzeeProjectItemDetail detail) {
		if (detail != null) {
			if (isEmpty(details)) {
				details = new LinkedHashSet<>();
			}
			details.add(detail);
		}
	}

	public void addPayment(final EzeeProjectPayment payment) {
		if (payment != null) {
			if (isEmpty(payments)) {
				payments = new LinkedHashSet<>();
			}
			payments.add(payment);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<EzeeProjectItemDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<EzeeProjectItemDetail> details) {
		this.details = details;
	}

	public Set<EzeeProjectPayment> getPayments() {
		return payments;
	}

	public void setPayments(Set<EzeeProjectPayment> payments) {
		this.payments = payments;
	}

	@Transient
	public EzeeProjectAmount budgeted() {
		return resolveBudgeted(this);
	}

	@Transient
	public EzeeProjectAmount actual() {
		return resolveActual(this);
	}

	@Transient
	public EzeeProjectAmount paid() {
		return resolvePaid(this);
	}

	@Transient
	public EzeeProjectAmount balance() {
		return actual().minus(paid());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		EzeeProjectItem other = (EzeeProjectItem) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}