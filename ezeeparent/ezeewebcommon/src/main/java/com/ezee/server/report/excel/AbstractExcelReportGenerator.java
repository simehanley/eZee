package com.ezee.server.report.excel;

import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_CENTER;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.ezee.server.report.AbstractReportGenerator;

/**
 * 
 * @author siborg
 *
 */
public abstract class AbstractExcelReportGenerator extends AbstractReportGenerator {

	protected int INVOICE_ID_INDEX = 0;
	protected int SUPPLIER_INDEX = 1;
	protected int PREMISES_INDEX = 2;
	protected int CLASSIFICATION_INDEX = 3;
	protected int AMOUNT_INDEX = 4;
	protected int TAX_INDEX = 5;
	protected int TOTAL_INDEX = 6;
	protected int INVOICE_DATE_INDEX = 7;
	protected int DUE_DATE_INDEX = 8;
	protected int PAYMENT_DATE_INDEX = 9;

	protected String INVOICE_ID = "InvoiceId";
	protected String SUPPLIER = "Supplier";
	protected String PREMISES = "Premises";
	protected String CLASSIFICATION = "Classification";
	protected String AMOUNT = "Amount";
	protected String TAX = "Tax";
	protected String TOTAL = "Total";
	protected String INVOICE_DATE = "Invoice Date";
	protected String DUE_DATE = "Due Date";
	protected String PAYMENT_DATE = "Payment Date";

	protected int FE_NAME_INDEX = 0;
	protected int FE_CONTACT_INDEX = 1;
	protected int FE_ADDRESS_LINE_1_INDEX = 2;
	protected int FE_ADDRESS_LINE_2_INDEX = 3;
	protected int FE_SUBURB_INDEX = 4;
	protected int FE_CITY_INDEX = 5;
	protected int FE_STATE_INDEX = 6;
	protected int FE_POSTCODE_INDEX = 7;
	protected int FE_PHONE_INDEX = 8;
	protected int FE_FAX_INDEX = 9;
	protected int FE_EMAIL_INDEX = 10;

	protected String FE_NAME = "Name";
	protected String FE_CONTACT = "Contact";
	protected String FE_ADDRESS_LINE_1 = "Address (1)";
	protected String FE_ADDRESS_LINE_2 = "Address (2)";
	protected String FE_SUBURB = "Suburb";
	protected String FE_CITY = "City";
	protected String FE_STATE = "State";
	protected String FE_POSTCODE = "PostCode";
	protected String FE_PHONE = "Phone";
	protected String FE_FAX = "Fax";
	protected String FE_EMAIL = "E-Mail";

	protected String PROJECT_START = "Start";
	protected String PROJECT_END = "End";

	protected int PROJECT_START_INDEX = 1;
	protected int PROJECT_END_INDEX = 2;

	protected String PROJECT_ITEM_NAME = "Name";
	protected String PROJECT_ITEM_CONTRACTOR = "Contractor";
	protected String PROJECT_ITEM_BUDGET = "Budget";
	protected String PROJECT_ITEM_ACTUAL = "Actual";
	protected String PROJECT_ITEM_PAID = "Paid";
	protected String PROJECT_ITEM_BALANCE = "Balance";
	protected String PROJECT_ITEM_COMPLETE = "% Complete";

	protected int PROJECT_ITEM_NAME_INDEX = 0;
	protected int PROJECT_ITEM_CONTRACTOR_INDEX = 1;
	protected int PROJECT_ITEM_BUDGET_INDEX = 2;
	protected int PROJECT_ITEM_ACTUAL_INDEX = 3;
	protected int PROJECT_ITEM_PAID_INDEX = 4;
	protected int PROJECT_ITEM_BALANCE_INDEX = 5;
	protected int PROJECT_ITEM_COMPLETE_INDEX = 6;

	protected String PROJECT_ITEM_DETAIL_DESC = "Description";
	protected String PROJECT_ITEM_DETAIL_TYPE = "Type";
	protected String PROJECT_ITEM_DETAIL_AMOUNT = "Amount";
	protected String PROJECT_ITEM_DETAIL_TAX = "Tax";
	protected String PROJECT_ITEM_DETAIL_TOTAL = "Total";

	protected int PROJECT_ITEM_DETAIL_DESC_INDEX = 1;
	protected int PROJECT_ITEM_DETAIL_TYPE_INDEX = 2;
	protected int PROJECT_ITEM_DETAIL_AMOUNT_INDEX = 3;
	protected int PROJECT_ITEM_DETAIL_TAX_INDEX = 4;
	protected int PROJECT_ITEM_DETAIL_TOTAL_INDEX = 5;

	protected String PROJECT_PMT_REF = "Ref";
	protected String PROJECT_PMT_DATE = "Date";

	protected int PROJECT_PMT_REF_INDEX = 6;
	protected int PROJECT_PMT_DATE_INDEX = 7;

	protected int[] INVOICE_REPORT_INDEXES = { INVOICE_ID_INDEX, SUPPLIER_INDEX, PREMISES_INDEX, CLASSIFICATION_INDEX,
			AMOUNT_INDEX, TAX_INDEX, TOTAL_INDEX, INVOICE_DATE_INDEX, DUE_DATE_INDEX, PAYMENT_DATE_INDEX };
	protected String[] INVOICE_REPORT_FIELDS = { INVOICE_ID, SUPPLIER, PREMISES, CLASSIFICATION, AMOUNT, TAX, TOTAL,
			INVOICE_DATE, DUE_DATE, PAYMENT_DATE };

	protected int[] FE_REPORT_INDEXES = { FE_NAME_INDEX, FE_CONTACT_INDEX, FE_ADDRESS_LINE_1_INDEX,
			FE_ADDRESS_LINE_2_INDEX, FE_SUBURB_INDEX, FE_CITY_INDEX, FE_STATE_INDEX, FE_POSTCODE_INDEX, FE_PHONE_INDEX,
			FE_FAX_INDEX, FE_EMAIL_INDEX };
	protected String[] FE_REPORT_FIELDS = { FE_NAME, FE_CONTACT, FE_ADDRESS_LINE_1, FE_ADDRESS_LINE_2, FE_SUBURB,
			FE_CITY, FE_STATE, FE_POSTCODE, FE_PHONE, FE_FAX, FE_EMAIL };

	protected int[] PROJECT_HEADER_INDEXES = { PROJECT_ITEM_NAME_INDEX, PROJECT_START_INDEX, PROJECT_END_INDEX,
			PROJECT_ITEM_BUDGET_INDEX + ONE, PROJECT_ITEM_ACTUAL_INDEX + ONE, PROJECT_ITEM_PAID_INDEX + ONE,
			PROJECT_ITEM_BALANCE_INDEX + ONE, PROJECT_ITEM_COMPLETE_INDEX + ONE };
	protected String[] PROJECT_HEADER_FIELDS = { PROJECT_ITEM_NAME, PROJECT_START, PROJECT_END, PROJECT_ITEM_BUDGET,
			PROJECT_ITEM_ACTUAL, PROJECT_ITEM_PAID, PROJECT_ITEM_BALANCE, PROJECT_ITEM_COMPLETE };

	protected int[] PROJECT_ITEM_HEADER_INDEXES = { PROJECT_ITEM_NAME_INDEX, PROJECT_ITEM_CONTRACTOR_INDEX,
			PROJECT_ITEM_BUDGET_INDEX, PROJECT_ITEM_ACTUAL_INDEX, PROJECT_ITEM_PAID_INDEX, PROJECT_ITEM_BALANCE_INDEX,
			PROJECT_ITEM_COMPLETE_INDEX };
	protected String[] PROJECT_ITEM_HEADER_FIELDS = { PROJECT_ITEM_NAME, PROJECT_ITEM_CONTRACTOR, PROJECT_ITEM_BUDGET,
			PROJECT_ITEM_ACTUAL, PROJECT_ITEM_PAID, PROJECT_ITEM_BALANCE, PROJECT_ITEM_COMPLETE };

	protected int[] PROJECT_ITEM_DETAIL_HEADER_INDEXES = { PROJECT_ITEM_DETAIL_DESC_INDEX,
			PROJECT_ITEM_DETAIL_TYPE_INDEX, PROJECT_ITEM_DETAIL_AMOUNT_INDEX, PROJECT_ITEM_DETAIL_TAX_INDEX,
			PROJECT_ITEM_DETAIL_TOTAL_INDEX };
	protected String[] PROJECT_ITEM_DETAIL_HEADER_FIELDS = { PROJECT_ITEM_DETAIL_DESC+" (DETAILS)", PROJECT_ITEM_DETAIL_TYPE,
			PROJECT_ITEM_DETAIL_AMOUNT, PROJECT_ITEM_DETAIL_TAX, PROJECT_ITEM_DETAIL_TOTAL };

	protected int[] PROJECT_PMT_HEADER_INDEXES = { PROJECT_ITEM_DETAIL_DESC_INDEX, PROJECT_ITEM_DETAIL_TYPE_INDEX,
			PROJECT_ITEM_DETAIL_AMOUNT_INDEX, PROJECT_ITEM_DETAIL_TAX_INDEX, PROJECT_ITEM_DETAIL_TOTAL_INDEX,
			PROJECT_PMT_REF_INDEX, PROJECT_PMT_DATE_INDEX };
	protected String[] PROJECT_PMT_HEADER_FIELDS = { PROJECT_ITEM_DETAIL_DESC+" (PAYMENTS)", PROJECT_ITEM_DETAIL_TYPE,
			PROJECT_ITEM_DETAIL_AMOUNT, PROJECT_ITEM_DETAIL_TAX, PROJECT_ITEM_DETAIL_TOTAL, PROJECT_PMT_REF,
			PROJECT_PMT_DATE };

	protected OutputStream createExcelFilenameResponseHeader(final HttpServletResponse resp, final String filename)
			throws IOException {
		OutputStream stream = resp.getOutputStream();
		resp.setContentType("application/vnd.ms-excel");
		resp.addHeader("Content-Type", "application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachement;filename=" + filename);
		return stream;
	}

	protected void generateExcelReportHeader(final Workbook book, final Sheet sheet, final int[] indexes,
			final String[] fieldnames) {
		generateExcelReportHeader(book, sheet, indexes, fieldnames, ZERO);
	}

	protected void generateExcelReportHeader(final Workbook book, final Sheet sheet, final int[] indexes,
			final String[] fieldnames, int currentRow) {
		Row header = sheet.createRow(currentRow);
		CellStyle headerStyle = boldStyle(book, true);
		for (int i = ZERO; i < indexes.length; i++) {
			Cell cell = header.createCell(indexes[i], CELL_TYPE_STRING);
			cell.setCellValue(fieldnames[i]);
			cell.setCellStyle(headerStyle);
		}
	}

	protected CellStyle dateStyle(final Workbook book, final boolean bold) {
		CellStyle style = book.createCellStyle();
		DataFormat format = book.createDataFormat();
		style.setDataFormat(format.getFormat("dd/MM/yyyy"));
		return style;
	}

	protected CellStyle currencyStyle(final Workbook book, final boolean bold) {
		CellStyle style = book.createCellStyle();
		DataFormat format = book.createDataFormat();
		style.setDataFormat(format.getFormat("$* #,##0.00"));
		if (bold) {
			style.setFont(boldFont(book));
		}
		return style;
	}

	protected CellStyle boldStyle(final Workbook book, final boolean centered) {
		CellStyle style = book.createCellStyle();
		style.setFont(boldFont(book));
		if (centered) {
			style.setAlignment(ALIGN_CENTER);
		}
		style.setWrapText(false);
		return style;
	}

	protected Font boldFont(final Workbook book) {
		Font font = book.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		return font;
	}

	protected void formatReport(final Sheet sheet, final int startIndex, final int endIndex) {
		for (int i = startIndex; i <= endIndex; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	protected void setPrintArea(final Sheet sheet) {
		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setLandscape(true);
		printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
		sheet.setAutobreaks(true);
	}
}