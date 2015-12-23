package com.ezee.server.report.excel.financialentity;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.EzeeCommonConstants.ONE;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.web.common.EzeeWebCommonConstants.EXCEL_FE_FILTER;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezee.model.entity.EzeeFinancialEntity;
import com.ezee.model.entity.filter.EzeeStringFilter;
import com.ezee.server.report.EzeeReportGenerator;
import com.ezee.server.report.excel.AbstractExcelReportGenerator;

public abstract class EzeeFinancialEntityReportGenerator<T extends EzeeFinancialEntity>
		extends AbstractExcelReportGenerator implements EzeeReportGenerator {

	private static final Logger log = LoggerFactory.getLogger(EzeeFinancialEntityReportGenerator.class);

	@Override
	public void generateReport(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			generateEzeeFeReport(request, response);
		} catch (IOException exception) {
			log.error("Error generating report.", exception);
		}
	}

	private void generateEzeeFeReport(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		String filename = UUID.randomUUID().toString() + ".xls";
		OutputStream stream = createExcelFilenameResponseHeader(response, filename);
		List<T> entities = getEntites(request);
		byte[] content = new byte[] {};
		if (!isEmpty(entities)) {
			Collections.sort(entities);
			content = generateEzeeFeReport(entities);
		}
		response.setContentLength(content.length);
		stream.write(content);
		stream.close();
	}

	private byte[] generateEzeeFeReport(final List<T> entities) throws IOException {
		Workbook book = new HSSFWorkbook();
		Sheet sheet = book.createSheet("entities");
		generateExcelReportHeader(book, sheet, FE_REPORT_INDEXES, FE_REPORT_FIELDS);
		generateEzeeFeReportContent(book, sheet, entities);
		formatReport(sheet, FE_NAME_INDEX, FE_EMAIL_INDEX);
		setPrintArea(sheet);
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		book.write(result);
		result.close();
		return result.toByteArray();
	}

	private void generateEzeeFeReportContent(final Workbook book, final Sheet sheet, final List<T> entities) {
		int currentRow = ONE;
		for (T entity : entities) {
			generateEzeeFeReportContent(book, sheet, entity, currentRow);
			currentRow++;
		}
	}

	private void generateEzeeFeReportContent(final Workbook book, final Sheet sheet, final T entity,
			final int currentRow) {
		Row row = sheet.createRow(currentRow);
		Cell name = row.createCell(FE_NAME_INDEX);
		name.setCellValue(entity.getName() != null ? entity.getName() : EMPTY_STRING);
		Cell contact = row.createCell(FE_CONTACT_INDEX);
		contact.setCellValue(entity.getContact() != null ? entity.getContact() : EMPTY_STRING);
		Cell address1 = row.createCell(FE_ADDRESS_LINE_1_INDEX);
		address1.setCellValue(entity.getAddressLineOne() != null ? entity.getAddressLineOne() : EMPTY_STRING);
		Cell address2 = row.createCell(FE_ADDRESS_LINE_2_INDEX);
		address2.setCellValue(entity.getAddressLineTwo() != null ? entity.getAddressLineTwo() : EMPTY_STRING);
		Cell suburb = row.createCell(FE_SUBURB_INDEX);
		suburb.setCellValue(entity.getSuburb() != null ? entity.getSuburb() : EMPTY_STRING);
		Cell city = row.createCell(FE_CITY_INDEX);
		city.setCellValue(entity.getCity() != null ? entity.getCity() : EMPTY_STRING);
		Cell state = row.createCell(FE_STATE_INDEX);
		state.setCellValue(entity.getState() != null ? entity.getState() : EMPTY_STRING);
		Cell postcode = row.createCell(FE_POSTCODE_INDEX);
		postcode.setCellValue(entity.getPostcode() != null ? entity.getPostcode() : EMPTY_STRING);
		Cell phone = row.createCell(FE_PHONE_INDEX);
		phone.setCellValue(entity.getPhone() != null ? entity.getPhone() : EMPTY_STRING);
		Cell fax = row.createCell(FE_FAX_INDEX);
		fax.setCellValue(entity.getFax() != null ? entity.getFax() : EMPTY_STRING);
		Cell email = row.createCell(FE_EMAIL_INDEX);
		email.setCellValue(entity.getEmail() != null ? entity.getEmail() : EMPTY_STRING);
	}

	protected abstract List<T> getEntites(HttpServletRequest request);

	protected EzeeStringFilter<T> resolveFilter(final HttpServletRequest request) {
		String entity = request.getParameter(EXCEL_FE_FILTER);
		return new EzeeStringFilter<T>(entity, false);
	}
}