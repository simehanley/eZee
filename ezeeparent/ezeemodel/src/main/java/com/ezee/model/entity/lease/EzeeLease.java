package com.ezee.model.entity.lease;

import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.common.numeric.EzeeNumericUtils.round;
import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;
import static com.ezee.model.entity.lease.EzeeLeaseConstants.TOTAL;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import com.ezee.model.entity.EzeeAuditableDatabaseEntity;
import com.google.gwt.user.client.rpc.IsSerializable;

@NamedNativeQueries({
		@NamedNativeQuery(name = "deleteIncidentalMappingsSql", query = "delete from EZEE_LEASE_TO_LEASE_INCIDENTAL_MAPPING where LEASE_ID = :id"),
		@NamedNativeQuery(name = "deleteTenantMappingsSql", query = "delete from EZEE_LEASE_TO_TENANT_MAPPING where LEASE_ID = :id"),
		@NamedNativeQuery(name = "deletePremisesMappingsSql", query = "delete from EZEE_LEASE_TO_PREMISES_MAPPING where LEASE_ID = :id"),
		@NamedNativeQuery(name = "deleteBondMappingsSql", query = "delete from EZEE_LEASE_TO_BOND_MAPPING where LEASE_ID = :id"),
		@NamedNativeQuery(name = "deleteMetaDataMappingsSql", query = "delete from EZEE_LEASE_TO_META_DATA_MAPPING where LEASE_ID = :id"),
		@NamedNativeQuery(name = "deleteCategoryMappingsSql", query = "delete from EZEE_LEASE_TO_CATEGORY_MAPPING where LEASE_ID = :id"),
		@NamedNativeQuery(name = "deleteNotesSql", query = "delete from EZEE_LEASE_TO_NOTES_MAPPING where LEASE_ID = :id"),
		@NamedNativeQuery(name = "deleteFilesSql", query = "delete from EZEE_LEASE_TO_FILES_MAPPING where LEASE_ID = :id") })
@Entity
@Table(name = "EZEE_LEASE")
public class EzeeLease extends EzeeAuditableDatabaseEntity implements IsSerializable {

	private static final long serialVersionUID = 5862291052151418408L;

	/** lease start date **/
	@Column(name = "LEASE_START")
	private String leaseStart;

	/** lease end date **/
	@Column(name = "LEASE_END")
	private String leaseEnd;

	/** leased area if applicable **/
	@Column(name = "AREA")
	private double leasedArea;

	/** leased unit(s) **/
	@Column(name = "UNITS")
	private String leasedUnits;

	/** incidentals **/
	@OneToMany(cascade = ALL, fetch = EAGER)
	@JoinTable(name = "EZEE_LEASE_TO_LEASE_INCIDENTAL_MAPPING", joinColumns = @JoinColumn(name = "LEASE_ID") , inverseJoinColumns = @JoinColumn(name = "LEASE_INCIDENTAL_ID") )
	private Set<EzeeLeaseIncidental> incidentals;

	/** tenant **/
	@ManyToOne(cascade = ALL, fetch = EAGER)
	@JoinTable(name = "EZEE_LEASE_TO_TENANT_MAPPING", joinColumns = @JoinColumn(name = "LEASE_ID") , inverseJoinColumns = @JoinColumn(name = "TENANT_ID") )
	private EzeeLeaseTenant tenant;

	/** premises **/
	@ManyToOne(cascade = ALL, fetch = EAGER)
	@JoinTable(name = "EZEE_LEASE_TO_PREMISES_MAPPING", joinColumns = @JoinColumn(name = "LEASE_ID") , inverseJoinColumns = @JoinColumn(name = "PREMISES_ID") )
	private EzeeLeasePremises premises;

	/** category **/
	@ManyToOne(cascade = ALL, fetch = EAGER)
	@JoinTable(name = "EZEE_LEASE_TO_CATEGORY_MAPPING", joinColumns = @JoinColumn(name = "LEASE_ID") , inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID") )
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

	/** indicates if the option to renew has been exercised **/
	@Column(name = "OPTION_EXERCISED")
	private boolean optionExercised;

	/** lease option start date **/
	@Column(name = "OPTION_START_DATE")
	private String optionStartDate;

	/** lease option end date **/
	@Column(name = "OPTION_END_DATE")
	private String optionEndDate;

	@OneToOne(cascade = ALL, fetch = EAGER)
	@JoinTable(name = "EZEE_LEASE_TO_BOND_MAPPING", joinColumns = @JoinColumn(name = "LEASE_ID") , inverseJoinColumns = @JoinColumn(name = "BOND_ID") )
	private EzeeLeaseBond bond;

	/** meta data **/
	@OneToMany(cascade = ALL, fetch = EAGER)
	@JoinTable(name = "EZEE_LEASE_TO_META_DATA_MAPPING", joinColumns = @JoinColumn(name = "LEASE_ID") , inverseJoinColumns = @JoinColumn(name = "META_DATA_ID") )
	@Sort(type = SortType.NATURAL)
	private SortedSet<EzeeLeaseMetaData> metaData;

	/** notes **/
	@OneToMany(cascade = ALL, fetch = EAGER)
	@JoinTable(name = "EZEE_LEASE_TO_NOTES_MAPPING", joinColumns = @JoinColumn(name = "LEASE_ID") , inverseJoinColumns = @JoinColumn(name = "NOTE_ID") )
	@Sort(type = SortType.NATURAL)
	private SortedSet<EzeeLeaseNote> notes;

	/** files **/
	@OneToMany(cascade = ALL, fetch = EAGER)
	@JoinTable(name = "EZEE_LEASE_TO_FILES_MAPPING", joinColumns = @JoinColumn(name = "LEASE_ID") , inverseJoinColumns = @JoinColumn(name = "FILE_ID") )
	@Sort(type = SortType.NATURAL)
	private SortedSet<EzeeLeaseFile> files;

	/** myob job number **/
	@Column(name = "JOB_NUMBER")
	private String jobNo;

	@Transient
	private boolean edited = false;

	public EzeeLease() {
		super();
	}

	public EzeeLease(final String leaseStart, final String leaseEnd, final Double leasedArea, final String leasedUnits,
			final Set<EzeeLeaseIncidental> incidentals, final EzeeLeaseTenant tenant, final EzeeLeasePremises premises,
			final EzeeLeaseCategory category, final SortedSet<EzeeLeaseMetaData> metaData, final boolean residential,
			final boolean inactive, final String jobNo, final String created, final String updated) {
		this(NULL_ID, leaseStart, leaseEnd, leasedArea, leasedUnits, incidentals, tenant, premises, category, metaData,
				residential, inactive, jobNo, created, updated);
	}

	protected EzeeLease(final Long id, final String leaseStart, final String leaseEnd, final Double leasedArea,
			final String leasedUnits, final Set<EzeeLeaseIncidental> incidentals, final EzeeLeaseTenant tenant,
			final EzeeLeasePremises premises, final EzeeLeaseCategory category,
			final SortedSet<EzeeLeaseMetaData> metaData, final boolean residential, final boolean inactive,
			final String jobNo, final String created, final String updated) {
		super(id, created, updated);
		this.leaseStart = leaseStart;
		this.leaseEnd = leaseEnd;
		this.leasedArea = (leasedArea == null) ? ZERO_DBL : leasedArea;
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

	public final String getEffectiveLeaseEnd() {
		if (hasOption && optionExercised) {
			return getOptionEndDate();
		}
		return getLeaseEnd();
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

	public boolean isOptionExercised() {
		return optionExercised;
	}

	public void setOptionExercised(boolean optionExercised) {
		this.optionExercised = optionExercised;
	}

	public final String getOptionStartDate() {
		return optionStartDate;
	}

	public void setOptionStartDate(final String optionStartDate) {
		this.optionStartDate = optionStartDate;
	}

	public final String getOptionEndDate() {
		return optionEndDate;
	}

	public void setOptionEndDate(final String optionEndDate) {
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
			incidentals = new HashSet<EzeeLeaseIncidental>();
		}
		incidentals.add(incidental);
	}

	public final Set<EzeeLeaseIncidental> getIncidentals() {
		return incidentals;
	}

	public void setIncidentals(final Set<EzeeLeaseIncidental> incidentals) {
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

	public SortedSet<EzeeLeaseMetaData> getMetaData() {
		return metaData;
	}

	public void setMetaData(SortedSet<EzeeLeaseMetaData> metaData) {
		this.metaData = metaData;
	}

	public void addMetaData(final EzeeLeaseMetaData data) {
		if (isEmpty(metaData)) {
			metaData = new TreeSet<>();
		}
		metaData.add(data);
	}

	public final SortedSet<EzeeLeaseMetaData> getMetaData(final String type) {
		SortedSet<EzeeLeaseMetaData> data = new TreeSet<EzeeLeaseMetaData>();
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
		return "EzeeLease [tenant=" + tenant.getName() + ", premises=" + premises.getName() + ", category="
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

	public SortedSet<EzeeLeaseNote> getNotes() {
		return notes;
	}

	public void setNotes(SortedSet<EzeeLeaseNote> notes) {
		this.notes = notes;
	}

	public SortedSet<EzeeLeaseFile> getFiles() {
		return files;
	}

	public void setFiles(SortedSet<EzeeLeaseFile> files) {
		this.files = files;
	}

	public void addNote(final EzeeLeaseNote note) {
		if (isEmpty(notes)) {
			notes = new TreeSet<>();
		}
		notes.add(note);
	}

	public void removeNote(final EzeeLeaseNote note) {
		if (!isEmpty(notes)) {
			notes.remove(note);
		}
	}

	public void addFile(final EzeeLeaseFile file) {
		if (isEmpty(files)) {
			files = new TreeSet<>();
		}
		files.add(file);
	}

	public void removeFile(final EzeeLeaseFile file) {
		if (!isEmpty(files)) {
			files.remove(file);
		}
	}

	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((leasedUnits == null) ? 0 : leasedUnits.hashCode());
		result = prime * result + ((premises == null) ? 0 : premises.hashCode());
		result = prime * result + ((tenant == null) ? 0 : tenant.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		EzeeLease other = (EzeeLease) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (leasedUnits == null) {
			if (other.leasedUnits != null)
				return false;
		} else if (!leasedUnits.equals(other.leasedUnits))
			return false;
		if (premises == null) {
			if (other.premises != null)
				return false;
		} else if (!premises.equals(other.premises))
			return false;
		if (tenant == null) {
			if (other.tenant != null)
				return false;
		} else if (!tenant.equals(other.tenant))
			return false;
		return true;
	}
}