package com.ezee.web.common.ui.crud.leasetenant;

import com.ezee.model.entity.lease.EzeeLeaseTenant;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.crud.payer.EzeeCreateUpdateDeletePayer;

public class EzeeCreateUpdateDeleteLeaseTenant extends EzeeCreateUpdateDeletePayer<EzeeLeaseTenant> {

	public EzeeCreateUpdateDeleteLeaseTenant(EzeeEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<EzeeLeaseTenant> handler, EzeeLeaseTenant entity,
			EzeeCreateUpdateDeleteEntityType type, String[] headers) {
		super(cache, handler, entity, type, headers);
	}

	public EzeeCreateUpdateDeleteLeaseTenant(EzeeEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<EzeeLeaseTenant> handler, String[] headers) {
		super(cache, handler, headers);
	}

	@Override
	protected EzeeLeaseTenant createEntity() {
		return new EzeeLeaseTenant();
	}
}