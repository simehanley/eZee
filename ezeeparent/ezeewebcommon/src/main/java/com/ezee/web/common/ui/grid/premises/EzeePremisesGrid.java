package com.ezee.web.common.ui.grid.premises;

import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;

import com.ezee.model.entity.EzeePremises;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.premises.EzeeCreateUpdateDeletePremises;
import com.ezee.web.common.ui.grid.payer.EzeePayerGrid;

public class EzeePremisesGrid extends EzeePayerGrid<EzeePremises> {

	public EzeePremisesGrid(final EzeeEntityCache cache, final String[] crudHeaders) {
		super(cache, crudHeaders);
	}

	@Override
	protected void createToolbar() {
		toolBar = new EzeePremisesToolbar(this);
		filterpanel.add(toolBar);
	}

	@Override
	public void deleteEntity() {
		EzeePremises entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePremises(cache, this, entity, delete, crudHeaders).show();
		}
	}

	@Override
	public void newEntity() {
		new EzeeCreateUpdateDeletePremises(cache, this, crudHeaders).show();
	}

	@Override
	public void editEntity() {
		EzeePremises entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePremises(cache, this, entity, update, crudHeaders).show();
		}
	}

	@Override
	public String getGridClass() {
		return EzeePremises.class.getName();
	}
}