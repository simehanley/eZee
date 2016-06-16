package com.ezee.server.report.excel.lease;

import static com.ezee.common.string.EzeeStringUtils.getStringValue;
import static com.ezee.server.EzeeServerDateUtils.SERVER_DATE_UTILS;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.LEASE_ID;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.OUTGOINGS;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.PARKING;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.RENT;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.SIGNAGE;
import static com.ezee.server.util.lease.EzeeLeaseDateUtils.formatFullDate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.dao.EzeeLeaseDao;
import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.server.report.EzeeReportGenerator;
import com.ezee.server.report.excel.AbstractExcelReportGenerator;
import com.ezee.server.util.lease.EzeeLeaseCurrentPeriodGenerator;
import com.ezee.web.common.datastructures.EzeePair;

public class EzeeLeaseInvoiceGenerator extends AbstractExcelReportGenerator implements EzeeReportGenerator {

	private static final Logger log = LoggerFactory.getLogger(EzeeLeaseInvoiceGenerator.class);

	private static final String INVOICE_TEMPLATE_NAME = "TAX_INVOICE_TEMPLATE.xls";

	@Autowired
	private EzeeLeaseCurrentPeriodGenerator generator;

	@Autowired
	private EzeeLeaseDao dao;

	@Override
	public void generateReport(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			generateExcelInvoice(request, response);
		} catch (IOException e) {
			log.error("Error generating lease excel invoice.", e);
		}
	}

	private void generateExcelInvoice(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		long leaseId = Long.valueOf(request.getParameter(LEASE_ID));
		EzeeLease lease = dao.get(leaseId, EzeeLease.class);
		if (lease != null) {
			generateExcelInvoice(lease, response);
		}
	}

	private void generateExcelInvoice(final EzeeLease lease, final HttpServletResponse response) throws IOException {
		String filename = UUID.randomUUID().toString() + ".xls";
		OutputStream stream = createExcelFilenameResponseHeader(response, filename);
		byte[] excelInvoice = generateExcelInvoice(lease, filename);
		if (excelInvoice != null) {
			response.setContentLength((int) excelInvoice.length);
			stream.write(excelInvoice);
		}
		stream.close();
	}

	private byte[] generateExcelInvoice(final EzeeLease lease, final String filename) throws IOException {
		Workbook book = new HSSFWorkbook(getClass().getResourceAsStream("/excel/" + INVOICE_TEMPLATE_NAME));
		Sheet sheet = book.getSheet("Yearly Invoice");
		try {
			if (sheet != null) {
				generateExcelInvoice(lease, book, sheet);
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

	private void generateExcelInvoice(final EzeeLease lease, final Workbook book, final Sheet sheet) {
		sheet.getRow(CATEGORY_COMPANY_INDEX.getFirst()).getCell(CATEGORY_COMPANY_INDEX.getSecond())
				.setCellValue(getStringValue(lease.getCategory().getCategoryCompany()));
		sheet.getRow(CATEGORY_ABN_INDEX.getFirst()).getCell(CATEGORY_ABN_INDEX.getSecond())
				.setCellValue(getStringValue(lease.getCategory().getAccountBsb()));
		sheet.getRow(CATEGORY_ADDRESS_INDEX.getFirst()).getCell(CATEGORY_ADDRESS_INDEX.getSecond())
				.setCellValue(getStringValue(lease.getCategory().getAddressLineOne()));
		sheet.getRow(CATEGORY_PHONE_INDEX.getFirst()).getCell(CATEGORY_PHONE_INDEX.getSecond())
				.setCellValue(getStringValue(lease.getCategory().getPhone()));
		sheet.getRow(CURRENT_DATE_INDEX.getFirst()).getCell(CURRENT_DATE_INDEX.getSecond())
				.setCellValue(SERVER_DATE_UTILS.fromString(lease.getUpdated()));
		sheet.getRow(TENANT_INDEX.getFirst()).getCell(TENANT_INDEX.getSecond())
				.setCellValue(lease.getTenant().getName());
		sheet.getRow(PREMISES_ADDRESS_1_INDEX.getFirst()).getCell(PREMISES_ADDRESS_1_INDEX.getSecond())
				.setCellValue(lease.getLeasedUnits() + "/" + lease.getPremises().getAddressLineOne());
		sheet.getRow(RENTAL_LINE_1_INDEX.getFirst()).getCell(RENTAL_LINE_1_INDEX.getSecond())
				.setCellValue(resolveRentalLineOne(lease));
		sheet.getRow(RENTAL_LINE_2_INDEX.getFirst()).getCell(RENTAL_LINE_2_INDEX.getSecond())
				.setCellValue(resolveRentalLineTwo(lease));
		sheet.getRow(PREMISES_ADDRESS_2_INDEX.getFirst()).getCell(PREMISES_ADDRESS_2_INDEX.getSecond())
				.setCellValue(lease.getPremises().getAddressLineTwo());
		sheet.getRow(YEARLY_RENT_INDEX.getFirst()).getCell(YEARLY_RENT_INDEX.getSecond())
				.setCellValue(lease.yearlyAmount(RENT));
		sheet.getRow(YEARLY_RENT_GST_INDEX.getFirst()).getCell(YEARLY_RENT_GST_INDEX.getSecond())
				.setCellValue(lease.yearlyGst(RENT));
		sheet.getRow(MONTHLY_RENT_INDEX.getFirst()).getCell(MONTHLY_RENT_INDEX.getSecond())
				.setCellValue(lease.monthlyAmount(RENT));
		sheet.getRow(MONTHLY_RENT_GST_INDEX.getFirst()).getCell(MONTHLY_RENT_GST_INDEX.getSecond())
				.setCellValue(lease.monthlyGst(RENT));
		sheet.getRow(YEARLY_OUTGOINGS_INDEX.getFirst()).getCell(YEARLY_OUTGOINGS_INDEX.getSecond())
				.setCellValue(lease.yearlyAmount(OUTGOINGS));
		sheet.getRow(YEARLY_OUTGOINGS_GST_INDEX.getFirst()).getCell(YEARLY_OUTGOINGS_GST_INDEX.getSecond())
				.setCellValue(lease.yearlyGst(OUTGOINGS));
		sheet.getRow(MONTHLY_OUTGOINGS_INDEX.getFirst()).getCell(MONTHLY_OUTGOINGS_INDEX.getSecond())
				.setCellValue(lease.monthlyAmount(OUTGOINGS));
		sheet.getRow(MONTHLY_OUTGOINGS_GST_INDEX.getFirst()).getCell(MONTHLY_OUTGOINGS_GST_INDEX.getSecond())
				.setCellValue(lease.monthlyGst(OUTGOINGS));
		sheet.getRow(YEARLY_PARKING_INDEX.getFirst()).getCell(YEARLY_PARKING_INDEX.getSecond())
				.setCellValue(lease.yearlyAmount(PARKING));
		sheet.getRow(YEARLY_PARKING_GST_INDEX.getFirst()).getCell(YEARLY_PARKING_GST_INDEX.getSecond())
				.setCellValue(lease.yearlyGst(PARKING));
		sheet.getRow(MONTHLY_PARKING_INDEX.getFirst()).getCell(MONTHLY_PARKING_INDEX.getSecond())
				.setCellValue(lease.monthlyAmount(PARKING));
		sheet.getRow(MONTHLY_PARKING_GST_INDEX.getFirst()).getCell(MONTHLY_PARKING_GST_INDEX.getSecond())
				.setCellValue(lease.monthlyGst(PARKING));
		sheet.getRow(YEARLY_SIGNAGE_INDEX.getFirst()).getCell(YEARLY_SIGNAGE_INDEX.getSecond())
				.setCellValue(lease.yearlyAmount(SIGNAGE));
		sheet.getRow(YEARLY_SIGNAGE_GST_INDEX.getFirst()).getCell(YEARLY_SIGNAGE_GST_INDEX.getSecond())
				.setCellValue(lease.yearlyGst(SIGNAGE));
		sheet.getRow(MONTHLY_SIGNAGE_INDEX.getFirst()).getCell(MONTHLY_SIGNAGE_INDEX.getSecond())
				.setCellValue(lease.monthlyAmount(SIGNAGE));
		sheet.getRow(MONTHLY_SIGNAGE_GST_INDEX.getFirst()).getCell(MONTHLY_SIGNAGE_GST_INDEX.getSecond())
				.setCellValue(lease.monthlyGst(SIGNAGE));
		sheet.getRow(BANK_ACCOUNT_NAME_INDEX.getFirst()).getCell(BANK_ACCOUNT_NAME_INDEX.getSecond())
				.setCellValue(lease.getCategory().getAccountName());
		sheet.getRow(BANK_ACCOUNT_LOCATION_INDEX.getFirst()).getCell(BANK_ACCOUNT_LOCATION_INDEX.getSecond())
				.setCellValue(lease.getCategory().getBank());
		sheet.getRow(BANK_ACCOUNT_BSB_INDEX.getFirst()).getCell(BANK_ACCOUNT_BSB_INDEX.getSecond())
				.setCellValue(lease.getCategory().getAccountBsb());
		sheet.getRow(BANK_ACCOUNT_NUMBER_INDEX.getFirst()).getCell(BANK_ACCOUNT_NUMBER_INDEX.getSecond())
				.setCellValue(lease.getCategory().getAccountNumber());
		HSSFFormulaEvaluator.evaluateAllFormulaCells(book);
	}

	private String resolveRentalLineOne(final EzeeLease lease) {
		StringBuilder builder = new StringBuilder("Rental for ");
		builder.append(lease.getLeasedUnits() + "/" + lease.getPremises().getAddressLineOne() + ", ");
		builder.append(lease.getPremises().getAddressLineTwo() + ".");
		return builder.toString();
	}

	private String resolveRentalLineTwo(final EzeeLease lease) {
		EzeePair<LocalDate, LocalDate> dates = generator.resolveCurrentPeriod(lease);
		StringBuilder builder = new StringBuilder(
				"For the period " + formatFullDate(dates.getFirst()) + "-" + formatFullDate(dates.getSecond()) + ".");
		return builder.toString();
	}
}