package com.ezee.model.entity.filter.lease;

import com.ezee.model.entity.filter.EzeeEntityFilter;
import com.ezee.model.entity.filter.EzeeStringFilter;
import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.model.entity.lease.EzeeLeaseCategory;
import com.ezee.model.entity.lease.EzeeLeasePremises;
import com.ezee.model.entity.lease.EzeeLeaseTenant;

public class EzeeLeaseFilter implements EzeeEntityFilter<EzeeLease> {

	private final EzeeEntityFilter<EzeeLeaseTenant> tenantFilter;

	private final EzeeEntityFilter<EzeeLeasePremises> premisesFilter;

	private final EzeeEntityFilter<EzeeLeaseCategory> categoryFilter;

	private final boolean includeInactive;

	public EzeeLeaseFilter(final String tenant, final String premises, final String category,
			final boolean includeInactive) {
		tenantFilter = new EzeeStringFilter<>(tenant);
		premisesFilter = new EzeeStringFilter<>(premises);
		categoryFilter = new EzeeStringFilter<>(category);
		this.includeInactive = includeInactive;
	}

	@Override
	public boolean include(final EzeeLease lease) {
		if (!tenantFilter.include(lease.getTenant())) {
			return false;
		} else if (!premisesFilter.include(lease.getPremises())) {
			return false;
		} else if (!categoryFilter.include(lease.getCategory())) {
			return false;
		} else if (!includeInactive && lease.isInactive()) {
			return false;
		}
		return true;
	}
}