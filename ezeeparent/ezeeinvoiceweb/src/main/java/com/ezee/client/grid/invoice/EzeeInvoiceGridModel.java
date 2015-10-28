package com.ezee.client.grid.invoice;

import static com.ezee.client.css.EzeeInvoiceDefaultResources.INSTANCE;
import static com.ezee.common.web.EzeeFromatUtils.getAmountFormat;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ezee.client.grid.EzeeGridModel;
import com.ezee.client.images.EzeeInvoiceImages;
import com.ezee.common.string.EzeeStringUtils;
import com.ezee.model.entity.EzeeInvoice;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceGridModel extends EzeeGridModel<EzeeInvoice> {

	private static final String INVOICE_NUM = "Invoice Num";
	private static final String SUPPLIER = "Supplier";
	private static final String PREMISES = "Premises";
	private static final String AMOUNT = "Amount";
	private static final String TAX = "Tax";
	private static final String TOTAL = "Total";
	private static final String DESCRIPTION = "Description";
	private static final String CREATED_DATE = "Created";
	private static final String DUE_DATE = "Due";
	private static final String PAID_PATE = "Paid";

	/* tmp */
	private static final String FILE = "File";
	private static final String FILE_WIDTH = "100px";

	private static final String INVOICE_NUM_WIDTH = "200px";
	private static final String SUPPLIER_WIDTH = "300px";
	private static final String PREMISES_WIDTH = "300px";
	private static final String DESCRIPTION_WIDTH = "400px";

	@Override
	protected Map<String, Column<EzeeInvoice, ?>> createColumns(final DataGrid<EzeeInvoice> grid) {
		Map<String, Column<EzeeInvoice, ?>> columns = new HashMap<>();
		createTextColumn(columns, grid, INVOICE_NUM, INVOICE_NUM_WIDTH, true);
		createTextColumn(columns, grid, SUPPLIER, SUPPLIER_WIDTH, true);
		createTextColumn(columns, grid, PREMISES, PREMISES_WIDTH, true);
		createNumericColumn(columns, grid, AMOUNT, NUMERIC_FIELD_WIDTH);
		createNumericColumn(columns, grid, TAX, NUMERIC_FIELD_WIDTH);
		createNumericColumn(columns, grid, TOTAL, NUMERIC_FIELD_WIDTH);
		createTextColumn(columns, grid, DESCRIPTION, DESCRIPTION_WIDTH, false);
		createDateColumn(columns, grid, CREATED_DATE, DATE_FIELD_WIDTH, true);
		createDateColumn(columns, grid, DUE_DATE, DATE_FIELD_WIDTH, true);
		createDateColumn(columns, grid, PAID_PATE, DATE_FIELD_WIDTH, true);
		createImageColumn(columns, grid, FILE, FILE_WIDTH);
		return columns;
	}

	@Override
	protected String resolveTextFieldValue(final String fieldName, final EzeeInvoice invoice) {
		switch (fieldName) {
		case INVOICE_NUM:
			return invoice.getInvoiceId();
		case SUPPLIER:
			return invoice.getPayee().getName();
		case PREMISES:
			return invoice.getPayer().getName();
		case DESCRIPTION:
			return invoice.getDescription();
		case AMOUNT:
			return getAmountFormat().format(invoice.getAmount());
		case TAX:
			return getAmountFormat().format(invoice.getTax());
		case TOTAL:
			return getAmountFormat().format(invoice.getInvoiceAmount());
		case FILE:
			return invoice.getFilename();
		}
		return null;
	}

	@Override
	protected Date resolveDateFieldValue(final String fieldName, final EzeeInvoice invoice) {
		switch (fieldName) {
		case CREATED_DATE:
			return invoice.getCreated();
		case DUE_DATE:
			return invoice.getDateDue();
		case PAID_PATE:
			return invoice.getDatePaid();
		}
		return null;
	}

	@Override
	protected boolean resolveBooleanFieldValue(final String fieldName, final EzeeInvoice invoice) {
		return invoice.isPaid();
	}

	@Override
	protected void addComparators(final Map<String, Column<EzeeInvoice, ?>> columns) {

		handler.setComparator(columns.get(INVOICE_NUM), new Comparator<EzeeInvoice>() {

			@Override
			public int compare(final EzeeInvoice one, final EzeeInvoice two) {
				return one.getInvoiceId().compareTo(two.getInvoiceId());
			}
		});

		handler.setComparator(columns.get(SUPPLIER), new Comparator<EzeeInvoice>() {

			@Override
			public int compare(final EzeeInvoice one, final EzeeInvoice two) {
				return one.getPayee().getName().compareTo(two.getPayee().getName());
			}
		});

		handler.setComparator(columns.get(PREMISES), new Comparator<EzeeInvoice>() {

			@Override
			public int compare(final EzeeInvoice one, final EzeeInvoice two) {
				return one.getPayer().getName().compareTo(two.getPayer().getName());
			}
		});

		handler.setComparator(columns.get(CREATED_DATE), new Comparator<EzeeInvoice>() {

			@Override
			public int compare(final EzeeInvoice one, final EzeeInvoice two) {
				return one.getCreated().compareTo(two.getCreated());
			}
		});

		handler.setComparator(columns.get(DUE_DATE), new Comparator<EzeeInvoice>() {

			@Override
			public int compare(final EzeeInvoice one, final EzeeInvoice two) {
				return one.getDateDue().compareTo(two.getDateDue());
			}
		});

		handler.setComparator(columns.get(PAID_PATE), new Comparator<EzeeInvoice>() {

			@Override
			public int compare(final EzeeInvoice one, final EzeeInvoice two) {
				return one.getDatePaid().compareTo(two.getDatePaid());
			}
		});
	}

	@Override
	protected void addSortColumns(final DataGrid<EzeeInvoice> grid, final Map<String, Column<EzeeInvoice, ?>> columns) {
		grid.getColumnSortList().push(columns.get(INVOICE_NUM));
		grid.getColumnSortList().push(columns.get(SUPPLIER));
		grid.getColumnSortList().push(columns.get(PREMISES));
		grid.getColumnSortList().push(columns.get(CREATED_DATE));
		grid.getColumnSortList().push(columns.get(DUE_DATE));
		grid.getColumnSortList().push(columns.get(PAID_PATE));
	}

	@Override
	protected String resolveCellStyleNames(final EzeeInvoice invoice) {
		if (invoice.isPaid()) {
			return INSTANCE.css().greenforeground();
		}
		return INSTANCE.css().lightorangeforeground();
	}

	protected void createImageColumn(final Map<String, Column<EzeeInvoice, ?>> columns,
			final DataGrid<EzeeInvoice> grid, final String fieldName, final String width) {
		Column<EzeeInvoice, String> column = new Column<EzeeInvoice, String>(new ImageCell()) {
			@Override
			public String getValue(final EzeeInvoice invoice) {
				if (EzeeStringUtils.hasLength(invoice.getFilename())) {
					return EzeeInvoiceImages.INSTANCE.pdf().getSafeUri().asString();
				}
				return null;
			}

		};
		createColumn(columns, grid, column, fieldName, width, false);
	}
}