package com.ezee.web.common.ui.crud.resource;

import com.ezee.model.entity.EzeeResource;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.crud.payee.EzeeCreateUpdateDeletePayee;

public class EzeeCreateUpdateDeleteResource extends EzeeCreateUpdateDeletePayee<EzeeResource> {

	public EzeeCreateUpdateDeleteResource(EzeeEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<EzeeResource> handler, EzeeResource entity,
			EzeeCreateUpdateDeleteEntityType type, String[] headers) {
		super(cache, handler, entity, type, headers);
	}

	public EzeeCreateUpdateDeleteResource(EzeeEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<EzeeResource> handler, String[] headers) {
		super(cache, handler, headers);
	}

	@Override
	protected EzeeResource createEntity() {
		return new EzeeResource();
	}
}
