package com.ezee.client.grid.leasemetadata;

import static com.ezee.client.EzeeLeaseWebConstants.LEASE_META_DATA_CRUD_HEADERS;
import static com.ezee.common.EzeeCommonConstants.MINUS_ONE;
import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import com.ezee.client.crud.leasemetadata.EzeeCreateUpdateDeleteLeaseMetaData;
import com.ezee.model.entity.lease.EzeeLeaseMetaData;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.grid.EzeeGrid;

public class EzeeLeaseMetaDataGrid extends EzeeGrid<EzeeLeaseMetaData> {

	private int maxSortOrder;

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

	public void setMetaData(final SortedSet<EzeeLeaseMetaData> entities) {
		List<EzeeLeaseMetaData> metadata = new ArrayList<>(entities);
		maxSortOrder = isEmpty(entities) ? MINUS_ONE : entities.last().getOrder();
		setEntities(metadata);
	}

	public final List<EzeeLeaseMetaData> getMetaData() {
		return model.getHandler().getList();
	}

	@Override
	public void onSave(final EzeeLeaseMetaData entity) {
		if (entity.getId()==null) {
			++maxSortOrder;
			entity.setOrder(maxSortOrder);
		}
		super.onSave(entity);
		listener.metaDataSaved(entity);
	}

	@Override
	public void onDelete(final EzeeLeaseMetaData entity) {
		super.onDelete(entity);
		listener.metaDataDeleted(entity);
	}
}