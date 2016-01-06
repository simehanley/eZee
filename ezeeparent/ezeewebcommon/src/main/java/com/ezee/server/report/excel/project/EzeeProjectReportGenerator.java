package com.ezee.server.report.excel.project;

import static com.ezee.common.EzeeCommonConstants.PERCENT_MULTIPLIER;
import static com.ezee.common.EzeeCommonConstants.TWO;
import static com.ezee.common.EzeeCommonConstants.ZERO;
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
		++currentRow;
		for (EzeeProjectItem item : project.getItems()) {
			currentRow += generateEzeeProjectItemReport(book, sheet, item, currentRow);
		}
	}

	private void generateEzeeProjectReportHeader(final Workbook book, final Sheet sheet, final EzeeProject project,
			final int currentRow) {
		/* implement me */
	}

	private void generateEzeeProjectReportContent(final Workbook book, final Sheet sheet, final EzeeProject project,
			final int currentRow) {
		/* implement me */
	}

	private int generateEzeeProjectItemReport(final Workbook book, final Sheet sheet, final EzeeProjectItem item,
			final int currentRow) {
		int row = currentRow;
		generateEzeeProjectItemReportHeader(book, sheet, item, row);
		++row;
		generateEzeeProjectItemReportContent(book, sheet, item, row);
		return TWO;
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

	private EzeeProject getProject(final HttpServletRequest request) {
		Long projectId = Long.parseLong(request.getParameter(EXCEL_PROJECT_ID));
		return dao.get(projectId, EzeeProject.class);
	}
}