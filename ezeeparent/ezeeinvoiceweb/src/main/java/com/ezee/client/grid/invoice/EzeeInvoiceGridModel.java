package com.ezee.client.grid.invoice;

import static com.ezee.client.EzeeInvoiceWebConstants.FILE_DOWNLOAD_SERVICE;
import static com.ezee.client.EzeeInvoiceWebConstants.INVOICE_ID;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.common.web.EzeeFromatUtils.getAmountFormat;
import static com.ezee.web.common.ui.css.EzeeDefaultResources.INSTANCE;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ezee.client.grid.EzeeGridModel;
import com.ezee.client.util.EzeeDateComparator;
import com.ezee.common.web.EzeeClientDateUtils;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.web.common.ui.images.EzeeImageResources;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.ImageResourceCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.Window;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceGridModel extends EzeeGridModel<EzeeInvoice> {

	private static final String CLICK = "click";

	public static final String INVOICE_NUM = "Invoice Num";
	public static final String SUPPLIER = "Supplier";
	public static final String PREMISES = "Premises";
	public static final String TOTAL = "Total";
	public static final String INVOICE_DATE = "Invoice";
	public static final String DUE_DATE = "Due";
	public static final String PAID_PATE = "Paid";
	public static final String FILE = "File";

	private final EzeeDateComparator dateComparator = new EzeeDateComparator();

	public EzeeInvoiceGridModel() {
		super();
	}

	public EzeeInvoiceGridModel(Set<String> hiddenColumns) {
		super(hiddenColumns);
	}

	@Override
	protected Map<String, Column<EzeeInvoice, ?>> createColumns(final DataGrid<EzeeInvoice> grid) {
		Map<String, Column<EzeeInvoice, ?>> columns = new HashMap<>();
		createTextColumn(columns, grid, INVOICE_NUM, true);
		createTextColumn(columns, grid, SUPPLIER, true);
		createTextColumn(columns, grid, PREMISES, true);
		createNumericColumn(columns, grid, TOTAL);
		createDateColumn(columns, grid, INVOICE_DATE, true);
		createDateColumn(columns, grid, DUE_DATE, true);
		createDateColumn(columns, grid, PAID_PATE, true);
		createImageColumn(columns, grid, FILE);
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
		case INVOICE_DATE:
			return EzeeClientDateUtils.fromString(invoice.getInvoiceDate());
		case DUE_DATE:
			return EzeeClientDateUtils.fromString(invoice.getDateDue());
		case PAID_PATE:
			return EzeeClientDateUtils.fromString(invoice.getDatePaid());
		}
		return null;
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

		handler.setComparator(columns.get(INVOICE_DATE), new Comparator<EzeeInvoice>() {

			@Override
			public int compare(final EzeeInvoice one, final EzeeInvoice two) {
				return dateComparator.compare(EzeeClientDateUtils.fromString(one.getInvoiceDate()),
						EzeeClientDateUtils.fromString(two.getInvoiceDate()));
			}
		});

		handler.setComparator(columns.get(DUE_DATE), new Comparator<EzeeInvoice>() {

			@Override
			public int compare(final EzeeInvoice one, final EzeeInvoice two) {
				return dateComparator.compare(EzeeClientDateUtils.fromString(one.getDateDue()),
						EzeeClientDateUtils.fromString(two.getDateDue()));
			}

		});

		handler.setComparator(columns.get(PAID_PATE), new Comparator<EzeeInvoice>() {

			@Override
			public int compare(final EzeeInvoice one, final EzeeInvoice two) {
				return dateComparator.compare(EzeeClientDateUtils.fromString(one.getDatePaid()),
						EzeeClientDateUtils.fromString(two.getDatePaid()));
			}
		});
	}

	@Override
	protected void addSortColumns(final DataGrid<EzeeInvoice> grid, final Map<String, Column<EzeeInvoice, ?>> columns) {
		grid.getColumnSortList().push(columns.get(INVOICE_NUM));
		grid.getColumnSortList().push(columns.get(SUPPLIER));
		grid.getColumnSortList().push(columns.get(PREMISES));
		grid.getColumnSortList().push(columns.get(INVOICE_DATE));
		grid.getColumnSortList().push(columns.get(DUE_DATE));
		grid.getColumnSortList().push(columns.get(PAID_PATE));
	}

	@Override
	protected String resolveCellStyleNames(final EzeeInvoice invoice) {
		boolean paid = (invoice.getDatePaid() != null);
		if (paid) {
			return INSTANCE.css().greenforeground();
		}
		return INSTANCE.css().lightorangeforeground();
	}

	protected void createImageColumn(final Map<String, Column<EzeeInvoice, ?>> columns,
			final DataGrid<EzeeInvoice> grid, final String fieldName) {

		if (!isHiddenColumn(fieldName)) {
			ImageResourceCell cell = new ImageResourceCell() {

				public Set<String> getConsumedEvents() {
					Set<String> events = new HashSet<String>();
					events.add(CLICK);
					return events;
				}
			};

			Column<EzeeInvoice, ImageResource> column = new Column<EzeeInvoice, ImageResource>(cell) {
				@Override
				public ImageResource getValue(final EzeeInvoice invoice) {
					if (hasLength(invoice.getFilename())) {
						return EzeeImageResources.INSTANCE.pdf();
					}
					return null;
				}

				@Override
				public void onBrowserEvent(final Context context, final Element elem, final EzeeInvoice invoice,
						final NativeEvent event) {
					if (CLICK.equals(event.getType())) {
						if (hasLength(invoice.getFilename())) {
							downloadInvoiceFile(invoice);
						}
					}
				}

			};
			column.setHorizontalAlignment(ALIGN_CENTER);
			createColumn(columns, grid, column, fieldName, false);
		}
	}

	private void downloadInvoiceFile(final EzeeInvoice invoice) {
		String downloadUrl = GWT.getModuleBaseURL() + FILE_DOWNLOAD_SERVICE + "?" + INVOICE_ID + "=" + invoice.getId();
		Window.Location.assign(downloadUrl);
	}
}