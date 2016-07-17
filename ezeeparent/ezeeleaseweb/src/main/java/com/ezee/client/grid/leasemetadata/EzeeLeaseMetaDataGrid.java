package com.ezee.client.grid.leasemetadata;

import static com.ezee.client.EzeeLeaseWebConstants.LEASE_META_DATA_CRUD_HEADERS;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.client.crud.leasemetadata.EzeeCreateUpdateDeleteLeaseMetaData;
import com.ezee.client.grid.lease.EzeeLeaseSubComponentChangeListener;
import com.ezee.client.grid.lease.EzeeLeaseSubComponentGrid;
import com.ezee.model.entity.lease.EzeeLeaseMetaData;
import com.ezee.web.common.cache.EzeeEntityCache;

public class EzeeLeaseMetaDataGrid extends EzeeLeaseSubComponentGrid<EzeeLeaseMetaData> {

	public EzeeLeaseMetaDataGrid(EzeeEntityCache cache, int pageSize, boolean disableContextMenu,
			EzeeLeaseSubComponentChangeListener listener) {
		super(cache, pageSize, disableContextMenu, listener);
	}

	@Override
	protected void initGrid() {
		super.initGrid();
		model = new EzeeLeaseMetaDataGridModel();
		model.bind(grid);
	}

	@Override
	public void deleteEntity() {
		EzeeLeaseMetaData entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteLeaseMetaData(cache, this, entity, delete, LEASE_META_DATA_CRUD_HEADERS).show();
		}
	}

	@Override
	public void newEntity() {
		new EzeeCreateUpdateDeleteLeaseMetaData(cache, this, LEASE_META_DATA_CRUD_HEADERS).show();
	}

	@Override
	public void editEntity() {
		EzeeLeaseMetaData entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteLeaseMetaData(cache, this, entity, update, LEASE_META_DATA_CRUD_HEADERS).show();
		}
	}

	@Override
	public String getGridClass() {
		return EzeeLeaseMetaData.class.getName();
	}
}