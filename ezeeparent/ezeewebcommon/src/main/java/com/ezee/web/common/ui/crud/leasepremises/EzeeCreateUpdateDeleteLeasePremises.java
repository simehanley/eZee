package com.ezee.web.common.ui.crud.leasepremises;

import com.ezee.model.entity.lease.EzeeLeasePremises;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.crud.payee.EzeeCreateUpdateDeletePayee;

public class EzeeCreateUpdateDeleteLeasePremises extends EzeeCreateUpdateDeletePayee<EzeeLeasePremises> {

	public EzeeCreateUpdateDeleteLeasePremises(EzeeEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<EzeeLeasePremises> handler, EzeeLeasePremises entity,
			EzeeCreateUpdateDeleteEntityType type, String[] headers) {
		super(cache, handler, entity, type, headers);
	}

	public EzeeCreateUpdateDeleteLeasePremises(EzeeEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<EzeeLeasePremises> handler, String[] headers) {
		super(cache, handler, headers);
	}

	@Override
	protected EzeeLeasePremises createEntity() {
		return new EzeeLeasePremises();
	}
}