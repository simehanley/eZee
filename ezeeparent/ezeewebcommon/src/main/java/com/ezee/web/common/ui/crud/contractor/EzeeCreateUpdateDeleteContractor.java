package com.ezee.web.common.ui.crud.contractor;

import com.ezee.model.entity.EzeeContractor;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType;
import com.ezee.web.common.ui.crud.payee.EzeeCreateUpdateDeletePayee;

public class EzeeCreateUpdateDeleteContractor extends EzeeCreateUpdateDeletePayee<EzeeContractor> {

	public EzeeCreateUpdateDeleteContractor(EzeeEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<EzeeContractor> handler, EzeeContractor entity,
			EzeeCreateUpdateDeleteEntityType type, String[] headers) {
		super(cache, handler, entity, type, headers);
	}

	public EzeeCreateUpdateDeleteContractor(EzeeEntityCache cache,
			EzeeCreateUpdateDeleteEntityHandler<EzeeContractor> handler, String[] headers) {
		super(cache, handler, headers);
	}

	@Override
	protected EzeeContractor createEntity() {
		return new EzeeContractor();
	}
}