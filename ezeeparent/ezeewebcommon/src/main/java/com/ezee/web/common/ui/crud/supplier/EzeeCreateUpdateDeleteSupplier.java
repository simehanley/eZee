package com.ezee.web.common.ui.crud.supplier;

import com.ezee.model.entity.EzeeSupplier;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.crud.payee.EzeeCreateUpdateDeletePayee;

public class EzeeCreateUpdateDeleteSupplier extends EzeeCreateUpdateDeletePayee<EzeeSupplier> {

	public EzeeCreateUpdateDeleteSupplier(EzeeEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<EzeeSupplier> handler, EzeeSupplier entity,
			EzeeCreateUpdateDeleteEntityType type, String[] headers) {
		super(cache, handler, entity, type, headers);
	}

	public EzeeCreateUpdateDeleteSupplier(EzeeEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<EzeeSupplier> handler, String[] headers) {
		super(cache, handler, headers);
	}

	@Override
	protected EzeeSupplier createEntity() {
		return new EzeeSupplier();
	}
}