package com.ezee.client.grid.leasemetadata;

import static com.ezee.common.EzeeCommonConstants.ZERO;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ezee.model.entity.lease.EzeeLeaseMetaData;
import com.ezee.web.common.ui.grid.EzeeGridModel;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;

public class EzeeLeaseMetaDataGridModel extends EzeeGridModel<EzeeLeaseMetaData> {

	public static final String META_DATA_TYPE = "Type";
	public static final String META_DATA_DESC = "Description";
	public static final String META_DATA_VALUE = "Value";

	public static final double META_DATA_TYPE_WIDTH = 150.;
	public static final double META_DATA_DESC_WIDTH = 300.;
	public static final double META_DATA_VALUE_WIDTH = 150;

	@Override
	protected Map<String, Column<EzeeLeaseMetaData, ?>> createColumns(DataGrid<EzeeLeaseMetaData> grid) {
		Map<String, Column<EzeeLeaseMetaData, ?>> columns = new HashMap<>();
		createTextColumn(columns, grid, META_DATA_TYPE, META_DATA_TYPE_WIDTH, true);
		createTextColumn(columns, grid, META_DATA_DESC, META_DATA_DESC_WIDTH, true);
		createTextColumn(columns, grid, META_DATA_VALUE, META_DATA_VALUE_WIDTH, true);
		return columns;
	}

	@Override
	protected String resolveTextFieldValue(String fieldName, EzeeLeaseMetaData entity) {
		switch (fieldName) {
		case META_DATA_TYPE:
			return entity.getType();
		case META_DATA_DESC:
			return entity.getDescription();
		default:
			return entity.getValue();
		}
	}

	@Override
	protected void setTextFieldValue(String fieldName, String fieldValue, EzeeLeaseMetaData entity) {
	}

	@Override
	protected Date resolveDateFieldValue(String fieldName, EzeeLeaseMetaData entity) {
		return null;
	}

	@Override
	protected void setDateFieldValue(String fieldName, Date fieldValue, EzeeLeaseMetaData entity) {
	}

	@Override
	protected void addComparators(Map<String, Column<EzeeLeaseMetaData, ?>> columns) {
		handler.setComparator(columns.get(META_DATA_TYPE), new Comparator<EzeeLeaseMetaData>() {
			@Override
			public int compare(final EzeeLeaseMetaData one, final EzeeLeaseMetaData two) {
				return one.getType().compareTo(two.getType());
			}
		});
		handler.setComparator(columns.get(META_DATA_DESC), new Comparator<EzeeLeaseMetaData>() {
			@Override
			public int compare(final EzeeLeaseMetaData one, final EzeeLeaseMetaData two) {
				return one.getDescription().compareTo(two.getDescription());
			}
		});
		handler.setComparator(columns.get(META_DATA_VALUE), new Comparator<EzeeLeaseMetaData>() {
			@Override
			public int compare(final EzeeLeaseMetaData one, final EzeeLeaseMetaData two) {
				return one.getValue().compareTo(two.getValue());
			}
		});
	}

	@Override
	protected void addSortColumns(DataGrid<EzeeLeaseMetaData> grid, Map<String, Column<EzeeLeaseMetaData, ?>> columns) {
		grid.getColumnSortList().push(columns.get(META_DATA_TYPE));
		grid.getColumnSortList().push(columns.get(META_DATA_DESC));
		grid.getColumnSortList().push(columns.get(META_DATA_VALUE));
	}

	@Override
	protected boolean resolveBooleanFieldValue(String fieldName, EzeeLeaseMetaData entity) {
		return false;
	}

	@Override
	protected void setBooleanFieldValue(String fieldName, boolean fieldValue, EzeeLeaseMetaData entity) {
	}

	public void setMetaData(final List<EzeeLeaseMetaData> md) {
		getHandler().getList().clear();
		for (EzeeLeaseMetaData metaData : md) {
			getHandler().getList().add(metaData);
		}
	}

	public void addMetaData(final EzeeLeaseMetaData md) {
		getHandler().getList().add(ZERO, md);
	}
}