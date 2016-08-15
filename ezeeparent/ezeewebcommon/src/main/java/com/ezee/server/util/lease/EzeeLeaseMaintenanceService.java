package com.ezee.server.util.lease;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.RENT;
import static com.ezee.server.util.lease.EzeeLeaseDateUtils.format;
import static com.ezee.server.util.lease.EzeeLeaseDateUtils.isBeforeOrEqual;
import static com.ezee.server.util.lease.EzeeLeaseDateUtils.toDate;
import static java.util.Collections.sort;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasLength;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.dao.EzeeLeaseDao;
import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.model.entity.lease.EzeeLeaseIncidental;

public class EzeeLeaseMaintenanceService {

	private static final Logger log = LoggerFactory.getLogger(EzeeLeaseMaintenanceService.class);

	private static final DecimalFormat NUMBER_FORMATTER = new DecimalFormat("#,##0.00");

	private final int monthlyUpdateInterval;

	@Autowired
	private EzeeLeaseDao dao;

	@Autowired
	private EzeeLeaseMaintenanceEmailHelper emailHelper;

	public EzeeLeaseMaintenanceService(int monthlyUpdateInterval) {
		super();
		this.monthlyUpdateInterval = monthlyUpdateInterval;
	}

	public void run() {
		try {
			log.info("Running lease maintenance service.");
			List<EzeeLease> leases = dao.get(EzeeLease.class);
			List<EzeeLease> expired = new ArrayList<EzeeLease>();
			List<EzeeLease> renew = new ArrayList<EzeeLease>();
			List<EzeeLease> reprice = new ArrayList<EzeeLease>();
			LocalDate now = new LocalDate();
			if (!isEmpty(leases)) {
				for (EzeeLease lease : leases) {
					if (!lease.isInactive()) {
						checkExpired(now, lease, expired);
						checkRenew(now, lease, renew);
						checkReprice(now, lease, reprice);
					}
				}
			}
			String emailBody = draftActions(now, expired, renew, reprice);
			if (hasLength(emailBody)) {
				emailHelper.sendEmail(emailBody);
				log.info("Successfully run lease maintenance service.");
			} else {
				log.info("Successfully run lease maintenance service with no actions found.");
			}
		} catch (Throwable t) {
			log.info("Error running lease maintenance service.", t);
		}
	}

	private void checkExpired(final LocalDate now, final EzeeLease lease, final List<EzeeLease> expired) {
		if (isBeforeOrEqual(toDate(lease.getEffectiveLeaseEnd()), now)) {
			expired.add(lease);
		}
		if (!isEmpty(expired)) {
			sort(expired);
		}
	}

	private void checkRenew(final LocalDate now, final EzeeLease lease, final List<EzeeLease> renew) {
		if (toDate(lease.getEffectiveLeaseEnd()).isAfter(now)) {
			LocalDate minimalRenewDate = now.plusMonths(monthlyUpdateInterval);
			if (isBeforeOrEqual(toDate(lease.getEffectiveLeaseEnd()), minimalRenewDate)) {
				LocalDate minimalUpdateDate = toDate(lease.getEffectiveLeaseEnd()).minusMonths(monthlyUpdateInterval);
				if (isBeforeOrEqual(toDate(lease.getUpdated()), minimalUpdateDate)) {
					renew.add(lease);
				}
			}
		}
		if (!isEmpty(renew)) {
			sort(renew);
		}
	}

	private void checkReprice(final LocalDate now, final EzeeLease lease, final List<EzeeLease> reprice) {
		if (toDate(lease.getEffectiveLeaseEnd()).isAfter(now)) {
			LocalDate minimalRenewDate = now.plusMonths(monthlyUpdateInterval);
			if (!isBeforeOrEqual(toDate(lease.getEffectiveLeaseEnd()), minimalRenewDate)) {
				LocalDate resolvedRepriceDate = resolveRepriceDate(now, lease);
				LocalDate minimalUpdateDate = resolvedRepriceDate.minusMonths(monthlyUpdateInterval);
				if (isBeforeOrEqual(minimalUpdateDate, now)
						&& isBeforeOrEqual(toDate(lease.getUpdated()), minimalUpdateDate)) {
					int lastUpdateYear = toDate(lease.getUpdated()).getYear();
					int minimalUpdateYear = minimalUpdateDate.getYear();
					if (lastUpdateYear < minimalUpdateYear) {
						reprice.add(lease);
					}
				}
			}
		}
		if (!isEmpty(reprice)) {
			sort(reprice);
		}
	}

	private String draftActions(final LocalDate now, final List<EzeeLease> expired, final List<EzeeLease> renew,
			final List<EzeeLease> reprice) {
		if (!isEmpty(expired) || !isEmpty(renew) || !isEmpty(reprice)) {
			List<String> actions = new ArrayList<String>();
			setEmailHtmlHeader(actions);
			if (!isEmpty(expired)) {
				actions.add("<h2>Expired Actions</h2><table><tr><th>Tenant</th>"
						+ "<th>Premises</th><th>Unit(s)</th><th>End Date</th><th>Last Update</th></tr>");
				for (EzeeLease lease : expired) {
					draftExpiredAction(lease, actions);
				}
				actions.add("</table>");
			}
			if (!isEmpty(renew)) {
				actions.add("<h2>Renew Actions</h2><table><tr><th>Tenant</th>"
						+ "<th>Premises</th><th>Unit(s)</th><th>End Date</th><th>Last Update</th></tr>");
				for (EzeeLease lease : renew) {
					draftRenewAction(lease, actions);
				}
				actions.add("</table>");
			}
			if (!isEmpty(reprice)) {
				actions.add("<h2>Reprice Actions</h2><table><tr><th>Tenant</th>"
						+ "<th>Premises</th><th>Unit(s)</th><th>Reprice Date</th><th>Last Update</th>"
						+ "<th>Old Lease Amount</th><th>New Lease Amount</th></tr>");
				for (EzeeLease lease : reprice) {
					draftRepriceAction(lease, actions, now);
				}
				actions.add("</table>");
			}
			actions.add("</html>");
			StringBuilder leaseBuilder = new StringBuilder();
			for (String str : actions) {
				leaseBuilder.append(str + "\n");
			}
			return leaseBuilder.toString();
		}
		return EMPTY_STRING;
	}

	private void draftExpiredAction(final EzeeLease expired, final List<String> actions) {
		draftDefaultAction(expired, actions);
	}

	private void draftRenewAction(final EzeeLease renew, final List<String> actions) {
		draftDefaultAction(renew, actions);
	}

	private void draftDefaultAction(final EzeeLease lease, final List<String> actions) {
		actions.add("<tr><td>" + lease.getTenant().getName() + "</td><td>" + lease.getPremises().getAddressLineOne()
				+ "</td><td>" + lease.getLeasedUnits() + "</td><td>" + format(toDate(lease.getEffectiveLeaseEnd()))
				+ "</td><td>" + format(toDate(lease.getUpdated())) + "</td></tr>");
	}

	private void draftRepriceAction(final EzeeLease reprice, final List<String> actions, final LocalDate now) {
		actions.add("<tr><td>" + reprice.getTenant().getName() + "</td><td>" + reprice.getPremises().getAddressLineOne()
				+ "</td><td>" + reprice.getLeasedUnits() + "</td><td>" + format(resolveRepriceDate(now, reprice))
				+ "</td><td>" + format(toDate(reprice.getUpdated())) + "</td><td>" + resolveCurrentLeaseAmount(reprice)
				+ "</td><td>" + resolveNewLeaseAmount(reprice) + "</td></tr>");
	}

	private LocalDate resolveRepriceDate(final LocalDate now, final EzeeLease lease) {
		LocalDate candidate = toDate(lease.getEffectiveLeaseEnd());
		while (candidate.isAfter(now)) {
			candidate = candidate.minusYears(ONE);
		}
		return candidate.plusYears(ONE);
	}

	private String resolveNewLeaseAmount(final EzeeLease lease) {
		EzeeLeaseIncidental incidental = lease.getIncidental(RENT);
		double newRent = incidental.getAmount() * (ONE + incidental.getPercentage());
		return NUMBER_FORMATTER.format(newRent);
	}

	private String resolveCurrentLeaseAmount(final EzeeLease lease) {
		EzeeLeaseIncidental incidental = lease.getIncidental(RENT);
		return NUMBER_FORMATTER.format(incidental.getAmount());
	}

	private void setEmailHtmlHeader(final List<String> actions) {
		actions.add("<!DOCTYPE html><html><head><style>" + "table {width:100%;} "
				+ "table, th, td {border: 1px solid black; border-collapse: collapse;} "
				+ "th { padding: 5px; text-align: center;} " + "tr { padding: 5px; text-align: left;} "
				+ "table tr {background-color:#eee;} " + "table th {background-color: black; color: white;} "
				+ "</style></head><body>");
	}
}