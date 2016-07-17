package com.ezee.client.grid.leasenote;

import static com.ezee.web.common.EzeeWebCommonConstants.DATE_UTILS;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ezee.model.entity.lease.EzeeLeaseNote;
import com.ezee.web.common.ui.grid.EzeeGridModel;
import com.ezee.web.common.ui.utils.EzeeDateComparator;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;

public class EzeeLeaseNoteGridModel extends EzeeGridModel<EzeeLeaseNote> {

	public static final String NOTE_DATE = "Date";
	public static final String NOTE_VALUE = "Note";

	public static final double NOTE_VALUE_WIDTH = 600.;

	private final EzeeDateComparator dateComparator = new EzeeDateComparator();

	@Override
	protected Map<String, Column<EzeeLeaseNote, ?>> createColumns(final DataGrid<EzeeLeaseNote> grid) {
		Map<String, Column<EzeeLeaseNote, ?>> columns = new HashMap<>();
		createTextColumn(columns, grid, NOTE_DATE, DATE_FIELD_WIDTH, true);
		createTextColumn(columns, grid, NOTE_VALUE, NOTE_VALUE_WIDTH, false);
		return columns;
	}

	@Override
	protected String resolveTextFieldValue(final String fieldName, final EzeeLeaseNote entity) {
		switch (fieldName) {
		case NOTE_DATE:
			return entity.getDate();
		default:
			return entity.getNote();
		}
	}

	@Override
	protected void setTextFieldValue(final String fieldName, final String fieldValue, final EzeeLeaseNote entity) {
	}

	@Override
	protected Date resolveDateFieldValue(final String fieldName, final EzeeLeaseNote entity) {
		return null;
	}

	@Override
	protected void setDateFieldValue(final String fieldName, final Date fieldValue, final EzeeLeaseNote entity) {

	}

	@Override
	protected void addComparators(final Map<String, Column<EzeeLeaseNote, ?>> columns) {
		handler.setComparator(columns.get(NOTE_DATE), new Comparator<EzeeLeaseNote>() {
			@Override
			public int compare(final EzeeLeaseNote one, final EzeeLeaseNote two) {
				return dateComparator.compare(DATE_UTILS.fromString(one.getDate()),
						DATE_UTILS.fromString(two.getDate()));
			}
		});
	}

	@Override
	protected void addSortColumns(final DataGrid<EzeeLeaseNote> grid,
			final Map<String, Column<EzeeLeaseNote, ?>> columns) {
		grid.getColumnSortList().push(columns.get(NOTE_DATE));
	}

	@Override
	protected boolean resolveBooleanFieldValue(final String fieldName, final EzeeLeaseNote entity) {
		return false;
	}

	@Override
	protected void setBooleanFieldValue(final String fieldName, final boolean fieldValue, final EzeeLeaseNote entity) {
	}
}