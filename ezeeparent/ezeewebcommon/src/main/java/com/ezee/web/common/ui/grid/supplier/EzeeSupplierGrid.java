package com.ezee.web.common.ui.grid.supplier;

import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.model.entity.EzeeSupplier;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.supplier.EzeeCreateUpdateDeleteSupplier;
import com.ezee.web.common.ui.grid.payee.EzeePayeeGrid;

public class EzeeSupplierGrid extends EzeePayeeGrid<EzeeSupplier> {

	public EzeeSupplierGrid(final EzeeEntityCache cache, final String[] crudHeaders) {
		super(cache, crudHeaders);
	}

	@Override
	protected void createToolbar() {
		toolBar = new EzeeSupplierToolbar(this);
		filterpanel.add(toolBar);

	}

	@Override
	public String getGridClass() {
		return EzeeSupplier.class.getName();
	}

	@Override
	public void deleteEntity() {
		EzeeSupplier supplier = getSelected();
		if (supplier != null) {
			new EzeeCreateUpdateDeleteSupplier(cache, this, supplier, delete, crudHeaders).show();
		}
	}

	@Override
	public void newEntity() {
		new EzeeCreateUpdateDeleteSupplier(cache, this, crudHeaders).show();
	}

	@Override
	public void editEntity() {
		EzeeSupplier supplier = getSelected();
		if (supplier != null) {
			new EzeeCreateUpdateDeleteSupplier(cache, this, supplier, update, crudHeaders).show();
		}
	}

}
