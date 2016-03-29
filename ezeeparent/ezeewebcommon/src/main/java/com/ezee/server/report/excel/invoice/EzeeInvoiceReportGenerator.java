package com.ezee.server.report.excel.invoice;

import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.common.EzeeCommonConstants.TWO;
import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.server.EzeeServerDateUtils.SERVER_DATE_UTILS;
import static com.ezee.web.common.EzeeWebCommonConstants.EXCEL_INVOICE_DATE_FROM_FILTER;
import static com.ezee.web.common.EzeeWebCommonConstants.EXCEL_INVOICE_DATE_TO_FILTER;
import static com.ezee.web.common.EzeeWebCommonConstants.EXCEL_INVOICE_INCLUDE_PAID_FILTER;
import static com.ezee.web.common.EzeeWebCommonConstants.EXCEL_INVOICE_INVOICES_FILTER;
import static com.ezee.web.common.EzeeWebCommonConstants.EXCEL_INVOICE_PREMISES_FILTER;
import static com.ezee.web.common.EzeeWebCommonConstants.EXCEL_INVOICE_SUPPLIER_FILTER;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
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
import com.ezee.model.entity.filter.invoice.EzeeInvoiceFilter;
import com.ezee.server.report.EzeeReportGenerator;
import com.ezee.server.report.excel.AbstractExcelReportGenerator;
import com.ezee.web.common.ui.utils.EzeeDateComparator;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceReportGenerator extends AbstractExcelReportGenerator implements EzeeReportGenerator {

	private static final Logger log = LoggerFactory.getLogger(EzeeInvoiceReportGenerator.class);

	@Autowired
	private EzeeInvoiceDao dao;

	private static final EzeeInvoiceSupplierNameCompataor EZEE_INVOICE_SUPLIER_NAME_COMPARATOR = new EzeeInvoiceSupplierNameCompataor();

	private static final EzeeInvoiceDateCompataor EZEE_INVOICE_DATE_COMPARATOR = new EzeeInvoiceDateCompataor();

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
		List<EzeeInvoice> invoices = getInvoices(request);
		Collections.sort(invoices, EZEE_INVOICE_SUPLIER_NAME_COMPARATOR);
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
		generateExcelReportHeader(book, sheet, INVOICE_REPORT_INDEXES, INVOICE_REPORT_FIELDS);
		generateEzeeInvoiceReportContent(book, sheet, supplierInvoices);
		formatReport(sheet, INVOICE_ID_INDEX, PAYMENT_DATE_INDEX);
		setPrintArea(sheet);
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		book.write(result);
		result.close();
		return result.toByteArray();
	}

	private void generateEzeeInvoiceReportContent(final Workbook book, final Sheet sheet,
			final Map<String, List<EzeeInvoice>> supplierInvoices) {
		int currentRow = ONE;
		for (String key : supplierInvoices.keySet()) {
			currentRow += generateEzeeInvoiceReportContent(book, sheet, supplierInvoices.get(key), currentRow);
		}
		if (supplierInvoices.size() > ONE) {
			generateEzeeInvoiceReportTotal(book, sheet, supplierInvoices, currentRow);
		}
	}

	private void generateEzeeInvoiceReportTotal(final Workbook book, final Sheet sheet,
			final Map<String, List<EzeeInvoice>> supplierInvoices, int currentRow) {
		double expenseamount = ZERO_DBL, expesetax = ZERO_DBL, expensetotal = ZERO_DBL;
		double capitalamount = ZERO_DBL, capitaltax = ZERO_DBL, capitaltotal = ZERO_DBL;
		double amount = ZERO_DBL, tax = ZERO_DBL, total = ZERO_DBL;
		CellStyle boldStyle = boldStyle(book, false, false);
		CellStyle boldCurrencyStyle = currencyStyle(book, true, false);
		for (String key : supplierInvoices.keySet()) {
			List<EzeeInvoice> invoices = supplierInvoices.get(key);
			for (EzeeInvoice invoice : invoices) {
				switch (invoice.getClassification()) {
				case captial:
					capitalamount += invoice.getAmount();
					capitaltax += invoice.getTax();
					capitaltotal += invoice.getInvoiceAmount();
					break;
				default:
					expenseamount += invoice.getAmount();
					expesetax += invoice.getTax();
					expensetotal += invoice.getInvoiceAmount();
					break;
				}
				amount += invoice.getAmount();
				tax += invoice.getTax();
				total += invoice.getInvoiceAmount();
			}
		}

		/* expense total */
		generateEzeeInvoiceReportContentTotalRow(sheet, currentRow, boldStyle, boldCurrencyStyle, expenseamount,
				expesetax, expensetotal, "Grand Total Expenses :");

		/* capital total */
		currentRow += TWO;
		generateEzeeInvoiceReportContentTotalRow(sheet, currentRow, boldStyle, boldCurrencyStyle, capitalamount,
				capitaltax, capitaltotal, "Grand Total Capital :");

		/* supplier total */
		currentRow += TWO;
		generateEzeeInvoiceReportContentTotalRow(sheet, currentRow, boldStyle, boldCurrencyStyle, amount, tax, total,
				"Grand Total :");

	}

	private int generateEzeeInvoiceReportContent(final Workbook book, final Sheet sheet,
			final List<EzeeInvoice> invoices, int currentRow) {

		double expenseamount = ZERO_DBL, expesetax = ZERO_DBL, expensetotal = ZERO_DBL;
		double capitalamount = ZERO_DBL, capitaltax = ZERO_DBL, capitaltotal = ZERO_DBL;
		double amount = ZERO_DBL, tax = ZERO_DBL, total = ZERO_DBL;

		CellStyle currencyStyle = currencyStyle(book, false, false);
		CellStyle boldCurrencyStyle = currencyStyle(book, true, false);
		CellStyle boldStyle = boldStyle(book, false, false);
		int rowsAdded = ZERO;
		String supplierName = invoices.get(EzeeCommonConstants.ZERO).getSupplier().getName();
		Collections.sort(invoices, EZEE_INVOICE_DATE_COMPARATOR);
		for (EzeeInvoice invoice : invoices) {
			Row newrow = sheet.createRow(currentRow);
			Cell invoiceNum = newrow.createCell(INVOICE_ID_INDEX, CELL_TYPE_STRING);
			invoiceNum.setCellValue(invoice.getInvoiceId());
			Cell supplier = newrow.createCell(SUPPLIER_INDEX, CELL_TYPE_STRING);
			supplier.setCellValue(invoice.getSupplier().getName());
			Cell premises = newrow.createCell(PREMISES_INDEX, CELL_TYPE_STRING);
			premises.setCellValue(invoice.getPremises().getName());
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
			switch (invoice.getClassification()) {
			case captial:
				capitalamount += invoice.getAmount();
				capitaltax += invoice.getTax();
				capitaltotal += invoice.getInvoiceAmount();
				break;
			default:
				expenseamount += invoice.getAmount();
				expesetax += invoice.getTax();
				expensetotal += invoice.getInvoiceAmount();
				break;
			}
			amount += invoice.getAmount();
			tax += invoice.getTax();
			total += invoice.getInvoiceAmount();
			++currentRow;
			++rowsAdded;
		}
		++currentRow;
		++rowsAdded;

		/* expense total */
		generateEzeeInvoiceReportContentTotalRow(sheet, currentRow, boldStyle, boldCurrencyStyle, expenseamount,
				expesetax, expensetotal, "Expenses For " + supplierName + " :");

		/* capital total */
		currentRow += TWO;
		rowsAdded += TWO;
		generateEzeeInvoiceReportContentTotalRow(sheet, currentRow, boldStyle, boldCurrencyStyle, capitalamount,
				capitaltax, capitaltotal, "Capital For " + supplierName + " :");

		/* supplier total */
		currentRow += TWO;
		rowsAdded += TWO;
		generateEzeeInvoiceReportContentTotalRow(sheet, currentRow, boldStyle, boldCurrencyStyle, amount, tax, total,
				"Total For " + supplierName + " :");

		rowsAdded += TWO;

		return rowsAdded;
	}

	private void generateEzeeInvoiceReportContentTotalRow(final Sheet sheet, final int currentRow,
			final CellStyle boldStyle, final CellStyle boldCurrencyStyle, final double amount, final double tax,
			final double total, final String summary) {
		Row totalrow = sheet.createRow(currentRow);
		Cell totalSummary = totalrow.createCell(INVOICE_ID_INDEX, CELL_TYPE_STRING);
		totalSummary.setCellStyle(boldStyle);
		totalSummary.setCellValue(summary);
		Cell totalAmount = totalrow.createCell(AMOUNT_INDEX, CELL_TYPE_NUMERIC);
		totalAmount.setCellStyle(boldCurrencyStyle);
		totalAmount.setCellValue(amount);
		Cell totalTax = totalrow.createCell(TAX_INDEX, CELL_TYPE_NUMERIC);
		totalTax.setCellStyle(boldCurrencyStyle);
		totalTax.setCellValue(tax);
		Cell grandTotal = totalrow.createCell(TOTAL_INDEX, CELL_TYPE_NUMERIC);
		grandTotal.setCellStyle(boldCurrencyStyle);
		grandTotal.setCellValue(total);
	}

	private Map<String, List<EzeeInvoice>> resolveInvoicesBySupplier(final List<EzeeInvoice> invoices) {
		Map<String, List<EzeeInvoice>> supplierInvoices = new LinkedHashMap<>();
		for (EzeeInvoice invoice : invoices) {
			String key = invoice.getSupplier().getName();
			if (!supplierInvoices.containsKey(key)) {
				supplierInvoices.put(key, new ArrayList<>());
			}
			supplierInvoices.get(key).add(invoice);
		}
		return supplierInvoices;
	}

	private List<EzeeInvoice> getInvoices(final HttpServletRequest request) {
		return dao.get(resolveFilter(request));
	}

	private EzeeInvoiceFilter resolveFilter(final HttpServletRequest request) {
		String supplier = request.getParameter(EXCEL_INVOICE_SUPPLIER_FILTER);
		String premises = request.getParameter(EXCEL_INVOICE_PREMISES_FILTER);
		String invoiceIds = request.getParameter(EXCEL_INVOICE_INVOICES_FILTER);
		String from = request.getParameter(EXCEL_INVOICE_DATE_FROM_FILTER);
		String to = request.getParameter(EXCEL_INVOICE_DATE_TO_FILTER);
		boolean includePaid = Boolean.valueOf(request.getParameter(EXCEL_INVOICE_INCLUDE_PAID_FILTER));
		return new EzeeInvoiceFilter(supplier, premises, invoiceIds, SERVER_DATE_UTILS.fromString(from),
				SERVER_DATE_UTILS.fromString(to), SERVER_DATE_UTILS, includePaid);
	}

	private static class EzeeInvoiceSupplierNameCompataor implements Comparator<EzeeInvoice> {

		@Override
		public int compare(final EzeeInvoice one, final EzeeInvoice two) {
			return one.getSupplier().getName().compareTo(two.getSupplier().getName());
		}
	}

	private static class EzeeInvoiceDateCompataor implements Comparator<EzeeInvoice> {

		private final EzeeDateComparator dateComparator = new EzeeDateComparator();

		@Override
		public int compare(final EzeeInvoice one, final EzeeInvoice two) {
			return dateComparator.compare(SERVER_DATE_UTILS.fromString(one.getInvoiceDate()),
					SERVER_DATE_UTILS.fromString(two.getInvoiceDate()));
		}
	}
}