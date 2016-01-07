package com.ezee.server.report.excel.project;

import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.common.EzeeCommonConstants.PERCENT_MULTIPLIER;
import static com.ezee.common.EzeeCommonConstants.TWO;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.web.common.EzeeWebCommonConstants.EXCEL_PROJECT_ID;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.common.numeric.EzeeNumericUtils;
import com.ezee.dao.EzeeProjectDao;
import com.ezee.model.entity.project.EzeeProject;
import com.ezee.model.entity.project.EzeeProjectItem;
import com.ezee.model.entity.project.EzeeProjectItemDetail;
import com.ezee.model.entity.project.EzeeProjectPayment;
import com.ezee.server.report.EzeeReportGenerator;
import com.ezee.server.report.excel.AbstractExcelReportGenerator;

public class EzeeProjectReportGenerator extends AbstractExcelReportGenerator implements EzeeReportGenerator {

	private static final Logger log = LoggerFactory.getLogger(EzeeProjectReportGenerator.class);

	@Autowired
	private EzeeProjectDao dao;

	@Override
	public void generateReport(final HttpServletRequest request, final HttpServletResponse response) {

		try {
			generateEzeeProjectReport(request, response);
		} catch (IOException exception) {
			log.error("Error generating ezee project report.", exception);
		}
	}

	private void generateEzeeProjectReport(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		EzeeProject project = getProject(request);
		generateEzeeProjectReport(project, response);
	}

	private void generateEzeeProjectReport(final EzeeProject project, final HttpServletResponse response)
			throws IOException {
		String filename = project.getName() + ".xls";
		OutputStream stream = createExcelFilenameResponseHeader(response, filename);
		byte[] content = generateEzeeProjectReport(project);
		response.setContentLength(content.length);
		stream.write(content);
		stream.close();
	}

	private byte[] generateEzeeProjectReport(final EzeeProject project) throws IOException {
		Workbook book = new HSSFWorkbook();
		Sheet sheet = book.createSheet(project.getName());
		generateEzeeProjectReport(book, sheet, project);
		formatReport(sheet);
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		book.write(result);
		result.close();
		return result.toByteArray();
	}

	private void generateEzeeProjectReport(final Workbook book, final Sheet sheet, final EzeeProject project) {
		int currentRow = ZERO;
		generateEzeeProjectReportHeader(book, sheet, project, currentRow);
		++currentRow;
		generateEzeeProjectReportContent(book, sheet, project, currentRow);
		currentRow += TWO;
		for (EzeeProjectItem item : project.getItems()) {
			currentRow += generateEzeeProjectItemReport(book, sheet, item, currentRow);
		}
	}

	private void generateEzeeProjectReportHeader(final Workbook book, final Sheet sheet, final EzeeProject project,
			final int currentRow) {
		generateExcelReportHeader(book, sheet, PROJECT_HEADER_INDEXES, PROJECT_HEADER_FIELDS, currentRow);
	}

	private void generateEzeeProjectReportContent(final Workbook book, final Sheet sheet, final EzeeProject project,
			final int currentRow) {
		CellStyle currencyStyle = currencyStyle(book, true);
		CellStyle boldStyle = boldStyle(book, true);
		Row projectSummary = sheet.createRow(currentRow);
		Cell projectName = projectSummary.createCell(PROJECT_ITEM_NAME_INDEX, CELL_TYPE_STRING);
		projectName.setCellValue(project.getName());
		projectName.setCellStyle(boldStyle);
		Cell projectStart = projectSummary.createCell(PROJECT_START_INDEX, CELL_TYPE_STRING);
		projectStart.setCellValue(project.getStartDate());
		projectStart.setCellStyle(boldStyle);
		Cell projectEnd = projectSummary.createCell(PROJECT_END_INDEX, CELL_TYPE_STRING);
		projectEnd.setCellValue(project.getEndDate());
		projectStart.setCellStyle(boldStyle);
		Cell projectBudget = projectSummary.createCell(PROJECT_ITEM_BUDGET_INDEX + ONE, CELL_TYPE_NUMERIC);
		projectBudget.setCellValue(project.budgeted().getTotal());
		projectBudget.setCellStyle(currencyStyle);
		Cell projectActual = projectSummary.createCell(PROJECT_ITEM_ACTUAL_INDEX + ONE, CELL_TYPE_NUMERIC);
		projectActual.setCellValue(project.actual().getTotal());
		projectActual.setCellStyle(currencyStyle);
		Cell projectPaid = projectSummary.createCell(PROJECT_ITEM_PAID_INDEX + ONE, CELL_TYPE_NUMERIC);
		projectPaid.setCellValue(project.paid().getTotal());
		projectPaid.setCellStyle(currencyStyle);
		Cell projectBalance = projectSummary.createCell(PROJECT_ITEM_BALANCE_INDEX + ONE, CELL_TYPE_NUMERIC);
		projectBalance.setCellValue(project.balance().getTotal());
		projectBalance.setCellStyle(currencyStyle);
		Cell projectPercentComplete = projectSummary.createCell(PROJECT_ITEM_COMPLETE_INDEX + ONE, CELL_TYPE_STRING);
		double percent = EzeeNumericUtils.round(project.percent() * PERCENT_MULTIPLIER);
		projectPercentComplete.setCellValue(percent + "%");
		projectPercentComplete.setCellStyle(boldStyle);
	}

	private int generateEzeeProjectItemReport(final Workbook book, final Sheet sheet, final EzeeProjectItem item,
			final int currentRow) {
		int row = currentRow;
		generateEzeeProjectItemReportHeader(book, sheet, item, row);
		++row;
		generateEzeeProjectItemReportContent(book, sheet, item, row);
		++row;
		if (!isEmpty(item.getDetails())) {
			++row;
			generateEzeeProjectItemDetailHeader(book, sheet, row);
			for (EzeeProjectItemDetail detail : item.getDetails()) {
				++row;
				generateEzeeProjectItemDetailContent(book, sheet, row, detail);
			}
			++row;
		}
		if (!isEmpty(item.getPayments())) {
			++row;
			generateEzeeProjectPaymentHeader(book, sheet, row);
			for (EzeeProjectPayment payment : item.getPayments()) {
				++row;
				generateEzeeProjectPaymentContent(book, sheet, row, payment);
			}
			++row;
		}
		return (row - currentRow) + ONE;
	}

	private void generateEzeeProjectItemReportHeader(final Workbook book, final Sheet sheet, final EzeeProjectItem item,
			final int currentRow) {
		generateExcelReportHeader(book, sheet, PROJECT_ITEM_HEADER_INDEXES, PROJECT_ITEM_HEADER_FIELDS, currentRow);
	}

	private void generateEzeeProjectItemReportContent(final Workbook book, final Sheet sheet,
			final EzeeProjectItem item, final int currentRow) {
		CellStyle currencyStyle = currencyStyle(book, true);
		CellStyle boldStyle = boldStyle(book, true);
		Row itemSummary = sheet.createRow(currentRow);
		Cell itemName = itemSummary.createCell(PROJECT_ITEM_NAME_INDEX, CELL_TYPE_STRING);
		itemName.setCellValue(item.getName());
		Cell itemContractor = itemSummary.createCell(PROJECT_ITEM_CONTRACTOR_INDEX, CELL_TYPE_STRING);
		itemContractor.setCellValue(item.getContractor().getName());
		Cell itemBudget = itemSummary.createCell(PROJECT_ITEM_BUDGET_INDEX, CELL_TYPE_NUMERIC);
		itemBudget.setCellValue(item.budgeted().getTotal());
		itemBudget.setCellStyle(currencyStyle);
		Cell itemActual = itemSummary.createCell(PROJECT_ITEM_ACTUAL_INDEX, CELL_TYPE_NUMERIC);
		itemActual.setCellValue(item.actual().getTotal());
		itemActual.setCellStyle(currencyStyle);
		Cell itemPaid = itemSummary.createCell(PROJECT_ITEM_PAID_INDEX, CELL_TYPE_NUMERIC);
		itemPaid.setCellValue(item.paid().getTotal());
		itemPaid.setCellStyle(currencyStyle);
		Cell itemBalance = itemSummary.createCell(PROJECT_ITEM_BALANCE_INDEX, CELL_TYPE_NUMERIC);
		itemBalance.setCellValue(item.balance().getTotal());
		itemBalance.setCellStyle(currencyStyle);
		Cell itemPercentComplete = itemSummary.createCell(PROJECT_ITEM_COMPLETE_INDEX, CELL_TYPE_STRING);
		double percent = EzeeNumericUtils.round(item.percent() * PERCENT_MULTIPLIER);
		itemPercentComplete.setCellValue(percent + "%");
		itemPercentComplete.setCellStyle(boldStyle);
	}

	private void generateEzeeProjectItemDetailHeader(final Workbook book, final Sheet sheet, final int currentRow) {
		generateExcelReportHeader(book, sheet, PROJECT_ITEM_DETAIL_HEADER_INDEXES, PROJECT_ITEM_DETAIL_HEADER_FIELDS,
				currentRow);
	}

	private void generateEzeeProjectItemDetailContent(final Workbook book, final Sheet sheet, final int currentRow,
			final EzeeProjectItemDetail detail) {
		CellStyle currencyStyle = currencyStyle(book, true);
		Row detailSummary = sheet.createRow(currentRow);
		Cell detailDesc = detailSummary.createCell(PROJECT_ITEM_DETAIL_DESC_INDEX, CELL_TYPE_STRING);
		detailDesc.setCellValue(detail.getDescription());
		Cell detailType = detailSummary.createCell(PROJECT_ITEM_DETAIL_TYPE_INDEX, CELL_TYPE_STRING);
		detailType.setCellValue(detail.getType().toString());
		Cell detailAmount = detailSummary.createCell(PROJECT_ITEM_DETAIL_AMOUNT_INDEX, CELL_TYPE_NUMERIC);
		detailAmount.setCellValue(detail.getAmount());
		detailAmount.setCellStyle(currencyStyle);
		Cell detailTax = detailSummary.createCell(PROJECT_ITEM_DETAIL_TAX_INDEX, CELL_TYPE_NUMERIC);
		detailTax.setCellValue(detail.getTax());
		detailTax.setCellStyle(currencyStyle);
		Cell detailTotal = detailSummary.createCell(PROJECT_ITEM_DETAIL_TOTAL_INDEX, CELL_TYPE_NUMERIC);
		detailTotal.setCellValue(detail.getTotal());
		detailTotal.setCellStyle(currencyStyle);
	}

	private void generateEzeeProjectPaymentHeader(final Workbook book, final Sheet sheet, final int currentRow) {
		generateExcelReportHeader(book, sheet, PROJECT_PMT_HEADER_INDEXES, PROJECT_PMT_HEADER_FIELDS, currentRow);
	}

	private void generateEzeeProjectPaymentContent(final Workbook book, final Sheet sheet, final int currentRow,
			final EzeeProjectPayment payment) {
		CellStyle currencyStyle = currencyStyle(book, true);
		CellStyle boldStyle = boldStyle(book, true);
		Row paymentSummary = sheet.createRow(currentRow);
		Cell paymentDesc = paymentSummary.createCell(PROJECT_ITEM_DETAIL_DESC_INDEX, CELL_TYPE_STRING);
		paymentDesc.setCellValue(payment.getDescription());
		Cell paymentType = paymentSummary.createCell(PROJECT_ITEM_DETAIL_TYPE_INDEX, CELL_TYPE_STRING);
		paymentType.setCellValue(payment.getType().toString());
		Cell paymentAmount = paymentSummary.createCell(PROJECT_ITEM_DETAIL_AMOUNT_INDEX, CELL_TYPE_NUMERIC);
		paymentAmount.setCellValue(payment.getAmount());
		paymentAmount.setCellStyle(currencyStyle);
		Cell paymentTax = paymentSummary.createCell(PROJECT_ITEM_DETAIL_TAX_INDEX, CELL_TYPE_NUMERIC);
		paymentTax.setCellValue(payment.getTax());
		paymentTax.setCellStyle(currencyStyle);
		Cell paymentTotal = paymentSummary.createCell(PROJECT_ITEM_DETAIL_TOTAL_INDEX, CELL_TYPE_NUMERIC);
		paymentTotal.setCellValue(payment.getTotal());
		paymentTotal.setCellStyle(currencyStyle);
		Cell paymentRef = paymentSummary.createCell(PROJECT_PMT_REF_INDEX, CELL_TYPE_NUMERIC);
		paymentRef.setCellValue(payment.getInvoiceRef());
		paymentRef.setCellStyle(boldStyle);
		Cell paymentDate = paymentSummary.createCell(PROJECT_PMT_DATE_INDEX, CELL_TYPE_NUMERIC);
		paymentDate.setCellValue(payment.getPaymentDate());
		paymentDate.setCellStyle(boldStyle);
	}

	private void formatReport(final Sheet sheet) {
		sheet.autoSizeColumn(0);
		sheet.setColumnWidth(1, 25500);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);
	}

	private EzeeProject getProject(final HttpServletRequest request) {
		Long projectId = Long.parseLong(request.getParameter(EXCEL_PROJECT_ID));
		return dao.get(projectId, EzeeProject.class);
	}
}