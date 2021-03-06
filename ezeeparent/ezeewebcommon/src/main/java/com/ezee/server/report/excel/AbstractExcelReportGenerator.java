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
import com.ezee.web.common.datastructures.EzeePair;

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

	protected int LEASE_EXCEL_REPORT_HEADER_ROW = 0;

	protected int LEASE_EXCEL_REPORT_TENANT_INDEX = 0;
	protected int LEASE_EXCEL_REPORT_UNIT_INDEX = 1;
	protected int LEASE_EXCEL_REPORT_AREA_INDEX = 2;
	protected int LEASE_EXCEL_REPORT_START_INDEX = 3;
	protected int LEASE_EXCEL_REPORT_END_INDEX = 4;
	protected int LEASE_EXCEL_REPORT_UPDATE_INDEX = 5;
	protected int LEASE_EXCEL_REPORT_ANNUAL_RENT_INDEX = 6;
	protected int LEASE_EXCEL_REPORT_ANNUAL_RENT_GST_INDEX = 7;
	protected int LEASE_EXCEL_REPORT_ANNUAL_GROSS_RENT_INDEX = 8;
	protected int LEASE_EXCEL_REPORT_MONTHLY_RENT_INDEX = 9;
	protected int LEASE_EXCEL_REPORT_MONTHLY_RENT_GST_INDEX = 10;
	protected int LEASE_EXCEL_REPORT_MONTHLY_GROSS_RENT_INDEX = 11;
	protected int LEASE_EXCEL_REPORT_ANNUAL_OUTGOINGS_INDEX = 12;
	protected int LEASE_EXCEL_REPORT_ANNUAL_OUTGOINGS_GST_INDEX = 13;
	protected int LEASE_EXCEL_REPORT_ANNUAL_GROSS_OUTGOINGS_INDEX = 14;
	protected int LEASE_EXCEL_REPORT_MONTHLY_OUTGOINGS_INDEX = 15;
	protected int LEASE_EXCEL_REPORT_MONTHLY_OUTGOINGS_GST_INDEX = 16;
	protected int LEASE_EXCEL_REPORT_MONTHLY_GROSS_OUTGOINGS_INDEX = 17;
	protected int LEASE_EXCEL_REPORT_ANNUAL_PARKING_INDEX = 18;
	protected int LEASE_EXCEL_REPORT_ANNUAL_PARKING_GST_INDEX = 19;
	protected int LEASE_EXCEL_REPORT_ANNUAL_GROSS_PARKING_INDEX = 20;
	protected int LEASE_EXCEL_REPORT_MONTHLY_PARKING_INDEX = 21;
	protected int LEASE_EXCEL_REPORT_MONTHLY_PARKING_GST_INDEX = 22;
	protected int LEASE_EXCEL_REPORT_MONTHLY_GROSS_PARKING_INDEX = 23;
	protected int LEASE_EXCEL_REPORT_ANNUAL_SIGNAGE_INDEX = 24;
	protected int LEASE_EXCEL_REPORT_ANNUAL_SIGNAGE_GST_INDEX = 25;
	protected int LEASE_EXCEL_REPORT_ANNUAL_GROSS_SIGNAGE_INDEX = 26;
	protected int LEASE_EXCEL_REPORT_MONTHLY_SIGNAGE_INDEX = 27;
	protected int LEASE_EXCEL_REPORT_MONTHLY_SIGNAGE_GST_INDEX = 28;
	protected int LEASE_EXCEL_REPORT_MONTHLY_GROSS_SIGNAGE_INDEX = 29;
	protected int LEASE_EXCEL_REPORT_ANNUAL_TOTAL_INDEX = 30;
	protected int LEASE_EXCEL_REPORT_ANNUAL_TOTAL_GST_INDEX = 31;
	protected int LEASE_EXCEL_REPORT_ANNUAL_GROSS_TOTAL_INDEX = 32;
	protected int LEASE_EXCEL_REPORT_MONTHLY_TOTAL_INDEX = 33;
	protected int LEASE_EXCEL_REPORT_MONTHLY_TOTAL_GST_INDEX = 34;
	protected int LEASE_EXCEL_REPORT_MONTHLY_GROSS_TOTAL_INDEX = 35;

	protected int LEASE_EXCEL_REPORT_TENANT_WIDTH = 40 * 256;
	protected int LEASE_EXCEL_REPORT_UNIT_WIDTH = 11 * 256;
	protected int LEASE_EXCEL_REPORT_AREA_WIDTH = 6 * 256;
	protected int LEASE_EXCEL_REPORT_DATE_WIDTH = 11 * 256;
	protected int LEASE_EXCEL_REPORT_NUMBER_WIDTH = 13 * 256;

	protected int LEASE_EXCEL_REPORT_MAX_INDEX = LEASE_EXCEL_REPORT_MONTHLY_GROSS_TOTAL_INDEX;

	protected String LEASE_EXCEL_GRAND_TOTAL = "Grand Total";

	protected EzeePair<Integer, Integer> CATEGORY_COMPANY_INDEX = new EzeePair<Integer, Integer>(0, 0);
	protected EzeePair<Integer, Integer> CATEGORY_ABN_INDEX = new EzeePair<Integer, Integer>(1, 0);
	protected EzeePair<Integer, Integer> CATEGORY_ADDRESS_INDEX = new EzeePair<Integer, Integer>(2, 0);
	protected EzeePair<Integer, Integer> CATEGORY_PHONE_INDEX = new EzeePair<Integer, Integer>(3, 0);
	protected EzeePair<Integer, Integer> CURRENT_DATE_INDEX = new EzeePair<Integer, Integer>(10, 4);

	protected EzeePair<Integer, Integer> TENANT_INDEX = new EzeePair<Integer, Integer>(14, 0);
	protected EzeePair<Integer, Integer> PREMISES_ADDRESS_1_INDEX = new EzeePair<Integer, Integer>(15, 0);
	protected EzeePair<Integer, Integer> PREMISES_ADDRESS_2_INDEX = new EzeePair<Integer, Integer>(16, 0);

	protected EzeePair<Integer, Integer> RENTAL_LINE_1_INDEX = new EzeePair<Integer, Integer>(20, 1);
	protected EzeePair<Integer, Integer> RENTAL_LINE_2_INDEX = new EzeePair<Integer, Integer>(21, 1);

	protected EzeePair<Integer, Integer> YEARLY_RENT_INDEX = new EzeePair<Integer, Integer>(26, 3);
	protected EzeePair<Integer, Integer> YEARLY_RENT_GST_INDEX = new EzeePair<Integer, Integer>(27, 3);
	protected EzeePair<Integer, Integer> MONTHLY_RENT_INDEX = new EzeePair<Integer, Integer>(26, 4);
	protected EzeePair<Integer, Integer> MONTHLY_RENT_GST_INDEX = new EzeePair<Integer, Integer>(27, 4);
	protected EzeePair<Integer, Integer> YEARLY_OUTGOINGS_INDEX = new EzeePair<Integer, Integer>(30, 3);
	protected EzeePair<Integer, Integer> YEARLY_OUTGOINGS_GST_INDEX = new EzeePair<Integer, Integer>(31, 3);
	protected EzeePair<Integer, Integer> MONTHLY_OUTGOINGS_INDEX = new EzeePair<Integer, Integer>(30, 4);
	protected EzeePair<Integer, Integer> MONTHLY_OUTGOINGS_GST_INDEX = new EzeePair<Integer, Integer>(31, 4);
	protected EzeePair<Integer, Integer> YEARLY_PARKING_INDEX = new EzeePair<Integer, Integer>(34, 3);
	protected EzeePair<Integer, Integer> YEARLY_PARKING_GST_INDEX = new EzeePair<Integer, Integer>(35, 3);
	protected EzeePair<Integer, Integer> MONTHLY_PARKING_INDEX = new EzeePair<Integer, Integer>(34, 4);
	protected EzeePair<Integer, Integer> MONTHLY_PARKING_GST_INDEX = new EzeePair<Integer, Integer>(35, 4);
	protected EzeePair<Integer, Integer> YEARLY_SIGNAGE_INDEX = new EzeePair<Integer, Integer>(38, 3);
	protected EzeePair<Integer, Integer> YEARLY_SIGNAGE_GST_INDEX = new EzeePair<Integer, Integer>(39, 3);
	protected EzeePair<Integer, Integer> MONTHLY_SIGNAGE_INDEX = new EzeePair<Integer, Integer>(38, 4);
	protected EzeePair<Integer, Integer> MONTHLY_SIGNAGE_GST_INDEX = new EzeePair<Integer, Integer>(39, 4);

	protected EzeePair<Integer, Integer> BANK_ACCOUNT_NAME_INDEX = new EzeePair<Integer, Integer>(51, 2);
	protected EzeePair<Integer, Integer> BANK_ACCOUNT_LOCATION_INDEX = new EzeePair<Integer, Integer>(52, 2);
	protected EzeePair<Integer, Integer> BANK_ACCOUNT_BSB_INDEX = new EzeePair<Integer, Integer>(53, 2);
	protected EzeePair<Integer, Integer> BANK_ACCOUNT_NUMBER_INDEX = new EzeePair<Integer, Integer>(54, 2);

	protected EzeePair<Integer, Integer> SCHEDULE_TITLE_1_INDEX = new EzeePair<Integer, Integer>(0, 0);
	protected EzeePair<Integer, Integer> SCHEDULE_TITLE_2_INDEX = new EzeePair<Integer, Integer>(1, 0);
	protected EzeePair<Integer, Integer> SCHEDULE_ADDRESS_INDEX = new EzeePair<Integer, Integer>(4, 0);

	protected EzeePair<Integer, Integer> SCHEDULE_DEVELOPMENT_INDEX = new EzeePair<Integer, Integer>(5, 1);
	protected EzeePair<Integer, Integer> SCHEDULE_UNIT_INDEX = new EzeePair<Integer, Integer>(6, 1);
	protected EzeePair<Integer, Integer> SCHEDULE_LESSEE_INDEX = new EzeePair<Integer, Integer>(7, 1);
	protected EzeePair<Integer, Integer> SCHEDULE_YEARLY_RENT_INDEX = new EzeePair<Integer, Integer>(12, 1);
	protected EzeePair<Integer, Integer> SCHEDULE_YEARLY_RENT_GST_INDEX = new EzeePair<Integer, Integer>(13, 1);
	protected EzeePair<Integer, Integer> SCHEDULE_MONTHLY_RENT_INDEX = new EzeePair<Integer, Integer>(16, 1);
	protected EzeePair<Integer, Integer> SCHEDULE_MONTHLY_RENT_GST_INDEX = new EzeePair<Integer, Integer>(17, 1);
	protected EzeePair<Integer, Integer> SCHEDULE_YEARLY_OUTGOINGS_INDEX = new EzeePair<Integer, Integer>(21, 1);
	protected EzeePair<Integer, Integer> SCHEDULE_YEARLY_OUTGOINGS_GST_INDEX = new EzeePair<Integer, Integer>(22, 1);
	protected EzeePair<Integer, Integer> SCHEDULE_MONTHLY_OUTGOINGS_INDEX = new EzeePair<Integer, Integer>(25, 1);
	protected EzeePair<Integer, Integer> SCHEDULE_MONTHLY_OUTGOINGS_GST_INDEX = new EzeePair<Integer, Integer>(26, 1);
	protected EzeePair<Integer, Integer> SCHEDULE_YEARLY_PARKING_INDEX = new EzeePair<Integer, Integer>(30, 1);
	protected EzeePair<Integer, Integer> SCHEDULE_YEARLY_PARKING_GST_INDEX = new EzeePair<Integer, Integer>(31, 1);
	protected EzeePair<Integer, Integer> SCHEDULE_MONTHLY_PARKING_INDEX = new EzeePair<Integer, Integer>(34, 1);
	protected EzeePair<Integer, Integer> SCHEDULE_MONTHLY_PARKING_GST_INDEX = new EzeePair<Integer, Integer>(35, 1);
	protected EzeePair<Integer, Integer> SCHEDULE_YEARLY_SIGNAGE_INDEX = new EzeePair<Integer, Integer>(39, 1);
	protected EzeePair<Integer, Integer> SCHEDULE_YEARLY_SIGNAGE_GST_INDEX = new EzeePair<Integer, Integer>(40, 1);
	protected EzeePair<Integer, Integer> SCHEDULE_MONTHLY_SIGNAGE_INDEX = new EzeePair<Integer, Integer>(43, 1);
	protected EzeePair<Integer, Integer> SCHEDULE_MONTHLY_SIGNAGE_GST_INDEX = new EzeePair<Integer, Integer>(44, 1);
	protected EzeePair<Integer, Integer> SCHEDULE_LEASE_START_INDEX = new EzeePair<Integer, Integer>(5, 4);
	protected EzeePair<Integer, Integer> SCHEDULE_LEASE_END_INDEX = new EzeePair<Integer, Integer>(6, 4);
	protected EzeePair<Integer, Integer> SCHEDULE_RENT_FREE_PERIOD_INDEX = new EzeePair<Integer, Integer>(7, 4);
	protected EzeePair<Integer, Integer> SCHEDULE_RENT_INCREASE_INDEX = new EzeePair<Integer, Integer>(8, 4);
	protected EzeePair<Integer, Integer> SCHEDULE_LEASE_OPTION_START_INDEX = new EzeePair<Integer, Integer>(9, 4);
	protected EzeePair<Integer, Integer> SCHEDULE_LEASE_OPTION_END_INDEX = new EzeePair<Integer, Integer>(10, 4);
	protected EzeePair<Integer, Integer> SCHEDULE_OUTGOINGS_PRECENT_INDEX = new EzeePair<Integer, Integer>(11, 4);
	protected EzeePair<Integer, Integer> SCHEDULE_BOND_BANK_GUARANTEE_INDEX = new EzeePair<Integer, Integer>(12, 4);
	protected EzeePair<Integer, Integer> SCHEDULE_AREA_INDEX = new EzeePair<Integer, Integer>(13, 4);

	protected EzeePair<Integer, Integer> HISTORIC_RENTS_START_INDEX = new EzeePair<Integer, Integer>(15, 3);
	protected EzeePair<Integer, Integer> NOTICES_START_INDEX = new EzeePair<Integer, Integer>(29, 3);
	protected EzeePair<Integer, Integer> SPECIAL_CONDITIONS_START_INDEX = new EzeePair<Integer, Integer>(38, 3);
	protected EzeePair<Integer, Integer> OPTIONS_START_INDEX = new EzeePair<Integer, Integer>(47, 3);

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

	protected CellStyle headerStyle(final Workbook book, final boolean centered) {
		CellStyle style = boldStyle(book, centered, false);
		style.setWrapText(true);
		return style;
	}

	public static CellStyle formattedStyle(final Workbook book, boolean centered, boolean wrapped) {
		CellStyle style = book.createCellStyle();
		if (centered) {
			style.setAlignment(ALIGN_CENTER);
		}
		style.setWrapText(wrapped);
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