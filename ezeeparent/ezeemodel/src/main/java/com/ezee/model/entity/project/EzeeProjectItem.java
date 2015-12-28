package com.ezee.model.entity.project;

import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.common.numeric.EzeeNumericUtils.percentComplete;
import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;
import static com.ezee.model.entity.project.EzeeProjectItemUtilities.resolveActual;
import static com.ezee.model.entity.project.EzeeProjectItemUtilities.resolveBudgeted;
import static com.ezee.model.entity.project.EzeeProjectItemUtilities.resolvePaid;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ezee.model.entity.EzeeContractor;

/**
 * 
 * @author siborg
 *
 */
@Entity
@Table(name = "EZEE_PROJECT_ITEM")
public class EzeeProjectItem extends EzeeProjectDatabaseEntity {

	private static final long serialVersionUID = -3908799031337740246L;

	@Column(name = "NAME")
	private String name;

	@OneToOne(fetch = EAGER)
	@JoinTable(name = "EZEE_PROJECT_ITEM_TO_CONTRACTOR_MAPPING", joinColumns = @JoinColumn(name = "ITEM_ID") , inverseJoinColumns = @JoinColumn(name = "RESOURCE_ID") )
	private EzeeContractor contractor;

	@OneToMany(cascade = ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "ITEM_ID")
	private Set<EzeeProjectItemDetail> details;

	@OneToMany(cascade = ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "ITEM_ID")
	private Set<EzeeProjectPayment> payments;

	public EzeeProjectItem() {
		super();
	}

	public EzeeProjectItem(final String name, final EzeeContractor contractor, final String created,
			final String updated) {
		this(NULL_ID, name, contractor, created, updated);
	}

	public EzeeProjectItem(final Long id, final String name, final EzeeContractor contractor, final String created,
			final String updated) {
		super(id, created, updated);
		this.name = name;
		this.contractor = contractor;
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

	public EzeeContractor getContractor() {
		return contractor;
	}

	public void setContractor(final EzeeContractor contractor) {
		this.contractor = contractor;
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

	public EzeeProjectAmount budgeted() {
		return resolveBudgeted(this);
	}

	public EzeeProjectAmount actual() {
		return resolveActual(this);
	}

	public EzeeProjectAmount paid() {
		return resolvePaid(this);
	}

	public EzeeProjectAmount balance() {
		return actual().minus(paid());
	}

	public double percent() {
		double balance = balance().getTotal();
		double actual = actual().getTotal();
		return percentComplete(actual, balance);
	}

	@Override
	public String toString() {
		return "EzeeProjectItem [name=" + name + ", resource=" + contractor + ", details=" + details + ", payments="
				+ payments + ", getGridId()=" + getGridId() + ", getId()=" + getId() + "]";
	}
}