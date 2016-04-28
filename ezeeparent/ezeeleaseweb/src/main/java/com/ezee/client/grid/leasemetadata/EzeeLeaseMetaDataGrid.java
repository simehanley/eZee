package com.ezee.client.grid.leasemetadata;

import static com.ezee.client.EzeeLeaseWebConstants.LEASE_META_DATA_CRUD_HEADERS;
import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.ezee.client.crud.leasemetadata.EzeeCreateUpdateDeleteLeaseMetaData;
import com.ezee.model.entity.lease.EzeeLeaseMetaData;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.grid.EzeeGrid;

public class EzeeLeaseMetaDataGrid extends EzeeGrid<EzeeLeaseMetaData> {

	private final EzeeLeaseMetaDataChangeListener listener;

	public EzeeLeaseMetaDataGrid(final EzeeEntityCache cache, final int pageSize, final boolean disableContextMenu,
			final EzeeLeaseMetaDataChangeListener listener) {
		super(cache, pageSize, disableContextMenu);
		initMetaDataGrid();
		this.listener = listener;
	}

	private void initMetaDataGrid() {
		getMain().setWidgetSize(filterpanel, ZERO_DBL);
	}

	@Override
	protected void init(final boolean disableContextMenu) {
		initFilter();
		initGrid();
		initContextMenu(disableContextMenu);
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

	public void setMetaData(final Set<EzeeLeaseMetaData> entities) {
		List<EzeeLeaseMetaData> metadata = new ArrayList<>(entities);
		Collections.sort(metadata);
		setEntities(metadata);
	}

	public final List<EzeeLeaseMetaData> getMetaData() {
		return model.getHandler().getList();
	}

	@Override
	public void onSave(final EzeeLeaseMetaData entity) {
		super.onSave(entity);
		listener.metaDataSaved(entity);
	}

	@Override
	public void onDelete(final EzeeLeaseMetaData entity) {
		super.onDelete(entity);
		listener.metaDataDeleted(entity);
	}
}