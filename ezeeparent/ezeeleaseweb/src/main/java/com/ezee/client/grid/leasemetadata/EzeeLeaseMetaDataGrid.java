package com.ezee.client.grid.leasemetadata;

import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.ezee.model.entity.lease.EzeeLeaseMetaData;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.grid.EzeeGrid;

public class EzeeLeaseMetaDataGrid extends EzeeGrid<EzeeLeaseMetaData> {

	public EzeeLeaseMetaDataGrid(final EzeeEntityCache cache, final int pageSize, final boolean disableContextMenu) {
		super(cache, pageSize, disableContextMenu);
		initMetaDataGrid();
	}

	private void initMetaDataGrid() {
		getMain().setWidgetSize(filterpanel, ZERO_DBL);
	}

	@Override
	protected void init(final int pageSize, final boolean disableContextMenu) {
		initFilter();
		initGrid(pageSize);
		initContextMenu(disableContextMenu);
	}

	@Override
	protected void initGrid(final int pageSize) {
		super.initGrid(pageSize);
		model = new EzeeLeaseMetaDataGridModel();
		model.bind(grid);
	}

	@Override
	public void deleteEntity() {
	}

	@Override
	public void newEntity() {
	}

	@Override
	public void editEntity() {
	}

	@Override
	public String getGridClass() {
		return EzeeLeaseMetaData.class.getName();
	}

	public void setMetaData(final Set<EzeeLeaseMetaData> entities) {
		List<EzeeLeaseMetaData> metadata = new ArrayList<>(entities);
		Collections.sort(metadata);
		setEntities(metadata);
	}

	public final List<EzeeLeaseMetaData> getMetaData() {
		return model.getHandler().getList();
	}
}