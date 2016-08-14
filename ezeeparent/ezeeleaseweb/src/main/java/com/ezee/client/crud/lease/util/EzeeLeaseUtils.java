package com.ezee.client.crud.lease.util;

import static com.ezee.client.EzeeLeaseWebConstants.VACANT_TENANT_NAME;
import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.common.EzeeCommonConstants.TWO;
import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.ezee.model.entity.EzeeHasName;
import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.model.entity.lease.EzeeLeaseIncidental;
import com.ezee.model.entity.lease.EzeeLeaseTenant;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;

public class EzeeLeaseUtils {

	public void createVacantLease(final EzeeEntityCache cache, final EzeeLease lease,
			final EzeeCreateUpdateDeleteEntityHandler<EzeeLease> handler) {
		if (lease != null) {
			EzeeLease vacant = cloneToVacantLease(cache, lease);
			if (vacant != null) {
				handler.onSave(vacant);
			}
		}
	}

	private EzeeLease cloneToVacantLease(final EzeeEntityCache cache, final EzeeLease lease) {
		if (lease != null) {
			String created = DATE_UTILS.toString(new Date());
			Date start = DATE_UTILS.addDays(DATE_UTILS.fromString(lease.getLeaseEnd()), ONE);
			Date end = DATE_UTILS.addYearsAsDays(start, TWO);
			Set<EzeeLeaseIncidental> incidentals = cloneToVacantLeaseIncidentals(cache, lease.getIncidentals());
			EzeeLeaseTenant tenant = resolveVacantTenant(cache);
			EzeeLease vacantLease = new EzeeLease(DATE_UTILS.toString(start), DATE_UTILS.toString(end),
					lease.getLeasedArea(), lease.getLeasedUnits(), incidentals, tenant, lease.getPremises(),
					lease.getCategory(), null, lease.isResidential(), false, lease.getJobNo(), created, created);
			vacantLease.setEdited(true);
			return vacantLease;
		}
		return null;
	}

	private Set<EzeeLeaseIncidental> cloneToVacantLeaseIncidentals(final EzeeEntityCache cache,
			final Set<EzeeLeaseIncidental> incidentals) {
		if (!isEmpty(incidentals)) {
			Set<EzeeLeaseIncidental> cloned = new LinkedHashSet<>();
			double taxRate = cache.getConfiguration().getInvoiceTaxRate();
			for (EzeeLeaseIncidental incidental : incidentals) {
				cloned.add(new EzeeLeaseIncidental(incidental.getName(), ZERO_DBL, taxRate, ZERO_DBL,
						incidental.getAccount()));
			}
			return cloned;
		}
		return null;
	}

	private static EzeeLeaseTenant resolveVacantTenant(final EzeeEntityCache cache) {
		Map<String, EzeeHasName> tennants = cache.getEntities(EzeeLeaseTenant.class);
		return (EzeeLeaseTenant) tennants.get(VACANT_TENANT_NAME);
	}
}