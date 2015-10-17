package com.ezee.client.grid.payee;

import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.crud.payee.EzeeCreateUpdateDeletePayee;
import com.ezee.client.grid.EzeeFinancialEntityGrid;
import com.ezee.model.entity.EzeePayee;

/**
 * 
 * @author siborg
 *
 */
public class EzeePayeeGrid extends EzeeFinancialEntityGrid<EzeePayee> {

	public EzeePayeeGrid(final EzeeInvoiceServiceAsync service) {
		super(service);
	}

	protected void initGrid() {
		super.initGrid();
		model = new EzeePayeeGridModel();
		model.bind(grid);
	}

	@Override
	public String getGridClass() {
		return EzeePayee.class.getName();
	}

	@Override
	protected void deleteEntity() {
		EzeePayee entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayee(getSelected(), service, delete).center();
			// model.getHandler().getList().remove(entity);
			/*
			 * change to move to a model where we receive the deleted entity via
			 * a message bus update
			 */
		}
	}

	@Override
	protected void newEntity() {
		new EzeeCreateUpdateDeletePayee(service).center();
		/*
		 * change to move to a model where we receive the new entity via a
		 * message bus update
		 */
	}

	@Override
	protected void editEntity() {
		EzeePayee entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayee(getSelected(), service, update).center();
			/*
			 * change to move to a model where we receive the edited entity via
			 * a message bus update
			 */
		}
	}
}