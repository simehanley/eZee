package com.ezee.client.grid.leasefile;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;
import static com.ezee.web.common.ui.dialog.EzeeMessageDialog.showNew;

import com.ezee.client.crud.lease.EzeeUploadLeaseFileForm;
import com.ezee.client.grid.lease.EzeeLeaseSubComponentChangeListener;
import com.ezee.client.grid.lease.EzeeLeaseSubComponentGrid;
import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.model.entity.lease.EzeeLeaseFile;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;

public class EzeeLeaseFileGrid extends EzeeLeaseSubComponentGrid<EzeeLeaseFile> {

	private final EzeeLease lease;

	public EzeeLeaseFileGrid(EzeeLease lease, EzeeEntityCache cache, int pageSize, boolean disableContextMenu,
			EzeeLeaseSubComponentChangeListener listener) {
		super(cache, pageSize, disableContextMenu, listener);
		this.lease = lease;
		((EzeeLeaseFileGridModel) model).setLease(this.lease);
	}

	@Override
	protected void initGrid() {
		super.initGrid();
		model = new EzeeLeaseFileGridModel();
		model.bind(grid);
	}

	@Override
	public void deleteEntity() {
		EzeeLeaseFile entity = getSelected();
		if (entity != null) {
			onDelete(entity);
		}
	}

	@Override
	public void newEntity() {
		if (lease != null && lease.getId() != NULL_ID) {
			uploadFile();
		} else {
			showNew("Lease Not Saved",
					"Lease has not yet been saved to the database.  Please do so prior to uploading a file.");
		}
	}

	@Override
	public void editEntity() {
		/* not implemented */
	}

	@Override
	public String getGridClass() {
		return EzeeLeaseFile.class.getName();
	}

	protected MenuBar createContextMenu() {
		MenuBar menu = new MenuBar(true);
		menu.setAnimationEnabled(true);
		menu.addItem("New", new Command() {
			@Override
			public void execute() {
				newEntity();
				contextMenu.hide();
			}
		});
		menu.addItem("Delete", new Command() {
			@Override
			public void execute() {
				deleteEntity();
				contextMenu.hide();
			}
		});
		return menu;
	}

	private void uploadFile() {
		EzeeUploadLeaseFileForm uploadFile = new EzeeUploadLeaseFileForm(lease, this);
		uploadFile.center();
	}
}