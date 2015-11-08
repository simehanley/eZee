package com.ezee.client.grid.payer;

import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.crud.payer.EzeeCreateUpdateDeletePayer;
import com.ezee.client.grid.EzeeFinancialEntityGrid;
import com.ezee.client.grid.EzeeFinancialEntityToolbar;
import com.ezee.model.entity.EzeePayer;

public class EzeePayerGrid extends EzeeFinancialEntityGrid<EzeePayer> {

	public EzeePayerGrid(final EzeeInvoiceEntityCache cache) {
		super(cache);
	}

	protected void initGrid() {
		super.initGrid();
		model = new EzeePayerGridModel();
		model.bind(grid);
	}

	@Override
	protected void initFilter() {
		super.initFilter();
		toolBar = new EzeeFinancialEntityToolbar<EzeePayer>(this);
		filterpanel.add(toolBar);
	}

	@Override
	public String getGridClass() {
		return EzeePayer.class.getName();
	}

	@Override
	public void deleteEntity() {
		EzeePayer entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayer(cache, this, entity, delete).center();
		}
	}

	@Override
	public void newEntity() {
		new EzeeCreateUpdateDeletePayer(cache, this).center();
	}

	@Override
	public void editEntity() {
		EzeePayer entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayer(cache, this, entity, update).center();
		}
	}
}