package com.ezee.web.common.ui.grid.payer;

import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.model.entity.EzeePayer;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.payer.EzeeCreateUpdateDeletePayer;
import com.ezee.web.common.ui.grid.EzeeFinancialEntityGrid;

public class EzeePayerGrid extends EzeeFinancialEntityGrid<EzeePayer> {

	public EzeePayerGrid(final EzeeEntityCache cache, final String[] crudHeaders) {
		super(cache, crudHeaders);
	}

	protected void initGrid() {
		super.initGrid();
		model = new EzeePayerGridModel();
		model.bind(grid);
	}

	@Override
	protected void initFilter() {
		super.initFilter();
		toolBar = new EzeePayerToolbar(this);
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
			new EzeeCreateUpdateDeletePayer(cache, this, entity, delete, crudHeaders).show();
		}
	}

	@Override
	public void newEntity() {
		new EzeeCreateUpdateDeletePayer(cache, this, crudHeaders).show();
	}

	@Override
	public void editEntity() {
		EzeePayer entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayer(cache, this, entity, update, crudHeaders).show();
		}
	}
}