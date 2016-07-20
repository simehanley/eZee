package com.ezee.server.report.myob.lease;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.EzeeCommonConstants.RETURN;
import static com.ezee.common.EzeeCommonConstants.SPACE;
import static com.ezee.common.EzeeCommonConstants.TAB;
import static com.ezee.common.EzeeCommonConstants.TWO;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.numeric.EzeeNumericUtils.isCloseToZero;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.LEASE_ID;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.OUTGOINGS;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.PARKING;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.RENT;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.SIGNAGE;
import static com.ezee.server.util.lease.EzeeLeaseDateUtils.format;
import static com.ezee.server.util.lease.EzeeLeaseDateUtils.formatInvoiceDate;
import static com.ezee.server.util.lease.EzeeLeaseDateUtils.formatShortDate;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.dao.EzeeLeaseDao;
import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.model.entity.lease.EzeeLeaseIncidental;
import com.ezee.server.report.EzeeReportGenerator;
import com.ezee.server.report.myob.AbstractMyobReportGenerator;
import com.ezee.server.util.lease.EzeeLeaseCurrentPeriodGenerator;

public class EzeeLeaseMyobScheduleGenerator extends AbstractMyobReportGenerator implements EzeeReportGenerator {

	private static final Logger log = LoggerFactory.getLogger(EzeeLeaseMyobScheduleGenerator.class);

	@Autowired
	private EzeeLeaseCurrentPeriodGenerator generator;

	@Autowired
	private EzeeLeaseDao dao;

	private static final String SCHEDULE_TEMPLATE_NAME = "MYOB_RENT_SCHEDULE.txt";

	@Override
	public void generateReport(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			generateMyobSchedule(request, response);
		} catch (Throwable t) {
			log.error("Error generating lease myob schedule.", t);
		}
	}

	private void generateMyobSchedule(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException, URISyntaxException {
		long id = Long.valueOf(request.getParameter(LEASE_ID));
		EzeeLease lease = dao.get(id, EzeeLease.class);
		if (lease != null) {
			generateMyobSchedule(lease, response);
		}
	}

	private void generateMyobSchedule(final EzeeLease lease, final HttpServletResponse response)
			throws IOException, URISyntaxException {
		String filename = UUID.randomUUID().toString() + ".txt";
		OutputStream stream = createMyobResponseHeader(response, filename);
		byte[] schedule = generateMyobSchedule(lease, filename);
		if (schedule != null) {
			response.setContentLength((int) schedule.length);
			stream.write(schedule);
		}
		stream.close();
	}

	@SuppressWarnings("unused")
	private byte[] generateMyobSchedule(final EzeeLease lease, final String filename)
			throws IOException, URISyntaxException {
		File source = new File(this.getClass().getClassLoader().getResource("/myob/" + SCHEDULE_TEMPLATE_NAME).toURI());
		String path = this.getClass().getClassLoader().getResource("/myob/").getPath();
		File dest = new File(path + filename);
		FileUtils.copyFile(source, dest);
		if (dest != null) {
			try {
				generateMyobSchedule(lease, dest);
				return FileUtils.readFileToByteArray(dest);
			} finally {
				Files.deleteIfExists(dest.toPath());
			}
		}
		return null;
	}

	private void generateMyobSchedule(final EzeeLease lease, final File file) throws IOException {
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
		writer.write(RETURN);
		try {
			List<LocalDate> schedule = generator.resolveCurrentSchedule(lease);
			for (LocalDate date : schedule) {
				generateMyobSchedule(date, RENT, lease, writer);
				generateMyobSchedule(date, OUTGOINGS, lease, writer);
				generateMyobSchedule(date, PARKING, lease, writer);
				generateMyobSchedule(date, SIGNAGE, lease, writer);
				writer.append("\r");
			}
		} finally {
			writer.close();
		}
	}

	private void generateMyobSchedule(final LocalDate date, final String type, final EzeeLease lease,
			final PrintWriter writer) {
		EzeeLeaseIncidental incidental = lease.getIncidental(type);
		if (incidental != null && !isCloseToZero(incidental.getAmount())) {
			StringBuilder builder = new StringBuilder();
			builder.append(lease.getTenant().getName() + TAB + TAB);
			builder.append(resolveAddressLineOne(lease));
			builder.append(resolveAddressLineTwo(lease));
			builder.append(resolveAddressLineThree(lease));
			builder.append(TAB);
			builder.append("X" + TAB);
			builder.append(resolveInvoiceNumber(lease, date) + TAB); /* invoice */
			builder.append(format(date) + TAB);
			builder.append(formatShortDate(date) + TAB);
			builder.append(TAB);
			builder.append(EMPTY_STRING + TAB);
			builder.append(type + TAB);
			builder.append(resolveAccount(incidental) + TAB);
			builder.append(lease.monthlyAmount(type) + TAB);
			builder.append(lease.monthlyTotal(type) + TAB);
			builder.append(resolveJobNumber(lease) + TAB);
			builder.append(resolveNotes(lease) + TAB);
			builder.append("Sale; " + lease.getTenant().getName() + TAB);
			builder.append(TAB + TAB + TAB + TAB);
			builder.append(resolveGstType(lease) + TAB);
			builder.append(ZERO_DBL + TAB);
			builder.append(lease.monthlyGst(type) + TAB);
			builder.append(ZERO_DBL + TAB);
			builder.append(TAB + TAB);
			builder.append(resolveGstType(lease) + TAB);
			builder.append(ZERO_DBL + TAB);
			builder.append(ZERO_DBL + TAB);
			builder.append(ZERO_DBL + TAB);
			builder.append("I" + TAB);
			builder.append(TAB + TAB);
			builder.append(TWO + TAB);
			builder.append(ZERO + TAB);
			builder.append(ZERO + TAB);
			builder.append(ZERO + TAB);
			builder.append(ZERO + TAB);
			builder.append(ZERO_DBL + TAB);
			builder.append(TAB + TAB + TAB + TAB + TAB + TAB + TAB + TAB + TAB + TAB + TAB);
			builder.append("*None" + TAB);
			builder.append(EMPTY_STRING + TAB); /* record id */
			writer.println(builder.toString());
		}
	}

	private String resolveAddressLineOne(final EzeeLease lease) {
		return lease.getTenant().getName() + TAB;
	}

	private String resolveAddressLineTwo(final EzeeLease lease) {
		return "Unit " + lease.getLeasedUnits() + "/" + lease.getPremises().getAddressLineOne() + TAB;
	}

	private String resolveAddressLineThree(final EzeeLease lease) {
		return lease.getPremises().getAddressLineTwo() + TAB;
	}

	private String resolveGstType(final EzeeLease lease) {
		return (lease.isResidential()) ? "ITS" : "GST";
	}

	private String resolveAccount(final EzeeLeaseIncidental incidental) {
		return (incidental.getAccount() != null) ? incidental.getAccount() : EMPTY_STRING;
	}

	private String resolveJobNumber(final EzeeLease lease) {
		return (lease.getJobNo() != null) ? lease.getJobNo() : EMPTY_STRING;
	}

	private String resolveNotes(final EzeeLease lease) {
		if (isEmpty(lease.getNotes())) {
			return EMPTY_STRING;
		}
		return lease.getNotes().last().getNote().replace(RETURN, SPACE).replace(RETURN, SPACE);
	}

	private String resolveInvoiceNumber(final EzeeLease lease, final LocalDate date) {
		return lease.getTenant().getName().substring(ZERO, TWO).toUpperCase() + formatInvoiceDate(date);
	}
}