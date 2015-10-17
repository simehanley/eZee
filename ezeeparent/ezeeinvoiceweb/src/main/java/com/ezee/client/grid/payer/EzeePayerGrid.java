package com.ezee.client.grid.payer;

import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.crud.payer.EzeeCreateUpdateDeletePayer;
import com.ezee.client.grid.EzeeFinancialEntityGrid;
import com.ezee.model.entity.EzeePayer;

public class EzeePayerGrid extends EzeeFinancialEntityGrid<EzeePayer> {

	public EzeePayerGrid(final EzeeInvoiceServiceAsync service) {
		super(service);
	}

	protected void initGrid() {
		super.initGrid();
		model = new EzeePayerGridModel();
		model.bind(grid);
	}

	@Override
	public String getGridClass() {
		return EzeePayer.class.getName();
	}

	@Override
	protected void deleteEntity() {
		EzeePayer entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayer(getSelected(), service, delete).center();
			/*
			 * change to move to a model where we receive the deleted entity via
			 * a message bus update
			 */
			//model.getHandler().getList().remove(entity);
		}
	}

	@Override
	protected void newEntity() {
		new EzeeCreateUpdateDeletePayer(service).center();
		/*
		 * change to move to a model where we receive the new entity via a
		 * message bus update
		 */
	}

	@Override
	protected void editEntity() {
		EzeePayer entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayer(getSelected(), service, update).center();
			/*
			 * change to move to a model where we receive the edited entity via
			 * a message bus update
			 */
		}
	}
}