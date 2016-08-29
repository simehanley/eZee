package com.ezee.client.grid.leasefile;

import static com.ezee.client.EzeeLeaseWebConstants.LEASE_FILE_NAME;
import static com.ezee.client.EzeeLeaseWebConstants.LEASE_ID;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;
import static com.ezee.web.common.EzeeWebCommonConstants.FILE_DOWNLOAD_SERVICE;
import static com.ezee.web.common.ui.images.EzeeImageResources.INSTANCE;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.model.entity.lease.EzeeLeaseFile;
import com.ezee.web.common.ui.grid.EzeeGridModel;
import com.ezee.web.common.ui.utils.EzeeDateComparator;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.ImageResourceCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.Window;

public class EzeeLeaseFileGridModel extends EzeeGridModel<EzeeLeaseFile> {

	private static final String CLICK = "click";

	public static final String FILE_DATE = "Date";
	public static final String FILE_NAME = "File Name";
	public static final String FILE = "File";

	public static final double FILE_WIDTH = 50.;
	public static final double FILE_NAME_WIDTH = 600.;

	private EzeeLease lease;

	private final EzeeDateComparator dateComparator = new EzeeDateComparator();

	public EzeeLeaseFileGridModel() {
	}

	@Override
	protected Map<String, Column<EzeeLeaseFile, ?>> createColumns(final DataGrid<EzeeLeaseFile> grid) {
		Map<String, Column<EzeeLeaseFile, ?>> columns = new HashMap<>();
		createTextColumn(columns, grid, FILE_DATE, DATE_FIELD_WIDTH, true);
		createTextColumn(columns, grid, FILE_NAME, FILE_NAME_WIDTH, true);
		createImageColumn(columns, grid, FILE, FILE_WIDTH);
		return columns;
	}

	@Override
	protected String resolveTextFieldValue(final String fieldName, final EzeeLeaseFile entity) {
		switch (fieldName) {
		case FILE_DATE:
			return entity.getDate();
		default:
			return entity.getFilename();
		}
	}

	@Override
	protected void setTextFieldValue(final String fieldName, final String fieldValue, final EzeeLeaseFile entity) {
	}

	@Override
	protected Date resolveDateFieldValue(final String fieldName, final EzeeLeaseFile entity) {
		return null;
	}

	@Override
	protected void setDateFieldValue(final String fieldName, final Date fieldValue, final EzeeLeaseFile entity) {

	}

	@Override
	protected void addComparators(final Map<String, Column<EzeeLeaseFile, ?>> columns) {
		handler.setComparator(columns.get(FILE_DATE), new Comparator<EzeeLeaseFile>() {
			@Override
			public int compare(final EzeeLeaseFile one, final EzeeLeaseFile two) {
				return dateComparator.compare(DATE_UTILS.fromString(one.getDate()),
						DATE_UTILS.fromString(two.getDate()));
			}
		});
		handler.setComparator(columns.get(FILE_NAME), new Comparator<EzeeLeaseFile>() {
			@Override
			public int compare(final EzeeLeaseFile one, final EzeeLeaseFile two) {
				return one.getFilename().compareTo(two.getFilename());
			}
		});
	}

	@Override
	protected void addSortColumns(final DataGrid<EzeeLeaseFile> grid,
			final Map<String, Column<EzeeLeaseFile, ?>> columns) {
		grid.getColumnSortList().push(columns.get(FILE_DATE));
		grid.getColumnSortList().push(columns.get(FILE_NAME));
	}

	@Override
	protected boolean resolveBooleanFieldValue(final String fieldName, final EzeeLeaseFile entity) {
		return false;
	}

	@Override
	protected void setBooleanFieldValue(final String fieldName, final boolean fieldValue, final EzeeLeaseFile entity) {
	}

	private void createImageColumn(final Map<String, Column<EzeeLeaseFile, ?>> columns,
			final DataGrid<EzeeLeaseFile> grid, final String fieldName, final double width) {

		if (!isHiddenColumn(fieldName)) {
			ImageResourceCell cell = new ImageResourceCell() {

				public Set<String> getConsumedEvents() {
					Set<String> events = new HashSet<String>();
					events.add(CLICK);
					return events;
				}
			};

			Column<EzeeLeaseFile, ImageResource> column = new Column<EzeeLeaseFile, ImageResource>(cell) {
				@Override
				public ImageResource getValue(final EzeeLeaseFile file) {
					if (hasLength(file.getFilename())) {
						String contentType = file.getContentType();
						switch (contentType) {
						case "application/msword":
						case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
							return INSTANCE.word();
						case "application/octet-stream":
						case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
							return INSTANCE.excel();
						case "image/jpeg":
						case "image/png":
							return INSTANCE.image();
						default:
							return INSTANCE.pdf();
						}
					}
					return null;
				}

				@Override
				public void onBrowserEvent(final Context context, final Element elem, final EzeeLeaseFile file,
						final NativeEvent event) {
					if (CLICK.equals(event.getType())) {
						if (hasLength(file.getFilename())) {
							downloadLeaseFile(file);
						}
					}
				}

			};
			column.setHorizontalAlignment(ALIGN_CENTER);
			createColumn(columns, grid, column, fieldName, width, false);
		}
	}

	private void downloadLeaseFile(final EzeeLeaseFile file) {
		String downloadUrl = GWT.getModuleBaseURL() + FILE_DOWNLOAD_SERVICE + "?" + LEASE_ID + "=" + lease.getId() + "&"
				+ LEASE_FILE_NAME + "=" + file.getFilename();
		Window.Location.assign(downloadUrl);
	}

	public void setLease(EzeeLease lease) {
		this.lease = lease;
	}
}