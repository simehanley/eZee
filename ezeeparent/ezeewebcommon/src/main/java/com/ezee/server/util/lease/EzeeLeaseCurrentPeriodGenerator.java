package com.ezee.server.util.lease;

import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.server.EzeeServerDateUtils.SERVER_DATE_UTILS;
import static com.ezee.server.util.lease.EzeeLeaseDateUtils.isBetween;
import static com.ezee.server.util.lease.EzeeLeaseDateUtils.isGreaterThan;
import static com.ezee.server.util.lease.EzeeLeaseDateUtils.isGreaterThanOrEqualTo;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.Months;

import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.web.common.datastructures.EzeePair;

public class EzeeLeaseCurrentPeriodGenerator {

	private final int minimumToleranceInMonths;

	public EzeeLeaseCurrentPeriodGenerator(int minimumToleranceInMonths) {
		this.minimumToleranceInMonths = minimumToleranceInMonths;
	}

	public EzeePair<LocalDate, LocalDate> resolveCurrentPeriod(final EzeeLease lease, final LocalDate current) {
		LocalDate leaseEnd = new LocalDate(SERVER_DATE_UTILS.fromString(lease.getEffectiveLeaseEnd()));
		LocalDate leaseStart = new LocalDate(SERVER_DATE_UTILS.fromString(lease.getLeaseStart()));
		if (isGreaterThanOrEqualTo(current, leaseEnd)) {
			LocalDate start = leaseEnd.minusYears(ONE).plusDays(ONE);
			return new EzeePair<LocalDate, LocalDate>(start, leaseEnd);
		} else if (isGreaterThanOrEqualTo(leaseStart, current)) {
			LocalDate end = leaseStart.plusYears(ONE).minusDays(ONE);
			return new EzeePair<LocalDate, LocalDate>(leaseStart, end);
		} else {
			leaseEnd = leaseStart.plusYears(ONE).minusDays(ONE);
			while (!isBetween(current, leaseStart, leaseEnd)) {
				leaseStart = leaseStart.plusYears(ONE);
				leaseEnd = leaseStart.plusYears(ONE).minusDays(ONE);
			}
			return new EzeePair<LocalDate, LocalDate>(leaseStart, leaseEnd);
		}
	}

	public EzeePair<LocalDate, LocalDate> resolveCurrentPeriod(final EzeeLease lease) {
		LocalDate proposedEnd = new LocalDate(SERVER_DATE_UTILS.fromString(lease.getEffectiveLeaseEnd()));
		LocalDate proposedStart = proposedEnd.minusYears(ONE).plusDays(ONE);
		while (isGreaterThan(proposedStart, new LocalDate(SERVER_DATE_UTILS.fromString(lease.getLeaseStart())))) {
			if (updatedInSameYear(proposedStart, new LocalDate(SERVER_DATE_UTILS.fromString(lease.getUpdated())))
					|| updatedWithinMinimumTolerance(proposedStart,
							new LocalDate(SERVER_DATE_UTILS.fromString(lease.getUpdated())))
					|| straddlesUpdateDate(proposedStart, proposedEnd,
							new LocalDate(SERVER_DATE_UTILS.fromString(lease.getUpdated())))) {
				return new EzeePair<LocalDate, LocalDate>(proposedStart, proposedEnd);
			}
			proposedEnd = proposedEnd.minusYears(ONE);
			proposedStart = proposedEnd.minusYears(ONE).plusDays(ONE);
		}
		return new EzeePair<LocalDate, LocalDate>(new LocalDate(SERVER_DATE_UTILS.fromString(lease.getLeaseStart())),
				proposedEnd);
	}

	public List<LocalDate> resolveCurrentSchedule(final EzeeLease lease) {
		EzeePair<LocalDate, LocalDate> currentPeriod = resolveCurrentPeriod(lease);
		LocalDate start = currentPeriod.getFirst();
		int rollDay = start.getDayOfMonth();
		List<LocalDate> schedule = new ArrayList<LocalDate>();
		while (start.isBefore(currentPeriod.getSecond())) {
			schedule.add(start);
			start = resolveAmendedStartDate(start, rollDay);
		}
		return schedule;
	}

	private LocalDate resolveAmendedStartDate(final LocalDate start, final int rollDay) {
		LocalDate candidate = start.plusMonths(ONE);
		if (rollDay == candidate.getDayOfMonth()
				|| rollDay > candidate.dayOfMonth().withMaximumValue().getDayOfMonth()) {
			return candidate;
		} else {
			int daysToAdd = rollDay - candidate.getDayOfMonth();
			return candidate.plusDays(daysToAdd);
		}
	}

	private boolean updatedInSameYear(final LocalDate proposedStart, final LocalDate lastUpdate) {
		return proposedStart.getYear() == lastUpdate.getYear();
	}

	private boolean updatedWithinMinimumTolerance(final LocalDate proposedStart, final LocalDate lastUpdate) {
		if (isGreaterThan(proposedStart, lastUpdate)) {
			int months = Months.monthsBetween(lastUpdate, proposedStart).getMonths();
			return months < minimumToleranceInMonths;
		}
		return false;
	}

	private boolean straddlesUpdateDate(final LocalDate proposedStart, final LocalDate proposedEnd,
			final LocalDate lastUpdate) {
		return isGreaterThanOrEqualTo(lastUpdate, proposedStart) && isGreaterThanOrEqualTo(proposedEnd, lastUpdate);
	}
}