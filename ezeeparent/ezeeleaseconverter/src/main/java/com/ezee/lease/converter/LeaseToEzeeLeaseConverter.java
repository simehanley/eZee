package com.ezee.lease.converter;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.server.EzeeServerDateUtils.SERVER_DATE_UTILS;
import static com.hg.leases.model.LeaseConstants.GST_PERCENTAGE;
import static java.util.Collections.sort;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.model.entity.lease.EzeeLeaseBond;
import com.ezee.model.entity.lease.EzeeLeaseBondType;
import com.ezee.model.entity.lease.EzeeLeaseCategory;
import com.ezee.model.entity.lease.EzeeLeaseIncidental;
import com.ezee.model.entity.lease.EzeeLeaseMetaData;
import com.ezee.model.entity.lease.EzeeLeasePremises;
import com.ezee.model.entity.lease.EzeeLeaseTenant;
import com.hg.leases.model.Lease;
import com.hg.leases.model.LeaseBond;
import com.hg.leases.model.LeaseCategory;
import com.hg.leases.model.LeaseIncidental;
import com.hg.leases.model.LeaseMetaData;
import com.hg.leases.model.LeasePremises;
import com.hg.leases.model.LeaseTenant;
import com.hg.leases.model.enums.LeaseBondType;

public class LeaseToEzeeLeaseConverter {

	private Map<String, EzeeLeaseCategory> categoriesCache = new HashMap<>();
	private Map<String, EzeeLeasePremises> premisesCache = new HashMap<>();
	private Map<String, EzeeLeaseTenant> tenantCache = new HashMap<>();

	public EzeeLease convert(final Lease lease) {
		String start = SERVER_DATE_UTILS.toString(lease.getLeaseStart().toDate());
		String end = SERVER_DATE_UTILS.toString(lease.getLeaseEnd().toDate());
		String updated = SERVER_DATE_UTILS.toString(lease.getLastUpdate().toDate());
		String created = SERVER_DATE_UTILS.toString(new Date());
		EzeeLeaseTenant tenant = tenantCache.containsKey(lease.getTenant().getName())
				? tenantCache.get(lease.getTenant().getName()) : convert(lease.getTenant());
		EzeeLeasePremises premises = premisesCache.containsKey(lease.getPremises().getAddressLineOne())
				? premisesCache.get(lease.getPremises().getAddressLineOne()) : convert(lease.getPremises());
		EzeeLeaseCategory category = categoriesCache.containsKey(lease.getCategory().getCategory())
				? categoriesCache.get(lease.getCategory().getCategory()) : convert(lease.getCategory());
		SortedSet<EzeeLeaseMetaData> metaData = convertMetaData(lease.getMetaData());
		Set<EzeeLeaseIncidental> incidentals = convertIncidentals(lease.getIncidentals());
		EzeeLease ezeeLease = new EzeeLease(start, end, lease.getNotes(), lease.getLeasedArea(), lease.getLeasedUnits(),
				incidentals, tenant, premises, category, metaData, lease.isResidential(), lease.isInactive(),
				lease.getJobNo(), created, updated);
		if (lease.hasOption()) {
			ezeeLease.setHasOption(true);
			if (lease.getOptionStartDate() != null) {
				ezeeLease.setOptionStartDate(SERVER_DATE_UTILS.toString(lease.getOptionStartDate().toDate()));
			}
			if (lease.getOptionEndDate() != null) {
				ezeeLease.setOptionEndDate(SERVER_DATE_UTILS.toString(lease.getOptionEndDate().toDate()));
			}
		} else {
			ezeeLease.setHasOption(false);
		}
		if (lease.getBond() != null) {
			ezeeLease.setBond(convertBond(lease.getBond()));
		}
		return ezeeLease;

	}

	private EzeeLeaseBond convertBond(final LeaseBond bond) {
		String created = SERVER_DATE_UTILS.toString(new Date());
		EzeeLeaseBond leaseBond = new EzeeLeaseBond();
		leaseBond.setAmount(bond.getAmount());
		leaseBond.setNotes(bond.getNotes());
		leaseBond.setCreated(created);
		leaseBond.setUpdated(created);
		leaseBond.setType(convert(bond.getType()));
		return leaseBond;
	}

	private Set<EzeeLeaseIncidental> convertIncidentals(final List<LeaseIncidental> incidentals) {
		String created = SERVER_DATE_UTILS.toString(new Date());
		if (!isEmpty(incidentals)) {
			Set<EzeeLeaseIncidental> ezeeLeaseIncidentals = new HashSet<>();
			for (LeaseIncidental incidental : incidentals) {
				ezeeLeaseIncidentals.add(new EzeeLeaseIncidental(incidental.getName(), incidental.getAmount(),
						GST_PERCENTAGE, incidental.getPercentage(), incidental.getAccount(), created, created));
			}
			return ezeeLeaseIncidentals;
		}
		return null;
	}

	private SortedSet<EzeeLeaseMetaData> convertMetaData(final List<LeaseMetaData> metaData) {
		String created = SERVER_DATE_UTILS.toString(new Date());
		if (!isEmpty(metaData)) {
			sort(metaData);
			SortedSet<EzeeLeaseMetaData> ezeeLeaseMetaData = new TreeSet<>();
			int order = ZERO;
			for (LeaseMetaData md : metaData) {
				EzeeLeaseMetaData data = new EzeeLeaseMetaData(md.getType(), md.getDescription(), md.getValue(),
						created, created);
				data.setOrder(order);
				++order;
				ezeeLeaseMetaData.add(data);
			}
			return ezeeLeaseMetaData;
		}
		return null;
	}

	private EzeeLeaseCategory convert(final LeaseCategory category) {
		String created = SERVER_DATE_UTILS.toString(new Date());
		String[] phoneNumbers = getCategoryPhoneNumbers(category.getCategoryPhone());
		return new EzeeLeaseCategory(category.getCategory(), category.getCategoryCompany(),
				category.getCategoryAddress(), null, null, null, null, null, phoneNumbers[0], phoneNumbers[1], null,
				null, category.getCategoryBank(), category.getCategoryAccountName(),
				category.getCategoryAccountNumber(), category.getCategoryBsb(),
				category.getCategoryAbn().replace("ABN : ", EMPTY_STRING), created, created);
	}

	private EzeeLeasePremises convert(final LeasePremises premises) {
		String created = SERVER_DATE_UTILS.toString(new Date());
		return new EzeeLeasePremises(premises.getAddressLineOne(), premises.getAddressLineOne(),
				premises.getAddressLineTwo(), null, null, null, null, null, null, null, null, created, created);
	}

	private EzeeLeaseTenant convert(final LeaseTenant tenant) {
		String created = SERVER_DATE_UTILS.toString(new Date());
		return new EzeeLeaseTenant(tenant.getName(), null, null, null, null, null, null, null, null, null, null,
				created, created);
	}

	private final String[] getCategoryPhoneNumbers(String leasePhoneString) {
		leasePhoneString = leasePhoneString.replace("PH:  ", EMPTY_STRING);
		leasePhoneString = leasePhoneString.replace("FX:  ", ",");
		leasePhoneString = leasePhoneString.replace(" ", EMPTY_STRING);
		return leasePhoneString.split(",");
	}

	public final Map<String, EzeeLeaseCategory> getCategoriesCache() {
		return categoriesCache;
	}

	public final Map<String, EzeeLeasePremises> getPremisesCache() {
		return premisesCache;
	}

	public final Map<String, EzeeLeaseTenant> getTenantCache() {
		return tenantCache;
	}

	private EzeeLeaseBondType convert(final LeaseBondType type) {
		switch (type) {
		case deposit_held:
			return EzeeLeaseBondType.deposit_held;
		default:
			return EzeeLeaseBondType.bank_guarantee;
		}
	}
}