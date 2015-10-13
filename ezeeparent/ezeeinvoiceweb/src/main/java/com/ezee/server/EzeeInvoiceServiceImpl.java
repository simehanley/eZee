package com.ezee.server;

import com.ezee.client.EzeeInvoiceService;
import com.ezee.model.entity.EzeePayer;
import com.ezee.server.cache.EzeeEntitiesCache;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceServiceImpl extends AbstractRemoteService implements EzeeInvoiceService {

	private static final long serialVersionUID = 1L;

	@Override
	public EzeePayer getPayer(final long id) {
		EzeeEntitiesCache cache = getSpringBean(EzeeEntitiesCache.class);
		return cache.get(id, EzeePayer.class);
	}
}