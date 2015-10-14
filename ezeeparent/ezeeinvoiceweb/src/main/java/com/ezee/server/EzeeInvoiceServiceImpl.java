package com.ezee.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezee.client.EzeeInvoiceService;
import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.server.cache.EzeeEntitiesCache;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceServiceImpl extends AbstractRemoteService implements EzeeInvoiceService {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(EzeeInvoiceServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public <T extends EzeeDatabaseEntity> List<T> getEntities(final String clazz) {
		try {
			return getSpringBean(EzeeEntitiesCache.class).get((Class<T>) Class.forName(clazz));
		} catch (ClassNotFoundException e) {
			log.error("Unable to resolve class '" + clazz + "'.", e);
		}
		return null;
	}
}