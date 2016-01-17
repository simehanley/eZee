package com.ezee.server.report.excel;

import static com.ezee.common.EzeeCommonConstants.ZERO;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_CENTER;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
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

	protected int PROJECT_HEADER_NAME_INDEX = 0;
	protected int PROJECT_HEADER_START_INDEX = 1;
	protected int PROJECT_HEADER_END_INDEX = 2;
	protected int PROJECT_HEADER_BUDGET_INDEX = 3;
	protected int PROJECT_HEADER_ACTUAL_INDEX = 4;
	protected int PROJECT_HEADER_PAID_INDEX = 5;
	protected int PROJECT_HEADER_BALANCE_INDEX = 6;
	protected int PROJECT_HEADER_COMPLETE_INDEX = 7;

	protected int PROJECT_CONTENT_NAME_INDEX = 0;
	protected int PROJECT_CONTENT_CONTRACTOR_INDEX = 1;
	protected int PROJECT_CONTENT_TYPE_INDEX = 2;
	protected int PROJECT_CONTENT_BUDGET_INDEX = 3;
	protected int PROJECT_CONTENT_ACTUAL_INDEX = 4;
	protected int PROJECT_CONTENT_PAID_INDEX = 5;
	protected int PROJECT_CONTENT_BALANCE_INDEX = 6;
	protected int PROJECT_CONTENT_COMPLETE_INDEX = 7;
	protected int PROJECT_CONTENT_REF_INDEX = 8;
	protected int PROJECT_CONTENT_DESC_INDEX = 9;

	protected int[] INVOICE_REPORT_INDEXES = { INVOICE_ID_INDEX, SUPPLIER_INDEX, PREMISES_INDEX, CLASSIFICATION_INDEX,
			AMOUNT_INDEX, TAX_INDEX, TOTAL_INDEX, INVOICE_DATE_INDEX, DUE_DATE_INDEX, PAYMENT_DATE_INDEX };
	protected String[] INVOICE_REPORT_FIELDS = { INVOICE_ID, SUPPLIER, PREMISES, CLASSIFICATION, AMOUNT, TAX, TOTAL,
			INVOICE_DATE, DUE_DATE, PAYMENT_DATE };

	protected int[] FE_REPORT_INDEXES = { FE_NAME_INDEX, FE_CONTACT_INDEX, FE_ADDRESS_LINE_1_INDEX,
			FE_ADDRESS_LINE_2_INDEX, FE_SUBURB_INDEX, FE_CITY_INDEX, FE_STATE_INDEX, FE_POSTCODE_INDEX, FE_PHONE_INDEX,
			FE_FAX_INDEX, FE_EMAIL_INDEX };
	protected String[] FE_REPORT_FIELDS = { FE_NAME, FE_CONTACT, FE_ADDRESS_LINE_1, FE_ADDRESS_LINE_2, FE_SUBURB,
			FE_CITY, FE_STATE, FE_POSTCODE, FE_PHONE, FE_FAX, FE_EMAIL };

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
		CellStyle headerStyle = boldStyle(book, true, false);
		for (int i = ZERO; i < indexes.length; i++) {
			Cell cell = header.createCell(indexes[i], CELL_TYPE_STRING);
			cell.setCellValue(fieldnames[i]);
			cell.setCellStyle(headerStyle);
		}
	}

	protected CellStyle borderStyle(final Workbook book, final boolean bold, final boolean centered) {
		CellStyle style = book.createCellStyle();
		setBorderStyle(style);
		if (bold) {
			style.setFont(boldFont(book));
		}
		if (centered) {
			style.setAlignment(ALIGN_CENTER);
		}
		return style;
	}

	protected CellStyle dateStyle(final Workbook book, final boolean bold, final boolean border) {
		CellStyle style = book.createCellStyle();
		DataFormat format = book.createDataFormat();
		style.setDataFormat(format.getFormat("dd/MM/yyyy"));
		if (border) {
			setBorderStyle(style);
		}
		return style;
	}

	protected CellStyle currencyStyle(final Workbook book, final boolean bold, final boolean border) {
		CellStyle style = book.createCellStyle();
		DataFormat format = book.createDataFormat();
		style.setDataFormat(format.getFormat("$* #,##0.00"));
		if (bold) {
			style.setFont(boldFont(book));
		}
		if (border) {
			setBorderStyle(style);
		}
		return style;
	}

	protected CellStyle boldStyle(final Workbook book, final boolean centered, final boolean border) {
		CellStyle style = book.createCellStyle();
		style.setFont(boldFont(book));
		if (centered) {
			style.setAlignment(ALIGN_CENTER);
		}
		if (border) {
			setBorderStyle(style);
		}
		style.setWrapText(false);
		return style;
	}

	private void setBorderStyle(final CellStyle style) {
		style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
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