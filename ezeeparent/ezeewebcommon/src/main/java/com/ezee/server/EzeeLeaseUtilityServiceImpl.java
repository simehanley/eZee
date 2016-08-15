package com.ezee.server;

import static com.ezee.server.EzeeServerDateUtils.SERVER_DATE_UTILS;

import org.joda.time.LocalDate;

import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.server.util.lease.EzeeLeaseCurrentPeriodGenerator;
import com.ezee.server.util.lease.EzeeLeaseMaintenanceService;
import com.ezee.web.common.datastructures.EzeePair;
import com.ezee.web.common.service.EzeeLeaseUtilityService;

public class EzeeLeaseUtilityServiceImpl extends AbstractRemoteService implements EzeeLeaseUtilityService {

	private static final long serialVersionUID = 585919041109927240L;

	@Override
	public String getCurrentLeasePeriodString(final EzeeLease lease) {
		EzeeLeaseCurrentPeriodGenerator generator = getSpringBean(EzeeLeaseCurrentPeriodGenerator.class);
		LocalDate current = new LocalDate();
		EzeePair<LocalDate, LocalDate> dates = generator.resolveCurrentPeriod(lease, current);
		return SERVER_DATE_UTILS.toString(dates.getFirst().toDate()) + "-"
				+ SERVER_DATE_UTILS.toString(dates.getSecond().toDate());
	}

	@Override
	public void sendEmail() {
		EzeeLeaseMaintenanceService service = getSpringBean(EzeeLeaseMaintenanceService.class);
		service.run();
	}
}