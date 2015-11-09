package com.ezee.server.report.excel;

import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_CENTER;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
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

	protected int[] INVOICE_REPORT_INDEXES = { INVOICE_ID_INDEX, SUPPLIER_INDEX, PREMISES_INDEX, CLASSIFICATION_INDEX,
			AMOUNT_INDEX, TAX_INDEX, TOTAL_INDEX, INVOICE_DATE_INDEX, DUE_DATE_INDEX, PAYMENT_DATE_INDEX };
	protected String[] INVOICE_REPORT_FIELDS = { INVOICE_ID, SUPPLIER, PREMISES, CLASSIFICATION, AMOUNT, TAX, TOTAL,
			INVOICE_DATE, DUE_DATE, PAYMENT_DATE };

	protected OutputStream createExcelFilenameResponseHeader(final HttpServletResponse resp, final String filename)
			throws IOException {
		OutputStream stream = resp.getOutputStream();
		resp.setContentType("application/vnd.ms-excel");
		resp.addHeader("Content-Type", "application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachement;filename=" + filename);
		return stream;
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
		style.setWrapText(true);
		return style;
	}

	protected Font boldFont(final Workbook book) {
		Font font = book.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		return font;
	}
}