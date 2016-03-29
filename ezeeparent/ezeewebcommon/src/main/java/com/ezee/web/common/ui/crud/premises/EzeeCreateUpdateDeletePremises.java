package com.ezee.web.common.ui.crud.premises;

import com.ezee.model.entity.EzeePremises;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.crud.payer.EzeeCreateUpdateDeletePayer;

public class EzeeCreateUpdateDeletePremises extends EzeeCreateUpdateDeletePayer<EzeePremises> {

	public EzeeCreateUpdateDeletePremises(EzeeEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<EzeePremises> handler, EzeePremises entity,
			EzeeCreateUpdateDeleteEntityType type, String[] headers) {
		super(cache, handler, entity, type, headers);
	}

	public EzeeCreateUpdateDeletePremises(EzeeEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<EzeePremises> handler, String[] headers) {
		super(cache, handler, headers);
	}

	@Override
	protected EzeePremises createEntity() {
		return new EzeePremises();
	}
}
