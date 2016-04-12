package com.ezee.model.entity.lease;

import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.common.numeric.EzeeNumericUtils.round;
import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;
import static com.ezee.model.entity.lease.EzeeLeaseConstants.TOTAL;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static org.hibernate.annotations.FetchMode.SUBSELECT;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ezee.model.entity.EzeeDatabaseEntity;
import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "EZEE_LEASE")
public class EzeeLease extends EzeeDatabaseEntity implements IsSerializable {

	private static final long serialVersionUID = 5862291052151418408L;

	/** lease start date **/
	@Column(name = "LEASE_START")
	private String leaseStart;

	/** lease end date **/
	@Column(name = "LEASE_END")
	private String leaseEnd;

	/** any pertinent notes on the lease record **/
	@Column(name = "NOTES")
	private String notes;

	/** leased area if applicable **/
	@Column(name = "AREA")
	private double leasedArea;

	/** leased unit(s) **/
	@Column(name = "UNITS")
	private String leasedUnits;

	/** incidentals **/
	@OneToMany(cascade = ALL, fetch = EAGER)
	@JoinTable(name = "LEASE_TO_LEASE_INCIDENTAL_MAPPING", joinColumns = @JoinColumn(name = "LEASE_ID") , inverseJoinColumns = @JoinColumn(name = "LEASE_INCIDENTAL_ID") )
	@Fetch(value = FetchMode.SUBSELECT)
	private List<EzeeLeaseIncidental> incidentals;

	/** tenant **/
	@ManyToOne(cascade = ALL, fetch = EAGER)
	@JoinTable(name = "LEASE_TO_TENANT_MAPPING", joinColumns = @JoinColumn(name = "LEASE_ID") , inverseJoinColumns = @JoinColumn(name = "TENANT_ID") )
	private EzeeLeaseTenant tenant;

	/** premises **/
	@ManyToOne(cascade = ALL, fetch = EAGER)
	@JoinTable(name = "LEASE_TO_PREMISES_MAPPING", joinColumns = @JoinColumn(name = "LEASE_ID") , inverseJoinColumns = @JoinColumn(name = "PREMISES_ID") )
	private EzeeLeasePremises premises;

	/** category **/
	@ManyToOne(cascade = ALL, fetch = EAGER)
	@JoinTable(name = "LEASE_TO_CATEGORY_MAPPING", joinColumns = @JoinColumn(name = "LEASE_ID") , inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID") )
	private EzeeLeaseCategory category;

	/** indicator for a residential (GST free) lease **/
	@Column(name = "RESIDENTIAL")
	private boolean residential;

	/** indicator for an inactive lease **/
	@Column(name = "INACTIVE")
	private boolean inactive;

	/** indicates if this option has an option to renew **/
	@Column(name = "HAS_OPTION")
	private boolean hasOption;

	/** lease option start date **/
	@Column(name = "OPTION_START_DATE")
	private Date optionStartDate;

	/** lease option end date **/
	@Column(name = "OPTION_END_DATE")
	private Date optionEndDate;

	@OneToOne(cascade = ALL, fetch = EAGER)
	@JoinTable(name = "LEASE_TO_BOND_MAPPING", joinColumns = @JoinColumn(name = "LEASE_ID") , inverseJoinColumns = @JoinColumn(name = "BOND_ID") )
	private EzeeLeaseBond bond;

	/** meta data **/
	@OneToMany(cascade = ALL, fetch = EAGER)
	@JoinTable(name = "LEASE_TO_META_DATA_MAPPING", joinColumns = @JoinColumn(name = "LEASE_ID") , inverseJoinColumns = @JoinColumn(name = "META_DATA_ID") )
	@Fetch(value = SUBSELECT)
	private List<EzeeLeaseMetaData> metaData;

	/** myob job number **/
	@Column(name = "JOB_NUMBER")
	private String jobNo;

	public EzeeLease() {
		super();
	}

	public EzeeLease(final String leaseStart, final String leaseEnd, final String notes, final Double leasedArea,
			final String leasedUnits, final List<EzeeLeaseIncidental> incidentals, final EzeeLeaseTenant tenant,
			final EzeeLeasePremises premises, final EzeeLeaseCategory category, final List<EzeeLeaseMetaData> metaData,
			final boolean residential, final boolean inactive, final String jobNo, final String created,
			final String updated) {
		this(NULL_ID, leaseStart, leaseEnd, notes, leasedArea, leasedUnits, incidentals, tenant, premises, category,
				metaData, residential, inactive, jobNo, created, updated);
	}

	protected EzeeLease(final Long id, final String leaseStart, final String leaseEnd, final String notes,
			final Double leasedArea, final String leasedUnits, final List<EzeeLeaseIncidental> incidentals,
			final EzeeLeaseTenant tenant, final EzeeLeasePremises premises, final EzeeLeaseCategory category,
			final List<EzeeLeaseMetaData> metaData, final boolean residential, final boolean inactive,
			final String jobNo, final String created, final String updated) {
		super(id, created, updated);
		this.leaseStart = leaseStart;
		this.leaseEnd = leaseEnd;
		this.notes = notes;
		this.leasedArea = leasedArea;
		this.leasedUnits = leasedUnits;
		this.incidentals = incidentals;
		this.tenant = tenant;
		this.premises = premises;
		this.category = category;
		this.metaData = metaData;
		this.residential = residential;
		this.inactive = inactive;
		this.jobNo = jobNo;
	}

	public final String getLeaseStart() {
		return leaseStart;
	}

	public void setLeaseStart(final String leaseStart) {
		this.leaseStart = leaseStart;
	}

	public final String getLeaseEnd() {
		return leaseEnd;
	}

	public void setLeaseEnd(final String leaseEnd) {
		this.leaseEnd = leaseEnd;
	}

	public EzeeLeaseTenant getTenant() {
		return tenant;
	}

	public void setTenant(EzeeLeaseTenant tenant) {
		this.tenant = tenant;
	}

	public EzeeLeasePremises getPremises() {
		return premises;
	}

	public void setPremises(EzeeLeasePremises premises) {
		this.premises = premises;
	}

	public EzeeLeaseCategory getCategory() {
		return category;
	}

	public void setCategory(EzeeLeaseCategory category) {
		this.category = category;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Double getLeasedArea() {
		return leasedArea;
	}

	public void setLeasedArea(Double leasedArea) {
		this.leasedArea = leasedArea;
	}

	public String getLeasedUnits() {
		return leasedUnits;
	}

	public void setLeasedUnits(String leasedUnits) {
		this.leasedUnits = leasedUnits;
	}

	public boolean isResidential() {
		return residential;
	}

	public void setResidential(boolean residential) {
		this.residential = residential;
	}

	public boolean isInactive() {
		return inactive;
	}

	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public boolean hasOption() {
		return hasOption;
	}

	public void setHasOption(boolean hasOption) {
		this.hasOption = hasOption;
	}

	public final Date getOptionStartDate() {
		return optionStartDate;
	}

	public void setOptionStartDate(final Date optionStartDate) {
		this.optionStartDate = optionStartDate;
	}

	public final Date getOptionEndDate() {
		return optionEndDate;
	}

	public void setOptionEndDate(final Date optionEndDate) {
		this.optionEndDate = optionEndDate;
	}

	public EzeeLeaseBond getBond() {
		return bond;
	}

	public void setBond(EzeeLeaseBond bond) {
		this.bond = bond;
	}

	public void addIncidental(final EzeeLeaseIncidental incidental) {
		if (isEmpty(incidentals)) {
			incidentals = new ArrayList<EzeeLeaseIncidental>();
		}
		incidentals.add(incidental);
	}

	public final List<EzeeLeaseIncidental> getIncidentals() {
		return incidentals;
	}

	public void setIncidentals(final List<EzeeLeaseIncidental> incidentals) {
		this.incidentals = incidentals;
	}

	public final EzeeLeaseIncidental getIncidental(final String name) {
		if (!isEmpty(incidentals)) {
			for (EzeeLeaseIncidental incidental : incidentals) {
				if (name.equals(incidental.getName())) {
					return incidental;
				}
			}
		}
		return null;
	}

	public void removeIncidental(final String name) {
		if (!isEmpty(incidentals)) {
			EzeeLeaseIncidental incidental = getIncidental(name);
			if (incidental != null) {
				incidentals.remove(incidental);
			}
		}
	}

	public List<EzeeLeaseMetaData> getMetaData() {
		return metaData;
	}

	public void setMetaData(List<EzeeLeaseMetaData> metaData) {
		this.metaData = metaData;
	}

	public void addMetaData(final EzeeLeaseMetaData data) {
		if (isEmpty(metaData)) {
			metaData = new ArrayList<EzeeLeaseMetaData>();
		}
		metaData.add(data);
	}

	public final List<EzeeLeaseMetaData> getMetaData(final String type) {
		List<EzeeLeaseMetaData> data = new ArrayList<EzeeLeaseMetaData>();
		if (!isEmpty(metaData)) {
			for (EzeeLeaseMetaData md : metaData) {
				if (type.equals(md.getType())) {
					data.add(md);
				}
			}
		}
		return data;
	}

	public void removeMetaData(final EzeeLeaseMetaData data) {
		if (!isEmpty(metaData)) {
			metaData.remove(data);
		}
	}

	@Override
	public String toString() {
		return "EzeeLease [tenant=" + tenant.getName() + ", premises=" + premises.getAddressLineOne() + ", category="
				+ category.getName() + "]";
	}

	public double monthlyAmount(final String type) {
		double monthlyAmount = ZERO_DBL;
		if (TOTAL.equals(type)) {
			for (EzeeLeaseIncidental incidental : getIncidentals()) {
				monthlyAmount += incidental.monthlyAmount();
			}
		} else {
			if (getIncidental(type) != null) {
				monthlyAmount = getIncidental(type).monthlyAmount();
			}

		}
		return monthlyAmount;
	}

	public double monthlyGst(final String type) {
		double monthlyGst = ZERO_DBL;
		if (!residential) {
			if (TOTAL.equals(type)) {
				for (EzeeLeaseIncidental incidental : getIncidentals()) {
					monthlyGst += incidental.monthlyGst();
				}
			} else {
				if (getIncidental(type) != null) {
					monthlyGst = getIncidental(type).monthlyGst();
				}
			}
		}
		return monthlyGst;
	}

	public double monthlyTotal(final String type) {
		return round(monthlyAmount(type) + monthlyGst(type));
	}

	public double yearlyAmount(final String type) {
		double yearlyAmount = ZERO_DBL;
		if (TOTAL.equals(type)) {
			for (EzeeLeaseIncidental incidental : getIncidentals()) {
				yearlyAmount += incidental.yearlyAmount();
			}
		} else {
			if (getIncidental(type) != null) {
				yearlyAmount = getIncidental(type).yearlyAmount();
			}

		}
		return yearlyAmount;
	}

	public double yearlyGst(final String type) {
		double yearlyGst = ZERO_DBL;
		if (!residential) {
			if (TOTAL.equals(type)) {
				for (EzeeLeaseIncidental incidental : getIncidentals()) {
					yearlyGst += incidental.yearlyGst();
				}
			} else {
				if (getIncidental(type) != null) {
					yearlyGst = getIncidental(type).yearlyGst();
				}
			}
		}
		return yearlyGst;
	}

	public double yearlyTotal(final String type) {
		return round(yearlyAmount(type) + yearlyGst(type));
	}
}