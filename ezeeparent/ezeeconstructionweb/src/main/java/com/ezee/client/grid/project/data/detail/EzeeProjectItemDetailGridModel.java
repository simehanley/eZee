package com.ezee.client.grid.project.data.detail;

import java.util.Date;
import java.util.Map;

import com.ezee.model.entity.project.EzeeProjectItemDetail;
import com.ezee.web.common.ui.grid.EzeeGridModel;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;

public class EzeeProjectItemDetailGridModel extends EzeeGridModel<EzeeProjectItemDetail> {

	@Override
	protected Map<String, Column<EzeeProjectItemDetail, ?>> createColumns(final DataGrid<EzeeProjectItemDetail> grid) {
		return null;
	}

	@Override
	protected void setTextFieldValue(final String fieldName, final String fieldValue,
			final EzeeProjectItemDetail entity) {
		/* implement me */
	}

	@Override
	protected String resolveTextFieldValue(String fieldName, EzeeProjectItemDetail entity) {
		return null;
	}

	@Override
	protected Date resolveDateFieldValue(String fieldName, EzeeProjectItemDetail entity) {
		return null;
	}

	@Override
	protected boolean resolveBooleanFieldValue(String fieldName, EzeeProjectItemDetail entity) {
		/* do nothing */
		return false;
	}

	@Override
	protected void setBooleanFieldValue(String fieldName, boolean fieldValue, EzeeProjectItemDetail entity) {
		/* do nothing */
	}

	@Override
	protected void addComparators(Map<String, Column<EzeeProjectItemDetail, ?>> columns) {

	}

	@Override
	protected void addSortColumns(DataGrid<EzeeProjectItemDetail> grid,
			Map<String, Column<EzeeProjectItemDetail, ?>> columns) {

	}
}