package com.ezee.client.grid;

import java.util.List;
import java.util.Map;

import com.ezee.model.entity.EzeeDatabaseEntity;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.view.client.ListDataProvider;

/**
 * 
 * @author siborg
 *
 */
public abstract class EzeeGridModel<T extends EzeeDatabaseEntity> {

	protected ListHandler<T> handler;

	public final ListHandler<T> getHandler() {
		return handler;
	}

	public void bind(final DataGrid<T> grid) {
		Map<String, Column<T, ?>> columns = createColumns(grid);
		createDataHandler(grid);
		addSortHandling(grid, columns);
	}

	protected void createDataHandler(final DataGrid<T> grid) {
		ListDataProvider<T> provider = new ListDataProvider<>();
		provider.addDataDisplay(grid);
		List<T> list = provider.getList();
		handler = new ListHandler<T>(list);
	}

	protected abstract Map<String, Column<T, ?>> createColumns(final DataGrid<T> grid);

	protected abstract void addSortHandling(final DataGrid<T> grid, final Map<String, Column<T, ?>> columns);
}