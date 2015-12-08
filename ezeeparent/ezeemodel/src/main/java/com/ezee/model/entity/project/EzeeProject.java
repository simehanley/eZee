package com.ezee.model.entity.project;

import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.common.numeric.EzeeNumericUtils.isCloseToZero;
import static com.ezee.common.numeric.EzeeNumericUtils.round;
import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ezee.model.entity.EzeeFinancialEntity;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author siborg
 *
 */
@Entity
@Table(name = "EZEE_PROJECT")
public class EzeeProject extends EzeeFinancialEntity implements IsSerializable {

	private static final long serialVersionUID = -8129003866149538189L;

	@Column(name = "START_DATE")
	private String startDate;

	@Column(name = "END_DATE")
	private String endDate;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "PROJECT_ID")
	private Set<EzeeProjectItem> items;

	@Transient
	private boolean modified = false;

	public EzeeProject() {
		super();
	}

	public EzeeProject(String name, String startDate, String endDate, String addressLineOne, String addressLineTwo,
			String suburb, String city, String state, String postcode, String phone, String fax, String email,
			String contact, String created, String updated) {
		this(NULL_ID, name, startDate, endDate, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone,
				fax, email, contact, created, updated);
	}

	public EzeeProject(Long id, String name, String startDate, String endDate, String addressLineOne,
			String addressLineTwo, String suburb, String city, String state, String postcode, String phone, String fax,
			String email, String contact, String created, String updated) {
		super(id, name, addressLineOne, addressLineTwo, suburb, city, state, postcode, phone, fax, email, contact,
				created, updated);
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public void addItem(final EzeeProjectItem item) {
		if (item != null) {
			if (isEmpty(items)) {
				items = new LinkedHashSet<>();
			}
			items.add(item);
		}
	}

	public final String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public final String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public final Set<EzeeProjectItem> getItems() {
		return items;
	}

	public final Set<EzeeProjectItem> getSortedItems() {
		if (!isEmpty(items)) {
			List<EzeeProjectItem> sorted = new ArrayList<>(items);
			Collections.sort(sorted);
			return new LinkedHashSet<>(sorted);
		}
		return items;
	}

	public void setItems(Set<EzeeProjectItem> items) {
		this.items = items;
	}

	public EzeeProjectAmount budgeted() {
		double amount = ZERO_DBL, tax = ZERO_DBL;
		if (!isEmpty(items)) {
			for (EzeeProjectItem item : items) {
				EzeeProjectAmount amt = item.budgeted();
				amount += amt.getAmount();
				tax += amt.getTax();
			}
		}
		return new EzeeProjectAmount(round(amount), round(tax));
	}

	public EzeeProjectAmount actual() {
		double amount = ZERO_DBL, tax = ZERO_DBL;
		if (!isEmpty(items)) {
			for (EzeeProjectItem item : items) {
				EzeeProjectAmount amt = item.actual();
				amount += amt.getAmount();
				tax += amt.getTax();
			}
		}
		return new EzeeProjectAmount(round(amount), round(tax));
	}

	public EzeeProjectAmount paid() {
		double amount = ZERO_DBL, tax = ZERO_DBL;
		if (!isEmpty(items)) {
			for (EzeeProjectItem item : items) {
				EzeeProjectAmount amt = item.paid();
				amount += amt.getAmount();
				tax += amt.getTax();
			}
		}
		return new EzeeProjectAmount(round(amount), round(tax));
	}

	public EzeeProjectAmount balance() {
		return actual().minus(paid());
	}

	public String percent() {
		double balance = balance().getTotal();
		double actual = actual().getTotal();
		if (isCloseToZero(balance)) {
			return "100.00%";
		}
		double percentComplete = round((actual - balance) / balance);
		return Double.toString(percentComplete) + "%";
	}

	@Override
	public String toString() {
		return "EzeeProject [getName()=" + getName() + "]";
	}

	public final boolean isModified() {
		return modified;
	}

	public final void setModified(boolean modified) {
		this.modified = modified;
	}
}