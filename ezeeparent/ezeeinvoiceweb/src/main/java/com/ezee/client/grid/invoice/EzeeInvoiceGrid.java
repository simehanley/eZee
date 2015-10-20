package com.ezee.client.grid.invoice;

import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.crud.invoice.EzeeCreateUpdateDeleteInvoice;
import com.ezee.client.grid.EzeeGrid;
import com.ezee.model.entity.EzeeInvoice;
import com.google.gwt.view.client.MultiSelectionModel;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceGrid extends EzeeGrid<EzeeInvoice> {

	public EzeeInvoiceGrid(final EzeeInvoiceServiceAsync service, final EzeeInvoiceEntityCache cache) {
		super(service, cache);
		this.cache = cache;
	}

	protected void initGrid() {
		super.initGrid();
		MultiSelectionModel<EzeeInvoice> selectModel = new MultiSelectionModel<>();
		grid.setSelectionModel(selectModel);
		model = new EzeeInvoiceGridModel();
		model.bind(grid);
	}

	@Override
	public String getGridClass() {
		return EzeeInvoice.class.getName();
	}

	@Override
	protected void deleteEntity() {
		EzeeInvoice entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteInvoice(service, cache, this, entity, delete).center();
		}
	}

	@Override
	protected void newEntity() {
		new EzeeCreateUpdateDeleteInvoice(service, cache, this).center();
	}

	@Override
	protected void editEntity() {
		EzeeInvoice entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteInvoice(service, cache, this, entity, update).center();
		}
	}
}