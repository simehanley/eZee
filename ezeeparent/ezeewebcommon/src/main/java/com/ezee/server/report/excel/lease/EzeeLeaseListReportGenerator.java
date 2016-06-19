package com.ezee.server.report.excel.lease;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.common.EzeeCommonConstants.TWO;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.ANNUAL_OUTGOINGS_GST;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.ANNUAL_PARKING_GST;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.ANNUAL_RENT_GST;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.ANNUAL_SIGNAGE_GST;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.ANNUAL_TOTAL_GST;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.AREA;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.CATEGORY;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.END_DATE;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.GROSS_ANNUAL_OUTGOINGS;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.GROSS_ANNUAL_PARKING;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.GROSS_ANNUAL_RENT;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.GROSS_ANNUAL_SIGNAGE;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.GROSS_ANNUAL_TOTAL;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.GROSS_MONTHLY_OUTGOINGS;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.GROSS_MONTHLY_PARKING;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.GROSS_MONTHLY_RENT;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.GROSS_MONTHLY_SIGNAGE;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.GROSS_MONTHLY_TOTAL;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.MONTHLY_OUTGOINGS_GST;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.MONTHLY_PARKING_GST;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.MONTHLY_RENT_GST;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.MONTHLY_SIGNAGE_GST;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.MONTHLY_TOTAL_GST;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.NET_ANNUAL_OUTGOINGS;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.NET_ANNUAL_PARKING;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.NET_ANNUAL_RENT;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.NET_ANNUAL_SIGNAGE;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.NET_ANNUAL_TOTAL;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.NET_MONTHLY_OUTGOINGS;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.NET_MONTHLY_PARKING;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.NET_MONTHLY_RENT;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.NET_MONTHLY_SIGNAGE;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.NET_MONTHLY_TOTAL;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.OUTGOINGS;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.PARKING;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.RENT;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.SHOW_INACTIVE;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.SIGNAGE;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.START_DATE;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.TENANT;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.UNITS;
import static com.ezee.server.report.excel.lease.EzeeLeaseReportConstants.UPDATE_DATE;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.dao.EzeeLeaseDao;
import com.ezee.model.entity.filter.lease.EzeeLeaseFilter;
import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.model.entity.lease.EzeeLeaseCollection;
import com.ezee.server.report.EzeeReportGenerator;
import com.ezee.server.report.excel.AbstractExcelReportGenerator;

public class EzeeLeaseListReportGenerator extends AbstractExcelReportGenerator implements EzeeReportGenerator {

	private static final Logger log = LoggerFactory.getLogger(EzeeLeaseListReportGenerator.class);

	@Autowired
	private EzeeLeaseDao dao;

	@Override
	public void generateReport(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			generateExcelLeaseReport(request, response);
		} catch (IOException e) {
			log.error("Error generating lease excel list report.", e);
		}
	}

	private void generateExcelLeaseReport(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		String tenant = request.getParameter(TENANT);
		String premises = request.getParameter(PREMISES);
		String category = request.getParameter(CATEGORY);
		boolean showInactive = Boolean.valueOf(request.getParameter(SHOW_INACTIVE));
		EzeeLeaseFilter filter = new EzeeLeaseFilter(tenant, premises, category, showInactive);
		List<EzeeLease> leases = dao.get(filter);
		if (!isEmpty(leases)) {
			Map<String, Map<String, List<EzeeLease>>> leaseByCategoryByPremises = new HashMap<String, Map<String, List<EzeeLease>>>();
			for (EzeeLease lease : leases) {
				String leaseCategory = lease.getCategory().getName();
				if (!leaseByCategoryByPremises.containsKey(leaseCategory)) {
					leaseByCategoryByPremises.put(leaseCategory, new HashMap<String, List<EzeeLease>>());
				}
				Map<String, List<EzeeLease>> leaseByCategory = leaseByCategoryByPremises.get(leaseCategory);
				String leasePremises = lease.getPremises().getAddressLineOne();
				if (!leaseByCategory.containsKey(leasePremises)) {
					leaseByCategory.put(leasePremises, new ArrayList<EzeeLease>());
				}
				leaseByCategory.get(leasePremises).add(lease);
			}
			generateExcelLeaseReport(leaseByCategoryByPremises, response);
		}
	}

	private void generateExcelLeaseReport(final Map<String, Map<String, List<EzeeLease>>> leases,
			final HttpServletResponse response) throws IOException {
		String filename = UUID.randomUUID().toString() + ".xls";
		OutputStream stream = createExcelFilenameResponseHeader(response, filename);
		byte[] excelLeaseReport = generateExcelLeaseReport(leases);
		response.setContentLength((int) excelLeaseReport.length);
		stream.write(excelLeaseReport);
		stream.close();
	}

	private byte[] generateExcelLeaseReport(final Map<String, Map<String, List<EzeeLease>>> leases) throws IOException {
		Workbook book = new HSSFWorkbook();
		Sheet sheet = book.createSheet("leases");
		generateExcelLeaseReportHeader(book, sheet);
		generateExcelLeaseReportContent(leases, book, sheet);
		formatSheet(sheet);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		book.write(stream);
		stream.close();
		book.close();
		return stream.toByteArray();
	}

	private void generateExcelLeaseReportHeader(final Workbook book, final Sheet sheet) {
		Row header = sheet.createRow(LEASE_EXCEL_REPORT_HEADER_ROW);
		createLeaseMetaDataHeader(new int[] { LEASE_EXCEL_REPORT_TENANT_INDEX, LEASE_EXCEL_REPORT_UNIT_INDEX,
				LEASE_EXCEL_REPORT_AREA_INDEX }, new String[] { TENANT, UNITS, AREA }, book, header);
		createLeaseDateHeader(
				new int[] { LEASE_EXCEL_REPORT_START_INDEX, LEASE_EXCEL_REPORT_END_INDEX,
						LEASE_EXCEL_REPORT_UPDATE_INDEX },
				new String[] { START_DATE, END_DATE, UPDATE_DATE }, book, header);
		createLeaseIncomeHeader(
				new int[] { LEASE_EXCEL_REPORT_ANNUAL_RENT_INDEX, LEASE_EXCEL_REPORT_ANNUAL_RENT_GST_INDEX,
						LEASE_EXCEL_REPORT_ANNUAL_GROSS_RENT_INDEX, LEASE_EXCEL_REPORT_MONTHLY_RENT_INDEX,
						LEASE_EXCEL_REPORT_MONTHLY_RENT_GST_INDEX, LEASE_EXCEL_REPORT_MONTHLY_GROSS_RENT_INDEX },
				new String[] { NET_ANNUAL_RENT, ANNUAL_RENT_GST, GROSS_ANNUAL_RENT, NET_MONTHLY_RENT, MONTHLY_RENT_GST,
						GROSS_MONTHLY_RENT },
				book, header);
		createLeaseIncomeHeader(
				new int[] { LEASE_EXCEL_REPORT_ANNUAL_OUTGOINGS_INDEX, LEASE_EXCEL_REPORT_ANNUAL_OUTGOINGS_GST_INDEX,
						LEASE_EXCEL_REPORT_ANNUAL_GROSS_OUTGOINGS_INDEX, LEASE_EXCEL_REPORT_MONTHLY_OUTGOINGS_INDEX,
						LEASE_EXCEL_REPORT_MONTHLY_OUTGOINGS_GST_INDEX,
						LEASE_EXCEL_REPORT_MONTHLY_GROSS_OUTGOINGS_INDEX },
				new String[] { NET_ANNUAL_OUTGOINGS, ANNUAL_OUTGOINGS_GST, GROSS_ANNUAL_OUTGOINGS,
						NET_MONTHLY_OUTGOINGS, MONTHLY_OUTGOINGS_GST, GROSS_MONTHLY_OUTGOINGS },
				book, header);
		createLeaseIncomeHeader(
				new int[] { LEASE_EXCEL_REPORT_ANNUAL_PARKING_INDEX, LEASE_EXCEL_REPORT_ANNUAL_PARKING_GST_INDEX,
						LEASE_EXCEL_REPORT_ANNUAL_GROSS_PARKING_INDEX, LEASE_EXCEL_REPORT_MONTHLY_PARKING_INDEX,
						LEASE_EXCEL_REPORT_MONTHLY_PARKING_GST_INDEX, LEASE_EXCEL_REPORT_MONTHLY_GROSS_PARKING_INDEX },
				new String[] { NET_ANNUAL_PARKING, ANNUAL_PARKING_GST, GROSS_ANNUAL_PARKING, NET_MONTHLY_PARKING,
						MONTHLY_PARKING_GST, GROSS_MONTHLY_PARKING },
				book, header);
		createLeaseIncomeHeader(
				new int[] { LEASE_EXCEL_REPORT_ANNUAL_SIGNAGE_INDEX, LEASE_EXCEL_REPORT_ANNUAL_SIGNAGE_GST_INDEX,
						LEASE_EXCEL_REPORT_ANNUAL_GROSS_SIGNAGE_INDEX, LEASE_EXCEL_REPORT_MONTHLY_SIGNAGE_INDEX,
						LEASE_EXCEL_REPORT_MONTHLY_SIGNAGE_GST_INDEX, LEASE_EXCEL_REPORT_MONTHLY_GROSS_SIGNAGE_INDEX },
				new String[] { NET_ANNUAL_SIGNAGE, ANNUAL_SIGNAGE_GST, GROSS_ANNUAL_SIGNAGE, NET_MONTHLY_SIGNAGE,
						MONTHLY_SIGNAGE_GST, GROSS_MONTHLY_SIGNAGE },
				book, header);
		createLeaseIncomeHeader(
				new int[] { LEASE_EXCEL_REPORT_ANNUAL_TOTAL_INDEX, LEASE_EXCEL_REPORT_ANNUAL_TOTAL_GST_INDEX,
						LEASE_EXCEL_REPORT_ANNUAL_GROSS_TOTAL_INDEX, LEASE_EXCEL_REPORT_MONTHLY_TOTAL_INDEX,
						LEASE_EXCEL_REPORT_MONTHLY_TOTAL_GST_INDEX, LEASE_EXCEL_REPORT_MONTHLY_GROSS_TOTAL_INDEX },
				new String[] { NET_ANNUAL_TOTAL, ANNUAL_TOTAL_GST, GROSS_ANNUAL_TOTAL, NET_MONTHLY_TOTAL,
						MONTHLY_TOTAL_GST, GROSS_MONTHLY_TOTAL },
				book, header);
	}

	private void generateExcelLeaseReportContent(final Map<String, Map<String, List<EzeeLease>>> leases,
			final Workbook book, final Sheet sheet) {
		int currentRow = ONE;
		EzeeLeaseCollection aggregateLeases = new EzeeLeaseCollection();
		for (String category : leases.keySet()) {
			EzeeLeaseCollection categoryLeases = new EzeeLeaseCollection();
			Map<String, List<EzeeLease>> leaseByCategory = leases.get(category);
			for (String premises : leaseByCategory.keySet()) {
				List<EzeeLease> leaseByPremises = leaseByCategory.get(premises);
				currentRow = generateExcelLeaseReportContent(leaseByPremises, book, sheet, currentRow);
				currentRow++;
				categoryLeases.addAll(leaseByPremises);
				aggregateLeases.addAll(leaseByPremises);
				generateSubTotal(premises, new EzeeLeaseCollection(leaseByPremises), book, sheet, currentRow, ZERO);
				currentRow += TWO;
			}
			generateSubTotal(category, categoryLeases, book, sheet, currentRow, ZERO);
			currentRow += TWO;
		}
		generateSubTotal(LEASE_EXCEL_GRAND_TOTAL, aggregateLeases, book, sheet, currentRow, ZERO);
	}

	private int generateExcelLeaseReportContent(final List<EzeeLease> leases, final Workbook book, final Sheet sheet,
			int currentRow) {
		for (EzeeLease lease : leases) {
			Row row = sheet.createRow(currentRow);
			appendLeaseMetaData(new int[] { LEASE_EXCEL_REPORT_TENANT_INDEX, LEASE_EXCEL_REPORT_UNIT_INDEX,
					LEASE_EXCEL_REPORT_AREA_INDEX }, book, row, lease);
			appendDates(book, new int[] { LEASE_EXCEL_REPORT_START_INDEX, LEASE_EXCEL_REPORT_END_INDEX,
					LEASE_EXCEL_REPORT_UPDATE_INDEX }, row, lease);
			appendIncome(RENT, book,
					new int[] { LEASE_EXCEL_REPORT_ANNUAL_RENT_INDEX, LEASE_EXCEL_REPORT_ANNUAL_RENT_GST_INDEX,
							LEASE_EXCEL_REPORT_ANNUAL_GROSS_RENT_INDEX, LEASE_EXCEL_REPORT_MONTHLY_RENT_INDEX,
							LEASE_EXCEL_REPORT_MONTHLY_RENT_GST_INDEX, LEASE_EXCEL_REPORT_MONTHLY_GROSS_RENT_INDEX },
					row, lease);
			appendIncome(OUTGOINGS, book,
					new int[] { LEASE_EXCEL_REPORT_ANNUAL_OUTGOINGS_INDEX,
							LEASE_EXCEL_REPORT_ANNUAL_OUTGOINGS_GST_INDEX,
							LEASE_EXCEL_REPORT_ANNUAL_GROSS_OUTGOINGS_INDEX, LEASE_EXCEL_REPORT_MONTHLY_OUTGOINGS_INDEX,
							LEASE_EXCEL_REPORT_MONTHLY_OUTGOINGS_GST_INDEX,
							LEASE_EXCEL_REPORT_MONTHLY_GROSS_OUTGOINGS_INDEX },
					row, lease);
			appendIncome(PARKING, book,
					new int[] { LEASE_EXCEL_REPORT_ANNUAL_PARKING_INDEX, LEASE_EXCEL_REPORT_ANNUAL_PARKING_GST_INDEX,
							LEASE_EXCEL_REPORT_ANNUAL_GROSS_PARKING_INDEX, LEASE_EXCEL_REPORT_MONTHLY_PARKING_INDEX,
							LEASE_EXCEL_REPORT_MONTHLY_PARKING_GST_INDEX,
							LEASE_EXCEL_REPORT_MONTHLY_GROSS_PARKING_INDEX },
					row, lease);
			appendIncome(SIGNAGE, book,
					new int[] { LEASE_EXCEL_REPORT_ANNUAL_SIGNAGE_INDEX, LEASE_EXCEL_REPORT_ANNUAL_SIGNAGE_GST_INDEX,
							LEASE_EXCEL_REPORT_ANNUAL_GROSS_SIGNAGE_INDEX, LEASE_EXCEL_REPORT_MONTHLY_SIGNAGE_INDEX,
							LEASE_EXCEL_REPORT_MONTHLY_SIGNAGE_GST_INDEX,
							LEASE_EXCEL_REPORT_MONTHLY_GROSS_SIGNAGE_INDEX },
					row, lease);
			appendIncome(TOTAL, book,
					new int[] { LEASE_EXCEL_REPORT_ANNUAL_TOTAL_INDEX, LEASE_EXCEL_REPORT_ANNUAL_TOTAL_GST_INDEX,
							LEASE_EXCEL_REPORT_ANNUAL_GROSS_TOTAL_INDEX, LEASE_EXCEL_REPORT_MONTHLY_TOTAL_INDEX,
							LEASE_EXCEL_REPORT_MONTHLY_TOTAL_GST_INDEX, LEASE_EXCEL_REPORT_MONTHLY_GROSS_TOTAL_INDEX },
					row, lease);
			currentRow++;
		}
		return currentRow;
	}

	private void generateSubTotal(final String name, final EzeeLeaseCollection leaseCollection, final Workbook book,
			final Sheet sheet, final int currentRow, final int columnIndex) {
		Row subtotalRow = sheet.createRow(currentRow);
		Cell categorySubtotal = subtotalRow.createCell(columnIndex, CELL_TYPE_STRING);
		categorySubtotal.setCellStyle(boldStyle(book, false, false));
		if (LEASE_EXCEL_GRAND_TOTAL.equals(name)) {
			categorySubtotal.setCellValue(name);
		} else {
			categorySubtotal.setCellValue(name + " Sub Total");
		}
		appendTotal(RENT, book,
				new int[] { LEASE_EXCEL_REPORT_ANNUAL_RENT_INDEX, LEASE_EXCEL_REPORT_ANNUAL_RENT_GST_INDEX,
						LEASE_EXCEL_REPORT_ANNUAL_GROSS_RENT_INDEX, LEASE_EXCEL_REPORT_MONTHLY_RENT_INDEX,
						LEASE_EXCEL_REPORT_MONTHLY_RENT_GST_INDEX, LEASE_EXCEL_REPORT_MONTHLY_GROSS_RENT_INDEX },
				subtotalRow, leaseCollection);
		appendTotal(OUTGOINGS, book,
				new int[] { LEASE_EXCEL_REPORT_ANNUAL_OUTGOINGS_INDEX, LEASE_EXCEL_REPORT_ANNUAL_OUTGOINGS_GST_INDEX,
						LEASE_EXCEL_REPORT_ANNUAL_GROSS_OUTGOINGS_INDEX, LEASE_EXCEL_REPORT_MONTHLY_OUTGOINGS_INDEX,
						LEASE_EXCEL_REPORT_MONTHLY_OUTGOINGS_GST_INDEX,
						LEASE_EXCEL_REPORT_MONTHLY_GROSS_OUTGOINGS_INDEX },
				subtotalRow, leaseCollection);
		appendTotal(PARKING, book,
				new int[] { LEASE_EXCEL_REPORT_ANNUAL_PARKING_INDEX, LEASE_EXCEL_REPORT_ANNUAL_PARKING_GST_INDEX,
						LEASE_EXCEL_REPORT_ANNUAL_GROSS_PARKING_INDEX, LEASE_EXCEL_REPORT_MONTHLY_PARKING_INDEX,
						LEASE_EXCEL_REPORT_MONTHLY_PARKING_GST_INDEX, LEASE_EXCEL_REPORT_MONTHLY_GROSS_PARKING_INDEX },
				subtotalRow, leaseCollection);
		appendTotal(SIGNAGE, book,
				new int[] { LEASE_EXCEL_REPORT_ANNUAL_SIGNAGE_INDEX, LEASE_EXCEL_REPORT_ANNUAL_SIGNAGE_GST_INDEX,
						LEASE_EXCEL_REPORT_ANNUAL_GROSS_SIGNAGE_INDEX, LEASE_EXCEL_REPORT_MONTHLY_SIGNAGE_INDEX,
						LEASE_EXCEL_REPORT_MONTHLY_SIGNAGE_GST_INDEX, LEASE_EXCEL_REPORT_MONTHLY_GROSS_SIGNAGE_INDEX },
				subtotalRow, leaseCollection);
		appendTotal(TOTAL, book,
				new int[] { LEASE_EXCEL_REPORT_ANNUAL_TOTAL_INDEX, LEASE_EXCEL_REPORT_ANNUAL_TOTAL_GST_INDEX,
						LEASE_EXCEL_REPORT_ANNUAL_GROSS_TOTAL_INDEX, LEASE_EXCEL_REPORT_MONTHLY_TOTAL_INDEX,
						LEASE_EXCEL_REPORT_MONTHLY_TOTAL_GST_INDEX, LEASE_EXCEL_REPORT_MONTHLY_GROSS_TOTAL_INDEX },
				subtotalRow, leaseCollection);
	}

	private void createLeaseMetaDataHeader(final int[] indices, final String[] values, final Workbook book,
			final Row row) {
		CellStyle boldStyle = headerStyle(book, true);
		Cell tenant = row.createCell(indices[0], CELL_TYPE_STRING);
		tenant.setCellValue(values[0]);
		tenant.setCellStyle(boldStyle);
		Cell unit = row.createCell(indices[1], CELL_TYPE_STRING);
		unit.setCellValue(values[1]);
		unit.setCellStyle(boldStyle);
		Cell area = row.createCell(indices[2], CELL_TYPE_STRING);
		area.setCellValue(values[2]);
		area.setCellStyle(boldStyle);
	}

	private void createLeaseDateHeader(final int[] indices, final String[] values, final Workbook book, final Row row) {
		CellStyle boldStyle = headerStyle(book, true);
		Cell start = row.createCell(indices[0], CELL_TYPE_STRING);
		start.setCellValue(values[0]);
		start.setCellStyle(boldStyle);
		Cell end = row.createCell(indices[1], CELL_TYPE_STRING);
		end.setCellValue(values[1]);
		end.setCellStyle(boldStyle);
		Cell update = row.createCell(indices[2], CELL_TYPE_STRING);
		update.setCellValue(values[2]);
		update.setCellStyle(boldStyle);
	}

	private void createLeaseIncomeHeader(final int[] indices, final String[] values, final Workbook book,
			final Row row) {
		CellStyle boldStyle = headerStyle(book, false);
		Cell netAnnual = row.createCell(indices[0], CELL_TYPE_STRING);
		netAnnual.setCellValue(values[0]);
		netAnnual.setCellStyle(boldStyle);
		Cell annualGst = row.createCell(indices[1], CELL_TYPE_STRING);
		annualGst.setCellValue(values[1]);
		annualGst.setCellStyle(boldStyle);
		Cell grossAnnual = row.createCell(indices[2], CELL_TYPE_STRING);
		grossAnnual.setCellValue(values[2]);
		grossAnnual.setCellStyle(boldStyle);
		Cell netMonthly = row.createCell(indices[3], CELL_TYPE_STRING);
		netMonthly.setCellValue(values[3]);
		netMonthly.setCellStyle(boldStyle);
		Cell monthlyGst = row.createCell(indices[4], CELL_TYPE_STRING);
		monthlyGst.setCellValue(values[4]);
		monthlyGst.setCellStyle(boldStyle);
		Cell grossMonthly = row.createCell(indices[5], CELL_TYPE_STRING);
		grossMonthly.setCellValue(values[5]);
		grossMonthly.setCellStyle(boldStyle);
	}

	private void appendLeaseMetaData(final int[] indices, final Workbook book, final Row row, final EzeeLease lease) {
		Cell tenant = row.createCell(indices[0], CELL_TYPE_STRING);
		tenant.setCellValue(lease.getTenant().getName());
		Cell unit = row.createCell(indices[1], CELL_TYPE_STRING);
		String unitString = (lease.getLeasedUnits() == null) ? EMPTY_STRING : lease.getLeasedUnits();
		unit.setCellStyle(formattedStyle(book, true, true));
		unit.setCellValue(unitString);
		Cell area = row.createCell(indices[2], CELL_TYPE_STRING);
		String areaString = (lease.getLeasedArea() == null) ? EMPTY_STRING : Double.toString(lease.getLeasedArea());
		area.setCellValue(areaString);
	}

	private void appendDates(final Workbook book, final int[] indices, final Row row, final EzeeLease lease) {
		CellStyle dateStyle = dateStyle(book, false, false);
		Cell start = row.createCell(indices[0], CELL_TYPE_NUMERIC);
		start.setCellValue(lease.getLeaseStart());
		start.setCellStyle(dateStyle);
		Cell end = row.createCell(indices[1], CELL_TYPE_NUMERIC);
		end.setCellValue(lease.getEffectiveLeaseEnd());
		end.setCellStyle(dateStyle);
		Cell update = row.createCell(indices[2], CELL_TYPE_NUMERIC);
		update.setCellValue(lease.getUpdated());
		update.setCellStyle(dateStyle);
	}

	private void appendIncome(final String type, final Workbook book, final int[] indices, final Row row,
			final EzeeLease lease) {
		CellStyle numericStyle = currencyStyle(book, false, false);
		Cell netAnnual = row.createCell(indices[0], CELL_TYPE_NUMERIC);
		netAnnual.setCellValue(lease.yearlyAmount(type));
		netAnnual.setCellStyle(numericStyle);
		Cell annualGst = row.createCell(indices[1], CELL_TYPE_NUMERIC);
		annualGst.setCellValue(lease.yearlyGst(type));
		annualGst.setCellStyle(numericStyle);
		Cell grossAnnual = row.createCell(indices[2], CELL_TYPE_NUMERIC);
		grossAnnual.setCellValue(lease.yearlyTotal(type));
		grossAnnual.setCellStyle(numericStyle);
		Cell netMonthly = row.createCell(indices[3], CELL_TYPE_NUMERIC);
		netMonthly.setCellValue(lease.monthlyAmount(type));
		netMonthly.setCellStyle(numericStyle);
		Cell monthlyGst = row.createCell(indices[4], CELL_TYPE_NUMERIC);
		monthlyGst.setCellValue(lease.monthlyGst(type));
		monthlyGst.setCellStyle(numericStyle);
		Cell grossMonthly = row.createCell(indices[5], CELL_TYPE_NUMERIC);
		grossMonthly.setCellValue(lease.monthlyTotal(type));
		grossMonthly.setCellStyle(numericStyle);
	}

	private void appendTotal(final String type, final Workbook book, final int[] indices, final Row row,
			final EzeeLeaseCollection leaseCollection) {
		CellStyle numericStyle = currencyStyle(book, true, false);
		Cell netAnnual = row.createCell(indices[0], CELL_TYPE_NUMERIC);
		netAnnual.setCellValue(leaseCollection.yearlyAmount(type));
		netAnnual.setCellStyle(numericStyle);
		Cell annualGst = row.createCell(indices[1], CELL_TYPE_NUMERIC);
		annualGst.setCellValue(leaseCollection.yearlyGst(type));
		annualGst.setCellStyle(numericStyle);
		Cell grossAnnual = row.createCell(indices[2], CELL_TYPE_NUMERIC);
		grossAnnual.setCellValue(leaseCollection.yearlyTotal(type));
		grossAnnual.setCellStyle(numericStyle);
		Cell netMonthly = row.createCell(indices[3], CELL_TYPE_NUMERIC);
		netMonthly.setCellValue(leaseCollection.monthlyAmount(type));
		netMonthly.setCellStyle(numericStyle);
		Cell monthlyGst = row.createCell(indices[4], CELL_TYPE_NUMERIC);
		monthlyGst.setCellValue(leaseCollection.monthlyGst(type));
		monthlyGst.setCellStyle(numericStyle);
		Cell grossMonthly = row.createCell(indices[5], CELL_TYPE_NUMERIC);
		grossMonthly.setCellValue(leaseCollection.monthlyTotal(type));
		grossMonthly.setCellStyle(numericStyle);
	}

	private void formatSheet(final Sheet sheet) {
		setColumnWidths(sheet);
		sheet.createFreezePane(LEASE_EXCEL_REPORT_UNIT_INDEX, ONE);
		setPrintSetup(sheet);
	}

	private void setColumnWidths(final Sheet sheet) {
		sheet.setColumnWidth(LEASE_EXCEL_REPORT_TENANT_INDEX, LEASE_EXCEL_REPORT_TENANT_WIDTH);
		sheet.setColumnWidth(LEASE_EXCEL_REPORT_UNIT_INDEX, LEASE_EXCEL_REPORT_UNIT_WIDTH);
		sheet.setColumnWidth(LEASE_EXCEL_REPORT_UNIT_INDEX, LEASE_EXCEL_REPORT_UNIT_WIDTH);
		sheet.setColumnWidth(LEASE_EXCEL_REPORT_AREA_INDEX, LEASE_EXCEL_REPORT_AREA_WIDTH);
		sheet.setColumnWidth(LEASE_EXCEL_REPORT_START_INDEX, LEASE_EXCEL_REPORT_DATE_WIDTH);
		sheet.setColumnWidth(LEASE_EXCEL_REPORT_END_INDEX, LEASE_EXCEL_REPORT_DATE_WIDTH);
		sheet.setColumnWidth(LEASE_EXCEL_REPORT_UPDATE_INDEX, LEASE_EXCEL_REPORT_DATE_WIDTH);
		for (int i = LEASE_EXCEL_REPORT_ANNUAL_RENT_INDEX; i <= LEASE_EXCEL_REPORT_MONTHLY_GROSS_TOTAL_INDEX; i++) {
			sheet.setColumnWidth(i, LEASE_EXCEL_REPORT_NUMBER_WIDTH);
		}
	}

	private void setPrintSetup(final Sheet sheet) {
		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setLandscape(true);
		printSetup.setPaperSize(HSSFPrintSetup.A3_PAPERSIZE);
		sheet.setAutobreaks(true);
		sheet.setFitToPage(true);
	}
}