package com.ezee.server.report.excel.lease;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.model.entity.lease.EzeeLeaseConstants.HISTORICAL_RENT;
import static com.ezee.model.entity.lease.EzeeLeaseConstants.NOTICE;
import static com.ezee.model.entity.lease.EzeeLeaseConstants.OPTION;
import static com.ezee.model.entity.lease.EzeeLeaseConstants.SPECIAL_CONDITION;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.LEASE_ID;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.OUTGOINGS;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.PARKING;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.RENT;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.SIGNAGE;
import static java.util.Collections.sort;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.dao.EzeeLeaseDao;
import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.model.entity.lease.EzeeLeaseIncidental;
import com.ezee.model.entity.lease.EzeeLeaseMetaData;
import com.ezee.server.report.EzeeReportGenerator;
import com.ezee.server.report.excel.AbstractExcelReportGenerator;
import com.ezee.web.common.datastructures.EzeePair;

public class EzeeLeaselScheduleGenerator extends AbstractExcelReportGenerator implements EzeeReportGenerator {

	private static final Logger log = LoggerFactory.getLogger(EzeeLeaselScheduleGenerator.class);

	private static final String SCHEDULE_TEMPLATE_NAME = "RENT_SCHEDULE_TEMPLATE.xls";

	@Autowired
	private EzeeLeaseDao dao;

	@Override
	public void generateReport(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			generateExcelSchedule(request, response);
		} catch (IOException e) {
			log.error("Error generating lease excel schedule.", e);
		}
	}

	private void generateExcelSchedule(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		long leaseId = Long.valueOf(request.getParameter(LEASE_ID));
		EzeeLease lease = dao.get(leaseId, EzeeLease.class);
		if (lease != null) {
			generateExcelSchedule(lease, response);
		}
	}

	private void generateExcelSchedule(final EzeeLease lease, final HttpServletResponse response) throws IOException {
		String filename = UUID.randomUUID().toString() + ".xls";
		OutputStream stream = createExcelFilenameResponseHeader(response, filename);
		byte[] excelSchedule = generateExcelSchedule(lease);
		if (excelSchedule != null) {
			response.setContentLength((int) excelSchedule.length);
			stream.write(excelSchedule);
		}
		stream.close();
	}

	private byte[] generateExcelSchedule(final EzeeLease lease) throws IOException {
		Workbook book = new HSSFWorkbook(getClass().getResourceAsStream("/excel/" + SCHEDULE_TEMPLATE_NAME));
		Sheet sheet = book.getSheet("Schedule");
		try {
			if (sheet != null) {
				generateExcelSchedule(lease, book, sheet);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				book.write(stream);
				stream.close();
				return stream.toByteArray();
			}
			return null;
		} finally {
			book.close();
		}
	}

	private void generateExcelSchedule(final EzeeLease lease, final Workbook book, final Sheet sheet) {
		sheet.getRow(SCHEDULE_TITLE_1_INDEX.getFirst()).getCell(SCHEDULE_TITLE_1_INDEX.getSecond())
				.setCellValue(resolveTitleLineOne(lease));
		sheet.getRow(SCHEDULE_TITLE_2_INDEX.getFirst()).getCell(SCHEDULE_TITLE_2_INDEX.getSecond())
				.setCellValue(resolveTitleLineTwo(lease));
		sheet.getRow(SCHEDULE_ADDRESS_INDEX.getFirst()).getCell(SCHEDULE_ADDRESS_INDEX.getSecond())
				.setCellValue(resolveAddress(lease));
		sheet.getRow(SCHEDULE_DEVELOPMENT_INDEX.getFirst()).getCell(SCHEDULE_DEVELOPMENT_INDEX.getSecond())
				.setCellValue(lease.getCategory().getName());
		sheet.getRow(SCHEDULE_UNIT_INDEX.getFirst()).getCell(SCHEDULE_UNIT_INDEX.getSecond())
				.setCellValue(lease.getLeasedUnits());
		sheet.getRow(SCHEDULE_LESSEE_INDEX.getFirst()).getCell(SCHEDULE_LESSEE_INDEX.getSecond())
				.setCellValue(lease.getTenant().getName());
		sheet.getRow(SCHEDULE_YEARLY_RENT_INDEX.getFirst()).getCell(SCHEDULE_YEARLY_RENT_INDEX.getSecond())
				.setCellValue(lease.yearlyAmount(RENT));
		sheet.getRow(SCHEDULE_YEARLY_RENT_GST_INDEX.getFirst()).getCell(SCHEDULE_YEARLY_RENT_GST_INDEX.getSecond())
				.setCellValue(lease.yearlyGst(RENT));
		sheet.getRow(SCHEDULE_MONTHLY_RENT_INDEX.getFirst()).getCell(SCHEDULE_MONTHLY_RENT_INDEX.getSecond())
				.setCellValue(lease.monthlyAmount(RENT));
		sheet.getRow(SCHEDULE_MONTHLY_RENT_GST_INDEX.getFirst()).getCell(SCHEDULE_MONTHLY_RENT_GST_INDEX.getSecond())
				.setCellValue(lease.monthlyGst(RENT));
		sheet.getRow(SCHEDULE_YEARLY_OUTGOINGS_INDEX.getFirst()).getCell(SCHEDULE_YEARLY_OUTGOINGS_INDEX.getSecond())
				.setCellValue(lease.yearlyAmount(OUTGOINGS));
		sheet.getRow(SCHEDULE_YEARLY_OUTGOINGS_GST_INDEX.getFirst())
				.getCell(SCHEDULE_YEARLY_OUTGOINGS_GST_INDEX.getSecond()).setCellValue(lease.yearlyGst(OUTGOINGS));
		sheet.getRow(SCHEDULE_MONTHLY_OUTGOINGS_INDEX.getFirst()).getCell(SCHEDULE_MONTHLY_OUTGOINGS_INDEX.getSecond())
				.setCellValue(lease.monthlyAmount(OUTGOINGS));
		sheet.getRow(SCHEDULE_MONTHLY_OUTGOINGS_GST_INDEX.getFirst())
				.getCell(SCHEDULE_MONTHLY_OUTGOINGS_GST_INDEX.getSecond()).setCellValue(lease.monthlyGst(OUTGOINGS));
		sheet.getRow(SCHEDULE_YEARLY_PARKING_INDEX.getFirst()).getCell(SCHEDULE_YEARLY_PARKING_INDEX.getSecond())
				.setCellValue(lease.yearlyAmount(PARKING));
		sheet.getRow(SCHEDULE_YEARLY_PARKING_GST_INDEX.getFirst())
				.getCell(SCHEDULE_YEARLY_PARKING_GST_INDEX.getSecond()).setCellValue(lease.yearlyGst(PARKING));
		sheet.getRow(SCHEDULE_MONTHLY_PARKING_INDEX.getFirst()).getCell(SCHEDULE_MONTHLY_PARKING_INDEX.getSecond())
				.setCellValue(lease.monthlyAmount(PARKING));
		sheet.getRow(SCHEDULE_MONTHLY_PARKING_GST_INDEX.getFirst())
				.getCell(SCHEDULE_MONTHLY_PARKING_GST_INDEX.getSecond()).setCellValue(lease.monthlyGst(PARKING));
		sheet.getRow(SCHEDULE_YEARLY_SIGNAGE_INDEX.getFirst()).getCell(SCHEDULE_YEARLY_SIGNAGE_INDEX.getSecond())
				.setCellValue(lease.yearlyAmount(SIGNAGE));
		sheet.getRow(SCHEDULE_YEARLY_SIGNAGE_GST_INDEX.getFirst())
				.getCell(SCHEDULE_YEARLY_SIGNAGE_GST_INDEX.getSecond()).setCellValue(lease.yearlyGst(SIGNAGE));
		sheet.getRow(SCHEDULE_MONTHLY_SIGNAGE_INDEX.getFirst()).getCell(SCHEDULE_MONTHLY_SIGNAGE_INDEX.getSecond())
				.setCellValue(lease.monthlyAmount(SIGNAGE));
		sheet.getRow(SCHEDULE_MONTHLY_SIGNAGE_GST_INDEX.getFirst())
				.getCell(SCHEDULE_MONTHLY_SIGNAGE_GST_INDEX.getSecond()).setCellValue(lease.monthlyGst(SIGNAGE));
		sheet.getRow(SCHEDULE_LEASE_START_INDEX.getFirst()).getCell(SCHEDULE_LEASE_START_INDEX.getSecond())
				.setCellValue(lease.getLeaseStart());
		sheet.getRow(SCHEDULE_LEASE_END_INDEX.getFirst()).getCell(SCHEDULE_LEASE_END_INDEX.getSecond())
				.setCellValue(lease.getLeaseEnd());
		sheet.getRow(SCHEDULE_RENT_FREE_PERIOD_INDEX.getFirst()).getCell(SCHEDULE_RENT_FREE_PERIOD_INDEX.getSecond())
				.setCellValue("TODO");
		sheet.getRow(SCHEDULE_RENT_INCREASE_INDEX.getFirst()).getCell(SCHEDULE_RENT_INCREASE_INDEX.getSecond())
				.setCellValue(resolveRentPercentage(lease));
		sheet.getRow(SCHEDULE_LEASE_OPTION_START_INDEX.getFirst())
				.getCell(SCHEDULE_LEASE_OPTION_START_INDEX.getSecond()).setCellValue(resolveOptionStart(lease));
		sheet.getRow(SCHEDULE_LEASE_OPTION_END_INDEX.getFirst()).getCell(SCHEDULE_LEASE_OPTION_END_INDEX.getSecond())
				.setCellValue(resolveOptionEnd(lease));
		sheet.getRow(SCHEDULE_OUTGOINGS_PRECENT_INDEX.getFirst()).getCell(SCHEDULE_OUTGOINGS_PRECENT_INDEX.getSecond())
				.setCellValue("100%");
		double bondAmount = (lease.getBond() != null) ? lease.getBond().getAmount() : ZERO_DBL;
		sheet.getRow(SCHEDULE_BOND_BANK_GUARANTEE_INDEX.getFirst())
				.getCell(SCHEDULE_BOND_BANK_GUARANTEE_INDEX.getSecond()).setCellValue(bondAmount);
		String area = (lease.getLeasedArea() == null) ? EMPTY_STRING : lease.getLeasedArea() + "sqm";
		sheet.getRow(SCHEDULE_AREA_INDEX.getFirst()).getCell(SCHEDULE_AREA_INDEX.getSecond()).setCellValue(area);

		resolveMetaData(lease, sheet, HISTORICAL_RENT, HISTORIC_RENTS_START_INDEX);
		resolveMetaData(lease, sheet, NOTICE, NOTICES_START_INDEX);
		resolveMetaData(lease, sheet, SPECIAL_CONDITION, SPECIAL_CONDITIONS_START_INDEX);
		resolveMetaData(lease, sheet, OPTION, OPTIONS_START_INDEX);

		HSSFFormulaEvaluator.evaluateAllFormulaCells(book);
	}

	private void resolveMetaData(final EzeeLease lease, final Sheet sheet, final String type,
			final EzeePair<Integer, Integer> metaDataIndex) {
		List<EzeeLeaseMetaData> metaData = lease.getMetaData(type);
		if (!isEmpty(metaData)) {
			sort(metaData);
			int row = metaDataIndex.getFirst();
			int columnTypeIndex = metaDataIndex.getSecond();
			int columnValueIndex = columnTypeIndex + ONE;
			for (EzeeLeaseMetaData md : metaData) {
				sheet.getRow(row).getCell(columnTypeIndex).setCellValue(md.getDescription());
				sheet.getRow(row).getCell(columnValueIndex).setCellValue(md.getValue());
				row++;
			}
		}
	}

	private String resolveTitleLineOne(final EzeeLease lease) {
		return lease.getCategory().getName() + " - RENT SCHEDULE";
	}

	private String resolveTitleLineTwo(final EzeeLease lease) {
		return "UNIT " + lease.getLeasedUnits() + " - " + lease.getTenant().getName() + " @ " + lease.getUpdated();
	}

	private String resolveAddress(final EzeeLease lease) {
		return ("UNIT " + lease.getLeasedUnits() + "/" + lease.getPremises().getAddressLineOne() + ", "
				+ lease.getPremises().getAddressLineTwo()).toUpperCase();
	}

	private String resolveOptionStart(final EzeeLease lease) {
		return lease.getOptionStartDate() == null ? "None" : lease.getOptionStartDate();
	}

	private String resolveOptionEnd(final EzeeLease lease) {
		return lease.getOptionEndDate() == null ? "None" : lease.getOptionEndDate();
	}

	private double resolveRentPercentage(final EzeeLease lease) {
		EzeeLeaseIncidental incidental = lease.getIncidental(RENT);
		if (incidental != null) {
			return incidental.getPercentage();
		}
		return ZERO_DBL;
	}
}