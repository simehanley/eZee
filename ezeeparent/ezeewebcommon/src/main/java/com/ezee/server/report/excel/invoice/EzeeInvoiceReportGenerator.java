package com.ezee.server.report.excel.invoice;

import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;

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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.common.EzeeCommonConstants;
import com.ezee.dao.EzeeInvoiceDao;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.server.report.EzeeReportGenerator;
import com.ezee.server.report.excel.AbstractExcelReportGenerator;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceReportGenerator extends AbstractExcelReportGenerator implements EzeeReportGenerator {

	private static final Logger log = LoggerFactory.getLogger(EzeeInvoiceReportGenerator.class);

	@Autowired
	private EzeeInvoiceDao dao;

	@Override
	public void generateReport(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			generateEzeeInvoiceReport(request, response);
		} catch (IOException exception) {
			log.error("Error generating ezee invoice report.", exception);
		}
	}

	private void generateEzeeInvoiceReport(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<EzeeInvoice> invoices = dao.get(EzeeInvoice.class);
		if (!isEmpty(invoices)) {
			Map<String, List<EzeeInvoice>> supplierInvoices = resolveInvoicesBySupplier(invoices);
			generateEzeeInvoiceReport(supplierInvoices, response);
		}
	}

	private void generateEzeeInvoiceReport(final Map<String, List<EzeeInvoice>> supplierInvoices,
			final HttpServletResponse response) throws IOException {
		String filename = UUID.randomUUID().toString() + ".xls";
		OutputStream stream = createExcelFilenameResponseHeader(response, filename);
		byte[] content = generateEzeeInvoiceReport(supplierInvoices);
		response.setContentLength(content.length);
		stream.write(content);
		stream.close();
	}

	private byte[] generateEzeeInvoiceReport(final Map<String, List<EzeeInvoice>> supplierInvoices) throws IOException {
		Workbook book = new HSSFWorkbook();
		Sheet sheet = book.createSheet("invoices");
		generateEzeeInvoiceReportHeader(book, sheet);
		generateEzeeInvoiceReportContent(book, sheet, supplierInvoices);
		formatReport(sheet);
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		book.write(result);
		result.close();
		return result.toByteArray();
	}

	private void formatReport(final Sheet sheet) {
	}

	private void generateEzeeInvoiceReportContent(final Workbook book, final Sheet sheet,
			final Map<String, List<EzeeInvoice>> supplierInvoices) {
		int row = ONE;
		for (String key : supplierInvoices.keySet()) {
			row += generateEzeeInvoiceReportContent(book, sheet, supplierInvoices.get(key), row);
		}
	}

	private int generateEzeeInvoiceReportContent(final Workbook book, final Sheet sheet,
			final List<EzeeInvoice> invoices, int row) {
		double amount = ZERO_DBL, tax = ZERO_DBL, total = ZERO_DBL;
		CellStyle currencyStyle = currencyStyle(book, false);
		CellStyle boldCurrencyStyle = currencyStyle(book, true);
		CellStyle boldStyle = boldStyle(book, false);
		String supplierName = invoices.get(EzeeCommonConstants.ZERO).getPayee().getName();
		for (EzeeInvoice invoice : invoices) {
			Row newrow = sheet.createRow(row);
			Cell invoiceNum = newrow.createCell(INVOICE_ID_INDEX, CELL_TYPE_STRING);
			invoiceNum.setCellValue(invoice.getInvoiceId());
			Cell supplier = newrow.createCell(SUPPLIER_INDEX, CELL_TYPE_STRING);
			supplier.setCellValue(invoice.getPayee().getName());
			Cell premises = newrow.createCell(PREMISES_INDEX, CELL_TYPE_STRING);
			premises.setCellValue(invoice.getPayer().getName());
			Cell classification = newrow.createCell(CLASSIFICATION_INDEX, CELL_TYPE_STRING);
			classification.setCellValue(invoice.getClassification().toString());
			Cell invoiceAmount = newrow.createCell(AMOUNT_INDEX, CELL_TYPE_NUMERIC);
			invoiceAmount.setCellValue(invoice.getAmount());
			invoiceAmount.setCellStyle(currencyStyle);
			Cell invoiceTax = newrow.createCell(TAX_INDEX, CELL_TYPE_NUMERIC);
			invoiceTax.setCellValue(invoice.getTax());
			invoiceTax.setCellStyle(currencyStyle);
			Cell invoiceTotal = newrow.createCell(TOTAL_INDEX, CELL_TYPE_NUMERIC);
			invoiceTotal.setCellValue(invoice.getInvoiceAmount());
			invoiceTotal.setCellStyle(currencyStyle);
			Cell invoiceDate = newrow.createCell(INVOICE_DATE_INDEX, CELL_TYPE_STRING);
			invoiceDate.setCellValue(invoice.getInvoiceDate());
			Cell dueDate = newrow.createCell(DUE_DATE_INDEX, CELL_TYPE_STRING);
			dueDate.setCellValue(invoice.getDateDue());
			Cell paymentDate = newrow.createCell(PAYMENT_DATE_INDEX, CELL_TYPE_STRING);
			paymentDate.setCellValue(invoice.getDatePaid());
			amount += invoice.getAmount();
			tax += invoice.getTax();
			total += invoice.getInvoiceAmount();
			row += ONE;
		}
		row += ONE;
		Row totalrow = sheet.createRow(row);
		Cell totalSummary = totalrow.createCell(INVOICE_ID_INDEX, CELL_TYPE_STRING);
		totalSummary.setCellStyle(boldStyle);
		totalSummary.setCellValue("Totals for '" + supplierName + " :");
		Cell totalAmount = totalrow.createCell(AMOUNT_INDEX, CELL_TYPE_NUMERIC);
		totalAmount.setCellStyle(boldCurrencyStyle);
		totalAmount.setCellValue(amount);
		Cell totalTax = totalrow.createCell(TAX_INDEX, CELL_TYPE_NUMERIC);
		totalTax.setCellStyle(boldCurrencyStyle);
		totalTax.setCellValue(tax);
		Cell grandTotal = totalrow.createCell(TOTAL_INDEX, CELL_TYPE_NUMERIC);
		grandTotal.setCellStyle(boldCurrencyStyle);
		grandTotal.setCellValue(total);
		row += ONE;
		return row;
	}

	private void generateEzeeInvoiceReportHeader(final Workbook book, final Sheet sheet) {
		Row header = sheet.createRow(ZERO);
		int[] indexes = INVOICE_REPORT_INDEXES;
		String[] fields = INVOICE_REPORT_FIELDS;
		CellStyle headerStyle = boldStyle(book, true);
		for (int i = ZERO; i < indexes.length; i++) {
			Cell cell = header.createCell(i, CELL_TYPE_STRING);
			cell.setCellValue(fields[i]);
			cell.setCellStyle(headerStyle);
		}
	}

	private Map<String, List<EzeeInvoice>> resolveInvoicesBySupplier(final List<EzeeInvoice> invoices) {
		Map<String, List<EzeeInvoice>> supplierInvoices = new HashMap<>();
		for (EzeeInvoice invoice : invoices) {
			String key = invoice.getPayee().getName();
			if (!supplierInvoices.containsKey(key)) {
				supplierInvoices.put(key, new ArrayList<>());
			}
			supplierInvoices.get(key).add(invoice);
		}
		return supplierInvoices;
	}
}