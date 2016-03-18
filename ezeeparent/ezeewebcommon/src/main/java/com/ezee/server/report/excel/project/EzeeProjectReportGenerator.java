package com.ezee.server.report.excel.project;

import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.common.EzeeCommonConstants.PERCENT_MULTIPLIER;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.model.entity.enums.EzeeProjectItemType.expense;
import static com.ezee.model.entity.util.EzeeDatabaseEntityUtils.sorted;
import static com.ezee.web.common.EzeeWebCommonConstants.EXCEL_PROJECT_ID;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

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

import com.ezee.common.collections.EzeeCollectionUtils;
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

	private static final String PROJECT_TEMPLATE_NAME = "project_template.xls";

	private static final int PROJECT_HEADER_START_ROW = 3;
	private static final int PROJECT_CONTENT_START_ROW = 8;

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
		Workbook book = new HSSFWorkbook(getClass().getResourceAsStream("/excel/" + PROJECT_TEMPLATE_NAME));
		Sheet sheet = book.getSheet("PROJECT");
		generateEzeeProjectReport(book, sheet, project);
		formatReport(sheet, PROJECT_CONTENT_NAME_INDEX, PROJECT_CONTENT_REF_INDEX);
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		book.write(result);
		result.close();
		return result.toByteArray();
	}

	private void generateEzeeProjectReport(final Workbook book, final Sheet sheet, final EzeeProject project) {
		int currentRow = PROJECT_HEADER_START_ROW;
		generateEzeeProjectReportContent(book, sheet, project, currentRow);
		if (!isEmpty(project.getItems())) {
			currentRow = PROJECT_CONTENT_START_ROW;
			Set<EzeeProjectItem> sortedItems = sorted(project.getItems());
			for (EzeeProjectItem item : sortedItems) {
				currentRow += generateEzeeProjectItemReport(book, sheet, item, currentRow);
			}
		}
	}

	private void generateEzeeProjectReportContent(final Workbook book, final Sheet sheet, final EzeeProject project,
			final int currentRow) {
		Row projectSummary = sheet.getRow(currentRow);
		Cell projectName = projectSummary.getCell(PROJECT_HEADER_NAME_INDEX);
		projectName.setCellValue(project.getName());
		Cell projectStart = projectSummary.getCell(PROJECT_HEADER_START_INDEX);
		projectStart.setCellValue(project.getStartDate());
		Cell projectEnd = projectSummary.getCell(PROJECT_HEADER_END_INDEX);
		projectEnd.setCellValue(project.getEndDate());
		Cell projectBudget = projectSummary.getCell(PROJECT_HEADER_BUDGET_INDEX);
		projectBudget.setCellValue(project.budgeted().getTotal());
		Cell projectActual = projectSummary.getCell(PROJECT_HEADER_ACTUAL_INDEX);
		projectActual.setCellValue(project.actual().getTotal());
		Cell projectPaid = projectSummary.getCell(PROJECT_HEADER_PAID_INDEX);
		projectPaid.setCellValue(project.paid().getTotal());
		Cell projectBalance = projectSummary.getCell(PROJECT_HEADER_BALANCE_INDEX);
		projectBalance.setCellValue(project.balance().getTotal());
		Cell projectPercentComplete = projectSummary.getCell(PROJECT_HEADER_COMPLETE_INDEX);
		double percent = EzeeNumericUtils.round(project.percent() * PERCENT_MULTIPLIER);
		projectPercentComplete.setCellValue(percent + "%");
	}

	private int generateEzeeProjectItemReport(final Workbook book, final Sheet sheet, final EzeeProjectItem item,
			final int currentRow) {
		int row = currentRow;
		generateEzeeProjectItemReportContent(book, sheet, item, row);
		if (!isEmpty(item.getDetails())) {
			Set<EzeeProjectItemDetail> sortedDetails = sorted(item.getDetails());
			for (EzeeProjectItemDetail detail : sortedDetails) {
				++row;
				generateEzeeProjectItemDetailContent(book, sheet, row, detail);
			}
		}
		if (!isEmpty(item.getPayments())) {
			Set<EzeeProjectPayment> sortedPayments = sorted(item.getPayments());
			for (EzeeProjectPayment payment : sortedPayments) {
				++row;
				generateEzeeProjectPaymentContent(book, sheet, row, payment);
			}
		}
		return (row - currentRow) + ONE;
	}

	private void generateEzeeProjectItemReportContent(final Workbook book, final Sheet sheet,
			final EzeeProjectItem item, final int currentRow) {
		CellStyle boldStyle = boldStyle(book, false, true);
		CellStyle ccyStyle = currencyStyle(book, true, true);
		CellStyle borderStyle = borderStyle(book, false, false);
		Row itemSummary = sheet.getRow(currentRow);
		Cell itemName = itemSummary.getCell(PROJECT_CONTENT_NAME_INDEX);
		itemName.setCellValue(item.getName());
		itemName.setCellStyle(boldStyle);
		Cell itemContractor = itemSummary.getCell(PROJECT_CONTENT_CONTRACTOR_INDEX);
		itemContractor.setCellValue(item.getContractor().getName());
		itemContractor.setCellStyle(boldStyle);
		Cell itemType = itemSummary.getCell(PROJECT_CONTENT_TYPE_INDEX);
		itemType.setCellStyle(borderStyle);
		Cell itemBudget = itemSummary.getCell(PROJECT_CONTENT_BUDGET_INDEX);
		itemBudget.setCellValue(item.budgeted().getTotal());
		itemBudget.setCellStyle(ccyStyle);
		Cell itemActual = itemSummary.getCell(PROJECT_CONTENT_ACTUAL_INDEX);
		itemActual.setCellValue(item.actual().getTotal());
		itemActual.setCellStyle(ccyStyle);
		Cell itemPaid = itemSummary.getCell(PROJECT_CONTENT_PAID_INDEX);
		itemPaid.setCellValue(item.paid().getTotal());
		itemPaid.setCellStyle(ccyStyle);
		Cell itemBalance = itemSummary.getCell(PROJECT_CONTENT_BALANCE_INDEX);
		itemBalance.setCellValue(item.balance().getTotal());
		itemBalance.setCellStyle(ccyStyle);
		CellStyle centeredBoldStyle = boldStyle(book, true, true);
		Cell itemPercentComplete = itemSummary.getCell(PROJECT_CONTENT_COMPLETE_INDEX);
		double percent = EzeeNumericUtils.round(item.percent() * PERCENT_MULTIPLIER);
		itemPercentComplete.setCellValue(percent + "%");
		itemPercentComplete.setCellStyle(centeredBoldStyle);
		Cell itemRef = itemSummary.getCell(PROJECT_CONTENT_REF_INDEX);
		itemRef.setCellStyle(borderStyle);
		Cell itemDesc = itemSummary.getCell(PROJECT_CONTENT_DESC_INDEX);
		itemDesc.setCellStyle(borderStyle);
	}

	private void generateEzeeProjectItemDetailContent(final Workbook book, final Sheet sheet, final int currentRow,
			final EzeeProjectItemDetail detail) {
		CellStyle borderStyle = borderStyle(book, false, false);
		CellStyle ccyStyle = currencyStyle(book, false, true);
		Row detailSummary = sheet.getRow(currentRow);
		Cell detailName = detailSummary.getCell(PROJECT_CONTENT_NAME_INDEX);
		detailName.setCellStyle(borderStyle);
		Cell detailContractor = detailSummary.getCell(PROJECT_CONTENT_CONTRACTOR_INDEX);
		detailContractor.setCellStyle(borderStyle);
		Cell detailType = detailSummary.getCell(PROJECT_CONTENT_TYPE_INDEX);
		detailType.setCellValue(detail.getType().toString());
		detailType.setCellStyle(borderStyle);
		if (detail.getType() == expense) {
			Cell detailBudget = detailSummary.getCell(PROJECT_CONTENT_BUDGET_INDEX);
			Cell detailActual = detailSummary.getCell(PROJECT_CONTENT_ACTUAL_INDEX);
			detailBudget.setCellValue(detail.getTotal());
			detailBudget.setCellStyle(ccyStyle);
			detailActual.setCellValue(detail.getTotal());
			detailActual.setCellStyle(ccyStyle);
		} else {
			Cell detailBudget = detailSummary.getCell(PROJECT_CONTENT_BUDGET_INDEX);
			detailBudget.setCellStyle(borderStyle);
			Cell detailActual = detailSummary.getCell(PROJECT_CONTENT_ACTUAL_INDEX);
			detailActual.setCellValue(detail.getTotal());
			detailActual.setCellStyle(ccyStyle);
		}
		Cell detailPaid = detailSummary.getCell(PROJECT_CONTENT_PAID_INDEX);
		detailPaid.setCellStyle(borderStyle);
		Cell detailBalance = detailSummary.getCell(PROJECT_CONTENT_BALANCE_INDEX);
		detailBalance.setCellStyle(borderStyle);
		Cell detailComplete = detailSummary.getCell(PROJECT_CONTENT_COMPLETE_INDEX);
		detailComplete.setCellStyle(borderStyle);
		Cell detailRef = detailSummary.getCell(PROJECT_CONTENT_REF_INDEX);
		detailRef.setCellStyle(borderStyle);
		Cell detailDesc = detailSummary.getCell(PROJECT_CONTENT_DESC_INDEX);
		detailDesc.setCellValue(detail.getDescription());
		detailDesc.setCellStyle(borderStyle);
	}

	private void generateEzeeProjectPaymentContent(final Workbook book, final Sheet sheet, final int currentRow,
			final EzeeProjectPayment payment) {
		CellStyle borderStyle = borderStyle(book, false, false);
		CellStyle ccyStyle = currencyStyle(book, false, true);
		Row paymentSummary = sheet.getRow(currentRow);
		Cell paymentName = paymentSummary.getCell(PROJECT_CONTENT_NAME_INDEX);
		paymentName.setCellStyle(borderStyle);
		Cell paymentContractor = paymentSummary.getCell(PROJECT_CONTENT_CONTRACTOR_INDEX);
		paymentContractor.setCellStyle(borderStyle);
		Cell paymentType = paymentSummary.getCell(PROJECT_CONTENT_TYPE_INDEX);
		paymentType.setCellValue(payment.getType().toString());
		paymentType.setCellStyle(borderStyle);
		Cell paymentBudget = paymentSummary.getCell(PROJECT_CONTENT_BUDGET_INDEX);
		paymentBudget.setCellStyle(borderStyle);
		Cell paymentActual = paymentSummary.getCell(PROJECT_CONTENT_ACTUAL_INDEX);
		paymentActual.setCellStyle(borderStyle);
		Cell paymentTotal = paymentSummary.getCell(PROJECT_CONTENT_PAID_INDEX);
		paymentTotal.setCellValue(payment.getTotal());
		paymentTotal.setCellStyle(ccyStyle);
		Cell paymentBalance = paymentSummary.getCell(PROJECT_CONTENT_BALANCE_INDEX);
		paymentBalance.setCellStyle(borderStyle);
		Cell paymentComplete = paymentSummary.getCell(PROJECT_CONTENT_COMPLETE_INDEX);
		paymentComplete.setCellStyle(borderStyle);
		CellStyle centeredBorderStyle = borderStyle(book, false, true);
		Cell paymentRef = paymentSummary.getCell(PROJECT_CONTENT_REF_INDEX);
		paymentRef.setCellValue(payment.getInvoiceRef());
		paymentRef.setCellStyle(centeredBorderStyle);
		Cell paymentDesc = paymentSummary.getCell(PROJECT_CONTENT_DESC_INDEX);
		paymentDesc.setCellValue(payment.getDescription());
		paymentDesc.setCellStyle(borderStyle);
	}

	private EzeeProject getProject(final HttpServletRequest request) {
		Long projectId = Long.parseLong(request.getParameter(EXCEL_PROJECT_ID));
		return dao.get(projectId, EzeeProject.class);
	}
}