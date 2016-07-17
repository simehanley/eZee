package com.ezee.client.grid.leasenote;

import static com.ezee.client.EzeeLeaseWebConstants.LEASE_NOTE_CRUD_HEADERS;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.client.crud.leasenote.EzeeCreateUpdateDeleteLeaseNote;
import com.ezee.client.grid.lease.EzeeLeaseSubComponentChangeListener;
import com.ezee.client.grid.lease.EzeeLeaseSubComponentGrid;
import com.ezee.model.entity.lease.EzeeLeaseNote;
import com.ezee.web.common.cache.EzeeEntityCache;

public class EzeeLeaseNoteGrid extends EzeeLeaseSubComponentGrid<EzeeLeaseNote> {

	public EzeeLeaseNoteGrid(EzeeEntityCache cache, int pageSize, boolean disableContextMenu,
			EzeeLeaseSubComponentChangeListener listener) {
		super(cache, pageSize, disableContextMenu, listener);
	}

	@Override
	protected void initGrid() {
		super.initGrid();
		model = new EzeeLeaseNoteGridModel();
		model.bind(grid);
	}

	@Override
	public void deleteEntity() {
		EzeeLeaseNote entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteLeaseNote(cache, this, entity, delete, LEASE_NOTE_CRUD_HEADERS).show();
		}
	}

	@Override
	public void newEntity() {
		new EzeeCreateUpdateDeleteLeaseNote(cache, this, LEASE_NOTE_CRUD_HEADERS).show();
	}

	@Override
	public void editEntity() {
		EzeeLeaseNote entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteLeaseNote(cache, this, entity, update, LEASE_NOTE_CRUD_HEADERS).show();
		}
	}

	@Override
	public String getGridClass() {
		return EzeeLeaseNote.class.getName();
	}
}